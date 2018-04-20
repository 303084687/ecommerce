package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: AgentIncomeEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:代理商预计收入表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午3:10:47</p>
 */
public class AgentIncomeEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 主键id
    private int orderId;// 订单id
    private String orderNo;// 订单编号
    private int userId;// 用户id
    private String userName;// 用户名
    private int salesId;// 业务人员id
    private String salesName;// 业务人员名称
    private int agentId;// 代理商id
    private String agentName;// 代理商名称
    private BigDecimal originalPrice;// 商品总金额
    private BigDecimal discountMoney;// 优惠金额
    private BigDecimal taxation;// 税费
    private BigDecimal freight;// 运费
    private BigDecimal receiveMoney;// 订单总金额，实付金额
    private Float percent;// 提成百分比
    private BigDecimal income;// 收入金额(实付金额*提成百分比)
    private Date createTime;// 创建时间
    private int withdraw;// 是否提现1-待提现 0-未提现 2-已提现
    private Date withdrawTime;// 提现时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

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

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public Float getPercent() {
        return percent;
    }

    public void setPercent(Float percent) {
        this.percent = percent;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Date getCreatetime() {
        return createTime;
    }

    public void setCreatetime(Date createtime) {
        this.createTime = createtime;
    }

    public int getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(int withdraw) {
        this.withdraw = withdraw;
    }

    public Date getWithdraw_time() {
        return withdrawTime;
    }

    public void setWithdraw_time(Date withdrawTime) {
        this.withdrawTime = withdrawTime;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
