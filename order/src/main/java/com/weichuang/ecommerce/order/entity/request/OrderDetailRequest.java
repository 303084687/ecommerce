package com.weichuang.ecommerce.order.entity.request;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>ClassName: OrderDetailRequest</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 订单详情数据请求</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/09/17 16:45 </p>
 */
public class OrderDetailRequest {
    private BigDecimal price;//商品单价
    private BigDecimal discountMoney;//优惠金额
    private BigDecimal totalPrice;//销商品总价
    private BigDecimal receiveMoney;//实收金额
    private int count;//购买数量
    private int productId;//商品id
    private String productCode;//商品编码
    private String productName;//商品名称

    //商品单价
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    //优惠金额
    public BigDecimal getDiscountMoney() {
        return this.discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    //销商品总价
    public BigDecimal getTotalPrice() {
        return this.totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    //实收金额
    public BigDecimal getReceiveMoney() {
        return this.receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    //购买数量
    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    //商品id
    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    //商品编码
    public String getProductCode() {
        return this.productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    //商品名称
    public String getProductName() {
        return this.productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
