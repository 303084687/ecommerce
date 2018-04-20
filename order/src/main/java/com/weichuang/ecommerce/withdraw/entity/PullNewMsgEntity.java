package com.weichuang.ecommerce.withdraw.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: PullNewMsgEntity.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:后台拉新信息显示 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年12月11日 下午3:10:47</p>
 */
public class PullNewMsgEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private int oneDayPullNew;// 一天的拉新人数

    private int totalPullNew;// 总的拉新人数（）

    private BigDecimal canReceiveRed;// 可以领取的红包

    private BigDecimal AlreadyReceiveRed;// 已经领取的红包

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
        return AlreadyReceiveRed;
    }

    public void setAlreadyReceiveRed(BigDecimal alreadyReceiveRed) {
        AlreadyReceiveRed = alreadyReceiveRed;
    }


}
