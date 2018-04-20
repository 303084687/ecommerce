package com.weichuang.ecommerce.coupon.thread;

import java.util.List;

import com.weichuang.ecommerce.coupon.entity.Coupon;
import com.weichuang.ecommerce.coupon.responsitory.ICouponDao;

@SuppressWarnings("all")
public class CouponThread extends Thread {
    private ICouponDao couponDao = null;

    // 本线程待执行的任务列表，你也可以指为任务索引的起始值
    private List<Coupon> bindCouponList = null;

    private int threadId;

    /** 
     * 构造工作线程，为其指派任务列表，及命名线程 ID 
     * @param taskList 欲执行的任务列表 
     */

    public CouponThread(List bindCouponList, int threadId, ICouponDao couponDao) {
        this.bindCouponList = bindCouponList;
        this.threadId = threadId;
        this.couponDao = couponDao;
    }

    @Override
    public void run() {
        couponDao.bindCoupon(bindCouponList);
    }

}
