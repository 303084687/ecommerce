package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: SalesWithdrawInfoEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:业务人员提现详情实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月26日 下午20:40:17</p>
 */
public class IncomeSetEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 参数id

    private int type;// 参数类型

    private Float salesPercent;// 业务提成百分比

    private Float agentPercent;// 代理提成百分比

    private int status;//状态：0--失效；1--正常

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Float getSalesPercent() {
        return salesPercent;
    }

    public void setSalesPercent(Float salesPercent) {
        this.salesPercent = salesPercent;
    }

    public Float getAgentPercent() {
        return agentPercent;
    }

    public void setAgentPercent(Float agentPercent) {
        this.agentPercent = agentPercent;
    }

    

}