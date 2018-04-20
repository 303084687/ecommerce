package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.UserNewDetailEntity;

import java.util.List;

public class UserNewDetailResponse {
    private Long total;
    private int pages;
    private List<UserNewDetailEntity> list;

    public List<UserNewDetailEntity> getList() {
        return list;
    }

    public void setList(List<UserNewDetailEntity> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }


}
