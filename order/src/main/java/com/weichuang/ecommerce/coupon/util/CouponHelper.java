package com.weichuang.ecommerce.coupon.util;

import java.util.UUID;

/**
 * <p>ClassName: CouponHelper.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 优惠券类型生成</p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午4:38:10</p>
 */
public class CouponHelper {
    // 优惠券uuid 8位
    public static String createUUID() {
        UUID uuid = UUID.randomUUID();
        String a = uuid.toString().toUpperCase();
        return a.substring(0, 8);
    }

}
