package com.weichuang.ecommerce.order.entity.response;

import java.util.List;

/**
 * <p>ClassName: OrderStatusCountListResponse.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户各个状态订单的数量 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月30日 下午16:48:17</p>
 */
public class OrderStatusCountListResponse {

    // 用户订单列表
    private List<OrderStatusCountResponse> orderStatusCountList = null;

    public List<OrderStatusCountResponse> getOrderStatusCountList() {
        return orderStatusCountList;
    }

    public void etOrderStatusCountList(List<OrderStatusCountResponse> orderList) {
        this.orderStatusCountList = orderList;
    }

}
