package com.weichuang.ecommerce.order.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.DateUtil;
import com.weichuang.commons.OrderNumberUtil;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.coupon.responsitory.ICouponDao;
import com.weichuang.ecommerce.order.constants.OrderSendStatusEnum;
import com.weichuang.ecommerce.order.constants.OrderStatusEnum;
import com.weichuang.ecommerce.order.constants.OrderUtil;
import com.weichuang.ecommerce.order.entity.*;
import com.weichuang.ecommerce.order.entity.request.OrderDetailRequest;
import com.weichuang.ecommerce.order.entity.request.OrderRecevierRequest;
import com.weichuang.ecommerce.order.entity.request.OrderRequest;
import com.weichuang.ecommerce.order.entity.response.*;
import com.weichuang.ecommerce.order.responsitory.*;
import com.weichuang.ecommerce.order.service.IOrderService;
import com.weichuang.ecommerce.product.entity.ProductEntity;
import com.weichuang.ecommerce.product.entity.response.ProductResponse;
import com.weichuang.ecommerce.product.responsitory.IProductDao;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>ClassName: IOrderService</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单服务</p>
 * <p>author: jiangkesen</p>
 * <p>date 2017年09月13日 下午16:56:25</p>
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    //订单接口
    @Autowired
    private IOrderDao orderDao;

    //订单详情接口
    @Autowired
    private IOrderDetailDao orderDetailDao;

    //订单收货人接口
    @Autowired
    private IOrderRecevierDao orderRecevierDao;

    //订单发货详情接口
    @Autowired
    private IOrderSentDao orderSentDao;

    //商品接口
    @Autowired
    private IProductDao productDao;

    //优惠卷接口
    @Autowired
    private ICouponDao couponDao;

    //优惠退款接口
    @Autowired
    private IOrderRefundDao orderRefundDao;

    /**
     * <p>Description: 创建订单</p>
     * <p>param: entity 订单参数实体 </p>
     * <p>author: jiangkesen </p>
     * <p>date: 2017年09月13日 下午16:56:25 </p>
     * <p>return: orderId</p>
     * <p>throws ServiceException </p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderAddResponse addOrder(OrderRequest request) throws ServiceException, Exception {
        if (request == null) {
            throw new IllegalArgumentException("request is null");
        }
        if (request.getOrderDetails() == null || request.getOrderDetails().isEmpty()) {
            throw new IllegalArgumentException("request.orderDetails is null or empty");
        }
        if (request.getUserId() <= 0 || StringUtils.isEmpty(request.getUserName())) {
            throw new IllegalArgumentException("request.userId or userName is null or empty");
        }
        //解析request生成订单实体
        OrderEntity order = this.getOrderEntity(request);
        //创建订单
        orderDao.addOrder(order);
        int orderId = order.getId();
        //订单创建失败
        if (orderId <= 0) {
            throw new ServiceException(Result.ORDER_CREATE_ERROR.getCode(), Result.ORDER_CREATE_ERROR.getMessage());
        }
        //循环创建订单详情
        for (OrderDetailRequest orderDetail : request.getOrderDetails()) {
            orderDetailDao.addOrderDetail(this.getOrderDetailEntity(orderDetail, order));
        }
        //创建收货人详情
        orderRecevierDao.addOrderRecevier(this.getOrderRecevierEntity(request.getOrderRecevier(), order));

        //设置每次收货人的信息记录（根据发货次数确认添加多少条记录）
        this.addOrderSend(request.getSendTime(), request.getSendTimes(), request.getOrderRecevier(), order);

        //如有优惠卷，更新优惠卷的状态
        if (StringUtils.isNotEmpty(request.getCouponCode())) {
            couponDao.referCouponOrder(request.getUserName(), order.getOrderNo(), request.getCouponCode(), 2);
        }

        OrderAddResponse result = this.getOrderAddResponse(order);

        return result;
    }

    // 每个月发货的时间及发货的次数
    private void addOrderSend(int sendTime, int sendTimes, OrderRecevierRequest orderRecevier, OrderEntity order) throws ParseException {
        // 获取计算后的发货日期及是否是本月还是次月发货
        int newSendTime = OrderUtil.getSendTime(sendTime);
        int sendMonth = OrderUtil.getSendMonth(sendTime);
        for (int i = 1; i <= sendTimes; i++) {
            //计划每个月的发货时间
            Date date = OrderUtil.getSendDate(newSendTime, i + sendMonth);//this.getSendDate(sendTime, i);
            OrderSentEntity orderSent = this.getOrderSentEntity(orderRecevier, order);
            orderSent.setSendTime(date);
            orderSentDao.addOrderSent(orderSent);
        }
    }

    // 收货详情信息
    private OrderSentEntity getOrderSentEntity(OrderRecevierRequest orderRecevier, OrderEntity order) {
        OrderSentEntity result = new OrderSentEntity();
        BeanUtils.copyProperties(orderRecevier, result);
        result.setOrderId(order.getId());
        result.setOrderNo(order.getOrderNo());
        result.setCreateTime(new Date());
        // 待发货状态
        result.setStatus(OrderSendStatusEnum.UNSHIPPING.getIndex());

        return result;
    }

    // 计划每个月的发货日期
//    private Date getSendDate(int sendTime, int times) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar c = Calendar.getInstance();
//        c.add(Calendar.MONTH, times);
//        c.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
//        int day = c.get(Calendar.DAY_OF_MONTH);
//        int needSendTime = 0;
//        if (day < sendTime) {
//            needSendTime = day;
//        } else {
//            needSendTime = sendTime;
//        }
//        c.set(Calendar.DAY_OF_MONTH, needSendTime);
//        Date result = format.parse(format.format(c.getTime()));
//
//        return result;
//    }

    // 获取订单创建后的返回结果
    private OrderAddResponse getOrderAddResponse(OrderEntity orderEntity) {
        OrderAddResponse result = new OrderAddResponse();
        BeanUtils.copyProperties(orderEntity, result);
        return result;
    }

    /**
     * <p>Description: 根据订单id查询订单</p>
     * <p>param: entity 订单参数实体 </p>
     * <p>author: jiangkesen </p>
     * <p>date: 2017年09月13日 下午16:56:25 </p>
     * <p>return: </p>
     * <p>throws ServiceException </p>
     */
    @Override
    public UserOrderDetailResponse getOrderById(int orderId) throws ServiceException, Exception {
        if (orderId <= 0) {
            throw new IllegalArgumentException("orderId less than 0");
        }
        //查询订单主体
        OrderEntity orderEntity = orderDao.getOrderById(orderId);
        if (orderEntity == null) {
            throw new ServiceException(Result.ORDER_NOT_EXIST.getCode(), Result.ORDER_NOT_EXIST.getMessage());
        }
        UserOrderDetailResponse result = this.generatOrderResponse(orderEntity);
        return result;
        /*
        UserOrderDetailResponse result = this.getOrderResponse(orderEntity);
        //查询订单详情
        List<OrderDetailResponse> orderDetailResponses = new ArrayList<OrderDetailResponse>();
        List<OrderDetailEntity> orderDetailEntities = orderDetailDao.getOrderDetailsByOrderId(orderId);
        for (OrderDetailEntity item : orderDetailEntities) {
            orderDetailResponses.add(this.getOrderDetailResponse(item));
        }
        result.setOrderDetails(orderDetailResponses);
        //查询订单收货人信息
        List<OrderRecevierResponse> orderRecevierResponses = new ArrayList<OrderRecevierResponse>();
        List<OrderRecevierEntity> orderRecevierEntities = orderRecevierDao.getOrderReceviersByOrderId(orderId);
        for (OrderRecevierEntity item : orderRecevierEntities) {
            orderRecevierResponses.add(this.getOrderRecevierResponse(item));
        }
        result.setOrderReceviers(orderRecevierResponses);
        return result;
        */
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
    public UserOrderDetailResponse getOrderByNo(String orderNo) throws ServiceException, Exception {
        if (orderNo == null || orderNo.isEmpty()) {
            throw new IllegalArgumentException("orderNo is empty or null");
        }
        //查询订单主体
        OrderEntity orderEntity = orderDao.getOrderByNo(orderNo);
        if (orderEntity == null) {
            throw new ServiceException(Result.ORDER_NOT_EXIST.getCode(), Result.ORDER_NOT_EXIST.getMessage());
        }
        UserOrderDetailResponse result = this.generatOrderResponse(orderEntity);
        return result;
    }

    // 构造订单返回值
    private UserOrderDetailResponse generatOrderResponse(OrderEntity orderEntity) {
        String orderNo = orderEntity.getOrderNo();
        UserOrderDetailResponse result = this.getOrderResponse(orderEntity);
        //查询订单详情
        List<OrderDetailResponse> orderDetailResponses = this.generatOrderDetailResponse(orderNo);
        result.setOrderDetails(orderDetailResponses);
        //查询订单收货人信息
        OrderRecevierResponse orderRecevierResponse = this.generatOrderRecevierResponse(orderNo);
        result.setOrderRecevier(orderRecevierResponse);
        // 发货信息详情
        List<OrderSentResponse> orderSentResponses = this.generatOrderSentResponse(orderNo);
        result.setOrderSents(orderSentResponses);

        // 如果订单为退款中或关闭状态需要查询订章的退款信息
        if (orderEntity.getStatus() == OrderStatusEnum.REFUNDING.getIndex() ||
                orderEntity.getStatus() == OrderStatusEnum.CLOSED.getIndex()) {
            OrderRefundResponse orderRefund = generatOrderRefundResponse(orderNo);
            result.setOrderRefund(orderRefund);
        }
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

    // 根据订单码构造订单退款信息
    private OrderRefundResponse generatOrderRefundResponse(String orderNo) {
        OrderRefundEntity refundEntity = orderRefundDao.getOrderRefundByOrderNo(orderNo);
        OrderRefundResponse result = this.getOrderRefundResponse(refundEntity);
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
            // 只显示已发货或已收货的记录
            if (item.getStatus() == OrderSendStatusEnum.SHIPPING.getIndex() || item.getStatus() == OrderSendStatusEnum.RECEIVE.getIndex()) {
                result.add(this.getOrderSentResponse(item));
            }
        }
        return result;
    }


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
    @Override
    public UserOrderListResponse getOrderList(int userId, int status, String startTime, String endTime, int pageNum, int pageSize) throws ServiceException, Exception {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId less than 0");
        }
        if (status > 0) {
            if (OrderStatusEnum.getOrderStatus(status) == null) {
                throw new IllegalArgumentException("status is not exist");
            }
        }
        if (StringUtils.isNotEmpty(startTime) && !DateUtil.validShortDate(startTime)) {
            throw new IllegalArgumentException("startTime format error");
        }
        if (StringUtils.isNotEmpty(endTime) && !DateUtil.validShortDate(endTime)) {
            throw new IllegalArgumentException("endTime format error");
        }
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 2;
        }

        if (StringUtils.isNotEmpty(endTime)) {
            endTime = DateUtil.plusDays(endTime, 1);
        }

        UserOrderListResponse result = new UserOrderListResponse();
        List<UserOrderResponse> userOrderList = new ArrayList<UserOrderResponse>();
        // 查询用户订单
        PageHelper.startPage(pageNum, pageSize);
        List<OrderEntity> orderList = orderDao.getOrderList(userId, status, startTime, endTime);
        result.setOrderList(userOrderList);
        boolean isNeedReceive = false;
        for (OrderEntity item : orderList) {
            // 构造返回订单对象
            UserOrderResponse userOrderResponse = this.getUserOrderResponse(item);
            userOrderList.add(userOrderResponse);

            // 查询订单详情
            List<OrderDetailResponse> orderDetailResponseList = this.generatOrderDetailResponse(item.getOrderNo());
            userOrderResponse.setOrderDetails(orderDetailResponseList);

            // 是否需要用户确信发货
            isNeedReceive = this.getNeedReceive(item.getOrderNo());
            userOrderResponse.setNeedReceive(isNeedReceive ? 1 : 0);
        }

        PageInfo pageInfo = new PageInfo(orderList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return result;
    }

    // 判断是否需要用户确认收货
    private boolean getNeedReceive(String orderNo) {
        boolean result = false;
        //发货记录
        List<OrderSentEntity> orderSentList = orderSentDao.getOrderSentByOrderNo(orderNo);
        for (OrderSentEntity orderSent : orderSentList) {
            if (orderSent.getStatus() == OrderSendStatusEnum.SHIPPING.getIndex()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * <p>Description:订单收货,更新订单的收货记录的状态</p>
     * <p>param orderNo:</p>
     * <p>author:jiangkesen</p>
     * <p>date:2017/11/21</p>
     * <p>return: boolean</p>
     * <p>throws: </p>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateOrderSentStatusToReceivedByOrderNo(String orderNo) throws ServiceException, Exception {
        if (StringUtils.isEmpty(orderNo)) {
            throw new IllegalArgumentException("orderNo is null or empty");
        }

        OrderEntity order = orderDao.getOrderByNo(orderNo);
        if (order == null) {
            throw new ServiceException(Result.ORDER_NOT_EXIST);
        }
        // 查询待收货的记录，
       /* List<OrderSentEntity> sentList = orderSentDao.getOrderSentByOrderNo(orderNo);
        int sentId = 0;
        for (OrderSentEntity item : sentList) {
            if (item.getStatus() == OrderSendStatusEnum.SHIPPING.getIndex()) {
                sentId = item.getId();
                break;
            }
        }*/
        int sentId = 0;
        OrderSentEntity lastOrderSent = orderSentDao.getOrderSentByNo(orderNo, OrderSendStatusEnum.SHIPPING.getIndex());
        if (lastOrderSent == null) {
            throw new ServiceException(Result.ORDER_SENT_NOT_EXIST.getCode(), Result.ORDER_SENT_NOT_EXIST.getMessage() + "未找到最后的发货记录");
        }
        sentId = lastOrderSent.getId();
        boolean result = false;
        // 更新待收
        if (sentId > 0) {
            int count = orderSentDao.updateOrderSentStatusToReceivedByIdAndOrderNo(sentId, orderNo);

            // 查看订单需要发货的次数与实际的发货次数做对比，如果两个次数一至则表示订单交易完成，更新订单的状态为交易完成
            if (order.getSendTimes() == order.getSentTimes()) {
                orderDao.updateOrderStatusByOrderNo(orderNo, OrderStatusEnum.COMPLETED.getIndex());
            }

            result = count > 0;
        }

        return result;
    }


    /**
     * <p>Description: 获取订单返回实体</p>
     * <p>param entity 订单参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月16日 下午12:56:25 </p>
     * <p>return UserOrderResponse</p>
     */
    private UserOrderResponse getUserOrderResponse(OrderEntity entity) {
        UserOrderResponse result = new UserOrderResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }


    /**
     * <p>Description: 获取订单实体</p>
     * <p>param entity 订单参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月13日 下午17:17:25 </p>
     * <p>return OrderEntity</p>
     */
    private OrderEntity getOrderEntity(OrderRequest entity) {
        OrderEntity result = new OrderEntity();

        BeanUtils.copyProperties(entity, result);

        /*
        result.setUserId(entity.getUserId());
        result.setUserName(entity.getUserName());
        result.setSalesId(entity.getSalesId());
        result.setSalesName(entity.getSalesName());

        result.setItemCount(entity.getItemCount());
        result.setOriginalPrice(entity.getOriginalPrice());
        result.setDiscountMoney(entity.getDiscountMoney());
        result.setTaxation(entity.getTaxation());
        result.setFreight(entity.getFreight());
        result.setReceiveMoney(entity.getReceiveMoney());

        result.setSource(entity.getSource());
        result.setStatus(entity.getStatus());

        result.setPaymentType(entity.getPaymentType());
        result.setSendTime(entity.getSendTime());
        result.setRemark(entity.getRemark());
        */

        // 订单创建时间
        result.setCreateTime(new Date());
        //生成订单编号
        String orderNum = OrderNumberUtil.getOrderNo();
        result.setOrderNo(orderNum);
        // 发货时间
        int newSendTime = OrderUtil.getSendTime(entity.getSendTime());
        result.setSendTime(newSendTime);

        return result;
    }

    /**
     * <p>Description: 获取订单详情实体</p>
     * <p>param entity 订单详情请求参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月13日 下午17:17:25 </p>
     * <p>return OrderDetailEntity</p>
     */
    private OrderDetailEntity getOrderDetailEntity(OrderDetailRequest entity, OrderEntity order) {
        OrderDetailEntity result = new OrderDetailEntity();
        BeanUtils.copyProperties(entity, result);

        result.setOrderId(order.getId());
        result.setOrderNo(order.getOrderNo());

        /*
        result.setPrice(entity.getPrice());
        result.setDiscountMoney(entity.getDiscountMoney());
        result.setTotalPrice(entity.getTotalPrice());
        result.setReceiveMoney(entity.getReceiveMoney());

        result.setCount(entity.getCount());
        result.setProductId(entity.getProductId());
        result.setProductCode(entity.getProductCode());
        result.setProductName(entity.getProductName());
        result.setProductType(entity.getProductType());
        */

        //查询商品，生成商品镜像
        // result.setProductContent();
        ProductEntity productEntity = productDao.getProductInfo(null, entity.getProductId());
        String pruductJson = JSONObject.toJSONString(productEntity);
        result.setProductContent(pruductJson);
        //ProductEntity p = JSONObject.parseObject(json, productEntity.getClass());

        return result;
    }

    /**
     * <p>Description: 获取订单收货人实体</p>
     * <p>param entity 订单收货人请求参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月14日 上午09:35:25 </p>
     * <p>return OrderRecevierEntity</p>
     */
    private OrderRecevierEntity getOrderRecevierEntity(OrderRecevierRequest entity, OrderEntity order) {

        OrderRecevierEntity result = new OrderRecevierEntity();

        BeanUtils.copyProperties(entity, result);

        //result.setReceiverName(entity.getReceiverName());
        //result.setAddress(entity.getAddress());
        //result.setMobile(entity.getMobile());
        //result.setPostcode(entity.getPostcode());

        result.setOrderId(order.getId());
        result.setOrderNo(order.getOrderNo());
        result.setCreateTime(new Date());

        return result;
    }


    /**
     * <p>Description: 获取订单实体</p>
     * <p>param entity 订单参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月15日 下午13:17:25 </p>
     * <p>return UserOrderDetailResponse</p>
     */
    private UserOrderDetailResponse getOrderResponse(OrderEntity entity) {
        UserOrderDetailResponse result = new UserOrderDetailResponse();

        BeanUtils.copyProperties(entity, result);

        /*
        result.setId(entity.getId());
        result.setOrderNo(entity.getOrderNo());
        result.setUserId(entity.getUserId());
        result.setUserName(entity.getUserName());
        result.setSalesId(entity.getSalesId());
        result.setSalesName(entity.getSalesName());
        result.setItemCount(entity.getItemCount());
        result.setOriginalPrice(entity.getOriginalPrice());
        result.setDiscountMoney(entity.getDiscountMoney());
        result.setTaxation(entity.getTaxation());
        result.setFreight(entity.getFreight());
        result.setReceiveMoney(entity.getReceiveMoney());

        result.setSource(entity.getSource());
        result.setStatus(entity.getStatus());

        result.setPaymentType(entity.getPaymentType());
        result.setPaymentSequence(entity.getPaymentSequence());
        result.setPaymentTime(entity.getPaymentTime());
        result.setCreateTime(entity.getCreateTime());
        result.setSendTime(entity.getSendTime());
        result.setRemark(entity.getRemark());
        */

        return result;
    }

    /**
     * <p>Description: 获取订单详情返回实体</p>
     * <p>param entity 订单详情参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月15日 下午14:17:25 </p>
     * <p>return OrderDetailResponse</p>
     */
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

    /**
     * <p>Description: 获取订单退款信息</p>
     * <p>param entity 订单退款参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年11月20日 下午15:57:25 </p>
     * <p>return OrderRefundResponse</p>
     */
    private OrderRefundResponse getOrderRefundResponse(OrderRefundEntity entity) {
        if (entity == null) {
            return null;
        }
        OrderRefundResponse result = new OrderRefundResponse();
        BeanUtils.copyProperties(entity, result);

        return result;
    }

    /**
     * <p>Description: 获取订单收货人返回实体</p>
     * <p>param entity 订单收货人参数实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017年09月15日 下午15:06:25 </p>
     * <p>return OrderRecevierResponse</p>
     */
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

    /**
     * <p>Description:会员详情-交易统计</p>
     * <p>param userid:</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/6 14:09</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    public TransactionStatisticsResponse getTransactionStatistics(int userid) {
        TransactionStatisticsResponse response = new TransactionStatisticsResponse();
        TransactionStatisticsEntity entity = orderDao.getTransactionStatistics(userid);
        if (entity != null)
            BeanUtils.copyProperties(entity, response);
        return response;
    }

    /**
     * <p>Description:根据用户查询各个状态订单的数量</p>
     * <p>param userid:</p>
     * <p>author:jiangkesen</p>
     * <p>date:2017/11/30 16:09</p>
     * <p>return: OrderStatusCountListResponse</p>
     * <p>throws: </p>
     */
    public OrderStatusCountListResponse getOrderStatusCountByUserId(int userId) throws ServiceException, Exception {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId less than 0");
        }
        List<OrderStatusCountEntity> statusCount = orderDao.getOrderStatusCountByUserId(userId);
        List<OrderStatusCountResponse> statusCountResponse = new ArrayList<OrderStatusCountResponse>();
        for (OrderStatusCountEntity item : statusCount) {
            statusCountResponse.add(this.getOrderStatusCountResponse(item));
        }
        OrderStatusCountListResponse result = new OrderStatusCountListResponse();
        result.etOrderStatusCountList(statusCountResponse);

        return result;
    }

    private OrderStatusCountResponse getOrderStatusCountResponse(OrderStatusCountEntity entity) {
        OrderStatusCountResponse result = new OrderStatusCountResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }
}
