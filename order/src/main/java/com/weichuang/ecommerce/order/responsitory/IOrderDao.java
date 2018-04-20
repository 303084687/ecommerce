package com.weichuang.ecommerce.order.responsitory;

import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.entity.OrderStatusCountEntity;
import com.weichuang.ecommerce.order.entity.TransactionStatisticsEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: IOrderDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月13日 下午2:51:13</p>
 */
public interface IOrderDao {
    /**
     * <p>Description: 添加订单</p>
     * <p>param entity 订单实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return </p>
     */
    public int addOrder(OrderEntity entity);

    /**
     * <p>Description: 根据订单Id查询订单</p>
     * <p>param orderId 订单Id </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return OrderEntity</p>
     */
    public OrderEntity getOrderById(int orderId);

    /**
     * <p>Description: 根据订单编号查询订单</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return OrderEntity</p>
     */
    public OrderEntity getOrderByNo(String orderNo);

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
    public List<OrderEntity> getOrderList(int userId, int status, String startTime, String endTime);

    /**
     * @param orderNo 订单号
     * @param status  订单状态
     */
    public int updateOrderStatusByOrderNo(String orderNo, Integer status);

    /**
     * @param orderNo         订单号
     * @param status          订单状态
     * @param paymentType     支付类型
     * @param paymentSequence 支付流水号
     * @param paymentTime     支付时间
     */
    public void updatePaymentStatusByOrderNo(String orderNo, Integer status, Integer paymentType, String paymentSequence);

    /**
     * <p>Description:会员详情-交易统计</p>
     * <p>param userid:</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/6 14:09</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public TransactionStatisticsEntity getTransactionStatistics(int userid);


    /**
     * <p>Description:根据用户查询各个状态订单的数量</p>
     * <p>param userid:</p>
     * <p>author:jiangkesen</p>
     * <p>date:2017/11/30 16:09</p>
     * <p>return: OrderStatusCountEntity</p>
     * <p>throws: </p>
     */
    public List<OrderStatusCountEntity> getOrderStatusCountByUserId(int userId);

    /**
     * 查询过期未关闭订单
     * @return
     */

    public List<OrderEntity> getOrderExpire(int days);

    /**
     * 查新最近几分钟订单
     * @param minute
     * @return
     */
    public List<OrderEntity> getOrderRemind(int minute);

    /**
     * @param orderNo 订单号
     */
    public int updateOrderRemindByOrderNo(String orderNo);

}
