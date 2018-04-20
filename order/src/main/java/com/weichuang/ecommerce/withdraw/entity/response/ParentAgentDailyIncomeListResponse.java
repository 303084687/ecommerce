package com.weichuang.ecommerce.withdraw.entity.response;

import com.weichuang.ecommerce.withdraw.entity.ParentAgentDailyIncomeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * <p>ClassName: ParentAgentDailyIncomeListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据业务人员的id查询某一时间段内每天的预计的可提成详情 </p>
 * <p>author liuzhanchao</p>
 * <p>2017年11月29日 下午3:10:47</p>
 */
public class ParentAgentDailyIncomeListResponse implements Serializable {
    private int pages;
    private Long total;// 返回的总条数

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    private List<ParentAgentDailyIncomeEntity> parentAgentDailyIncomeList;

    public List<ParentAgentDailyIncomeEntity> getParentAgentDailyIncomeList() {
        return parentAgentDailyIncomeList;
    }

    public void setParentAgentDailyIncomeList(List<ParentAgentDailyIncomeEntity> parentAgentDailyIncomeList) {
        this.parentAgentDailyIncomeList = parentAgentDailyIncomeList;
    }
}
