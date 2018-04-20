package com.weichuang.ecommerce.tenant.repository;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.*;
import com.weichuang.ecommerce.tenant.entity.response.SalesPullNewSetListResponse;

import java.util.List;

/**
 * <p>
 * ClassName: IAgentDao.java
 * </p>
 * <p>
 * Company: 伟业创投(北京)科技有限公司 http://www.weichuangwuxian.cn
 * </p>
 * <p>
 * Description:代理商数据访层
 * </p>
 * <p>
 * author: jiangkesen
 * </p>
 * <p>
 * 2017年09月22日 下午15:14:13
 * </p>
 */
public interface IAgentDao {

	/**
	 * <p>
	 * Description: 根据代理商id查询信息
	 * </p>
	 * <p>
	 * agentId 代理商id
	 * </p>
	 * <p>
	 * date 2017/9/22 15:00
	 * </p>
	 * <p>
	 * return AgentEntity
	 * </p>
	 */
	public AgentEntity getAgentById(int agentId);

	/**
	 * <p>
	 * Description: 根据代理商父id、名称查询信息
	 * </p>
	 * <p>
	 * agentId 代理商id
	 * </p>
	 * <p>
	 * date 2017/9/22 15:00
	 * </p>
	 * <p>
	 * return AgentEntity
	 * </p>
	 */
	public List<AgentEntity> getAgentList(Integer parentId, String agentName);

	/**
	 * @Title:addCompany
	 * @Description:增加商品,后台使用
	 * @param entity
	 * @return
	 */
	public int addAgent(AgentEntity entity);

	/**
	 * @Title:updateCompany
	 * @Description:修改商品,后台使用
	 * @param entity
	 * @return
	 */
	public int updateAgent(AgentEntity entity);

	/**
	 * @param operatorName
	 * @param operator
	 * @Title:updateCompanyStatus
	 * @Description:更新公司信息
	 * @param companyId
	 * @return
	 */
	public int updateAgentStatusByAgentId(int agentId, int operator,
			String operatorName, int status);

	/**
	 * @Title:getCompanyDetail
	 * @Description:根据公司主键公司详情
	 * @param companyId
	 * @return
	 */
	/* public CompanyDetailEntity getCompanyDetail( int companyId); */

	/**
	 * @Title:getCompanyList
	 * @Description:根据公司名称或法人或公司编号查询公司列表后台使用
	 * @param status
	 * @param keyword
	 * @return
	 */
	public List<ParentAgentEntity> getParentAgentList(Integer status,
			String keyWord);

	/**
	 * @Title:getCompany
	 * @Description:根据公司id查询查询公司基本信息
	 * @return
	 */

	public AgentEntity getAgentInfoByAgentId(int AgentId);

	/**
	 * @Title:getCompany
	 * @Description:根据代理名称查询查询基本信息
	 * @return
	 */
	public AgentEntity getAgentInfoByAgentName(String agentName);

	/**
	 * @Title:getCompany
	 * @Description:根据代理编号查询查询基本信息
	 * @return
	 */
	public AgentEntity getAgentInfoByAgentCode(String agentCode);

	/**
	 * @Title:getSalseCount
	 * @Description:根据agentId查询员工数量
	 * @param companyId
	 * @return
	 */
	public int getSalseCountByParentAgentId(int parentAgentId);

	/**
	 * getSalseCountToday
	 * 
	 * @Description:根据agentId查询员工数量
	 * @param companyId
	 * @return
	 */
	public int getSalseCountTodayByParentAgentId(int parentAgentId, String date);

	/**
	 * @Title:getShopInfoList
	 * @Description:根据公司id查询门店列表基本信息(含门店)
	 * @return
	 */
	public List<ChildAgentEntity> getChildAgentInfoList(int status,
			int parentId, String keyWord);

	/**
	 * getCompanyInfoById
	 * 
	 * @Description:根据公司id查询查询公司基本信息（含门店）
	 * @return
	 */
	public ParentAgentEntity getParentAgentInfoByParentAgentId(int parentAgentId);

	/**
	 * @Title:getCompany
	 * @Description:根据门店id查询门店基本信息（含员工）
	 * @return
	 */
	public ChildAgentEntity getChildAgentInfoByChildAgentId(int childAgentId);

	/**
	 * @Title:salesInfoList
	 * @Description:管理列表显示
	 * @param name
	 *            门店，公司，或编号
	 * @param status
	 *            状态1合作中2未合作
	 * @return
	 * @throws ServiceException
	 */
	public List<SalesEntity> getSalesList(int roleId, int agentId,int status,
			String keyWord);

	public List <SalesEntity> getSalesAppList(int companyId,int roleId,int agentId,String keyWord);
	/**
	 * @Title: salesInfo
	 * @Description:根据主键查询员工资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	public SalesEntity getSalesInfoBySalesId(int salesId);

	/**
	 * @Title:companyAgentSalesList
	 * @Description:公司列表（含店面资料，员工资料）
	 * @return
	 */
	public List<ParentAgentChildAgentSalesEntity> getParentAgentChildAgentSalesList();

	/**
	 * @Title:updateSalesStatus
	 * @Description:修改员工状态
	 * @param 员工Id
	 * @param status
	 *            1正常2冻结3逻辑移除
	 * @return
	 * @throws Exception
	 */
	public int updateSalesStatusBySalesId(int salesId, int loginStatus);

	/**
	 * @param agentName
	 * @Title:updateSalesAgentBySalseId
	 * @Description:修改员工所属店面
	 * @param 员工Id
	 * @param 店面Id
	 * @return
	 * @throws Exception
	 */
	public int updateSalesAgentBySalseId(int salesId, int agentId,
			String agentName);

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
			String salesName, int agentId, String agentName);

	/**
	 * @Title:pullSetList
	 * @Description:拉新设置表
	 * @return
	 */

	public List<SalesPullNewSetEntity> getPullSetList();

	/**
	 * @Title: salesBsaeInfo
	 * @Description:根据主键查询员工基本资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	public SalesBaseEntity getSalesBaseInfoBySalesId(int salesId);

	/**
	 * 增加二维码地址
	 * 
	 * @param agentId
	 * @param barcodeUrl
	 * @return
	 */
	public int updateBarcodeurl(int agentId, String barcodeUrl);

	/**
	 * @Title:childAgentSalesCountList
	 * @Description:管理列表显示
	 * @param name
	 *            门店，公司，或编号
	 * @param status
	 *            状态1合作中2未合作
	 * @return
	 */
	public List<ChildAgentEntity> getChildAgentSalesCountInfoList(int status,
			int agentId, String keyWord);

	/**
	 * @Title:salesDetailByAgentId
	 * @Description:管理列表显示
	 * @param name
	 *            门店，公司，或编号
	 * @param status
	 *            状态1合作中2未合作
	 * @return
	 */
	public List<SalesEntity> getSalesDetailByAgentId(int agentId);

	/**
	 * @Title: ChildAgentSalesCountInfoByChildAgentId
	 * @Description:根据主键查询店面资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	public ChildAgentEntity getChildAgentSalesCountInfoByChildAgentId(
			int childAgentId);

	/**
	 * <p>
	 * Description:根据二维码键值查组织
	 * </p>
	 * <p>
	 * param codekey:键值
	 * </p>
	 * <p>
	 * author:zhanghongsheng
	 * </p>
	 * <p>
	 * date:2017/11/21 16:50
	 * </p>
	 * <p>
	 * return:
	 * </p>
	 */
	public AgentEntity selectByCodekey(String codekey);

	/**
	 * @Title:parentAgentBaseList(只包含公司id和公司名称)
	 * @Description:管理列表显示
	 * @param name
	 *            公司，法人，或公司编号
	 * @param status
	 *            状态1正常2禁止
	 * @return
	 */
	public List<ParentAgentBaseEntity> getParentAgentBaseList(int status);

	/**
	 * @Title: getUserCountByParentAgentId
	 * @Description:根据公司主键查询会员数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	public int getUserCountByParentAgentId(int parentAgentId);

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
			int operator, String operatorName, int status);

	/**
	 * @Title: getUserCountByAgentId
	 * @Description:根据门店主键查询员工数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	public int getUserCountByAgentId(int agentId);

	public List<MyShopEntity> getShopListByAgentId(int companyId);


	/**
	 *<p>Description:通过agentId修改agentName</p>
	 *<p>param agentId:Id</p>
	 *<p>param agentName:名称</p>
	 *<p>author:liuzhanchao</p>
	 *<p>date:2018/1/4 13:35</p>
	 */

	public int updateAgentNameByAgentIdOnAgentRole(int agentId,String agentName);

	/**
	 *<p>Description:通过agentId修改agentName</p>
	 *<p>param agentId:Id</p>
	 *<p>param agentName:名称</p>
	 *<p>author:liuzhanchao</p>
	 *<p>date:2018/1/4 13:35</p>
	 */

	public int updateAgentNameByAgentIdOnAgentIncome(int agentId,String agentName);

	/**
	 *<p>Description:通过agentId修改agentName</p>
	 *<p>param agentId:Id</p>
	 *<p>param agentName:名称</p>
	 *<p>author:liuzhanchao</p>
	 *<p>date:2018/1/4 13:35</p>
	 */

	public int updateAgentNameByAgentIdOnAgentWithdraw(int agentId,String agentName);

	public List<AgentCouponEntity> selectCompanyByPostCounpon(int status,String agentName,int type);

}