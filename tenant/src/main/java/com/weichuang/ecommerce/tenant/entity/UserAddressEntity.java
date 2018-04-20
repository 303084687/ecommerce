package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;

public class UserAddressEntity {
    private int id;

    // 用户主键
    private int userId;

    // 收货人姓名
    private String receiveName;

    // 电话 加密
    private String mobile;

    // 城市外键
    private int cityId;

    // 所在省份外键
    private int provinceId;

    // 所在县/区外键
    private int conutyId;

    // 详细地址 加密
    private String detail;

    // 是否默认：0-否(默认)；1-是
    private Integer isDefault;

    // 创建时间
    private Date createTime;

    // 加密参数
    private String keyCode;

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
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

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getConutyId() {
        return conutyId;
    }

    public void setConutyId(int conutyId) {
        this.conutyId = conutyId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}