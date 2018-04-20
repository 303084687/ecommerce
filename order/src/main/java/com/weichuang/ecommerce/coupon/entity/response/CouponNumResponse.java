package com.weichuang.ecommerce.coupon.entity.response;

/**
 * <p>ClassName: CouponNumResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:用户可用和不可用优惠券数量 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月4日 上午9:23:33</p>
 */
public class CouponNumResponse {
    private int usableNum;// 可用的数量

    private int unUsableNum;// 不可用的数量

    public int getUsableNum() {
        return usableNum;
    }

    public void setUsableNum(int usableNum) {
        this.usableNum = usableNum;
    }

    public int getUnUsableNum() {
        return unUsableNum;
    }

    public void setUnUsableNum(int unUsableNum) {
        this.unUsableNum = unUsableNum;
    }

}
