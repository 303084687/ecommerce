package com.weichuang.ecommerce.coupon.entity.response;

import com.weichuang.ecommerce.coupon.entity.Coupon;

/**
 * <p>ClassName: CouponResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券详情返回 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午3:35:11</p>
 */
public class CouponResponse {
    private Coupon coupon;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}
