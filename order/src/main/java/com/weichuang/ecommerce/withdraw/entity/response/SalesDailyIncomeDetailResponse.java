package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: SalesDailyIncomeDetailResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据业务人员的id查询某一时间段内每天的预计的可提成详情 </p>
 * <p>author jiangkesen</p>
 * <p>2017年11月23日 下午3:10:47</p>
 */
public class SalesDailyIncomeDetailResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private int salesId;// 业务人员id
    private String salesName;// 业务人员名称
    private String orderNo;// 订单编号
    private String userNickName;// 购买用户昵称
    private BigDecimal receiveMoney;// 每单应付订单总金额
    private BigDecimal income;// 每单收入金额
    private String receiveMoneyString;//每单应付订单总金额
    private String incomeString;//每单收入金额

    public String getReceiveMoneyString() {
        return receiveMoneyString;
    }

    public String getIncomeString() {
        return incomeString;
    }

    private String createTime;// 创建时间
    private String agentName;//所属店面

    private List<ShortProductResponse> shortProductList;//短商品信息


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
        receiveMoneyString=receiveMoney.toString();
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
        incomeString=income.toString();
    }

    public List<ShortProductResponse> getShortProductList() {
        return shortProductList;
    }

    public void setShortProductList(List<ShortProductResponse> shortProductList) {
        this.shortProductList = shortProductList;
    }

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
}
