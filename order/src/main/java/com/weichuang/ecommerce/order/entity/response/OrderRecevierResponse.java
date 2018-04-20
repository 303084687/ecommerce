package com.weichuang.ecommerce.order.entity.response;

import java.util.Date;

/**
 * <p>ClassName: OrderRecevierRequest 用于查询订单详情</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 订单收货人数据返回象</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/09/15 09:11 </p>
 */
public class OrderRecevierResponse {
    private int id;//id
    private int orderId;//订单id
    private String orderNo;//订单编号
    private String receiverName;//收货人姓名
    private String address;//收货详细地址
    private String mobile;//收货人手机号
    private String postcode;//邮编
    private Date createTime;//创建时间


    //    private Date sendTime;//发货时间
//    private String trackingNum;//快递单号
//    private Date receiveTime;//确认收货时间
//    private int status;//状态 	2-待发货	3-待收货	4-已完成（已确认收货）

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddress() {
        return address;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

//    public Date getSendTime() {
//        return sendTime;
//    }
//
//    public void setSendTime(Date sendTime) {
//        this.sendTime = sendTime;
//    }
//
//    public String getTrackingNum() {
//        return trackingNum;
//    }
//
//    public void setTrackingNum(String trackingNum) {
//        this.trackingNum = trackingNum;
//    }
//
//    public Date getReceiveTime() {
//        return receiveTime;
//    }
//
//    public void setReceiveTime(Date receiveTime) {
//        this.receiveTime = receiveTime;
//    }
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }

}


