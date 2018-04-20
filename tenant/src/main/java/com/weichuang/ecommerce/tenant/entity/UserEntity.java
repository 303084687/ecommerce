package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;

/**
 * <p>ClassName: UserEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月19日 下午19:12:17</p>
 */
public class UserEntity {
    //主键自增长
    private int id;
    //用户名以邮箱为唯一标示
    private String userName;
    //用户密码
    private String password;
    //用户昵称
    private String nickName;
    //真实姓名
    private String realName;
    //性别    1-男    2-女
    private int gender;
    //头像地址
    private String iconUrl;
    //绑定手机号
    private String mobile;
    //绑定的邮箱
    private String email;
    //创建时间
    private Date createTime;
    //最后登陆时间
    private Date loginTime;
    //是否关注
    private int isSubscribe;

    
    //状态0：冻结 1：有效;2:逻辑移除
    private int loginStatus;
    //开始时间
    private String startTime;
    //结束时间
    private String endTime;
    //备注信息
    private String descripe;

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }


    public int getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(int isSubscribe) {
        this.isSubscribe = isSubscribe;
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

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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

    
}