package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.UserAddressTemplate;

public class UserAddressEntityResponse {
    private UserAddressTemplate entity;

    public UserAddressTemplate getEntity() {
        return entity;
    }

    public void setEntity(UserAddressTemplate entity) {
        this.entity = entity;
    }
}
