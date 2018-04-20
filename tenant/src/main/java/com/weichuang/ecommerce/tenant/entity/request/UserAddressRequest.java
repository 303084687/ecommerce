package com.weichuang.ecommerce.tenant.entity.request;

/**
 * <p>ClassName: UserAddressAddRequest</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 这里用一句话描述这个方法的作用</p>
 * <p>author: licheng</p>
 * <p>date: 2016/10/20 15:45 </p>
 */
public class UserAddressRequest {
    private int id;

    private int userId;

    private String receiveName;

    private String mobile;

    private int cityId;

    private int provinceId;

    private int conutyId;

    private String detail;

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

}
