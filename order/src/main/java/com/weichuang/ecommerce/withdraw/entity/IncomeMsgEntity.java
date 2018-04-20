package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: IncomeMsgEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:后台提成信息显示 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月11日 下午3:10:47</p>
 */
public class IncomeMsgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal oneDayMoney;// 一天的预计收入

    private BigDecimal totalMoney;// 总的收入（包含已提现的和未提现的）

    private BigDecimal cashMoney;// 可以提现的金额

    private BigDecimal AlreadyMoney;// 已经体现的总额

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

	public BigDecimal getCashMoney() {
		return cashMoney;
	}

	public void setCashMoney(BigDecimal cashMoney) {
		this.cashMoney = cashMoney;
	}

	public BigDecimal getAlreadyMoney() {
		return AlreadyMoney;
	}

	public void setAlreadyMoney(BigDecimal alreadyMoney) {
		AlreadyMoney = alreadyMoney;
	}
    

    

}
