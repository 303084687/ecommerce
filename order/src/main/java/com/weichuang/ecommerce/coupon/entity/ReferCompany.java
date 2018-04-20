package com.weichuang.ecommerce.coupon.entity;

import java.io.Serializable;

/**
 * <p>ClassName: ReferCompany.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:公司统计查询返回 </p>
 * <p>author wanggongliang</p>
 * <p>2017年12月22日 下午2:18:50</p>
 */
public class ReferCompany implements Serializable {
    private static final long serialVersionUID = 1L;

    private int number;// 总数

    private String dayType;// 天数

    private int referUserId;// 推荐者主键

    private int referStoreId;// 推荐者门店主键

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDayType() {
        return dayType;
    }

    public void setDayType(String dayType) {
        this.dayType = dayType;
    }

    public int getReferUserId() {
        return referUserId;
    }

    public void setReferUserId(int referUserId) {
        this.referUserId = referUserId;
    }

    public int getReferStoreId() {
        return referStoreId;
    }

    public void setReferStoreId(int referStoreId) {
        this.referStoreId = referStoreId;
    }

}
