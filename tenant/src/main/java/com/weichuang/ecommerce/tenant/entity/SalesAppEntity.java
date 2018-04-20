package com.weichuang.ecommerce.tenant.entity;
/**
* <p>ClassName:SalesAppEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/27 15:24</p>
**/
public class SalesAppEntity extends SalesEntity {
    private int inviteDayNum;

    public int getInviteDayNum() {
        return inviteDayNum;
    }

    public void setInviteDayNum(int inviteDayNum) {
        this.inviteDayNum = inviteDayNum;
    }

    public int getCouponDayNum() {
        return couponDayNum;
    }

    public void setCouponDayNum(int couponDayNum) {
        this.couponDayNum = couponDayNum;
    }

    private int couponDayNum;

}
