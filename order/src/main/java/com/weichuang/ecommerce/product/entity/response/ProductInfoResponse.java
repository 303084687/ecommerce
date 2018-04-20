package com.weichuang.ecommerce.product.entity.response;

import java.util.List;

import com.weichuang.ecommerce.product.entity.ProductEntity;
import com.weichuang.ecommerce.product.entity.ProductStockEntity;

/**
 * <p>ClassName: ProductInfoResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:后台查询详情0 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月14日 下午1:44:47</p>
 */
public class ProductInfoResponse {
    private ProductEntity entity;

    private List<ProductStockEntity> list;

    public List<ProductStockEntity> getList() {
        return list;
    }

    public void setList(List<ProductStockEntity> list) {
        this.list = list;
    }

    public ProductEntity getEntity() {
        return entity;
    }

    public void setEntity(ProductEntity entity) {
        this.entity = entity;
    }

}
