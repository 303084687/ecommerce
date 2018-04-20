package com.weichuang.ecommerce.barcode.entity;

import java.util.Date;

/**
* <p>ClassName:SalesPullNewcEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:邀新实体</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/20 10:35</p>
**/
public class SalesPullNewcEntity {
    private Integer id;

    private Integer aid;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    //销售id
    private Integer saleId;

    //键值
    private String codeKey;

    //用户id
    private Integer userId;

    //用户手机
    private String mobile;

    //类型   1:拉新 2:分享商品 3:分享优惠券
    private Integer invitetype;

    //状态 0 初始 1 有效 2 无效
    private Integer state;

    //生效时间
    private Date useTime;

    //创建时间
    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey == null ? null : codeKey.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getInvitetype() {
        return invitetype;
    }

    public void setInvitetype(Integer invitetype) {
        this.invitetype = invitetype;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}