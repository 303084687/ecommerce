package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: CompanyEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:公司实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月1日 下午14:12:17</p>
 */
public class ParentAgentEntity extends AgentEntity {
	private int agentCount;
	private List<AgentEntity> agentList;

    public List<AgentEntity> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<AgentEntity> agentList) {
        this.agentList = agentList;
    }

    public int getAgentCount() {
        return agentCount;
    }

    public void setAgentCount(int agentCount) {
        this.agentCount = agentCount;
    } 
    

    
	
}
