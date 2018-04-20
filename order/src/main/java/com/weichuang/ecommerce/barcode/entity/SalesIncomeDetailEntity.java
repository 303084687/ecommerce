package com.weichuang.ecommerce.barcode.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
* <p>ClassName:SalesIncomeDetailEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 18:01</p>
**/
public class SalesIncomeDetailEntity {
    private String comDate;//显示时间
    private Date incomeDate;//日期
    private String incomeTime;//时间
    private int comeType;//类型
    private String comeTypeMsg;//类型标识
    private String nickName;//昵称
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getComeType() {
        return comeType;
    }

    public void setComeType(int comeType) {
        this.comeType = comeType;
    }

    public String getComeTypeMsg() {
        return comeTypeMsg;
    }

    public void setComeTypeMsg(String comeTypeMsg) {
        this.comeTypeMsg = comeTypeMsg;
    }

    private BigDecimal income;//收支金额

    public String getComDate() {
        return comDate;
    }

    public void setComDate(String comDate) {
        this.comDate = comDate;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public String getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime = incomeTime;
    }


    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }
}
