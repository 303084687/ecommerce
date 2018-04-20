package com.weichuang.ecommerce.barcode.entity;

import java.util.Date;

/**
 * <p>ClassName: UserEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户实体 </p>
 * <p>author: zhanghongsheng</p>
 * <p>2017年11月23日 下午17:12:17</p>
 */
public class SalesPullNewSetEntity {
    // 主键自增长
    private int id;
    private int aid;
    //人数
    private int pullNewNum;

    // 收益
    private double pullNewIncome;

    private int operatorId;

    private Date ctime;

    private Date utime;

    private int state;

    private int isArrived;//是否达到

    public int getIsArrived() {
        return isArrived;
    }

    public void setIsArrived(int isArrived) {
        this.isArrived = isArrived;
    }

    private int hasRec;//是否领取

    public int getHasRec() {
        return hasRec;
    }

    public void setHasRec(int hasRec) {
        this.hasRec = hasRec;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

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
