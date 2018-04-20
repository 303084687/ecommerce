package com.weichuang.ecommerce.tenant.repository.impl;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.*;
import com.weichuang.ecommerce.tenant.entity.response.SalesPullNewSetListResponse;
import com.weichuang.ecommerce.tenant.repository.IAgentDao;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>ClassName: AgentDaoImpl.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:代理商数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午19:49:13</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class AgentDaoImpl implements IAgentDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 增加代理商信息</p>
     * <p>param entity 用户实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/22 15:00 </p>
     * <p>return </p>
     */
    @Override
    public int addAgent(AgentEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.AGENT + ".addAgent",
                entity);
    }

    /**
     * <p>Description: 根据代理商id查询信息</p>
     * <p>agentId 代理商id</p>
     * <p>date 2017/9/22 15:00 </p>
     * <p>author: zhanghongsheng</p>
     * <p>return AgentEntity</p>
     */
    @Override
    public AgentEntity getAgentById(int agentId) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getAgentById", agentId);
    }

    /**
     * <p>Description: 根据代理商父id名称查询信息</p>
     * <p>agentId 代理商id</p>
     * <p>author: zhanghongsheng</p>
     * <p>date 2017/9/22 15:00 </p>
     * <p>return AgentEntity</p>
     */
    public List<AgentEntity> getAgentList(Integer parentId, String agentName) {
        Map paramMap = new HashMap();
        if (parentId != null) {
            paramMap.put("parentId", parentId);
        }
        if (StringUtils.isNotEmpty(agentName)) {
            paramMap.put("agentName", agentName);
        }
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT
                + ".getAgentList", paramMap);
    }

    /**
     * @Title:updateCompany  
     * @Description:修改公司,后台使用
     * @param entity
     * @param author: liuzhanchao
     * @return
     */
    @Override
    public int updateAgent(AgentEntity entity) {
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT
                + ".updateAgent", entity);
    }

    /**
     * @Title:updateCompanyStatus  
     * @Description:修改公司合作状态1--合作中 ；0--未合作
     * @param companyId
     * @param author: liuzhanchao
     * @return
     */
    @Override
    public int updateAgentStatusByAgentId(int agentId, int operator,
            String operatorName, int status) {
        Map<String, Object> map = new HashMap<>();

        map.put("agentId", agentId);
        map.put("operator", operator);
        map.put("operatorName", operatorName);
        map.put("status", status);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT
                + ".updateAgentStatusByAgentId", map);
    }
    
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
	@Override
	public int updateAgentStatusByParentAgentId(int parentAgentId,
			int operator, String operatorName, int status){
		Map<String, Object> map = new HashMap<>();

        map.put("parentAgentId", parentAgentId);
        map.put("operator", operator);
        map.put("operatorName", operatorName);
        map.put("status", status);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT
                + ".updateAgentStatusByParentAgentId", map);
	}

    /**
     * getCompanyDetail  
     * @Description:根据公司主键查询公司详情
     * @param companyId
     * @param author: liuzhanchao
     * @return
     */
    /*
     * @Override public CompanyDetailtEntity getCompanyDetail(int companyId) {
     * Map<String, Object> map = new HashMap<>();
     * 
     * map.put("companyId", companyId); return
     * sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT + ".getProductInfo",
     * map); }
     */

    /**
     * @Title:getCompanyList  
     * @Description:根据公司状态，公司名称或公司编号或公司法人查询公司列表，后台使用
     * @param status
     * @param keyWord
     * @param author: liuzhanchao
     * @return
     */
    @Override
    public List<ParentAgentEntity> getParentAgentList(Integer status,
            String keyWord) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("keyWord", keyWord);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT
                + ".getParentAgentList", map);
    }
    
    
    /**
	 * @Title:parentAgentBaseList(只包含公司id和公司名称)
	 * @Description:管理列表显示
	 * @param name
	 *            公司，法人，或公司编号
	 * @param status
	 *            状态1正常2禁止
	 * @return
	 */
	public List<ParentAgentBaseEntity> getParentAgentBaseList(int status){
		 Map<String, Object> map = new HashMap<>();
	        map.put("status", status);
	        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT
	                + ".getParentAgentBaseList", map);
	}

    /**
     * 根据代理Id查询基本信息
     * @param author: liuzhanchao
     */
    @Override
    public AgentEntity getAgentInfoByAgentId(int agentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("agentId", agentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getAgentInfoByAgentId", map);

    }

    /**
     * 根据代理名称查询基本信息
     * @param author: liuzhanchao
     */
    @Override
    public AgentEntity getAgentInfoByAgentName(String agentName) {
        Map<String, Object> map = new HashMap<>();
        map.put("agentName", agentName);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getAgentInfoByAgentName", map);

    }

    /**
     * 根据代理编号查询基本信息
     * @param author: liuzhanchao
     */
    @Override
    public AgentEntity getAgentInfoByAgentCode(String agentCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("agentCode", agentCode);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getAgentInfoByAgentCode", map);

    }

    /**
     * @Title:getSalseCount 
     * @Description:获取员工数量
     * @param companyId
     * @param author: liuzhanchao
     * @return
     */
    @Override
    public int getSalseCountByParentAgentId(int parentAgentId) {
        Map<String, Object> map = new HashMap<>();

        map.put("parentAgentId", parentAgentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getSalseCountByParentAgentId", map);
    }
    
    
    /**
	 * @Title: getUserCountByParentAgentId
	 * @Description:根据公司主键查询会员数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
    @Override
	public int getUserCountByParentAgentId(int parentAgentId){
    	Map<String, Object> map = new HashMap<>();
    	 map.put("parentAgentId", parentAgentId);
         return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                 + ".getUserCountByParentAgentId", map);
    }
    
    /**
	 * @Title: getUserCountByAgentId
	 * @Description:根据门店主键查询员工数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
    @Override
	public int getUserCountByAgentId(int agentId){
    	Map<String, Object> map = new HashMap<>();
   	 map.put("agentId", agentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getUserCountByAgentId", map);
	}

    /**
     * @Title:getSalseCount 
     * @Description:获取员工数量
     * @param companyId
     * @param author: liuzhanchao
     * @return
     */
    @Override
    public int getSalseCountTodayByParentAgentId(int parentAgentId, String date) {
        Map<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("parentAgentId", parentAgentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getSalseCountTodayByParentAgentId", map);
    }

    /**
     * 根据代理Id查询基本信息
     * @param author: liuzhanchao
     */
    @Override
    public List<ChildAgentEntity> getChildAgentInfoList(int status,
            int parentId, String keyWord) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("parentId", parentId);
        map.put("keyWord", keyWord);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT
                + ".getChildAgentInfoList", map);
    }

    /**
     * @Title:childAgentSalesCountList
     * @Description:管理列表显示
     * @param name
     *            门店，公司，或编号
     * @param status
     *            状态1合作中2未合作
     * @return
     */

    @Override
    public List<ChildAgentEntity> getChildAgentSalesCountInfoList(int status,
            int agentId, String keyWord) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("agentId", agentId);
        map.put("keyWord", keyWord);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT
                + ".getChildAgentSalesCountInfoList", map);
    }

    /**
     * 根据代理Id查询基本信息
     * @param author: liuzhanchao
     */
    @Override
    public ParentAgentEntity getParentAgentInfoByParentAgentId(int parentAgentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("parentAgentId", parentAgentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getParentAgentInfoByParentAgentId", map);
    }

    /**
     * 根据代理Id查询基本信息
     * @param author: liuzhanchao
     */
    @Override
    public ChildAgentEntity getChildAgentInfoByChildAgentId(int childAgentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("childAgentId", childAgentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getChildAgentInfoByChildAgentId", map);
    }

    /**
     * @Title:salesInfoList
     * @Description:管理列表显示
     * @param name
     *            门店，公司，或编号
     * @param status
     *            状态1合作中2未合作
     * @return
     * @param author: liuzhanchao
     * @throws ServiceException 
     */
    @Override
    public List<SalesEntity> getSalesList(int roleId, int agentId,int status,
            String keyWord) {
        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("agentId", agentId);
        map.put("status",status);
        map.put("keyWord", keyWord);
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER
                + ".getSalesList", map);
    }
    /**
     *<p>Description:</p>
     *<p>param companyid:公司id</p>
     *<p>param roleId:角色id</p>
     *<p>param agentId:门店id</p>
     *<p>param keyWord:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/28 10:39</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public List <SalesEntity> getSalesAppList(int companyId,int roleId,int agentId,String keyWord){

        Map<String, Object> map = new HashMap<>();
        map.put("roleId", roleId);
        map.put("companyId", companyId);
        map.put("agentId", agentId);
        map.put("keyWord", keyWord);
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER
                + ".getSalesAppList", map);
    }
    /**
     * @Title:salesDetailByAgentId
     * @Description:管理列表显示
     * @param name
     *            门店，公司，或编号
     * @param status
     *            状态1合作中2未合作
     * @return
     */

    @Override
    public List<SalesEntity> getSalesDetailByAgentId(int agentId) {
        Map<String, Object> map = new HashMap<>();

        map.put("agentId", agentId);

        return sqlSessionTemplate.selectList(NameSpaceConstant.USER
                + ".getSalesDetailByAgentId", map);
    }

    /**
     * @Title: salesInfo
     * @Description:根据主键查询员工资料
     * @param id
     *            主键
     * @return
     * @param author: liuzhanchao
     * @throws Exception
     */
    @Override
    public SalesEntity getSalesInfoBySalesId(int salesId) {
        Map<String, Object> map = new HashMap<>();
        map.put("salesId", salesId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER
                + ".getSalesInfoBySalesId", map);
    }

    /**
     * @Title:companyAgentSalesList
     * @Description:公司列表（含店面资料，员工资料）
     * @param author: liuzhanchao
     * @return
     */
    @Override
    public List<ParentAgentChildAgentSalesEntity> getParentAgentChildAgentSalesList() {
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT
                + ".getParentAgentChildAgentSalesList");
    }

    /**
     * @Title:updateSalesStatus
     * @Description:修改员工状态
     * @param 员工Id
     * @param status
     *            1正常2冻结3逻辑移除
     * @return
     * @param author: liuzhanchao
     * @throws Exception
     */
    @Override
    public int updateSalesStatusBySalesId(int salesId, int loginStatus) {
        Map<String, Object> map = new HashMap<>();

        map.put("salesId", salesId);
        map.put("loginStatus", loginStatus);
        return sqlSessionTemplate.update(NameSpaceConstant.USER
                + ".updateSalesStatusBySalesId", map);
    }

    /**
     * @Title:updateSalesAgentBySalseId
     * @Description:修改员工所属店面
     * @param 员工Id
     * @param 店面Id
     * @param author: liuzhanchao
     * @return
     * @throws Exception
     */
    @Override
    public int updateSalesAgentBySalseId(int salesId, int agentId,
            String agentName) {
        Map<String, Object> map = new HashMap<>();

        map.put("salesId", salesId);
        map.put("agentId", agentId);
        map.put("agentName", agentName);
        return sqlSessionTemplate.update(NameSpaceConstant.USER
                + ".updateSalesAgentBySalesId", map);
    }

    /**
     * @Title:updateSalesAgentBySalseId
     * @Description:修改会员所属员工
     * @param 员工Id
     * @param 店面Id
     * @param author: liuzhanchao
     * @return
     * @throws Exception
     */
    @Override
    public int updateUserSalesBySalseId(int oldSalesId, int salesId,
            String salesName, int agentId, String agentName) {
        Map<String, Object> map = new HashMap<>();
        map.put("oldSalesId", oldSalesId);
        map.put("salesId", salesId);
        map.put("salesName", salesName);
        map.put("agentId", agentId);
        map.put("agentName", agentName);
        return sqlSessionTemplate.update(NameSpaceConstant.USER
                + ".updateUserSalesBySalesId", map);
    }

    /**
     * @Title:pullSetList
     * @Description:拉新设置表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<SalesPullNewSetEntity> getPullSetList() throws ServiceException {
        return sqlSessionTemplate.selectList(NameSpaceConstant.PULL_NEW
                + ".getPullSetList");
    }

    /**
     * @Title: salesBaseInfo
     * @Description:根据主键查询员工基本资料
     * @param id
     *            主键
     * @return
     * @param author: liuzhanchao
     * @throws Exception
     */
    @Override
    public SalesBaseEntity getSalesBaseInfoBySalesId(int salesId) {
        Map<String, Object> map = new HashMap<>();
        map.put("salesId", salesId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER
                + ".getSalesBaseInfoBySalesId", map);
    }

    /**
     * @Title:updateSalesAgentBySalseId
     * @Description:修改员工所属店面
     * @param 员工Id
     * @param 店面Id
     * @param author: liuzhanchao
     * @return
     * @throws Exception
     */
    @Override
    public int updateBarcodeurl(int agentId, String barcodeUrl) {
        Map<String, Object> map = new HashMap<>();

        map.put("barcodeUrl", barcodeUrl);
        map.put("agentId", agentId);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT
                + ".updateBarcodeurl", map);
    }

    /**
     * @Title: ChildAgentSalesCountInfoByChildAgentId
     * @Description:根据主键查询店面资料
     * @param id
     *            主键
     * @return
     * @throws Exception
     */
    public ChildAgentEntity getChildAgentSalesCountInfoByChildAgentId(
            int childAgentId) {
        Map<String, Object> map = new HashMap<>();
        map.put("childAgentId", childAgentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT
                + ".getChildAgentSalesCountInfoByChildAgentId", map);
    }
    /**
     *<p>Description:根据二维码键值查组织</p>
     *<p>param codekey:键值</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/21 16:50</p>
     *<p>return:</p>
     */
    public AgentEntity selectByCodekey(String codekey){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT+".selectByCodekey",codekey);
    }
    /**
     *<p>Description:通过公司id查询会员信息</p>
     *<p>param companyId:公司id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/27 13:55</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public List<MyShopEntity> getShopListByAgentId(int companyId){
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT+".getShopListByAgentId",companyId);

    }

    /**
     *<p>Description:通过agentId修改agentName</p>
     *<p>param agentId:Id</p>
     *<p>param agentName:名称</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2018/1/4 13:35</p>
     */
    @Override
    public int updateAgentNameByAgentIdOnAgentRole(int agentId,String agentName){
        Map param=new HashMap();
        param.put("agentId",agentId);
        param.put("agentName",agentName);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT + ".updateAgentNameByAgentIdOnAgentRole", param);
    }

    /**
     *<p>Description:通过agentId修改agentName</p>
     *<p>param agentId:Id</p>
     *<p>param agentName:名称</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2018/1/4 13:35</p>
     */
    @Override
    public int updateAgentNameByAgentIdOnAgentIncome(int agentId,String agentName){
        Map param=new HashMap();
        param.put("agentId",agentId);
        param.put("agentName",agentName);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT + ".updateAgentNameByAgentIdOnAgentIncome", param);
    }

    /**
     *<p>Description:通过agentId修改agentName</p>
     *<p>param agentId:Id</p>
     *<p>param agentName:名称</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2018/1/4 13:35</p>
     */
    @Override
    public int updateAgentNameByAgentIdOnAgentWithdraw(int agentId,String agentName){
        Map param=new HashMap();
        param.put("agentId",agentId);
        param.put("agentName",agentName);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT + ".updateAgentNameByAgentIdOnAgentWithdraw", param);
    }
    /**
     *<p>Description:查询公司列表</p>
     *<p>param status:状态</p>
     *<p>param agentName:公司名称</p>
     *<p>param type:1 员工 2：会员</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/23 14:16</p>
     */
    public List<AgentCouponEntity> selectCompanyByPostCounpon(int status,String agentName,int type){
        Map param=new HashMap();
        param.put("status",status);
        param.put("agentName",agentName);
        param.put("type",type);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT + ".selectCompanyByPostCounpon", param);
    }

}
