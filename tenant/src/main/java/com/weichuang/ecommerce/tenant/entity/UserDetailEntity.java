package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;

/**
 * <p>ClassName: UserDetailEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户详情实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月22日 上午11:11:17</p>
 */
public class UserDetailEntity {
    //用户id
    private int id;
    //用户名
    private String userName;
    //密码
    private String password;
    //用户昵称
    private String nickName;
    //真实姓名
    private String realName;
    //性别
    private int gender;
    //头像地址
    private String iconUrl;
    //绑定的手机或注册手机号
    private String mobile;
    //绑定的邮箱
    private String email;
    //用户注册时间
    private Date createTime;
    // 最后登陆时间
    private Integer loginTime;
    //代理商id，目前是指用户或销售人员所在的健身，或理解成哪个健身房拉入的用户
    private int agentId;
    //健身房名称
    private String agentName;
    //用户角色id
    private int roleId;
    //用户角色名称
    private String roleName;
    //销售人员id，健身房的人员无此值，普通用户有此值，表示所属的业务人员
    private int salesId;
    //销售人员名称
    private String salesName;
    //推荐人id（只有c推c用户的情况下才会产生此值）
    private int referrerId;
    //推荐人名称
    private String referrerName;
    //第三方openid
    private String openId;
    //全局id
    private String unionId;
    //app端openId
    private String appOpenId;

    //第三方登陆类型：	1-微信登陆	2-QQ登陆	3-支付宝登陆
    private int registerType;
  //状态0：冻结 1：有效;2:逻辑移除
    private int loginStatus;

    private int receiverAccountid;

    public int getReceiverAccountid() {
        return receiverAccountid;
    }

    public void setReceiverAccountid(int receiverAccountid) {
        this.receiverAccountid = receiverAccountid;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getAppOpenId() {
        return appOpenId;
    }

    public void setAppOpenId(String appOpenId) {
        this.appOpenId = appOpenId;
    }
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Integer loginTime) {
        this.loginTime = loginTime;
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

    public int getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(int referrerId) {
        this.referrerId = referrerId;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }
}