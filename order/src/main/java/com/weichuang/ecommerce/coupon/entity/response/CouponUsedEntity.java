package com.weichuang.ecommerce.coupon.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: CouponUsedEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:用户可用不可用列表返回entity </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月23日 下午5:02:07</p>
 */
public class CouponUsedEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String couponCode;// 优惠券号

    private String couponPass;// 优惠券密码

    private int isUsed;// 1:未使用,2:已使用

    private Date startTime;// 优惠券使用开始时间

    private Date expireTime;// 优惠券结束日期

    private Date usedTime;// 使用时间

    private String typeCode;// 批次号,创建不可修改

    private int category;// 1:立减 2：满减

    private int saleId;// 销售人员主键

    private String title;// 优惠券名称

    private BigDecimal orderMin;// 最低订单金额

    private String platLimit;// 限制使用平台,1商城2渠道

    private BigDecimal discountMoney;// 优惠金额

    private int productLimit;// 1：全部商品，2：指定商品

    private String productId;// 限制使用的商品主键多个用,隔开。只有product_limit为2的时候才有用

    private int status;// 状态（1：正常 2：作废）

    // 体验区用户信息
    private String mobile;

    // 健身房名称
    private String agentName;

    // 真实姓名
    private String realName;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    private String description;// 使用说明，可以被界面直接调用

    private int usedType;

    public int getUsedType() {
        return usedType;
    }

    public void setUsedType(int usedType) {
        this.usedType = usedType;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponPass() {
        return couponPass;
    }

    public void setCouponPass(String couponPass) {
        this.couponPass = couponPass;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getOrderMin() {
        return orderMin;
    }

    public void setOrderMin(BigDecimal orderMin) {
        this.orderMin = orderMin;
    }

    public String getPlatLimit() {
        return platLimit;
    }

    public void setPlatLimit(String platLimit) {
        this.platLimit = platLimit;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public int getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(int productLimit) {
        this.productLimit = productLimit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
