package com.weichuang.ecommerce.order.responsitory.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.order.constants.NameSpaceConstant;
import com.weichuang.ecommerce.order.entity.OrderSentEntity;
import com.weichuang.ecommerce.order.responsitory.IOrderSentDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>ClassName: IOrderSentDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单发货详细信息 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月3日</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class OrderSentDaoImpl implements IOrderSentDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 增加订单发货详情</p>
     * <p>param OrderSentEntity 订单发货实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/3 09:00 </p>
     * <p>return int</p>
     */
    @Override
    public int addOrderSent(OrderSentEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.ORDER_SENT + ".addOrderSent", entity);
    }

    /**
     * <p>Description: 根据订单Id查询订单发货详情</p>
     * <p>param orderId 订单Id </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/03 11:00 </p>
     * <p>return List<OrderSentEntity></p>
     */
    @Override
    public List<OrderSentEntity> getOrderSentByOrderId(int orderId) {
        List<OrderSentEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER_SENT + ".getOrderSentByOrderId", orderId);
        return result;
    }

    /**
     * <p>Description: 根据订单编号查询订单发货详情</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/03 10:00 </p>
     * <p>return List<OrderSentEntity></p>
     */
    public List<OrderSentEntity> getOrderSentByOrderNo(String orderNo) {
        List<OrderSentEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER_SENT + ".getOrderSentByOrderNo", orderNo);
        return result;
    }

    /**
     * <p>Description: 根据订单编号查询订单发货详情,根据id升序</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/27 10:00 </p>
     * <p>return List<OrderSentEntity></p>
     */
    public List<OrderSentEntity> getOrderSentByOrderNoOrderByIdASC(String orderNo) {
        List<OrderSentEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER_SENT + ".getOrderSentByOrderNoOrderByIdASC", orderNo);
        return result;
    }

    /**
     * 根据订单编号查询未订单发货详情
     *
     * @param orderNo
     * @return
     */
    public OrderSentEntity getOrderSentByNo(String orderNo, int status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("status", status);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER_SENT + ".getOrderSentByNo", map);
    }

    /**
     * 查询过期未收货发货详情
     *
     * @param orderNo
     * @return
     */
    public List<OrderSentEntity> getOrderSentExpire(String orderNo) {
        List<OrderSentEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER_SENT + ".getOrderSentExpire", orderNo);
        return result;
    }

    /**
     * <p>Description: 根据发货id，订单编号更新发货状态,更新为已收货</p>
     * <p>param id 发货记录id </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param receiveTime 收货时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/11 15:00 </p>
     * <p>return int</p>
     */
    @Override
    public int updateOrderSentStatusToReceivedByIdAndOrderNo(int id, String orderNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("id", id);
        return sqlSessionTemplate.update(NameSpaceConstant.ORDER_SENT + ".updateOrderSentStatusToReceivedByIdAndOrderNo", map);
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
    public int updateOrderSentStatusToShippingByIdAndOrderNo(int id,
                                                             String orderNo,
                                                             Date sendingTime,
                                                             String trackingNum,
                                                             int operator,
                                                             String operatorName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("id", id);
        map.put("sendingTime", sendingTime);
        map.put("trackingNum", trackingNum);
        map.put("operator", operator);
        map.put("operatorName", operatorName);
        return sqlSessionTemplate.update(NameSpaceConstant.ORDER_SENT + ".updateOrderSentStatusToShippingByIdAndOrderNo", map);
    }

    /**
     * <p>Description: 根据订单编号和发货记录id，更新未发货记录的发货时间-</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param sendTime 更新后的发货时间 </p>
     * <p>param operator 操作人id </p>
     * <p>param operatorName 操作人姓名 </p>
     * <p>param operateTime 操作时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/14 </p>
     * <p>return int</p>
     */
    @Override
    public int updateOrderSendTimeByIdAndOrderNo(int id,
                                                 String orderNo,
                                                 Date sendTime,
                                                 int operator,
                                                 String operatorName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("orderNo", orderNo);
        map.put("sendTime", sendTime);
        map.put("operator", operator);
        map.put("operatorName", operatorName);
        int result = sqlSessionTemplate.update(NameSpaceConstant.ORDER_SENT + ".updateOrderSendTimeByIdAndOrderNo", map);
        return result;
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
     * <p>author jiangkesen </p>
     * <p>date 2017/12/25 </p>
     * <p>return int</p>
     */
    @Override
    public int updateOrderSendAddressByIdAndOrderNo(int id,
                                                    String orderNo,
                                                    String receiverName,
                                                    int provinceId,
                                                    int cityId,
                                                    int countyId,
                                                    String address,
                                                    String mobile,
                                                    int operator,
                                                    String operatorName) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("orderNo", orderNo);
        map.put("receiverName", receiverName);
        map.put("provinceId", provinceId);
        map.put("cityId", cityId);
        map.put("countyId", countyId);
        map.put("address", address);
        map.put("mobile", mobile);
        map.put("operator", operator);
        map.put("operatorName", operatorName);
        int result = sqlSessionTemplate.update(NameSpaceConstant.ORDER_SENT + ".updateOrderSendAddressByIdAndOrderNo", map);
        return result;
    }

    /**
     * <p>Description: 根据订单编号和发货记录id，查询发货记录</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/27 </p>
     * <p>return OrderSentEntity</p>
     */
    public OrderSentEntity getOrderSentByIdAndOrderNo(int id, String orderNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("orderNo", orderNo);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER_SENT + ".getOrderSentByIdAndOrderNo", map);
    }
}
