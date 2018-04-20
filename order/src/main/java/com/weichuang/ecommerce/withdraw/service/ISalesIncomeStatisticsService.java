package com.weichuang.ecommerce.withdraw.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.withdraw.entity.response.SalesDailyIncomeDetailListResponse;
import com.weichuang.ecommerce.withdraw.entity.response.SalesDailyIncomeSumListResponse;

/**
 * <p>ClassName: ISalesIncomeStatisticsService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:业务人员收入统计service</p>
 * <p>author jiangkesen</p>
 * <p>2017年11月23日 下午15:10:46</p>
 */
public interface ISalesIncomeStatisticsService {

    /**
     * <p>Description: 根据业务人员的id查询某一时间段内每天的预计的可提成的汇总-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param startTime 开始时间 </p> 
     * <p>param endTime 结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/23 </p>
     * <p>return SalesDailyIncomeSumListResponse</p>
     * @param pageSize 
     * @param pageNum 
     */
    public SalesDailyIncomeSumListResponse getDailyIncomeSumBySalesId(int saleId, int pageNum, int pageSize) throws ServiceException, Exception;

    /**
     * <p>Description: 根据业务人员的id查询某一时间段内每天的预计的可提成的详细信息-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param startTime 开始时间 </p>
     * <p>param endTime 结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/23 </p>
     * <p>return boolean</p>
     */
    public SalesDailyIncomeDetailListResponse getDailyIncomeDetailBySalesId(int saleId) throws ServiceException, Exception;

    
    /**
     * <p>Description: 根据业务人员的id查询某一时间段内每天的预计的可提成的详细信息-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param oneDay 日期 </p>
    
     * <p>author liuzhanchao </p>
     * <p>date 2017/11/25 </p>
     * <p>return boolean</p>
     */
	public SalesDailyIncomeDetailListResponse getDailyIncomeDetailBySalesIdDate (
			int saleId, String oneDay) throws ServiceException, Exception;
}
