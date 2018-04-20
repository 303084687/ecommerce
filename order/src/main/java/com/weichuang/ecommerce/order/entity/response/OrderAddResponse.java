package com.weichuang.ecommerce.order.entity.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: UserOrderDetailResponse 用于查询订单详情</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 订单数据返回对象</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/10/21 14:11 </p>
 */
public class OrderAddResponse {
    private int id;//订单id
    private String orderNo;//订单编号
    private int userId;//用户Id
    private String userName;//用户名
    private int salesId;//销售人员Id(销售人员id)
    private String salesName;//销售人员名称
    private int itemCount;//商品数
    private BigDecimal originalPrice;//商品总金额
    private BigDecimal discountMoney;//优惠金额
    private BigDecimal taxation;//税费
    private BigDecimal freight;//运费
    private BigDecimal receiveMoney;//订单总金额，实付金额
    private int paymentType;//商品数支付方式：	1-支付宝	2-微信	3-信用卡


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

    //商品数支付方式：	1-支付宝	2-微信	3-信用卡
    public int getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
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
}


