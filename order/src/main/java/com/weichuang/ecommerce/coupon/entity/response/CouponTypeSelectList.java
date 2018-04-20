package com.weichuang.ecommerce.coupon.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: CouponTypeSelectList.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:生成优惠券时候下拉列表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午3:10:11</p>
 */
public class CouponTypeSelectList implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private String typeCode;

    private int category;// 1:满减2:活动券3:体验券

    private String title;// 优惠券名称

    private BigDecimal discountMoney;// 优惠金额

    private int timeType;// 1：固定时间，2：动态时间

    private Date startTime;// 限定优惠券使用的开始时间

    private Date expireTime;// 限定优惠券使用的结束时间

    private Integer day;// 动态时间指定的天数,timeType=2的时有效

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

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

}
