package com.weichuang.ecommerce.tenant.entity;



/**
 * <p>ClassName: SalesEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户详情实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月14日 上午11:11:17</p>
 */
public class SalesBaseEntity {

    // 员工id
    private int id;

    // 员工userName
    private String userName;

    // 门店id
    private int agentId;

    // 门店名称

    private String agentName;

    // 所属公司id

    private int parentAgentId;

    // 所属公司名称
    private String parentAgentName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public int getParentAgentId() {
        return parentAgentId;
    }

    public void setParentAgentId(int parentAgentId) {
        this.parentAgentId = parentAgentId;
    }

    public String getParentAgentName() {
        return parentAgentName;
    }

    public void setParentAgentName(String parentAgentName) {
        this.parentAgentName = parentAgentName;
    }

}