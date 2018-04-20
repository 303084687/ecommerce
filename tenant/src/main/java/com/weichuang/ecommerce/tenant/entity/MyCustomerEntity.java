package com.weichuang.ecommerce.tenant.entity;
/**
* <p>ClassName:MyCustomerEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/22 16:03</p>
**/
public class MyCustomerEntity {
    private int dayInviteNum;//当天发送邀请个数
    private int dayCouponNum;//当天体验券领取个数
    private int dayCustomerNum;//当天的客户数
    private int allInviteNum;//总计发送邀请个数
    private int allCouponNum;//总计体验券领取个数
    private int allCustomerNum;//总计客户数

    public int getDayInviteNum() {
        return dayInviteNum;
    }

    public void setDayInviteNum(int dayInviteNum) {
        this.dayInviteNum = dayInviteNum;
    }

    public int getDayCouponNum() {
        return dayCouponNum;
    }

    public void setDayCouponNum(int dayCouponNum) {
        this.dayCouponNum = dayCouponNum;
    }

    public int getDayCustomerNum() {
        return dayCustomerNum;
    }

    public void setDayCustomerNum(int dayCustomerNum) {
        this.dayCustomerNum = dayCustomerNum;
    }

    public int getAllInviteNum() {
        return allInviteNum;
    }

    public void setAllInviteNum(int allInviteNum) {
        this.allInviteNum = allInviteNum;
    }

    public int getAllCouponNum() {
        return allCouponNum;
    }

    public void setAllCouponNum(int allCouponNum) {
        this.allCouponNum = allCouponNum;
    }

    public int getAllCustomerNum() {
        return allCustomerNum;
    }

    public void setAllCustomerNum(int allCustomerNum) {
        this.allCustomerNum = allCustomerNum;
    }
}
