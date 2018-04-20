package com.weichuang.ecommerce.order.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.request.OrderRequest;
import com.weichuang.ecommerce.order.entity.response.*;

import java.util.Date;

/**
 * <p>ClassName: IOrderService</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单服务</p>
 * <p>author: jiangkesen</p>
 * <p>date 2017年09月13日 下午16:56:25</p>
 */
public interface IOrderService {

    /**
     * <p>Description: 创建订单</p>
     * <p>param: entity 订单参数实体 </p>
     * <p>author: jiangkesen </p>
     * <p>date: 2017年09月13日 下午16:56:25 </p>
     * <p>return: </p>
     * <p>throws ServiceException </p>
     */
    public OrderAddResponse addOrder(OrderRequest request) throws ServiceException, Exception;

    /**
     * <p>Description: 根据订单id查询订单</p>
     * <p>param: orderId 订单id </p>
     * <p>author: jiangkesen </p>
     * <p>date: 2017年09月13日 下午16:56:25 </p>
     * <p>return: </p>
     * <p>throws ServiceException </p>
     */
    public UserOrderDetailResponse getOrderById(int orderId) throws ServiceException, Exception;

    /**
     * <p>Description: 根据订单编号查询订单</p>
     * <p>param: orderNo 订单编号 </p>
     * <p>author: jiangkesen </p>
     * <p>date: 2017年09月25日 下午18:56:25 </p>
     * <p>return: </p>
     * <p>throws ServiceException </p>
     */
    public UserOrderDetailResponse getOrderByNo(String orderNo) throws ServiceException, Exception;

    /**
     * <p>Description: 根据条件查询会员中心用户订单列表</p>
     * <p>param userId 用户Id </p>
     * <p>param status 订单状态 </p>
     * <p>param startTime 订单创建开始时间 </p>
     * <p>param endTime 订单创建结束时间 </p>
     * <p>param pageNum 开始页面码 </p>
     * <p>param pageSize 每页显示数据的数量 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/15 16:00 </p>
     * <p>return UserOrderListResponse</p>
     */
    public UserOrderListResponse getOrderList(int userId, int status, String startTime, String endTime, int pageNum, int pageSize) throws ServiceException, Exception;

    /**
     * <p>Description:会员详情-交易统计</p>
     * <p>param userid:</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/6 14:09</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public TransactionStatisticsResponse getTransactionStatistics(int userid);

    /**
     * <p>Description:订单收货,更新订单的收货记录的状态</p>
     * <p>param orderNo:</p>
     * <p>author:jiangkesen</p>
     * <p>date:2017/11/21</p>
     * <p>return: boolean</p>
     * <p>throws: </p>
     */
    public boolean updateOrderSentStatusToReceivedByOrderNo(String orderNo) throws ServiceException, Exception;

    /**
     * <p>Description:根据用户查询各个状态订单的数量</p>
     * <p>param userid:</p>
     * <p>author:jiangkesen</p>
     * <p>date:2017/11/30 16:09</p>
     * <p>return: OrderStatusCountListResponse</p>
     * <p>throws: </p>
     */
    public OrderStatusCountListResponse getOrderStatusCountByUserId(int userId) throws ServiceException, Exception;
}
