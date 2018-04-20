package com.weichuang.ecommerce.coupon.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: CouponExpire.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券过期提醒 </p>
 * <p>author wanggongliang</p>
 * <p>2017年12月20日 下午2:05:09</p>
 */
public class CouponExpire implements Serializable {
    private static final long serialVersionUID = 1L;

    private String openId;// 优惠券号

    private int diffDay;// 优惠券使用开始时间

    private Date expireTime;// 优惠券结束日期

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getDiffDay() {
        return diffDay;
    }

    public void setDiffDay(int diffDay) {
        this.diffDay = diffDay;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

}
