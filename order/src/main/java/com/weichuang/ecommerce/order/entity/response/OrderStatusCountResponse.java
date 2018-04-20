package com.weichuang.ecommerce.order.entity.response;

import java.io.Serializable;

/**
 * <p>ClassName: OrderStatusCountResponse.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户各个状态订单的数量 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月30日 下午16:48:17</p>
 */
public class OrderStatusCountResponse implements Serializable {

    private int userId;//用户id
    private int status;//订单状态
    private int statusCount;//状态对应的订单数量

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(int statusCount) {
        this.statusCount = statusCount;
    }

}
