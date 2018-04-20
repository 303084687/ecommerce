package com.weichuang.ecommerce.coupon.feign;

/**
 * <p>ClassName: UserEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月19日 下午19:12:17</p>
 */
public class UserResponse {
    // 主键自增长
    private int id;

    // 用户名以邮箱为唯一标示
    private String userName;

    // 用户密码
    private String password;

    // 用户昵称
    private String nickName;

    // 真实姓名
    private String realName;

    // 性别 1-男 2-女
    private int gender;

    // 头像地址
    private String iconUrl;

    // 绑定手机号
    private String mobile;

    // 绑定的邮箱
    private String email;

    // 是否订阅0：否 1：是
    private int isSubscribe;

    public int getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(int isSubscribe) {
        this.isSubscribe = isSubscribe;
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
}
