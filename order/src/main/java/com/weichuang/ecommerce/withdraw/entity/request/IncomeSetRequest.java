package com.weichuang.ecommerce.withdraw.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>ClassName: WithdrawEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:提成分红返回实体综合代理商和销售 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月26日 上午9:51:45</p>
 */
public class IncomeSetRequest implements Serializable {

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
