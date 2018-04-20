package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>ClassName: AllFinanceMsgResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:后台拉新信息 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月12日 下午14:14:47</p>
 */
public class AllFinanceMsgResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int  oneDayPullNew;// 一天的拉新人数

    private int  totalPullNew;// 总的拉新人数

    private BigDecimal oneDayMoney;// 一天的预计收入

    private BigDecimal totalMoney;// 总的收入（包含已提现的和未提现的）

    public int getOneDayPullNew() {
        return oneDayPullNew;
    }

    public void setOneDayPullNew(int oneDayPullNew) {
        this.oneDayPullNew = oneDayPullNew;
    }

    public int getTotalPullNew() {
        return totalPullNew;
    }

    public void setTotalPullNew(int totalPullNew) {
        this.totalPullNew = totalPullNew;
    }

    public BigDecimal getOneDayMoney() {
        return oneDayMoney;
    }

    public void setOneDayMoney(BigDecimal oneDayMoney) {
        this.oneDayMoney = oneDayMoney;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
