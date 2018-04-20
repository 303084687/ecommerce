package com.weichuang.ecommerce.withdraw.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.withdraw.entity.request.WithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.response.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>ClassName: IAgentIncomeService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:代理序计service</p>
 * <p>author jiangkesen</p>
 * <p>2017年10月08日 下午15:10:46</p>
 */
public interface IAgentIncomeService {

    /**
     * <p>Description: 根据条件查询代理商预计收入列表</p>
     * <p>param agentId 代理商Id </p>
     * <p>param startTime 创建开始时间 </p>
     * <p>param endTime 创建结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/10/08 15:00 </p>
     * <p>return List<AgentIncomeEntity></p>
     */
    public AgentIncomeListResponse getAgentIncomeList(int agentId, Date startTime, Date endTime, int pageNum, int pageSize);


    /**
     * @param parentAgentId 代理/销售主键
     * @return Response<SalesDailyIncomeSumListResponse>
     * @throws Exception
     * @Title:getDailyIncomeSumBySalesId
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Description:根据公司的id查询所有提成的汇总（按天显示）
     */
    public ParentAgentDailyIncomeSumListResponse getParentAgentDailyIncomeSumByParentAgentId(int parentAgentId, int pageNum, int pageSize);

    /**
     * @param parentAgentId 代理/销售主键
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Description:根据公司的id查询当天的预计的可提成的详细信息（按门店显示）
     */
    public ParentAgentDailyIncomeListResponse getParentAgentDailyIncomeListByParentAgentId(int parentAgentId, int pageNum, int pageSize);


    /**
     * @param parentAgentId 代理/销售主键
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据公司的id查询某天的预计的可提成的详细信息（按门店显示）
     */
    public ParentAgentDailyIncomeListResponse getParentAgentDailyIncomeListByParentAgentIdDate(int parentAgentId, String oneDay, int pageNum, int pageSize);


    /**
     * @param agentId 代理/销售主键

     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据门店的id查询当天的预计的可提成的详细信息
     */
    public AgentDailyIncomeDetailListResponse getAgentDailyIncomeDetailByAgentId(int agentId, int pageNum, int pageSize);


    /**
     * @param agentId    代理/销售主键
     * @param oneDay 开始时间
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据门店的id查询某一天的预计的可提成的详细信息
     */
    public AgentDailyIncomeDetailListResponse getAgentDailyIncomeDetailByAgentIdDate(int agentId,String oneDay,int pageNum, int pageSize);
}
