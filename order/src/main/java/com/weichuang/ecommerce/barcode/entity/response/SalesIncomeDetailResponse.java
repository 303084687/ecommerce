package com.weichuang.ecommerce.barcode.entity.response;

import com.weichuang.ecommerce.barcode.entity.SalesIncomeDetailEntity;

import java.util.LinkedHashMap;
import java.util.List;

public class SalesIncomeDetailResponse {
    private LinkedHashMap<String,List<SalesIncomeDetailEntity>> linkedMap;

    public LinkedHashMap<String, List<SalesIncomeDetailEntity>> getLinkedMap() {
        return linkedMap;
    }

    public void setLinkedMap(LinkedHashMap<String, List<SalesIncomeDetailEntity>> linkedMap) {
        this.linkedMap = linkedMap;
    }
}
