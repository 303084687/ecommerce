package com.weichuang.ecommerce.tenant.entity.request;

import java.util.Date;

/**
 * <p>ClassName: UserEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月22日 下午14:12:17</p>
 */
public class AgentRequest {
    //主键自增长
    private int id;
    //代理名称
    private String agentName;
    //代理编号
    private String agentCode;
    //地址
    private String address;
    //父级id
    private int parentId;
    //法人
    private String corporation;
    //合作状态
    private int status;
    //操作人id
    private int operator;
    //操作人姓名
    private String operatorName;
    //更新时间
    private String operateTime;



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

    public String getOperatorTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }

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

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

   
}
