package com.weichuang.ecommerce.tenant.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.request.AgentRequest;
import com.weichuang.ecommerce.tenant.entity.response.*;

import java.util.List;

/**
 * <p>
 * ClassName:IAgentService
 * </p>
 * <p>
 * Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com
 * </p>
 * <p>
 * Description:查询代理商信息
 * </p>
 * <p>
 * author:zhanghongsheng
 * </p>
 * <p>
 * 2017/11/2 10:27
 * </p>
 **/

public interface IAgentService {
    public AgentListResponse getAgentList(Integer parentId, String agentName);
    public AgentAppListResponse getAppAgentList(Integer pageNum,Integer pageSize,Integer parentId, String agentName);
    /**
     * <p>
     * ClassName: ICompanyService.java
     * </p>
     * <p>
     * Company: 伟创科技(北京)科技有限公司
     * </p>
     * <p>
     * Description:公司接口类
     * </p>
     * <p>
     * author liuzhanchao
     * </p>
     * <p>
     * 2017年11月3日 上午8:28:46
     * </p>
     */

    /**
     * @Title:addCompany
     * @Description:增加公司,后台使用
     * @param entity
     * @return
     */
    public int addParentAgent(AgentRequest request) throws ServiceException;

    /**
     * updateCompany
     * 
     * @Description:修改公司,后台使用
     * @param entity
     * @return
     */
    public int updateAgent(AgentRequest request) throws ServiceException;

    /**
     * updateAgentStatus
     * @param operatorName 
     * @param operator 
     * 
     * @Description:设置公司合作状态,1合作中0未合作
     * @param agentId
     * @return
     */
    public int updateAgentStatusByAgentId(int agentId, int operator,
            String operatorName, int status) throws ServiceException;

    /**
     * @Title:getCompanyDetail
     * @Description:根据公司id查询公司详情,后台管理使用
     * @param productCode
     * @param productId
     * @return
     */
    /*
     * public CompanyDetailResponse getCompanyDetail( int companyId) throws
     * ServiceException;
     */

    /**
     * @Title:getCompanyList
     * @Description:根据公司状态，输入关键字（名称，法人，编号）查询公司列表
     * @param status
     * @param keyWord
     * @return
     */
    public ParentAgentListResponse getParentAgentList(Integer status,
            String keyWord, int pageNum, int pageSize) throws ServiceException;

    /**
     * @Title:getAgentInfobyId
     * @Description:根据公司id获取公司基本资料
     * @param companyId
     * @return
     */
    public AgentResponse getAgentInfoByAgentId(int agentId)
            throws ServiceException;
    /**
     * @Title:getAgentInfobyId
     * @Description:根据组织id获取公司基本资料
     * @param agentId
     * @return
     */
    public AgentResponse getCompanyInfoByAgentId(int agentId)
            throws ServiceException;
    /**
     * @Title:getAgentInfobyId
     * @Description:根据代理名称获取资料
     * @param companyId
     * @return
     */
    public AgentResponse getAgentInfoByAgentName(String agentName)
            throws ServiceException;

    /**
     * @Title:getAgentInfobyId
     * @Description:根据代理编号获取资料
     * @param companyId
     * @return
     */
    public AgentResponse getAgentInfoByAgentCode(String agentCode)
            throws ServiceException;

    /**
     * 根据代理id获取员工数量
     * 
     * @param agentId
     * @return
     */
    public int getSalseCountByParentAgentId(int parentAgentId)
            throws ServiceException;

    /**
     * 根据代理id获取员工数量
     * 
     * @param agentId
     * @return
     * @throws ServiceException
     */
    public int getSalseCountTodayByParentAgentId(int parentAgentId)
            throws ServiceException;

    /**
     * @Title:getCompanyList
     * @Description:根据店面状态，所属公司id，输入关键字（名称，编号）查询公司列表
     * @param status
     * @param keyWord
     * @return
     * @throws ServiceException
     */
    public ChildAgentListResponse getChildAgentInfoList(int status,
            int parentId, String keyWord, int pageNum, int pageSize)
            throws ServiceException;

    /**
     * getCompanyInfo
     * 
     * @Description:根据公司id获取公司基本资料（包含所属店面）
     * @param companyId
     * @return
     */
    public ParentAgentResponse getParentAgentInfoByParentAgentId(
            int parentAgentId) throws ServiceException;

    /**
     * getShopInfo
     * 
     * @Description:根据店面id获取资料（包含员工资料）
     * @param companyId
     * @return
     */
    public ChildAgentResponse getChildAgentInfoByChildAgentId(int childAgentId)
            throws ServiceException;

    /**
     * @Title:userDetailList
     * @Description:管理列表显示
     * @param name
     *            门店，公司，或编号
     * @param status
     *            状态1合作中2未合作
     * @return
     * @throws ServiceException
     */

    public SalesListResponse getSalesList(int roleId, int agentId,int status,
            String keyWord, int pageNum, int pageSize) throws ServiceException;
    /**
     * @Title:userDetailList
     * @Description:管理列表显示
     * @param name
     *            门店，公司，或编号
     * @param status
     *            状态1合作中2未合作
     * @return
     * @throws ServiceException
     */

    public SaleListAppResponse getSalesAppList(int companyId,int roleId, int agentId,
                                               String keyWord, int pageNum, int pageSize) throws ServiceException,Exception;
    /**
     * @Title: salesInfo
     * @Description:根据主键查询员工资料
     * @param id
     *            主键
     * @return
     * @throws Exception
     */
    public SalesResponse getSalesInfoBySalesId(int salesId)
            throws ServiceException;

    /**
     * @Title:companyAgentSalesList
     * @Description:公司列表（含店面资料，员工资料）
     * @return
     */

    public ParentAgentChildAgentSalesListResponse getParentAgentChildAgentSalesList()
            throws ServiceException;

    /**
     * @Title:updateSalesStatus
     * @Description:修改员工状态
     * @param 员工Id
     * @param status
     *            1正常2冻结3逻辑移除
     * @return
     * @throws Exception
     */

    public int updateSalesStatusBySalesId(int salesId, int loginStatus)
            throws ServiceException;

    /**
     * @param agentName 
     * @Title:updateSalesAgentBySalseId
     * @Description:修改员工所属店面
     * @param 员工Id
     * @param 店面Id
     * @return
     * @throws Exception
     */
    public int updateSalesAgentBySalseId(int salesId, int agentId, String agentName)
            throws ServiceException;

    /**
     * @param agentName 
     * @param salesName 
     * @Title:updateSalesAgentBySalseId
     * @Description:修改会员所属员工
     * @param 员工Id
     * @param 店面Id
     * @return
     * @throws Exception
     */
    public int updateUserSalesBySalseId(int oldSalesId, int salesId,
            String salesName, int agentId, String agentName) throws ServiceException;

    /**
     * @Title:pullSetList
     * @Description:拉新设置表
     * @return
     */

    public SalesPullNewSetListResponse getPullSetList() throws ServiceException;

    /**
     * @Title: salesBaseInfo
     * @Description:根据主键查询员工基本资料
     * @param id
     *            主键
     * @return
     * @throws Exception
     */
    public SalesBaseResponse getSalesBaseInfoBySalesId(int salesId)
            throws ServiceException;

    /**
     * @Title:addChildAgent
     * @Description:增加店面,后台使用
     * @param entity
     * @return
     */
    public int addChildAgent(AgentRequest request) throws ServiceException;

    /**
     * @Title:childAgentSalesCountList
     * @Description:管理列表显示
     * @param name
     *            门店，公司，或编号
     * @param status
     *            状态1合作中2未合作
     * @return
     */
    public ChildAgentListResponse getChildAgentSalesCountInfoList(int status,
            int agentId, String keyWord, int pageNum, int pageSize) throws ServiceException;

    /**
     * @Title:salesDetailByAgentId
     * @Description:管理列表显示
     * @param name
     *            门店，公司，或编号
     * @param status
     *            状态1合作中2未合作
     * @return
     */
    public SalesListResponse getSalesDetailByAgentId(int agentId,int pageNum,int pageSize) throws ServiceException;

    /**
     * @Title: ChildAgentSalesCountInfoByChildAgentId
     * @Description:根据主键查询店面资料
     * @param id
     *            主键
     * @return
     * @throws Exception
     */
    public ChildAgentResponse getChildAgentSalesCountInfoByChildAgentId(
            int childAgentId) throws ServiceException;
    /**
	 * @Title:parentAgentBaseList(只包含公司id和公司名称)
	 * @Description:管理列表显示
	 * @param name
	 *            公司，法人，或公司编号
	 * @param status
	 *            状态1正常2禁止
	 * @return
	 */
	public ParentAgentBaseListResponse getParentAgentBaseList(int status) throws ServiceException;

	
	/**
	 * @Title: getUserCountByParentAgentId
	 * @Description:根据公司主键查询会员数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	public int getUserCountByParentAgentId(int parentAgentId) throws ServiceException;


	/**
	 * @Title:updateAgentStatusByparentAgentId
	 * @Description:修改合作状态
	 * @param companyId
	 *            公司主键
	 * @param status
	 *            1合作2未合作
	 * @return
	 * @throws Exception
	 */
	public int updateAgentStatusByParentAgentId(int parentAgentId,
			int operator, String operatorName, int status) throws ServiceException;

	
	/**
	 * @Title: getUserCountByAgentId
	 * @Description:根据门店主键查询员工数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	public int getUserCountByAgentId(int agentId) throws ServiceException;

    public MyShopListResponse getShopListByAgentId(int companyId) throws ServiceException,Exception;

    public AgentCouponEntityResponse selectCompanyByPostCounpon(Integer status, String agentName, Integer type) throws ServiceException,Exception;

}
