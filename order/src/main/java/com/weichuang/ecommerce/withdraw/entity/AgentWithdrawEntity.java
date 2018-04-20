package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: SalesWithdrawEntity.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:代理商(健身房)提现详情实体 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月18日 下午16:07:17</p>
 */
public class AgentWithdrawEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;// 主键自增长

    private int agentId;// 销售人员Id(销售人员id)

    private String agentName;// 销售人员名称

    private String openId;// 提现的微信openId

    private String paymentNo;// 第三方订单号

    private BigDecimal withdraw;// 提现总金额

    private int paymentType;// 商品数支付方式： 1-支付宝 2-微信 3-信用卡

    private int status;// 1待提现2已提现

    private String paymentSequence;// 支付流水号

    private Date createTime;// 创建时间同支付时间

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgentId() {
        return agentId;
    }

    public void setAgentId(int agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentSequence() {
        return paymentSequence;
    }

    public void setPaymentSequence(String paymentSequence) {
        this.paymentSequence = paymentSequence;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
