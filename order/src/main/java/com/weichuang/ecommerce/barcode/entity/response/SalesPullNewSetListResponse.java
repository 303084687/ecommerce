package com.weichuang.ecommerce.barcode.entity.response;


import java.util.List;

import com.weichuang.ecommerce.barcode.entity.SalesPullNewSetEntity;



public class SalesPullNewSetListResponse {
   
   
    private List<SalesPullNewSetEntity> list;

    public List<SalesPullNewSetEntity> getList() {
        return list;
    }

    public void setList(List<SalesPullNewSetEntity> list) {
        this.list = list;
    }
    
}
