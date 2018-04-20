package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;

public class TenantCodeStoreEntity {
    private Integer bid;

    private String codeKey;

    private Integer codeType;

    private Integer actionType;

    private Integer userAgentId;

    private String storeData;

    private String addressUrl;

    private Integer state;

    private Date ctime;

    private String otherData;

    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public String getAddressUrl() {
        return addressUrl;
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }

    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }
    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey == null ? null : codeKey.trim();
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(Integer userAgentId) {
        this.userAgentId = userAgentId;
    }

    public String getStoreData() {
        return storeData;
    }

    public void setStoreData(String storeData) {
        this.storeData = storeData == null ? null : storeData.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}