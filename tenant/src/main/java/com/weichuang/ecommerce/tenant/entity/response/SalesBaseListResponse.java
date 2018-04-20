package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.SalesBaseEntity;
import com.weichuang.ecommerce.tenant.entity.SalesEntity;

public class SalesBaseListResponse {
    private List<SalesBaseEntity> list;

    private int pages;

    private Long total;// 返回的总条数

    public List<SalesBaseEntity> getList() {
        return list;
    }

    public void setList(List<SalesBaseEntity> list) {
        this.list = list;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}
