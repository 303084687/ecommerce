package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;

/**
 * <p>ClassName: UserAgentRoleEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月21日 下午17:12:17</p>
 */
public class UserAgentRoleEntity {
    //用户Id
    private int userId;
    //用户名
    private String userName;
    //代理id（用户所属代理商，这里是用户所属的键身房）
    private int agentId;
    //代理名称(用户所属代理商名称，目前是健身房的名称)
    private String agentName;
    //角色Id
    private int roleId;
    //角色名称
    private String roleName;
    //用户所属的业务人员id(只有角色为普通用户时，才有用户所属的业务人员id，如果是业务人员角色此值为0)
    private int salesId;
    //用户所属的业务人员名称，同sales_id
    private String salesName;
    //推荐人id（只有c推c用户的情况下才会产生此值）
    private int referrerId;
    //推荐人名称
    private String referrerName;
    //绑定的邮箱
    private Date createTime;
    //后台操作人id
    private int operator;
    //后台操作人员名字
    private String operatorName;
    // 最后一次操作时间，第一次录入与创建时间相同
    private Date operateTime;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public int getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(int referrerId) {
        this.referrerId = referrerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
}