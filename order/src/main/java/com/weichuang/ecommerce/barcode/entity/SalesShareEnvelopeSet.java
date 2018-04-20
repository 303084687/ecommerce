package com.weichuang.ecommerce.barcode.entity;

import java.math.BigDecimal;
import java.util.Date;

public class SalesShareEnvelopeSet {
    private Integer id;

    private Integer envelopType;

    private Integer shareCount;

    private BigDecimal shareMinIncome;

    private BigDecimal shareMaxIncome;

    private Date startTime;

    private Date endTime;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnvelopType() {
        return envelopType;
    }

    public void setEnvelopType(Integer envelopType) {
        this.envelopType = envelopType;
    }

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public BigDecimal getShareMinIncome() {
        return shareMinIncome;
    }

    public void setShareMinIncome(BigDecimal shareMinIncome) {
        this.shareMinIncome = shareMinIncome;
    }

    public BigDecimal getShareMaxIncome() {
        return shareMaxIncome;
    }

    public void setShareMaxIncome(BigDecimal shareMaxIncome) {
        this.shareMaxIncome = shareMaxIncome;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}