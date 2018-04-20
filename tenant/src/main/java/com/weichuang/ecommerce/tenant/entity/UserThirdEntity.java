package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;

/**
 * <p>ClassName: UserThirdEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月19日 下午19:37:17</p>
 */
public class UserThirdEntity {
    //主键自增长
    private int id;
    //用户id
    private int userId;
    //第三方openid
    private String openId;
    private String unionId;//全局id
    private String appOpenId;//app端的openId
    //第三方登陆类型：	1-微信登陆	2-QQ登陆	3-支付宝登陆
    private int registerType;
    //创建时间
    private Date createTime;
    private int isSubscribe;

    public int getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(int isSubscribe) {
        this.isSubscribe = isSubscribe;
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getRegisterType() {
        return registerType;
    }

    public void setRegisterType(int registerType) {
        this.registerType = registerType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}