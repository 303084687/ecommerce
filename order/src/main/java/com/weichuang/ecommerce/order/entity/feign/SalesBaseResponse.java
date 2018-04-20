package com.weichuang.ecommerce.order.entity.feign;

/**
 * Created by jiangks on 2017-11-14.
 */
public class SalesBaseResponse {
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
