package com.weichuang.ecommerce.coupon.entity.request;

import java.io.Serializable;

/**
 * <p>ClassName: CouponAddRequest.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券增加 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午4:04:42</p>
 */
public class CouponAddRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String typeCode;// 类型编码多个用,分割

    private int hasSend;// 1多次使用2仅一次

    private int userId;// 所属用户

    private String creator;// 创建人

    private String remark;// 说明

    private int createNum;// 批量生成的数量

    public int getCreateNum() {
        return createNum;
    }

    public void setCreateNum(int createNum) {
        this.createNum = createNum;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public int getHasSend() {
        return hasSend;
    }

    public void setHasSend(int hasSend) {
        this.hasSend = hasSend;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
