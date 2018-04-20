package com.weichuang.ecommerce.tenant.entity.request;

import java.math.BigDecimal;

/**
 * <p>ClassName: CompanyEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:公司请求表 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年11月3日 上午8:40:46</p>
 */
public class ParentAgentRequest {
	 //主键自增长
    private int id;
    //代理名称
    private String agentName;
    //地址
    private String address;
    //父级id
    private int parentId;
    //法人
    private String corporation;
    //合作状态
    private int status;
    //添加时间
    private String createTime;
    //操作人
    private int operator;
    //操作人姓名
    private String operatorName;
    //操作时间
    private String operateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getOperator() {
        return operator;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

	public String getCorporation() {
		return corporation;
	}

	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
