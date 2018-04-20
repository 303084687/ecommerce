package com.weichuang.ecommerce.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.*;
import com.weichuang.ecommerce.coupon.responsitory.ICouponDao;
import com.weichuang.ecommerce.order.constants.OrderSendStatusEnum;
import com.weichuang.ecommerce.order.constants.OrderStatusEnum;
import com.weichuang.ecommerce.order.constants.OrderUtil;
import com.weichuang.ecommerce.order.constants.PaymentTypeEnum;
import com.weichuang.ecommerce.order.entity.*;
import com.weichuang.ecommerce.order.entity.feign.SalesBaseResponse;
import com.weichuang.ecommerce.order.entity.request.OrderDetailRequest;
import com.weichuang.ecommerce.order.entity.request.OrderRecevierRequest;
import com.weichuang.ecommerce.order.entity.request.OrderRequest;
import com.weichuang.ecommerce.order.entity.response.*;
import com.weichuang.ecommerce.order.feign.IAgent;
import com.weichuang.ecommerce.order.responsitory.*;
import com.weichuang.ecommerce.order.service.IAdminOrderService;
import com.weichuang.ecommerce.product.entity.ProductEntity;
import com.weichuang.ecommerce.product.entity.response.ProductResponse;
import com.weichuang.ecommerce.product.responsitory.IProductDao;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.payment.PayUtil;
import com.weichuang.ecommerce.withdraw.repository.IAgentIncomeDao;
import com.weichuang.ecommerce.withdraw.repository.ISalesIncomeDao;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * <p>ClassName: AdminOrderServiceImpl</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单服务</p>
 * <p>author: jiangkesen</p>
 * <p>date 2017年11月3日 下午16:56:25</p>
 */
@Service("adminOrderService")
public class AdminOrderServiceImpl implements IAdminOrderService {

    //订单接口
    @Autowired
    private IAdminOrderDao orderDao;

    //订单详情接口
    @Autowired
    private IOrderDetailDao orderDetailDao;

    //订单收货人接口
    @Autowired
    private IOrderRecevierDao orderRecevierDao;

    //订单发货详情接口
    @Autowired
    private IOrderSentDao orderSentDao;

    //退款接口
    @Autowired
    private IOrderRefundDao orderRefundDao;

    @Autowired
    private IAgent agentService;

    @Autowired
    private WeiXinProperties weiXinProperties;

    //优惠卷接口
    @Autowired
    private ICouponDao couponDao;

    @Autowired
    private ISalesIncomeDao salesIncomeDao;

    @Autowired
    private IAgentIncomeDao agentIncomeDao;

    // 获取订单创建后的返回结果
    private OrderAddResponse getOrderAddResponse(OrderEntity orderEntity) {
        OrderAddResponse result = new OrderAddResponse();
        BeanUtils.copyProperties(orderEntity, result);
        return result;
    }

    /**
     * <p>Description: 根据订单编号查询订单详情</p>
     * <p>param: orderNo 订单编号 </p>
     * <p>author: jiangkesen </p>
     * <p>date: 2017年09月25日 下午18:56:25 </p>
     * <p>return: </p>
     * <p>throws ServiceException </p>
     */
    @Override
    public AdminOrderDetailResponse getOrderByOrderNo(String orderNo) throws ServiceException, Exception {
        if (orderNo == null || orderNo.isEmpty()) {
            throw new IllegalArgumentException("orderNo is empty or null");
        }
        //查询订单主体
        AdminOrderDetailEntity orderEntity = orderDao.getOrderByNo(orderNo);
        if (orderEntity == null) {
            throw new ServiceException(Result.ORDER_NOT_EXIST.getCode(), Result.ORDER_NOT_EXIST.getMessage());
        }
        AdminOrderDetailResponse result = this.generatOrderResponse(orderEntity);
        return result;
    }

    // 构造订单返回值
    private AdminOrderDetailResponse generatOrderResponse(AdminOrderDetailEntity orderEntity) {
        String orderNo = orderEntity.getOrderNo();
        AdminOrderDetailResponse result = this.getOrderResponse(orderEntity);
        //查询订单详情
        List<OrderDetailResponse> orderDetailResponses = this.generatOrderDetailResponse(orderNo);
        result.setOrderDetails(orderDetailResponses);
        //查询订单收货人信息
        OrderRecevierResponse orderRecevierResponse = this.generatOrderRecevierResponse(orderNo);
        result.setOrderRecevier(orderRecevierResponse);
        // 发货信息详情
        List<OrderSentResponse> orderSentResponses = this.generatOrderSentResponse(orderNo);
        result.setOrderSents(orderSentResponses);

        //所属代理信息
        result.setSalesAgentInfo(this.getSalesAgentInfo(orderEntity.getSalesId()));

        return result;
    }

    // 根据订单编号构造订单详情
    private List<OrderDetailResponse> generatOrderDetailResponse(String orderNo) {
        //查询订单详情
        List<OrderDetailResponse> result = new ArrayList<OrderDetailResponse>();
        List<OrderDetailEntity> orderDetailEntities = orderDetailDao.getOrderDetailsByOrderNo(orderNo);
        for (OrderDetailEntity item : orderDetailEntities) {
            result.add(this.getOrderDetailResponse(item));
        }
        return result;
    }

    // 根据订单码构造订单收货人信息
    private OrderRecevierResponse generatOrderRecevierResponse(String orderNo) {
        //查询订单收货人信息
        OrderRecevierEntity orderRecevierEntitiey = orderRecevierDao.getOrderReceviersByOrderNo(orderNo);
        OrderRecevierResponse result = this.getOrderRecevierResponse(orderRecevierEntitiey);
        return result;
    }

    // 根据订单码构造订单详情
    private List<OrderSentResponse> generatOrderSentResponse(String orderNo) {
        //查询订单详情
        List<OrderSentResponse> result = new ArrayList<OrderSentResponse>();
        List<OrderSentEntity> orderDetailEntities = orderSentDao.getOrderSentByOrderNo(orderNo);
        for (OrderSentEntity item : orderDetailEntities) {
            result.add(this.getOrderSentResponse(item));
        }
        return result;
    }


    /**
     * <p>Description: 根据条件查询会员中心用户订单列表</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param sendStartTime 发货时间起始时间 </p>
     * <p>param sendEndTime 发货时间终止时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    @Override
    public AdminOrderListResponse getSentOrderList(String createStartTime,
                                                   String createEndTime,
                                                   String sendStartTime,
                                                   String sendEndTime,
                                                   String orderNo,
                                                   String mobile,
                                                   String receiverName,
                                                   String receiverMobile,
                                                   int pageNum,
                                                   int pageSize) throws ServiceException, Exception {
        if (StringUtils.isNotEmpty(createStartTime) && !DateUtil.validateDateTime(createStartTime)) {
            throw new IllegalArgumentException("createStartTime format error");
        }
        if (StringUtils.isNotEmpty(createEndTime) && !DateUtil.validateDateTime(createEndTime)) {
            throw new IllegalArgumentException("createEndTime format error");
        }
        if (StringUtils.isNotEmpty(sendStartTime) && !DateUtil.validateDateTime(sendStartTime)) {
            throw new IllegalArgumentException("sendStartTime format error");
        }
        if (StringUtils.isNotEmpty(sendEndTime) && !DateUtil.validateDateTime(sendEndTime)) {
            throw new IllegalArgumentException("sendEndTime format error");
        }
        if (StringUtils.isNotEmpty(mobile) && !PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }

        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 2;
        }
        if (StringUtils.isNotEmpty(sendEndTime)) {
            sendEndTime = DateUtil.plusDays(sendEndTime, 1);
        }
        if (StringUtils.isNotEmpty(createEndTime)) {
            createEndTime = DateUtil.plusDays(createEndTime, 1);
        }
        AdminOrderListResponse result = new AdminOrderListResponse();
        List<AdminOrderResponse> userOrderList = new ArrayList<AdminOrderResponse>();
        // 查询用户订单
        PageHelper.startPage(pageNum, pageSize);
        List<AdminOrderEntity> orderList = orderDao.getSentOrderList(
                createStartTime,
                createEndTime,
                sendStartTime,
                sendEndTime,
                StringEscapeUtils.escapeSql(orderNo),
                mobile,
                receiverName,
                receiverMobile);
        result.setOrderList(userOrderList);
        for (AdminOrderEntity item : orderList) {
            // 构造返回订单对象
            AdminOrderResponse orderResponse = this.getUserOrderResponse(item);
            userOrderList.add(orderResponse);

            // 查询订单详情
            List<OrderDetailResponse> orderDetailResponseList = this.generatOrderDetailResponse(item.getOrderNo());
            orderResponse.setOrderDetails(orderDetailResponseList);

            //发货详情
            List<OrderSentResponse> orderSentResponse = this.getOrderSend(item.getOrderNo());
            orderResponse.setOrderSendDetails(orderSentResponse);

            //订单收货人信息
            /*OrderRecevierResponse orderRecevierResponse = this.generatOrderRecevierResponse(item.getOrderNo());
            orderResponse.setOrderRecevier(orderRecevierResponse);*/

            //业务人员所属代理商信息
            orderResponse.setSalesAgentInfo(this.getSalesAgentInfo(item.getSalesId()));
        }

        PageInfo pageInfo = new PageInfo(orderList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * <p>Description: 根据条件查询订单管理订单列表,用户未发货的订单列表的查询（配送中的订单）</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param sendStartTime 发货时间起始时间 </p>
     * <p>param sendEndTime 发货时间终止时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    @Override
    public AdminOrderListResponse getUnsentOrderList(String createStartTime,
                                                     String createEndTime,
                                                     String sendStartTime,
                                                     String sendEndTime,
                                                     String orderNo,
                                                     String mobile,
                                                     String receiverName,
                                                     String receiverMobile,
                                                     int pageNum,
                                                     int pageSize) throws ServiceException, Exception {
        if (StringUtils.isNotEmpty(createStartTime) && !DateUtil.validateDateTime(createStartTime)) {
            throw new IllegalArgumentException("createStartTime format error");
        }
        if (StringUtils.isNotEmpty(createEndTime) && !DateUtil.validateDateTime(createEndTime)) {
            throw new IllegalArgumentException("createEndTime format error");
        }
        if (StringUtils.isNotEmpty(sendStartTime) && !DateUtil.validateDateTime(sendStartTime)) {
            throw new IllegalArgumentException("sendStartTime format error");
        }
        if (StringUtils.isNotEmpty(sendEndTime) && !DateUtil.validateDateTime(sendEndTime)) {
            throw new IllegalArgumentException("sendEndTime format error");
        }
        if (StringUtils.isNotEmpty(mobile) && !PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (StringUtils.isNotEmpty(mobile) && !PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 2;
        }
        if (StringUtils.isNotEmpty(sendEndTime)) {
            sendEndTime = DateUtil.plusDays(sendEndTime, 1);
        }
        if (StringUtils.isNotEmpty(createEndTime)) {
            createEndTime = DateUtil.plusDays(createEndTime, 1);
        }
        AdminOrderListResponse result = new AdminOrderListResponse();
        List<AdminOrderResponse> userOrderList = new ArrayList<AdminOrderResponse>();
        // 查询用户订单
        PageHelper.startPage(pageNum, pageSize);
        List<AdminOrderEntity> orderList = orderDao.getUnsentOrderList(
                createStartTime,
                createEndTime,
                sendStartTime,
                sendEndTime,
                StringEscapeUtils.escapeSql(orderNo),
                mobile,
                receiverName,
                receiverMobile);
        result.setOrderList(userOrderList);
        for (AdminOrderEntity item : orderList) {
            // 构造返回订单对象
            AdminOrderResponse orderResponse = this.getUserOrderResponse(item);
            userOrderList.add(orderResponse);

            // 查询订单详情
            List<OrderDetailResponse> orderDetailResponseList = this.generatOrderDetailResponse(item.getOrderNo());
            orderResponse.setOrderDetails(orderDetailResponseList);

            //查询收货详情
            List<OrderSentResponse> orderSentResponse = this.getOrderSend(item.getOrderNo());
            orderResponse.setOrderSendDetails(orderSentResponse);

            //订单收货人信息
          /*  OrderRecevierResponse orderRecevierResponse = this.generatOrderRecevierResponse(item.getOrderNo());
            orderResponse.setOrderRecevier(orderRecevierResponse);*/

            //业务人员所属代理商信息
            orderResponse.setSalesAgentInfo(this.getSalesAgentInfo(item.getSalesId()));
        }

        PageInfo pageInfo = new PageInfo(orderList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    // 获取业务人员所属的代理商信息
    private SalesBaseResponse getSalesAgentInfo(int salesId) {
        //业务人员所属代理商信息
        SalesBaseResponse result = null;
        if (salesId != 0) {
            Response<SalesBaseResponse> response = agentService.getAgentInfo(salesId);
            result = response.getValue();
        }

        return result;
    }

    // 获取订单收货详情
    private List<OrderSentResponse> getOrderSend(String orderNo) {
        List<OrderSentResponse> result = new ArrayList<OrderSentResponse>();
        List<OrderSentEntity> orderSentEntities = orderSentDao.getOrderSentByOrderNoOrderByIdASC(orderNo);
        for (OrderSentEntity item : orderSentEntities) {
            OrderSentResponse sentResponse = new OrderSentResponse();
            BeanUtils.copyProperties(item, sentResponse);
            result.add(sentResponse);
        }
        return result;
    }

    /**
     * <p>Description: 根据条件查询订单管理用户订单列表</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param status 订单状态 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    @Override
    public AdminOrderListResponse getAdminOrderList(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile,
                                                    int pageNum,
                                                    int pageSize) throws ServiceException, Exception {
        if (StringUtils.isNotEmpty(mobile) && !PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (status > 0 && OrderStatusEnum.getOrderStatus(status) == null) {
            throw new IllegalArgumentException("status is not exist");
        }
        if (StringUtils.isNotEmpty(createStartTime) && !DateUtil.validateDateTime(createStartTime)) {
            throw new IllegalArgumentException("createStartTime format error");
        }
        if (StringUtils.isNotEmpty(createEndTime) && !DateUtil.validateDateTime(createEndTime)) {
            throw new IllegalArgumentException("createEndTime format error");
        }
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 2;
        }

        if (StringUtils.isNotEmpty(createEndTime)) {
            createEndTime = DateUtil.plusDays(createEndTime, 1);
        }
        AdminOrderListResponse result = new AdminOrderListResponse();
        List<AdminOrderResponse> userOrderList = new ArrayList<AdminOrderResponse>();
        // 查询用户订单
        PageHelper.startPage(pageNum, pageSize);
        List<AdminOrderEntity> orderList = orderDao.getAdminOrderList(
                createStartTime,
                createEndTime,
                StringEscapeUtils.escapeSql(orderNo),
                mobile,
                status,
                receiverName,
                receiverMobile);
        result.setOrderList(userOrderList);
        for (AdminOrderEntity item : orderList) {
            // 构造返回订单对象
            AdminOrderResponse orderResponse = this.getUserOrderResponse(item);
            userOrderList.add(orderResponse);

            // 查询订单详情
            List<OrderDetailResponse> orderDetailResponseList = this.generatOrderDetailResponse(item.getOrderNo());
            orderResponse.setOrderDetails(orderDetailResponseList);

            //业务人员所属代理商信息
            orderResponse.setSalesAgentInfo(this.getSalesAgentInfo(item.getSalesId()));
        }

        PageInfo pageInfo = new PageInfo(orderList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * 订单回收站
     * @param createStartTime
     * @param createEndTime
     * @param orderNo
     * @param mobile
     * @param status
     * @param receiverName
     * @param receiverMobile
     * @param pageNum
     * @param pageSize
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    @Override
    public AdminOrderListResponse getAdminOrderRecycle(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile,
                                                    int pageNum,
                                                    int pageSize) throws ServiceException, Exception {
        if (StringUtils.isNotEmpty(mobile) && !PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (status > 0 && OrderStatusEnum.getOrderStatus(status) == null) {
            throw new IllegalArgumentException("status is not exist");
        }
        if (StringUtils.isNotEmpty(createStartTime) && !DateUtil.validateDateTime(createStartTime)) {
            throw new IllegalArgumentException("createStartTime format error");
        }
        if (StringUtils.isNotEmpty(createEndTime) && !DateUtil.validateDateTime(createEndTime)) {
            throw new IllegalArgumentException("createEndTime format error");
        }
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 2;
        }

        if (StringUtils.isNotEmpty(createEndTime)) {
            createEndTime = DateUtil.plusDays(createEndTime, 1);
        }
        AdminOrderListResponse result = new AdminOrderListResponse();
        List<AdminOrderResponse> userOrderList = new ArrayList<AdminOrderResponse>();
        // 查询用户订单
        PageHelper.startPage(pageNum, pageSize);
        List<AdminOrderEntity> orderList = orderDao.getAdminOrderRecycle(
                createStartTime,
                createEndTime,
                StringEscapeUtils.escapeSql(orderNo),
                mobile,
                status,
                receiverName,
                receiverMobile);
        result.setOrderList(userOrderList);
        for (AdminOrderEntity item : orderList) {
            // 构造返回订单对象
            AdminOrderResponse orderResponse = this.getUserOrderResponse(item);
            userOrderList.add(orderResponse);

            // 查询订单详情
            List<OrderDetailResponse> orderDetailResponseList = this.generatOrderDetailResponse(item.getOrderNo());
            orderResponse.setOrderDetails(orderDetailResponseList);

            //业务人员所属代理商信息
            orderResponse.setSalesAgentInfo(this.getSalesAgentInfo(item.getSalesId()));
        }

        PageInfo pageInfo = new PageInfo(orderList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     * <p>Description: 订单退款</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param refundType 退款类型 </p>
     * <p>param refundMoney 退款金额 </p>
     * <p>param operator 操作人 </p>
     * <p>param operatorName 操作时间 </p>
     * <p>param refundReason 退款原因 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/10 15:00 </p>
     * <p>return</p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addOrderRefund(String orderNo, int refundType, BigDecimal refundMoney, int operator, String operatorName, String refundReason) throws ServiceException, Exception {
        if (StringUtils.isEmpty(orderNo)) {
            throw new IllegalArgumentException("orderNo is empty");
        }
        if (StringUtils.isEmpty(operatorName)) {
            throw new IllegalArgumentException("operatorName is empty");
        }
        if (operator <= 0) {
            throw new IllegalArgumentException("operator is empty");
        }
        if (StringUtils.isEmpty(refundReason)) {
            throw new IllegalArgumentException("refundReason is empty");
        }
        AdminOrderDetailEntity orderDetailEntity = orderDao.getOrderByNo(orderNo);
        if (orderDetailEntity == null) {
            throw new ServiceException(Result.ORDER_NOT_EXIST.getCode(), Result.ORDER_NOT_EXIST.getMessage());
        }
        if (orderDetailEntity.getStatus() == OrderStatusEnum.CLOSED.getIndex()) {
            throw new ServiceException(Result.FAIL.getCode(), "已关闭的订单不能再退款");
        }
        if (refundMoney.compareTo(orderDetailEntity.getReceiveMoney()) > 1) {
            throw new ServiceException(Result.FAIL.getCode(), "退款金额不能大于付款金额");
        }
        OrderRefundEntity entity = orderRefundDao.getOrderRefundByOrderNo(orderNo);
        // 查看是否有过退款记录，没有则插入记录（这种情况一般很少发生）
        if (entity == null) {
            entity = new OrderRefundEntity();
            entity.setOrderNo(orderNo);
            entity.setOrderId(orderDetailEntity.getId());
            // 退款单号
            entity.setRefundNo(OrderNumberUtil.getRefundNo());
            entity.setRefundMoney(refundMoney);
            entity.setOperator(operator);
            entity.setOperatorName(operatorName);
            entity.setOperateTime(new Date());
            entity.setCreateTime(new Date());
            entity.setPaymentType(PaymentTypeEnum.WEIXIN.getIndex());
            entity.setPaymentTime(new Date());
            entity.setRefundReason(refundReason);
            entity.setRefundType(refundType);
            orderRefundDao.addOrderRefund(entity);
        }

        // 更新订单状态到退款中
        orderDao.updateOrderStatusByOrderNo(orderNo, OrderStatusEnum.REFUNDING.getIndex());
        // 更新提成信息
        agentIncomeDao.deleteAgentIncomeByOrderNo(orderNo);
        salesIncomeDao.deleteSalesIncomeByOrderNo(orderNo);

        // 调用接口支付接口退款
        boolean refundResult = PayUtil.orderRefund(weiXinProperties.getAppId(), weiXinProperties.getMchId(), orderNo,
                weiXinProperties.getPaternerKey(), weiXinProperties.getCertPath(), orderDetailEntity.getReceiveMoney().toString(), refundMoney.toString());
        //boolean refundResult = true;
        if (refundResult) {
            // 更新订单状态到退款中
            orderDao.updateOrderStatusByOrderNo(orderNo, OrderStatusEnum.CLOSED.getIndex());
            // 查找发货状态次数，优惠卷是否可退
            if (orderDetailEntity.getSentTimes() == 0) {
                // 没有发货的情况下退优惠卷
                couponDao.cancelCouponOrder(orderDetailEntity.getCouponCode());
            }
            // 更退款状态为成功
            orderRefundDao.updateOrderRefundStatusToClosedByOrderNo(orderNo);
        }
        return refundResult;
    }

    /**
     * <p>Description: 根据发货id，订单编号更新发货状态,更新为侍收货</p>
     * <p>param id 发货记录id </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param id 发货记录id </p>
     * <p>param sendingTime 发货时间 </p>
     * <p>param operator 操作人id </p>
     * <p>param operatorName 操作人姓名 </p>
     * <p>param operateTime 操作时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/11 15:00 </p>
     * <p>return int</p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderSentStatusToShippingByOrderNo(String orderNo,
                                                            String sendingTime,
                                                            String trackingNum,
                                                            int operator,
                                                            String operatorName) throws ServiceException, Exception {

        if (StringUtils.isEmpty(orderNo)) {
            throw new IllegalArgumentException("orderNo is empty");
        }
        if (StringUtils.isNotEmpty(sendingTime) && !DateUtil.validateDateTime(sendingTime)) {
            throw new IllegalArgumentException("sendingTime format error");
        }
        if (StringUtils.isEmpty(trackingNum)) {
            throw new IllegalArgumentException("trackingNum is empty");
        }
        if (StringUtils.isEmpty(operatorName)) {
            throw new IllegalArgumentException("operatorName is empty");
        }
        if (operator <= 0) {
            throw new IllegalArgumentException("operator is empty");
        }

        //找到最近一条没有发货的记录进行更新
       /* List<OrderSentEntity> sentList = orderSentDao.getOrderSentByOrderNo(orderNo);
        if (sentList.isEmpty()) {
            throw new ServiceException(Result.ORDER_SENT_NOT_EXIST.getCode(), Result.ORDER_SENT_NOT_EXIST.getMessage());
        }*/
        OrderSentEntity lastOrderSent = orderSentDao.getOrderSentByNo(orderNo, OrderSendStatusEnum.UNSHIPPING.getIndex());
        // 查询的结果已经按id升序排序，只需要找到第一个符合的条件返回
       /* for (OrderSentEntity item : sentList) {
            // 未发货的记录
            if (item.getStatus() == OrderSendStatusEnum.UNSHIPPING.getIndex()) {
                lastOrderSent = item;
                break;
            }
        }*/
        if (lastOrderSent == null) {
            throw new ServiceException(Result.ORDER_SENT_NOT_EXIST.getCode(), Result.ORDER_SENT_NOT_EXIST.getMessage() + "未找到最后的发货记录");
        }

        Date sendDate = DateUtil.toDateTime(sendingTime);
        int result = orderSentDao.updateOrderSentStatusToShippingByIdAndOrderNo(lastOrderSent.getId(), orderNo, sendDate, trackingNum, operator, operatorName);

        // 订单发货次数加1
        orderDao.updateOrderSentTimesByOrderNo(orderNo);

        return (result > 0);
    }

    /**
     * <p>Description: 根据订单编号和发货记录id，更新未发货记录的发货时间-</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param sendTime 更新后的发货时间 </p>
     * <p>param operator 操作人id </p>
     * <p>param operatorName 操作人姓名 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/14 </p>
     * <p>return boolean</p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderSendTimeByOrderNo(String orderNo,
                                                int sendTime,
                                                int operator,
                                                String operatorName) throws ServiceException, Exception {
        if (StringUtils.isEmpty(orderNo)) {
            throw new IllegalArgumentException("orderNo is empty");
        }
        if (sendTime <= -2 || sendTime > 32) {
            throw new IllegalArgumentException("sendTime format error");
        }
        if (StringUtils.isEmpty(operatorName)) {
            throw new IllegalArgumentException("operatorName is empty");
        }
        if (operator <= 0) {
            throw new IllegalArgumentException("operator is empty");
        }
        // 查出订单对应的发货记录，并找到未发货的记录，更新未发货记录的需要发货的时间
        List<OrderSentEntity> sentList = orderSentDao.getOrderSentByOrderNo(orderNo);
        SortedMap<Integer, OrderSentEntity> needUpdateSents = new TreeMap<Integer, OrderSentEntity>();
        //List<OrderSentEntity> needUpdateSents = new ArrayList<OrderSentEntity>();
        for (OrderSentEntity item : sentList) {
            if (item.getStatus() == OrderSendStatusEnum.UNSHIPPING.getIndex()) {
                //needUpdateSents.add(item);
                needUpdateSents.put(item.getId(), item);
            }
        }
        // 找到本月发货的记录
        OrderSentEntity currentMonthSent = null;
        for (OrderSentEntity item : sentList) {
            // 已发货或已货的记录
            if (item.getStatus() == OrderSendStatusEnum.SHIPPING.getIndex() ||
                    item.getStatus() == OrderSendStatusEnum.RECEIVE.getIndex()) {
                //发货时间在本月内
                if (OrderUtil.isInCurrentMonth(item.getSendingTime())) {
                    currentMonthSent = item;
                    break;
                }
            }
        }
        // 更新发货时间
        Date sentDate = null;
        OrderSentEntity item = null;

        // 获取计算后的发货日期及是否是本月还是次月发货
        int newSendTime = OrderUtil.getSendTime(sendTime);
        int sendMonth = OrderUtil.getSendMonth(sendTime);
        // 如果本月发过货则下个月发货，或下个月发货
        sendMonth = sendMonth | (currentMonthSent == null ? 0 : 1);

        for (int i = 1; i <= needUpdateSents.keySet().toArray().length; i++) {
            item = needUpdateSents.get(needUpdateSents.keySet().toArray()[i - 1]);
            sentDate = OrderUtil.getSendDate(newSendTime, i + sendMonth);
            orderSentDao.updateOrderSendTimeByIdAndOrderNo(item.getId(), item.getOrderNo(), sentDate, operator, operatorName);
        }
        // 更新订单的发货时间
        orderDao.updateOrderSendTimeByOrderNo(orderNo, newSendTime);

        return true;
    }

    /**
     * <p>Description: 根据订单编号和发货记录id，更新未发货记录的收货地址</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param receiverName 收货人名称 </p>
     * <p>param provinceId 省id </p>
     * <p>param cityId 市id </p>
     * <p>param conutyId 县id </p>
     * <p>param address 详情地址 </p>
     * <p>param mobile 收货人手机号 </p>
     * <p>param operator 操作人id </p>
     * <p>param operatorName 操作人姓名 </p>
     * <p>param operateTime 操作时间 </p>
     * <p>param usedNewAddress 是否使用新的地址替换后续所有未发货的地址 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/25 </p>
     * <p>return int</p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderSendAddressByIdAndOrderNo(int id,
                                                        String orderNo,
                                                        String receiverName,
                                                        int provinceId,
                                                        int cityId,
                                                        int countyId,
                                                        String address,
                                                        String mobile,
                                                        int operator,
                                                        String operatorName,
                                                        int useNewAddress) throws ServiceException, Exception {

        if (id <= 0) {
            throw new IllegalArgumentException("id is less than 0");
        }
        if (StringUtils.isEmpty(orderNo)) {
            throw new IllegalArgumentException("orderNo is empty");
        }
        if (StringUtils.isEmpty(receiverName)) {
            throw new IllegalArgumentException("receiverName is empty");
        }
        if (provinceId <= 0) {
            throw new IllegalArgumentException("provinceId is less than 0");
        }
        if (cityId <= 0) {
            throw new IllegalArgumentException("cityId is less than 0");
        }
        if (countyId <= 0) {
            throw new IllegalArgumentException("countyId is less than 0");
        }
        if (StringUtils.isEmpty(address)) {
            throw new IllegalArgumentException("address is empty");
        }
        if (StringUtils.isNotEmpty(mobile) && !PatternHelper.isMobile(mobile)) {
            throw new IllegalArgumentException("mobile format error");
        }
        if (StringUtils.isEmpty(operatorName)) {
            throw new IllegalArgumentException("operatorName is empty");
        }
        if (operator <= 0) {
            throw new IllegalArgumentException("operator is empty");
        }

        List<OrderSentEntity> sentList = orderSentDao.getOrderSentByOrderNo(orderNo);
        List<OrderSentEntity> needUpdate = new ArrayList<OrderSentEntity>();
        for (OrderSentEntity item : sentList) {
            if (item.getStatus() == OrderSendStatusEnum.UNSHIPPING.getIndex()) {
                if (useNewAddress > 0) {
                    // 后续收货地址都更新为新的收货地址
                    needUpdate.add(item);
                } else {
                    //只更新某条记录
                    if (item.getId() == id) {
                        needUpdate.add(item);
                        break;
                    }
                }
            }
        }

        boolean result = false;
        if (needUpdate.size() > 0) {
            for (OrderSentEntity item : needUpdate) {
                orderSentDao.updateOrderSendAddressByIdAndOrderNo(item.getId(),
                        orderNo,
                        receiverName,
                        provinceId,
                        cityId,
                        countyId,
                        address,
                        mobile,
                        operator,
                        operatorName);
            }
            // 更新订单的收货地址
            orderRecevierDao.updateOrderRecevierByOrderNo(orderNo,
                    receiverName,
                    provinceId,
                    cityId,
                    countyId,
                    address,
                    mobile);
            result = true;
        }

        return result;
    }

    /**
     * <p>Description: 根据订单编号和发货记录id，查询发货记录</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/27 </p>
     * <p>return OrderSentResponse</p>
     */
    public OrderSentResponse getOrderSentByIdAndOrderNo(int id, String orderNo) throws ServiceException, Exception {
        if (id <= 0) {
            throw new IllegalArgumentException("id is less than 0");
        }
        if (StringUtils.isEmpty(orderNo)) {
            throw new IllegalArgumentException("orderNo is empty");
        }

        OrderSentEntity entity = orderSentDao.getOrderSentByIdAndOrderNo(id, orderNo);
        OrderSentResponse result = this.getOrderSentResponse(entity);

        return result;
    }

    //构造后台管理订单查询返回实体
    private AdminOrderResponse getUserOrderResponse(AdminOrderEntity entity) {
        AdminOrderResponse result = new AdminOrderResponse();
        BeanUtils.copyProperties(entity, result);
        // 解码昵称
        result.setSalesNickName(EncryptUtil.getFromBase64(entity.getSalesNickName()));
        result.setUserNickName(EncryptUtil.getFromBase64(entity.getUserNickName()));
        //result.setSalesRealName(EncryptUtil.getFromBase64(entity.getSalesRealName()));
        return result;
    }

    //构造后台管理订单详情返回实体
    private AdminOrderDetailResponse getOrderResponse(AdminOrderDetailEntity entity) {
        AdminOrderDetailResponse result = new AdminOrderDetailResponse();

        BeanUtils.copyProperties(entity, result);

        // 解码昵称
        result.setSalesNickName(EncryptUtil.getFromBase64(entity.getSalesNickName()));
        result.setUserNickName(EncryptUtil.getFromBase64(entity.getUserNickName()));
        //result.setSalesRealName(EncryptUtil.getFromBase64(entity.getSalesRealName()));

        return result;
    }

    //构造后台管理订单所购商品返回实体
    private OrderDetailResponse getOrderDetailResponse(OrderDetailEntity entity) {
        OrderDetailResponse result = new OrderDetailResponse();

        BeanUtils.copyProperties(entity, result);

        // 加载商品镜像
        String productJson = entity.getProductContent();
        ProductEntity productEntity = JSONObject.parseObject(productJson, ProductEntity.class);
        ProductResponse productResponse = new ProductResponse();
        productResponse.setEntity(productEntity);
        result.setProductResponse(productResponse);
        /*
        result.setId(entity.getId());
        result.setOrderId(entity.getOrderId());
        result.setPrice(entity.getPrice());
        result.setDiscountMoney(entity.getDiscountMoney());
        result.setTotalPrice(entity.getTotalPrice());
        result.setReceiveMoney(entity.getReceiveMoney());
        result.setCount(entity.getCount());
        result.setProductCode(entity.getProductCode());
        result.setProductId(entity.getProductId());
        result.setProductName(entity.getProductName());
        */

        //result.setRemark(entity.getRemark());商品镜像


        return result;
    }


    private OrderRecevierResponse getOrderRecevierResponse(OrderRecevierEntity entity) {
        OrderRecevierResponse result = new OrderRecevierResponse();

        BeanUtils.copyProperties(entity, result);
        /*
        result.setId(entity.getId());
        result.setOrderId(entity.getOrderId());
        result.setReceiverName(entity.getReceiverName());
        result.setAddress(entity.getAddress());
        result.setMobile(entity.getMobile());
        result.setPostcode(entity.getPostcode());
        result.setSendTime(entity.getSendTime());
        result.setCompany(entity.getCompany());
        result.setTrackingNum(entity.getTrackingNum());
        result.setReceiveTime(entity.getReceiveTime());
        result.setStatus(entity.getStatus());
        */

        return result;
    }

    /**
     * <p>Description: 获取订单发货详情返回对象</p>
     * <p>param entity 订单发货情况参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年11月03日</p>
     * <p>return OrderSentResponse</p>
     */
    private OrderSentResponse getOrderSentResponse(OrderSentEntity entity) {
        OrderSentResponse result = new OrderSentResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    public boolean updateOrdeStatusToDel(String orderNo) throws ServiceException, Exception {
        return orderDao.updateOrdeStatusToDel(orderNo);
    }

}
