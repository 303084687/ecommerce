package com.weichuang.ecommerce.tenant.entity;

/**
 * <p>ClassName: UserAddressTemplate.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户地址返回实体 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月7日 上午11:04:25</p>
 */
public class UserAddressTemplate {
    private Long id;

    // 用户主键
    private Long userId;

    // 收货人姓名
    private String receiveName;

    // 电话 加密
    private String mobile;

    // 城市外键
    private Long cityId;

    // 所在省份外键
    private Long provinceId;

    // 所在县/区外键
    private Long conutyId;

    // 详细地址 加密
    private String detail;

    // 是否默认：0-否(默认)；1-是
    private Integer isDefault;

    private String provinceName;

    private String cityName;

    private String conutyName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getConutyId() {
        return conutyId;
    }

    public void setConutyId(Long conutyId) {
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getConutyName() {
        return conutyName;
    }

    public void setConutyName(String conutyName) {
        this.conutyName = conutyName;
    }

}