package com.weichuang.ecommerce.product.entity.response;

import com.weichuang.ecommerce.product.entity.ProductClassifyEntity;

/**
 * <p>ClassName: ProductClassifyInfoResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品类型返回类 </p>
 * <p>author wanggongliang</p>
 * <p>2018年1月9日 下午4:15:20</p>
 */
public class ProductClassifyInfoResponse {
    private ProductClassifyEntity classify;

    public ProductClassifyEntity getClassify() {
        return classify;
    }

    public void setClassify(ProductClassifyEntity classify) {
        this.classify = classify;
    }

}
