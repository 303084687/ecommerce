package com.weichuang.ecommerce.order.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionStatisticsEntity {
    private BigDecimal customerPrice;//客单价
    private BigDecimal consumerPrice;//累计消费金额
    private int consumerOrderNum;//累计消费订单数
    private BigDecimal refundAmount;//累计退款金额
    private int refundOrderNumber;//退款订单个数
    private Date createTime;//最近下单时间

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(BigDecimal customerPrice) {
        this.customerPrice = customerPrice;
    }

    public BigDecimal getConsumerPrice() {
        return consumerPrice;
    }

    public void setConsumerPrice(BigDecimal consumerPrice) {
        this.consumerPrice = consumerPrice;
    }

    public int getConsumerOrderNum() {
        return consumerOrderNum;
    }

    public void setConsumerOrderNum(int consumerOrderNum) {
        this.consumerOrderNum = consumerOrderNum;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public int getRefundOrderNumber() {
        return refundOrderNumber;
    }

    public void setRefundOrderNumber(int refundOrderNumber) {
        this.refundOrderNumber = refundOrderNumber;
    }
}
