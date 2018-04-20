package com.weichuang.ecommerce.withdraw.entity.response;

import java.util.List;

/**
 * <p>ClassName: AgentIncomeListResponse 代理商预计收入列表返回对象</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户订单列表</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/10/08 15:11 </p>
 */
public class AgentIncomeListResponse {

    // 总页数
    private int pages;
    // 预计收入列表
    private List<AgentIncomeResponse> agentIncomeList = null;

    public List<AgentIncomeResponse> getOrderList() {
        return agentIncomeList;
    }

    public void setOrderList(List<AgentIncomeResponse> orderList) {
        this.agentIncomeList = orderList;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
