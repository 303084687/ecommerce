package com.weichuang.ecommerce.product.entity.response;

import java.util.List;

import com.weichuang.ecommerce.product.entity.ProductEntity;

public class ProductListResponse {
    private List<ProductEntity> list;

    private int pages;

    private Long total;// 返回的总条数

    public List<ProductEntity> getList() {
        return list;
    }

    public void setList(List<ProductEntity> list) {
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
