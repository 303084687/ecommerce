package com.weichuang.ecommerce.barcode.entity;

import java.math.BigDecimal;

/**
* <p>ClassName:SalesInvteNewCountEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/9 17:48</p>
**/
public class SalesInvteNewCountEntity {
    private int dayCount;//日邀新数量
    private int allCount;//总计邀新数量
    private BigDecimal canUseIncome;//可提现
    private BigDecimal allIncome;//历史总计
    public BigDecimal getAllIncome() {
        return allIncome;
    }

    public BigDecimal getCanUseIncome() {
        return canUseIncome;
    }

    public void setCanUseIncome(BigDecimal canUseIncome) {
        this.canUseIncome = canUseIncome;
    }

    public void setAllIncome(BigDecimal allIncome) {
        this.allIncome = allIncome;
    }



    public int getDayCount() {
        return dayCount;
    }

    public void setDayCount(int dayCount) {
        this.dayCount = dayCount;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }
}
