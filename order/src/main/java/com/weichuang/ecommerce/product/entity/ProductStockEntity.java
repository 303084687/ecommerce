package com.weichuang.ecommerce.product.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: ProductStockEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品对用的库存表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月13日 下午2:25:45</p>
 */
public class ProductStockEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    // 自增主键
    private int id;

    // 对应t_product表中的id主键
    private int productId;

    // 对应单品的code
    private String productCode;

    // 对应单品的商品名
    private String productName;

    // 对应t_product中的单品id
    private int goodId;

    // 对应t_product中的code
    private String goodCode;

    // 对用sku商品的名称
    private String goodName;

    // 对应单品的规格
    private String goodNorms;

    // 最小单位
    private String unit;

    // 对应单品的发货次数
    private String goodMonth;

    // 创建时间
    private Date createTime;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodNorms() {
        return goodNorms;
    }

    public void setGoodNorms(String goodNorms) {
        this.goodNorms = goodNorms;
    }

    public String getGoodMonth() {
        return goodMonth;
    }

    public void setGoodMonth(String goodMonth) {
        this.goodMonth = goodMonth;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
