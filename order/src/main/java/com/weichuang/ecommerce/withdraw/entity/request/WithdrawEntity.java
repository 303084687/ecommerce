package com.weichuang.ecommerce.withdraw.entity.request;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: WithdrawEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:提成分红返回实体综合代理商和销售 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月29日 上午9:51:45</p>
 */
public class WithdrawEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;// 主键自增长

    private int agentId;// 销售人员Id(销售人员id)

    private String paymentNo;// 第三方订单号

    private String openId;// 微信openid

    private BigDecimal withdraw;// 提现总金额

    private Date createTime;// 创建时间同支付时间

    private int type;//类型

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
