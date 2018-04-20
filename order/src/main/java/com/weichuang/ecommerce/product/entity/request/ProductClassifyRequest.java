package com.weichuang.ecommerce.product.entity.request;

import java.io.Serializable;

/**
 * <p>ClassName: ProductClassify.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 商品类型实体类</p>
 * <p>author wanggongliang</p>
 * <p>2018年1月9日 下午3:07:10</p>
 */
public class ProductClassifyRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 自增主键

    private String title;// 分类的名称

    private String imageUrl;// 分类的图片地址

    private int status;// 1正常2作废

    private int sort;// 排序最大的放在最前面

    private String description;// 说明

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
