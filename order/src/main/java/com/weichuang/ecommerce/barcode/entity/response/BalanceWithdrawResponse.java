package com.weichuang.ecommerce.barcode.entity.response;

import java.math.BigDecimal;

/**
* <p>ClassName:BalanceWithdrawResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/4 17:54</p>
**/
public class BalanceWithdrawResponse {
    private boolean canUse;//是否可提现
    private BigDecimal balance;//余额

    public boolean isCanUse() {
        return canUse;
    }

    public void setCanUse(boolean canUse) {
        this.canUse = canUse;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
