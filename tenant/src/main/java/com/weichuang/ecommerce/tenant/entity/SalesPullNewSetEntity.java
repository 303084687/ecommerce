package com.weichuang.ecommerce.tenant.entity;

import java.util.Date;

/**
 * <p>ClassName: UserEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月13日 下午14:12:17</p>
 */
public class SalesPullNewSetEntity {
    // 主键自增长
    private int id;

    // 代理名称
    private int pullNewNum;

    // 代理编号
    private double pullNewIncome;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPullNewNum() {
        return pullNewNum;
    }

    public void setPullNewNum(int pullNewNum) {
        this.pullNewNum = pullNewNum;
    }

    public double getPullNewIncome() {
        return pullNewIncome;
    }

    public void setPullNewIncome(double pullNewIncome) {
        this.pullNewIncome = pullNewIncome;
    }

}
