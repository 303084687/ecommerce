package com.weichuang.ecommerce.withdraw.entity.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: SalesDailyIncomeDetailListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:根据业务人员的id查询某一时间段内每天的预计的可提成详情 </p>
 * <p>author jiangkesen</p>
 * <p>2017年11月23日 下午3:10:47</p>
 */
public class SalesDailyIncomeDetailListResponse implements Serializable {
    private List<SalesDailyIncomeDetailResponse> salesDailyIncomeDetailList;
    public List<SalesDailyIncomeDetailResponse> getSalesDailyIncomeDetailList() {
        return salesDailyIncomeDetailList;
    }

    public void setSalesDailyIncomeDetailList(List<SalesDailyIncomeDetailResponse> salesDailyIncomeDetailList) {
        this.salesDailyIncomeDetailList = salesDailyIncomeDetailList;
    }
}
