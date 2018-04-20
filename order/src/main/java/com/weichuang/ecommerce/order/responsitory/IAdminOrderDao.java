package com.weichuang.ecommerce.order.responsitory;

import com.weichuang.ecommerce.order.entity.AdminOrderDetailEntity;
import com.weichuang.ecommerce.order.entity.AdminOrderEntity;
import com.weichuang.ecommerce.order.entity.OrderEntity;

import java.util.Date;
import java.util.List;


/**
 * <p>ClassName: IAdminOrderDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台管理订单数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月2日 下午2:51:13</p>
 */
public interface IAdminOrderDao {


    /**
     * <p>Description: 根据订单编号查询订单</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 09:00 </p>
     * <p>return OrderEntity</p>
     */
    public AdminOrderDetailEntity getOrderByNo(String orderNo);

    /**
     * <p>Description: 根据条件查询订单管理用户订单列表,已发货的订单列表的查询(配送中的订单)</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param sendStartTime 发货时间起始时间 </p>
     * <p>param sendEndTime 发货时间终止时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return List<OrderEntity></p>
     */
    public List<AdminOrderEntity> getSentOrderList(String createStartTime,
                                                   String createEndTime,
                                                   String sendStartTime,
                                                   String sendEndTime,
                                                   String orderNo,
                                                   String mobile,
                                                   String receiverName,
                                                   String receiverMobile);

    /**
     * <p>Description: 根据条件查询订单管理订单列表,用户未发货的订单列表的查询（配送中的订单）</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param sendStartTime 发货时间起始时间 </p>
     * <p>param sendEndTime 发货时间终止时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return List<OrderEntity></p>
     */
    public List<AdminOrderEntity> getUnsentOrderList(String createStartTime,
                                                     String createEndTime,
                                                     String sendStartTime,
                                                     String sendEndTime,
                                                     String orderNo,
                                                     String mobile,
                                                     String receiverName,
                                                     String receiverMobile);


    /**
     * <p>Description: 根据条件查询订单管理用户订单列表</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param status 订单状态 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return List<OrderEntity></p>
     */
    public List<AdminOrderEntity> getAdminOrderList(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile);

    /**
     * 订单回收站
     * @param createStartTime
     * @param createEndTime
     * @param orderNo
     * @param mobile
     * @param status
     * @param receiverName
     * @param receiverMobile
     * @return
     */
    public List<AdminOrderEntity> getAdminOrderRecycle(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile);
    /**
     * <p>Description: 根据订单编号更新发货次数加1</p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/13 </p>
     * <p>return int</p>
     */
    public int updateOrderSentTimesByOrderNo(String orderNo);

    /**
     * <p>Description: 根据订单编号更新未发货记录的发货时间-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param sendTime 更新后的发货时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/15 </p>
     * <p>return int</p>
     */
    public int updateOrderSendTimeByOrderNo(String orderNo, int sendTime);

    /**
     * <p>Description: 根据订单编号更新订单状态</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param status 订单状态 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/17 </p>
     * <p>return int</p>
     */
    public int updateOrderStatusByOrderNo(String orderNo, int status);

    /**
     * 根据订单编号删除订单
     * @param orderNo
     * @return
     */
    public boolean updateOrdeStatusToDel(String orderNo);

    /**
     * 列表
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    public List<OrderEntity> orderList(String startTime, String endTime, int status);

}
