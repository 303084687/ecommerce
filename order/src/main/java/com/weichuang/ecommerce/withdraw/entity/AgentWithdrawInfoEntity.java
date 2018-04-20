package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>ClassName: AgentWithdrawInfoEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:业务人员提现详情实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月28日 下午14:16:17</p>
 */
public class AgentWithdrawInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int agentId;// 公司id



    private String createTime;// 提现日期

    private BigDecimal withdraw;// 提现总金额

    private int status;// 1待提现2已提现3

    private String nickName;//昵称

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}