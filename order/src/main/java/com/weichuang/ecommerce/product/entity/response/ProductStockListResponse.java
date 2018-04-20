package com.weichuang.ecommerce.product.entity.response;

import java.util.List;

import com.weichuang.ecommerce.product.entity.ProductStockEntity;

/**
 * <p>ClassName: ProductStockListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品库存返回 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月13日 下午5:13:48</p>
 */
public class ProductStockListResponse {
    private List<ProductStockEntity> list;

    public List<ProductStockEntity> getList() {
        return list;
    }

    public void setList(List<ProductStockEntity> list) {
        this.list = list;
    }

}
