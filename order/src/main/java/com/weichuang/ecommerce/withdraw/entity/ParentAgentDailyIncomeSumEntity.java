package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>ClassName: AgentDailyIncomeSumEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据公司id查询某一时间段内每天的预计的可提成的汇总 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年11月29日 下午3:10:47</p>
 */
public class ParentAgentDailyIncomeSumEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int agentId;// 公司id
    private String agentName;// 公司名称
    private BigDecimal receiveMoney;// 订单总金额，每日实付金额汇总
    private BigDecimal income;// 每日收入金额
    private String createDay;// 每天的日期

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
