package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: PullNewMsgResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:后台拉新信息 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月11日 下午16:14:47</p>
 */
public class PullNewMsgResponse implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int oneDayPullNew;// 一天的拉新人数

    private int totalPullNew;// 总的拉新人数（）

    private BigDecimal canReceiveRed;// 可以领取的红包

    private BigDecimal alreadyReceiveRed;// 已经领取的红包

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

    public BigDecimal getCanReceiveRed() {
        return canReceiveRed;
    }

    public void setCanReceiveRed(BigDecimal canReceiveRed) {
        this.canReceiveRed = canReceiveRed;
    }

    public BigDecimal getAlreadyReceiveRed() {
        return alreadyReceiveRed;
    }

    public void setAlreadyReceiveRed(BigDecimal alreadyReceiveRed) {
        this.alreadyReceiveRed = alreadyReceiveRed;
    }
}
