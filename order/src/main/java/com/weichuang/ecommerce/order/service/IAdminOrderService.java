package com.weichuang.ecommerce.order.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.response.AdminOrderDetailResponse;
import com.weichuang.ecommerce.order.entity.response.AdminOrderListResponse;
import com.weichuang.ecommerce.order.entity.response.OrderSentResponse;

import java.math.BigDecimal;

/**
 * <p>ClassName: IAdminOrderService</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台订单服务</p>
 * <p>author: jiangkesen</p>
 * <p>date 2017年11月2日</p>
 */
public interface IAdminOrderService {

    /**
     * <p>Description: 根据订单编号查询订单</p>
     * <p>param: orderNo 订单编号 </p>
     * <p>author: jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return: </p>
     * <p>throws ServiceException </p>
     */
    public AdminOrderDetailResponse getOrderByOrderNo(String orderNo) throws ServiceException, Exception;

    /**
     * <p>Description: 根据条件查询订单管理用户订单列表,用户已发货的订单列表的查询(配送中的订单)</p>
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
    public AdminOrderListResponse getSentOrderList(String createStartTime,
                                                   String createEndTime,
                                                   String sendStartTime,
                                                   String sendEndTime,
                                                   String orderNo,
                                                   String mobile,
                                                   String receiverName,
                                                   String receiverMobile,
                                                   int pageNum,
                                                   int pageSize) throws ServiceException, Exception;

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
    public AdminOrderListResponse getUnsentOrderList(String createStartTime,
                                                     String createEndTime,
                                                     String sendStartTime,
                                                     String sendEndTime,
                                                     String orderNo,
                                                     String mobile,
                                                     String receiverName,
                                                     String receiverMobile,
                                                     int pageNum,
                                                     int pageSize) throws ServiceException, Exception;


    /**
     * <p>Description: 根据条件查询订单管理用户订单列表</p>
     * <p>param createStartTime 订单创建开始时间 </p>
     * <p>param createEndTime 订单创建结束时间 </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param mobile 购买人手机号 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/2 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    public AdminOrderListResponse getAdminOrderList(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile,
                                                    int pageNum,
                                                    int pageSize) throws ServiceException, Exception;

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
    public AdminOrderListResponse getAdminOrderRecycle(String createStartTime,
                                                    String createEndTime,
                                                    String orderNo,
                                                    String mobile,
                                                    int status,
                                                    String receiverName,
                                                    String receiverMobile,
                                                    int pageNum,
                                                    int pageSize) throws ServiceException, Exception;
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
    public boolean addOrderRefund(String orderNo,
                               int refundType,
                               BigDecimal refundMoney,
                               int operator,
                               String operatorName,
                               String refundReason) throws ServiceException, Exception;

    /**
     * <p>Description: 根据发货id，订单编号更新发货状态,更新为侍收货</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param sendingTime 发货时间 </p>
     * <p>param trackingNum 订单编号 </p>
     * <p>param operator 操作人id </p>
     * <p>param operatorName 操作人姓名 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/11 15:00 </p>
     * <p>return int</p>
     */
    public boolean updateOrderSentStatusToShippingByOrderNo(String orderNo,
                                                            String sendingTime,
                                                            String trackingNum,
                                                            int operator,
                                                            String operatorName) throws ServiceException, Exception;

    /**
     * <p>Description: 根据订单编号更新未发货记录的发货时间-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param sendTime 更新后的发货时间 </p>
     * <p>param operator 操作人id </p>
     * <p>param operatorName 操作人姓名 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/14 </p>
     * <p>return boolean</p>
     */
    public boolean updateOrderSendTimeByOrderNo(String orderNo,
                                                int sendTime,
                                                int operator,
                                                String operatorName) throws ServiceException, Exception;

    /**
     * <p>Description: 根据订单编号和发货记录id，更新未发货记录的收货地址</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param receiverName 收货人名称 </p>
     * <p>param provinceId 省id </p>
     * <p>param cityId 市id </p>
     * <p>param countyId 县id </p>
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
                                                        int usedNewAddress) throws ServiceException, Exception;

    /**
     * <p>Description: 根据订单编号和发货记录id，查询发货记录</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/27 </p>
     * <p>return OrderSentResponse</p>
     */
    public OrderSentResponse getOrderSentByIdAndOrderNo(int id, String orderNo) throws ServiceException, Exception;

    /**
     * 逻辑删除
     * @param orderNo
     * @return
     * @throws ServiceException
     * @throws Exception
     */
    public boolean updateOrdeStatusToDel(String orderNo)throws ServiceException, Exception;

}
