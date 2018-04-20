package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>ClassName: BonusTemplate.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:个人中心提成金额显示返回类实体</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午3:35:21</p>
 */
public class BonusTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    private int saleId;// 销售/代理的主键

    private BigDecimal oneDayMoney;// 一天的预计收入

    private BigDecimal sevenDayMoney;// 7天的预计收入

    private BigDecimal cashMoney;// 可以提现的金额

    private BigDecimal everyMoney;// 每天的销售金额,总算符合条件的总和

    private String day;// 查询出来的天，用于统计销售总额

    private String oneDayMoneyString;//一天的预计收入

    private String sevenDayMoneyString;//总计收入

    public String getOneDayMoneyString() {
        return oneDayMoneyString;
    }

    public String getSevenDayMoneyString() {
        return sevenDayMoneyString;
    }

    public String getCashMoneyString() {
        return cashMoneyString;
    }

    private String cashMoneyString;//可以提现的金额

    public BigDecimal getEveryMoney() {
        return everyMoney;
    }

    public void setEveryMoney(BigDecimal everyMoney) {
        this.everyMoney = everyMoney;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public BigDecimal getOneDayMoney() {
        return oneDayMoney;
    }

    public void setOneDayMoney(BigDecimal oneDayMoney) {
        this.oneDayMoney = oneDayMoney;
        oneDayMoneyString=oneDayMoney.toString();
    }

    public BigDecimal getSevenDayMoney() {
        return sevenDayMoney;
    }

    public void setSevenDayMoney(BigDecimal sevenDayMoney) {
        this.sevenDayMoney = sevenDayMoney;
        sevenDayMoneyString=sevenDayMoney.toString();
    }

    public BigDecimal getCashMoney() {
        return cashMoney;
    }

    public void setCashMoney(BigDecimal cashMoney) {
        this.cashMoney = cashMoney;
        cashMoneyString=cashMoney.toString();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
