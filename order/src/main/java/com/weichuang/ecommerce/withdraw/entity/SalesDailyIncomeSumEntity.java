package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: SalesDailyIncomeSumEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据业务人员的id查询某一时间段内每天的预计的可提成的汇总 </p>
 * <p>author jiangkesen</p>
 * <p>2017年11月23日 下午3:10:47</p>
 */
public class SalesDailyIncomeSumEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int salesId;// 业务人员id
    private String salesName;// 业务人员名称
    private BigDecimal receiveMoney;// 订单总金额，每日实付金额汇总
    private BigDecimal income;// 每日收入金额
    private String createDay;// 每天的日期

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

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }
}
