package com.weichuang.ecommerce.tenant.entity;

public class UserNewDetailEntity extends UserDetailEntity{
    private String referrerNamed;//客户类型

    public String getReferrerNamed() {
        return referrerNamed;
    }

    public void setReferrerNamed(String referrerNamed) {
        this.referrerNamed = referrerNamed;
    }
}
