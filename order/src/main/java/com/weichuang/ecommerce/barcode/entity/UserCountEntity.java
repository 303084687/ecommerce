package com.weichuang.ecommerce.barcode.entity;
/**
* <p>ClassName:UserCountEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/22 14:59</p>
**/
public class UserCountEntity {
    private Integer allCount;//总客户数

    private Integer nowCount;//当天客户数

    private Integer nowCouponNum;//当天体验券领取

    private Integer allCouponNum;//历史体验券领取

    public Integer getAllCount() {
        return allCount;
    }

    public void setAllCount(Integer allCount) {
        this.allCount = allCount;
    }

    public Integer getNowCount() {
        return nowCount;
    }

    public void setNowCount(Integer nowCount) {
        this.nowCount = nowCount;
    }

    public Integer getNowCouponNum() {
        return nowCouponNum;
    }

    public void setNowCouponNum(Integer nowCouponNum) {
        this.nowCouponNum = nowCouponNum;
    }

    public Integer getAllCouponNum() {
        return allCouponNum;
    }

    public void setAllCouponNum(Integer allCouponNum) {
        this.allCouponNum = allCouponNum;
    }
}
