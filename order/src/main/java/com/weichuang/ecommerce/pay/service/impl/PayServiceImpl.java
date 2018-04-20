package com.weichuang.ecommerce.pay.service.impl;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.constants.OrderStatusEnum;
import com.weichuang.ecommerce.order.constants.PaymentTypeEnum;
import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.feign.IUser;
import com.weichuang.ecommerce.order.responsitory.IOrderDao;
import com.weichuang.ecommerce.pay.constants.VaribleConstant;
import com.weichuang.ecommerce.pay.dao.IAgentDao;
import com.weichuang.ecommerce.pay.entity.AgentEntity;
import com.weichuang.ecommerce.pay.resource.PayResources;
import com.weichuang.ecommerce.pay.service.IPayService;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import com.weichuang.ecommerce.withdraw.entity.AgentIncomeEntity;
import com.weichuang.ecommerce.withdraw.entity.IncomeSetEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesIncomeEntity;
import com.weichuang.ecommerce.withdraw.repository.IAgentIncomeDao;
import com.weichuang.ecommerce.withdraw.repository.IIncomeSetDao;
import com.weichuang.ecommerce.withdraw.repository.ISalesIncomeDao;
import com.weichuang.ecommerce.order.entity.response.UserDetailResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * ClassName:PayServiceImpl
 * </p>
 * <p>
 * Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com
 * </p>
 * <p>
 * Description:支付回调service
 * </p>
 * <p>
 * author:zhanghongsheng
 * </p>
 * <p>
 * 2017/10/10 9:20
 * </p>
 **/
@Service
public class PayServiceImpl implements IPayService {
    private static final Logger log = LoggerFactory
            .getLogger(PayServiceImpl.class);
    @Autowired
    private IOrderDao orderDao;
    @Autowired
    private ISalesIncomeDao salesIncomeDao;
    @Autowired
    private IAgentIncomeDao agentIncomeDao;
    @Autowired
    private IAgentDao agentDao;
    @Autowired
    private IUser user;
    @Autowired
    private IIncomeSetDao incomeSetDao;


    /**
     * @param map 微信回调数据
     * @return 回调后处理结果
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean isPayed(SortedMap map) throws ServiceException, Exception {

        boolean status = false;
        // 微信返回的用户订单号
        String orderNo = map.get("out_trade_no") == null ? "" : map.get(
                "out_trade_no").toString();
        // 没有订单号
        if (StringUtils.isEmpty(orderNo)) {
            throw new Exception("微信回调获取不到orderNo订单号");
        }
        // 根据订单号获取订单
        OrderEntity order = orderDao.getOrderByNo(orderNo);
        if (order == null) {
            throw new Exception("微信回调，查询不到orderNo=" + orderNo + "的订单信息");
        }

        // 订单状态不是未支付
        if (order.getStatus() != 1) {

            throw new Exception("微信回调，只有待付款的才可以处理回调");
        }

        try {
            // 更新订单状态
            updatePaymentStatus(orderNo, map);
            
            //获取所属销售详情
            Integer salesId = order.getSalesId();
            // 获取用户详情
            UserDetailResponse salesDetail = user.getUserDetailByUserId(salesId)
                    .getValue();
            int loginStatus = 0;
            if(salesDetail != null){
                loginStatus = salesDetail.getLoginStatus();
            }
            // 获取用户详细信息
            UserDetailResponse userDetail = getUserDetail(order);

            float salesPercent = 0;
            float agentPercent = 0;

            float  salesPer=0 ;
            float agentPer=0;

            float salesPercent_c2c=0;
            float agentPercent_c2c=0;
            List<IncomeSetEntity> list = incomeSetDao.getAllIncomeSetList();
            for (int i = 0; i <list.size() ; i++) {
                if(list.get(i).getType()==1){

                    salesPer =list.get(i).getSalesPercent();
                    agentPer=list.get(i).getAgentPercent();
                }else if(list.get(i).getType()==2){
                    salesPercent_c2c=list.get(i).getSalesPercent();
                    agentPercent_c2c= list.get(i).getAgentPercent();
                }
            }

            if (userDetail.getSalesId() == 0 || loginStatus != 1) {
                log.info("salesLoginStratus=" + loginStatus);
                // 不加记录
                return true;
            } else if (userDetail.getRoleId()==5 || userDetail.getReferrerId() == 0 ) {
                // B端销售提成15%
                //salesPercent = VaribleConstant.SALE_PER;
                salesPercent = salesPer;
                // 代理商提成10%
                //agentPercent = VaribleConstant.AGENT_PER;
                agentPercent=agentPer;
            } else if (userDetail.getReferrerId() != 0) {
                // B端销售提成10%
                //salesPercent = VaribleConstant.SALE_PER_c2c;
                salesPercent=salesPercent_c2c;
                // 代理商提成5%
                //agentPercent = VaribleConstant.AGENT_PER_c2c;
                agentPercent=agentPercent_c2c;
            }

            // 保存提成信息
            saveIncome(salesPercent, agentPercent, order, userDetail);

            status = true;

        } catch (Exception ex) {
            throw ex;
        }
        return status;
    }

    /**
     * 保存提成信息
     */
    private void saveIncome(float salesPercent, float agentPercent,
                            OrderEntity order, UserDetailResponse userDetail) {
        SalesIncomeEntity salesIncomeEntity = new SalesIncomeEntity();
        //BeanUtils.copyProperties(order, salesIncomeEntity);
        BigDecimal receiveMoney = order.getReceiveMoney().setScale(2,
                BigDecimal.ROUND_HALF_UP);

        salesIncomeEntity.setOrderId(order.getId());
        salesIncomeEntity.setOrderNo(order.getOrderNo());
        salesIncomeEntity.setUserId(order.getUserId());
        salesIncomeEntity.setUserName(order.getUserName());
        salesIncomeEntity.setSalesId(order.getSalesId());
        salesIncomeEntity.setSalesName(order.getSalesName());
        salesIncomeEntity.setOriginalPrice(order.getOriginalPrice());
        salesIncomeEntity.setDiscountMoney(order.getDiscountMoney());
        salesIncomeEntity.setTaxation(order.getTaxation());
        salesIncomeEntity.setFreight(order.getFreight());
        salesIncomeEntity.setReceiveMoney(receiveMoney);
        
        
        BigDecimal per = new BigDecimal(salesPercent).setScale(2,
                BigDecimal.ROUND_HALF_UP);
        // B端销售
        salesIncomeEntity.setPercent(salesPercent);
        // 计算提成 订单总金额*提成
        BigDecimal income = receiveMoney.multiply(per);
        salesIncomeEntity.setIncome(income);
        salesIncomeEntity.setWithdraw(0);
        salesIncomeEntity.setCreatetime(new Date());
        // 保存销售人员提成
        salesIncomeDao.addSalesIncome(salesIncomeEntity);

        // 代理商提成（目前健身房）
        AgentIncomeEntity agentIncomeEntity = new AgentIncomeEntity();
        //BeanUtils.copyProperties(order, agentIncomeEntity);
        agentIncomeEntity.setOrderId(order.getId());
        agentIncomeEntity.setOrderNo(order.getOrderNo());
        agentIncomeEntity.setUserId(order.getUserId());
        agentIncomeEntity.setUserName(order.getUserName());
        agentIncomeEntity.setSalesId(order.getSalesId());
        agentIncomeEntity.setSalesName(order.getSalesName());
        agentIncomeEntity.setAgentId(userDetail.getAgentId());
        agentIncomeEntity.setAgentName(userDetail.getAgentName());
        agentIncomeEntity.setOriginalPrice(order.getOriginalPrice());
        agentIncomeEntity.setDiscountMoney(order.getDiscountMoney());
        agentIncomeEntity.setReceiveMoney(order.getReceiveMoney());
        agentIncomeEntity.setTaxation(order.getTaxation());
        agentIncomeEntity.setFreight(order.getFreight());
        
        // 代理商提成
        BigDecimal agent_per = new BigDecimal(agentPercent).setScale(2,
                BigDecimal.ROUND_HALF_UP);
        agentIncomeEntity.setPercent(agentPercent);
        BigDecimal agent_income = receiveMoney.multiply(agent_per).setScale(2,
                BigDecimal.ROUND_HALF_UP);
        agentIncomeEntity.setIncome(agent_income);
        agentIncomeEntity.setWithdraw(0);
        agentIncomeEntity.setCreatetime(new Date());
        // 保存代理商的提成信息
        agentIncomeDao.addAgentIncome(agentIncomeEntity);
    }

    /**
     * 获取用户详情
     *
     * @throws ServiceException
     */
    private UserDetailResponse getUserDetail(OrderEntity order)
            throws ServiceException {
        // 获取用户Id
        Integer userId = order.getUserId();
        // 获取用户详情
        UserDetailResponse userDetail = user.getUserDetailByUserId(userId)
                .getValue();
        return userDetail;
    }

    /**
     * 更新订单信息
     */

    private void updatePaymentStatus(String orderNo, SortedMap map) {
        Map param = new HashMap();
        // 订单状态：
        int status = OrderStatusEnum.SHIPPING.getIndex();
        // 微信支付
        int paymentType = PaymentTypeEnum.WEIXIN.getIndex();
        // 交易流水号取微信支付订单号
        String paymentSequence = map.get("transaction_id") == null ? ""
                : map.get("transaction_id").toString();
        //String paymentTime=map.get("time_end") == null ? "" : map.get("time_end").toString();

        // 更新订单信息
        orderDao.updatePaymentStatusByOrderNo(orderNo, status, paymentType, paymentSequence);
    }
}
