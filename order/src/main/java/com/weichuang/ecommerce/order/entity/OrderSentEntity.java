package com.weichuang.ecommerce.order.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: OrderSentEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单发货详情表 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月3日</p>
 */
public class OrderSentEntity implements Serializable {

    private int id;//主键自增长
    private int orderId;//订单id
    private String orderNo;//订单编号
    private String receiverName;//收货人姓名
    private int provinceId;//省的id
    private int cityId;//市的id
    private int conutyId;//县的id
    private String address;//收货详细地址
    private String mobile;//收货人手机号
    private String postcode;//邮编
    private Date createTime;//创建时间
    private Date sendTime;//发货时间
    private Date sendingTime;//实际发货时间，录入快速单号时间
    private String trackingNum;//快递单号
    private Date receiveTime;//确认收货时间
    private int status;//状态     2-待发货    3-待收货    4-已完成（已确认收货）

    //主键自增长
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //订单id
    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    //收货人姓名
    public String getReceiverName() {
        return this.receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getConutyId() {
        return conutyId;
    }

    public void setConutyId(int conutyId) {
        this.conutyId = conutyId;
    }

    //收货详细地址
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //收货人手机号
    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    //邮编
    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    //创建时间
    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    //发货时间
    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Date sendingTime) {
        this.sendingTime = sendingTime;
    }


    //快递单号
    public String getTrackingNum() {
        return this.trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    //确认收货时间
    public Date getReceiveTime() {
        return this.receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    //状态     2-待发货    3-待收货    4-已完成（已确认收货）
    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
