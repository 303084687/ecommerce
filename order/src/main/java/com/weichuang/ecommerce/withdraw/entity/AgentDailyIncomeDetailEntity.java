package com.weichuang.ecommerce.withdraw.entity;

import com.weichuang.ecommerce.withdraw.entity.response.ShortProductResponse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>ClassName: SalesDailyIncomeDetailEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据业务人员的id查询某一时间段内每天的预计的可提成详情 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年11月29日 下午2:10:47</p>
 */
public class AgentDailyIncomeDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int salesId;// 业务人员id
    private String salesRealName;// 业务人员名称
    private String mobile;//业务人员电话
    private String orderNo;// 订单编号
    private BigDecimal receiveMoney;// 每单应付订单总金额
    private BigDecimal income;// 每单收入金额
    private String createTime;// 创建时间
    private List<ShortProductResponse> shortProductList;//短商品信息
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<ShortProductResponse> getShortProductList() {
        return shortProductList;
    }

    public void setShortProductList(List<ShortProductResponse> shortProductList) {
        this.shortProductList = shortProductList;
    }

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

    public String getSalesRealName() {
        return salesRealName;
    }

    public void setSalesRealName(String salesRealName) {
        this.salesRealName = salesRealName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }



    public BigDecimal getReceiveMoney() {
        return receiveMoney;
    }

    public void setReceiveMoney(BigDecimal receiveMoney) {
        this.receiveMoney = receiveMoney;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }


}
