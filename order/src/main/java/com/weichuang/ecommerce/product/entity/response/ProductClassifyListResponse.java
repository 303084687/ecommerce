package com.weichuang.ecommerce.product.entity.response;

import java.util.List;

import com.weichuang.ecommerce.product.entity.ProductClassifyEntity;

/**
 * <p>ClassName: ProductClassifyListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品分类返回集合 </p>
 * <p>author wanggongliang</p>
 * <p>2018年1月9日 下午4:13:46</p>
 */
public class ProductClassifyListResponse {
    // 返回的集合
    private List<ProductClassifyEntity> list;

    // 总的页数
    private int pages;

    // 总的记录数
    private Long Total;

    public List<ProductClassifyEntity> getList() {
        return list;
    }

    public void setList(List<ProductClassifyEntity> list) {
        this.list = list;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return Total;
    }

    public void setTotal(Long total) {
        Total = total;
    }

}
