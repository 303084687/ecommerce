package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: ShortProductResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:短商品描述 </p>
 * <p>author jiangkesen</p>
 * <p>2017年11月26日 下午3:10:47</p>
 */
public class ShortProductResponse implements Serializable {
    private int productId;// 商品id
    private String productName;// 商品名称

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


}
