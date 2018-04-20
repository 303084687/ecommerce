package com.weichuang.ecommerce.order.responsitory.impl;

import com.weichuang.ecommerce.MyBatisConfig;

import com.weichuang.ecommerce.order.constants.NameSpaceConstant;
import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.entity.OrderStatusCountEntity;
import com.weichuang.ecommerce.order.entity.TransactionStatisticsEntity;
import com.weichuang.ecommerce.order.responsitory.IOrderDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>ClassName: OrderDaoImpl.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单数据访问 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月13日 下午2:55:24</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class OrderDaoImpl implements IOrderDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 添加订单</p>
     * <p>param entity 订单实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/13 15:00 </p>
     * <p>return </p>
     */
    @Override
    public int addOrder(OrderEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.ORDER + ".addOrder", entity);
    }

    /**
     * <p>Description: 根据订单Id查询订单</p>
     * <p>param orderId 订单Id </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return OrderEntity</p>
     */
    @Override
    public OrderEntity getOrderById(int orderId) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER + ".getOrderById", orderId);
    }

    /**
     * <p>Description: 根据订单编号查询订单</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return OrderEntity</p>
     */
    public OrderEntity getOrderByNo(String orderNo) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER + ".getOrderByNo", orderNo);
    }


    /**
     * <p>Description: 根据条件查询订单列表</p>
     * <p>param userId 用户Id </p>
     * <p>param status 订单状态 </p>
     * <p>param startTime 订单创建开始时间 </p>
     * <p>param endTime 订单创建结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 16:00 </p>
     * <p>return List<OrderEntity></p>
     */
    @Override
    public List<OrderEntity> getOrderList(int userId, int status, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("status", status);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<OrderEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getOrderList", map);
        return result;
    }


    /**
     * 更新订单状态
     *
     * @param orderNo 订单号
     * @param status  订单状态
     */
    public int updateOrderStatusByOrderNo(String orderNo, Integer status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("status", status);
        return sqlSessionTemplate.update(NameSpaceConstant.ORDER + ".updateOrderStatusByOrderNo", map);
    }


    /**
     * 更新支付状态
     *
     * @param orderNo         订单号
     * @param status          订单状态
     * @param paymentType     支付类型
     * @param paymentSequence 支付流水号
     * @param paymentTime     支付时间
     */
    public void updatePaymentStatusByOrderNo(String orderNo, Integer status, Integer paymentType, String paymentSequence) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        map.put("status", status);
        map.put("paymentType", paymentType);
        map.put("paymentSequence", paymentSequence);

        sqlSessionTemplate.update(NameSpaceConstant.ORDER + ".updatePaymentStatusByOrderNo", map);
    }

    /**
     * <p>Description:会员详情-交易统计</p>
     * <p>param userid:</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/6 14:09</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public TransactionStatisticsEntity getTransactionStatistics(int userid) {

        return sqlSessionTemplate.selectOne(NameSpaceConstant.ORDER_STATISTICS + ".getTransactionStatistics", userid);
    }

    /**
     * <p>Description:根据用户查询各个状态订单的数量</p>
     * <p>param userid:</p>
     * <p>author:jiangkesen</p>
     * <p>date:2017/11/30 16:09</p>
     * <p>return: OrderStatusCountEntity</p>
     * <p>throws: </p>
     */
    public List<OrderStatusCountEntity> getOrderStatusCountByUserId(int userId) {
        return sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getOrderStatusCountByUserId", userId);
    }

    @Override
    public List<OrderEntity> getOrderExpire(int days) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("days", days);
        List<OrderEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getOrderExpire",map);
        return result;
    }

    @Override
    public List<OrderEntity> getOrderRemind(int minute) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("minute", minute);
        List<OrderEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.ORDER + ".getOrderRemind",map);
        return result;
    }

    /**
     * 更新订单提醒状态
     *
     * @param orderNo 订单号
     */
    public int updateOrderRemindByOrderNo(String orderNo) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderNo", orderNo);
        return sqlSessionTemplate.update(NameSpaceConstant.ORDER + ".updateOrderRemindByOrderNo", map);
    }

}
