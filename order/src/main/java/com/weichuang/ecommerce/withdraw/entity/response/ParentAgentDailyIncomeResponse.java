package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>ClassName: ParentAgentDailyIncomeResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据公司的id查询某一时间段内每天的预计的可提成详情 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年11月29日 下午2:14:47</p>
 */
public class ParentAgentDailyIncomeResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int agentId;// 门店id
    private String agentName;// 门店名称
    private BigDecimal receiveMoney;// 订单总金额
    private BigDecimal income;// 收入总金额


    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
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

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
}

