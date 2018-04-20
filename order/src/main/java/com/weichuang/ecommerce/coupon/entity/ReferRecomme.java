package com.weichuang.ecommerce.coupon.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: ReferRecomme.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:推荐者和被推荐关系表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月1日 下午2:02:40</p>
 */
public class ReferRecomme implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;// 主键

    private String recommeOpenId;// 被推荐人openId

    private String referOpenId;// 推荐者openId;

    private int referUserId;// 推荐者主键

    private int referStoreId;// 推荐者门店主键

    private int referCompanyId;// 推荐者公司主键

    private String typeCode;// 发放体验券的类型code

    private Date createTime;// 创建时间

    public int getReferStoreId() {
        return referStoreId;
    }

    public void setReferStoreId(int referStoreId) {
        this.referStoreId = referStoreId;
    }

    public int getReferCompanyId() {
        return referCompanyId;
    }

    public void setReferCompanyId(int referCompanyId) {
        this.referCompanyId = referCompanyId;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecommeOpenId() {
        return recommeOpenId;
    }

    public void setRecommeOpenId(String recommeOpenId) {
        this.recommeOpenId = recommeOpenId;
    }

    public String getReferOpenId() {
        return referOpenId;
    }

    public void setReferOpenId(String referOpenId) {
        this.referOpenId = referOpenId;
    }

    public int getReferUserId() {
        return referUserId;
    }

    public void setReferUserId(int referUserId) {
        this.referUserId = referUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
