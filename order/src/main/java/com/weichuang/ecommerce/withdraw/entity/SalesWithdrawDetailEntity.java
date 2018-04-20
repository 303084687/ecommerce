package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: SalesWithdrawDetailEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:业务人员提现详情实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月18日 下午16:011:17</p>
 */
public class SalesWithdrawDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 主键自增长

    private int withdrawId;// t_withdraw表提现信息表id

    private int incomeId;// t_sales_income表提现信息表id

    private int userId;// 用户Id

    private String userName;// 用户名

    private int orderId;// 订单id

    private String orderNo;// 订单编号

    private BigDecimal receiveMoney;// 订单总金额，实付金额

    private float percent;// 购买数量

    private BigDecimal income;// 商品id

    private Date createTime;// 商品编码

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