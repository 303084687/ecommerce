package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>ClassName: SalesDailyIncomeSumListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据业务人员的id查询某一时间段内每天的预计的可提成的汇总 </p>
 * <p>author jiangkesen</p>
 * <p>2017年11月23日 下午3:10:47</p>
 */
public class SalesDailyIncomeSumListResponse implements Serializable {

	// 总页数
    private int pages;
    private Long total;// 返回的总条数
    private List<SalesDailyIncomeSumResponse> salesDailyIncomeSumList;

    public List<SalesDailyIncomeSumResponse> getSalesDailyIncomeSumList() {
        return salesDailyIncomeSumList;
    }

    public void setSalesDailyIncomeSumList(List<SalesDailyIncomeSumResponse> salesDailyIncomeSumList) {
        this.salesDailyIncomeSumList = salesDailyIncomeSumList;
    }

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

}
