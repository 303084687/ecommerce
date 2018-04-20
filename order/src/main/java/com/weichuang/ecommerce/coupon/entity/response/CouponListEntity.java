package com.weichuang.ecommerce.coupon.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: CouponList.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:管理后台优惠券显示列表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月23日 下午4:01:45</p>
 */
public class CouponListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 主键自增长

    private int typeId;// 类型的主键

    private String typeCode;// 类型编码

    private String couponCode;// 优惠券号

    private String couponPass;// 优惠券密码

    private Date startTime;// 优惠券使用开始时间

    private Date expireTime;// 优惠券结束日期

    private int usedPlat;// 使用平台1web,2h5,3安卓4ios

    private BigDecimal discountMoney;// 优惠金额

    private String userName;// 使用者

    private Date usedTime;// 使用时间

    private int isUsed;// 1:未使用,2:已使用

    private String orderId;// 使用优惠券的订单号

    private Date createTime;// 创建时间

    private Date activeTime;// 绑定/领取时间

    private int status;// 状态 1:正常,2:禁止

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponPass() {
        return couponPass;
    }

    public void setCouponPass(String couponPass) {
        this.couponPass = couponPass;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public int getUsedPlat() {
        return usedPlat;
    }

    public void setUsedPlat(int usedPlat) {
        this.usedPlat = usedPlat;
    }

    public BigDecimal getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(BigDecimal discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Date usedTime) {
        this.usedTime = usedTime;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
