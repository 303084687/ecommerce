package com.weichuang.ecommerce.order.entity.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: UserOrderResponse 用于用户中心用户订单,订单列表</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户订单列表</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/09/15 14:11 </p>
 */
public class UserOrderResponse {

    private int id;//订单id
    private String orderNo;//订单编号
    private int userId;//用户Id
    private String userName;//用户名
    private int itemCount;//商品数
    private BigDecimal originalPrice;//商品总金额
    private String couponCode;//优惠卷编码
    private BigDecimal discountMoney;//优惠金额
    private BigDecimal taxation;//税费
    private BigDecimal freight;//运费
    private BigDecimal receiveMoney;//订单总金额，实付金额
    private int source;//订单来源（0：预售，1：购买）
    private int status;//订单状态	1-	待付款	2-	待发货	3-	待收货	4-	已完成	5-	已关闭	6-	退款中
    private Date createTime;//订单创建时间
    private String express;//快递公司名称，现在默认都是顺丰
    private int sendTime; //要求每个月发货时间1到31，若送货的月份没有29、30、31日，则在当月最后一天送货

    private List<OrderDetailResponse> orderDetails;//订单详情
    private int needReceive;// 是否需要确认收货,1表示需要确认收货，0表示不需要确认收货

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

    //要求每个月发货时间1到31，若送货的月份没有29、30、31日，则在当月最后一天送货
    public int getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public List<OrderDetailResponse> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetailResponse> orderDetails) {
        this.orderDetails = orderDetails;
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


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public int getNeedReceive() {
        return needReceive;
    }

    public void setNeedReceive(int needReceive) {
        this.needReceive = needReceive;
    }
}


