package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: AgentWithdrawDetailEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:公司管理员提现详情实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年12月28日 下午17:50:17</p>
 */
public class AgentWithdrawDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 主键自增长

    private int withdrawId;// t_withdraw表提现信息表id

    private int incomeId;// t_sales_income表提现信息表id

    private int userId;// 用户Id

    private String userName;// 用户名

    private int orderId;// 订单id

    private String orderNo;// 订单编号

    private BigDecimal originalPrice;// 商品总金额

    private BigDecimal discountMoney;// 优惠金额

    private BigDecimal taxation;// 税费

    private BigDecimal freight;// 运费

    private BigDecimal receiveMoney;// 订单总金额，实付金额

    private float percent;// 提成百分比

    private BigDecimal income;// 提成

    private Date createTime;// 提现时间

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public BigDecimal getTaxation() {
        return taxation;
    }

    public void setTaxation(BigDecimal taxation) {
        this.taxation = taxation;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    // 主键自增长
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWithdrawId() {
        return withdrawId;
    }

    public void setWithdrawId(int withdrawId) {
        this.withdrawId = withdrawId;
    }

    // 订单id
    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    // 实收金额
    public BigDecimal getReceiveMoney() {
        return this.receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}