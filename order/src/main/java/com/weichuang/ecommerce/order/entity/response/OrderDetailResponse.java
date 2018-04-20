package com.weichuang.ecommerce.order.entity.response;

import com.weichuang.ecommerce.product.entity.response.ProductResponse;

import java.math.BigDecimal;

/**
 * <p>ClassName: OrderDetailResponse 用于查询订单详情</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 订单详情数据返回对象</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/09/15 16:45 </p>
 */
public class OrderDetailResponse {
    private int id;//商品详情id
    private int orderId;//订单id
    private String orderNo;//订单编号
    private BigDecimal price;//商品单价
    private BigDecimal discountMoney;//优惠金额
    private BigDecimal totalPrice;//销商品总价
    private BigDecimal receiveMoney;//实收金额
    private int count;//购买数量
    private int productId;//商品id
    private String productCode;//商品编码
    private String productName;//商品名称
    private ProductResponse productResponse;//商品镜像

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //订单id
    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


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

    public ProductResponse getProductResponse() {
        return productResponse;
    }

    public void setProductResponse(ProductResponse productResponse) {
        this.productResponse = productResponse;
    }
}
