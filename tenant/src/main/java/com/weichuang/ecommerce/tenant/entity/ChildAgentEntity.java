package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: ShopEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:店面实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月7日 下午14:12:17</p>
 */
public class ChildAgentEntity extends AgentEntity {
    private String parentName;
    private int salesCount;
	
	private List<UserEntity> userList;

    public List<UserEntity> getUserList() {
        return userList;
    }

    public void setUserList(List<UserEntity> userList) {
        this.userList = userList;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(int salesCount) {
        this.salesCount = salesCount;
    } 
    
}
