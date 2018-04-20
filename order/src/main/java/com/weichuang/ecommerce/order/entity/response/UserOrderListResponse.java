package com.weichuang.ecommerce.order.entity.response;

import java.util.List;

/**
 * <p>ClassName: UserOrderResponse 用于用户中心用户订单列表</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户订单列表</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/09/25 10:11 </p>
 */
public class UserOrderListResponse {

    // 总页数
    private int pages;
    private long total;// 返回的总条数
    // 用户订单列表
    private List<UserOrderResponse> orderList = null;

    public List<UserOrderResponse> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<UserOrderResponse> orderList) {
        this.orderList = orderList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
