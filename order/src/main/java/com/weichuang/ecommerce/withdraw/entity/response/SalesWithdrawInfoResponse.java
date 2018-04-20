package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>ClassName: SalesWithdrawInfoResponse.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:业务人员提现详情实体 </p>
 * <p>author: liuzhanchao</p>
 * <p>2017年11月21日 下午20:40:17</p>
 */
public class SalesWithdrawInfoResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int salesId;// 员工id

    private String salesNickName;// 员工昵称

    private String createTime;// 提现日期

    private BigDecimal withdraw;// 提现总金额

    private int status;// 1待提现2已提现3

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesNickName() {
        return salesNickName;
    }

    public void setSalesNickName(String salesNickName) {
        this.salesNickName = salesNickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public BigDecimal getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(BigDecimal withdraw) {
        this.withdraw = withdraw;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   

    
}