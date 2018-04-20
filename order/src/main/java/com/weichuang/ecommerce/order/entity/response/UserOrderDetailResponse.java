package com.weichuang.ecommerce.order.entity.response;

import com.weichuang.ecommerce.order.entity.request.OrderDetailRequest;
import com.weichuang.ecommerce.order.entity.request.OrderRecevierRequest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: UserOrderDetailResponse 用于查询订单详情</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 订单数据返回对象</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/09/13 14:11 </p>
 */
public class UserOrderDetailResponse {
    private int id;//订单id
    private String orderNo;//订单编号
    private int userId;//用户Id
    private String userName;//用户名
    private int salesId;//销售人员Id(销售人员id)
    private String salesName;//销售人员名称
    private int itemCount;//商品数
    private BigDecimal originalPrice;//商品总金额
    private String couponCode;//优惠卷编码
    private BigDecimal discountMoney;//优惠金额
    private BigDecimal taxation;//税费
    private BigDecimal freight;//运费
    private BigDecimal receiveMoney;//订单总金额，实付金额
    private int source;//订单来源（0：预售，1：购买）
    private int status;//订单状态	1-	待付款	2-	待发货	3-	待收货	4-	已完成	5-	已关闭	6-	退款中
    private int paymentType;//商品数支付方式：	1-支付宝	2-微信	3-信用卡
    private String paymentSequence;//支付流水号
    private Date paymentTime;//支付时间
    private Date createTime;//订单创建时间
    private String express;//快递公司名称，现在默认都是顺丰
    private int sendTime; //要求每个月发货时间1到31，若送货的月份没有29、30、31日，则在当月最后一天送货
    private int sendTimes; //需要发货次数
    private int sentTimes; //已经发货次数
    private Date endTime;//订单结束时间,订单关闭或订单交易完成
    private String remark;//买家留言
    private List<OrderDetailResponse> orderDetails;//订单详情
    private OrderRecevierResponse orderRecevier;//订单收货详情（创建订单的情况下默认一个收货人地址）
    private List<OrderSentResponse> orderSents;//订单发货列表
    private OrderRefundResponse orderRefund;//订单退货信息


    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSalesId() {
        return this.salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return this.salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getOriginalPrice() {
        return this.originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getDiscountMoney() {
        return this.discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getTaxation() {
        return this.taxation;
    }

    public void setTaxation(BigDecimal taxation) {
        this.taxation = taxation;
    }

    public BigDecimal getFreight() {
        return this.freight;
    }

    public void getFreight(BigDecimal freight) {
        this.freight = freight;
    }

    //订单总金额，实付金额
    public BigDecimal getReceiveMoney() {
        return this.receiveMoney;
    }
    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    //订单来源（0：预售，1：购买）
    public int getSource() {
        return this.source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    //订单状态	1-	待付款	2-	待发货	3-	待收货	4-	已完成	5-	已关闭	6-	退款中
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //商品数支付方式：	1-支付宝	2-微信	3-信用卡
    public int getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    //支付流水号
    public String getPaymentSequence() {
        return this.paymentSequence;
    }

    public void setPaymentSequence(String paymentSequence) {
        this.paymentSequence = paymentSequence;
    }

    //要求每个月发货时间1到31，若送货的月份没有29、30、31日，则在当月最后一天送货
    public int getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public int getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(int sendTimes) {
        this.sendTimes = sendTimes;
    }

    public int getSentTimes() {
        return sentTimes;
    }

    public void setSentTimes(int sentTimes) {
        this.sentTimes = sentTimes;
    }


    //买家留言
    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailResponse> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public OrderRecevierResponse getOrderRecevier() {
        return orderRecevier;
    }

    public void setOrderRecevier(OrderRecevierResponse orderRecevier) {
        this.orderRecevier = orderRecevier;
    }

    public List<OrderSentResponse> getOrderSents() {
        return orderSents;
    }

    public void setOrderSents(List<OrderSentResponse> orderSents) {
        this.orderSents = orderSents;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public OrderRefundResponse getOrderRefund() {
        return orderRefund;
    }

    public void setOrderRefund(OrderRefundResponse orderRefund) {
        this.orderRefund = orderRefund;
    }
}


