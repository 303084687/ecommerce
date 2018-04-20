package com.weichuang.ecommerce.coupon.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: CouponType.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券类型表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月19日 下午6:25:47</p>
 */
public class CouponType implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 类型编号,主键自增长

    private String typeCode;// 批次号,创建不可修改

    private int category;// 1:满减2:活动券3:体验券

    private String title;// 优惠券名称

    private BigDecimal orderMin;// 最低订单金额

    private String platLimit;// 限制使用平台,1商城2渠道

    private BigDecimal discountMoney;// 优惠金额

    private int receiveCounts;// 总数量0：无限制，>0为最多可领取的张数

    private int receiveSurplus;// 剩余数量每领取一次，更新一次，counts为0，此字段无效

    private int personalCounts;// 每个人可领取的数量0：无限制，>0为每人最多可领取的张数

    private int timeType;// 1：固定时间，2：动态时间

    private Date startTime;// 限定优惠券使用的开始时间

    private Date expireTime;// 限定优惠券使用的结束时间

    private Integer day;// 动态时间指定的天数,timeType=2的时有效

    private Date createTime;// 创建时间

    private String creator;// 创建者

    private int productLimit;// 1：全部商品，2：指定商品

    private String productId;// 限制使用的商品主键多个用,隔开。只有product_limit为2的时候才有用

    private String platLimitId;// 限制使用渠道多个用,隔开，只有plat_limit为2才有用

    private int status;// 状态（1：正常 2：作废）

    private String description;// 使用说明，可以被界面直接调用

    public String getPlatLimitId() {
        return platLimitId;
    }

    public void setPlatLimitId(String platLimitId) {
        this.platLimitId = platLimitId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getReceiveCounts() {
        return receiveCounts;
    }

    public void setReceiveCounts(int receiveCounts) {
        this.receiveCounts = receiveCounts;
    }

    public int getReceiveSurplus() {
        return receiveSurplus;
    }

    public void setReceiveSurplus(int receiveSurplus) {
        this.receiveSurplus = receiveSurplus;
    }

    public int getPersonalCounts() {
        return personalCounts;
    }

    public void setPersonalCounts(int personalCounts) {
        this.personalCounts = personalCounts;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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

}
