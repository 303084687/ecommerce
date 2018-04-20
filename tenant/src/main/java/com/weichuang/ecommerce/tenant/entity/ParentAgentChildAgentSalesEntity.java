package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: CompanyAgentSalesEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:公司实体(含店面信息，员工信息) </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月1日 下午14:12:17</p>
 */
public class ParentAgentChildAgentSalesEntity extends AgentEntity {
	
	private List<ChildAgentEntity> childAgentList;

    public List<ChildAgentEntity> getChildAgentList() {
        return childAgentList;
    }

    public void setChildAgentList(List<ChildAgentEntity> childAgentList) {
        this.childAgentList = childAgentList;
    } 
    

    
	
}
