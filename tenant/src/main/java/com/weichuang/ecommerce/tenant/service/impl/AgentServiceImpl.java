package com.weichuang.ecommerce.tenant.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.RedisHelper;
import com.weichuang.ecommerce.tenant.entity.*;
import com.weichuang.ecommerce.tenant.entity.request.AgentRequest;
import com.weichuang.ecommerce.tenant.entity.response.*;
import com.weichuang.ecommerce.tenant.feign.IBarCode;
import com.weichuang.ecommerce.tenant.repository.IAgentDao;
import com.weichuang.ecommerce.tenant.repository.IUserAgentRoleDao;
import com.weichuang.ecommerce.tenant.service.IAgentService;

import com.weichuang.ecommerce.tenant.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * ClassName:AgentServiceImpl
 * </p>
 * <p>
 * Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * author:zhanghongsheng
 * </p>
 * <p>
 * 2017/11/2 10:29
 * </p>
 **/
@Service
public class AgentServiceImpl implements IAgentService {
    @Autowired
    private IAgentDao agentDao;

    @Autowired
    private IBarCode Ibarcode;
    @Value("${webchat.h5NetworkUrl}")
    private String h5NetworkUrl;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private IUserService userService;

    @Autowired
    private IUserAgentRoleDao userAgentRoleDao;

    /**
     * <p>
     * Description:通过id或者代理商名称查询
     * </p>
     * <p>
     * param parentId:编号
     * </p>
     * <p>
     * param agentName:名称
     * </p>
     * <p>
     * author:zhanghongsheng
     * </p>
     * <p>
     * date:2017/11/2 11:02
     * </p>
     * <p>
     * return:
     * </p>
     * <p>
     * throws:
     * </p>
     */
    @Transactional(readOnly = true)
    public AgentListResponse getAgentList(Integer parentId, String agentName) {
        AgentListResponse response = new AgentListResponse();
        List<AgentEntity> list = agentDao.getAgentList(parentId, agentName);
        response.setList(list);
        return response;
    }

    /**
     * <p>
     * Description:通过id或者代理商名称查询
     * param parentId:编号
     * param agentName:名称
     * author:zhanghongsheng
     * date:2017/12/27 16:02
     * </p>
     */
    @Transactional(readOnly = true)
    public AgentAppListResponse getAppAgentList(Integer pageNum,Integer pageSize,Integer parentId, String agentName){
        AgentAppListResponse response=new AgentAppListResponse();
        PageHelper.startPage(pageNum,pageSize);
        List<AgentEntity> list = agentDao.getAgentList(parentId, agentName);
        PageInfo page=new PageInfo(list);
        response.setPages(page.getPages());
        response.setTotal(page.getTotal());
        response.setList(list);
        return response;
    }
    /**
     * @Title:addCompany
     * @Description:增加公司,后台使用
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int addParentAgent(AgentRequest request) throws ServiceException {
        AgentEntity entity = new AgentEntity();
        entity.setAgentName(request.getAgentName());
        entity.setAgentCode(getAgentCode(request.getParentId()));
        entity.setAddress(request.getAddress());
        entity.setCorporation(request.getCorporation());
        
        int res = agentDao.addAgent(entity);
        if (res >= 0) {

            AgentResponse agentInfo2 = getAgentInfoByAgentName(request.getAgentName());
            int agentId = agentInfo2.getEntity().getId();
            redisHelper.set("addBarCode:company:"+agentId,JSON.toJSONString(agentInfo2.getEntity()));
            redisHelper.expire("addBarCode:company:"+agentId,600);
            // 调用生成二维码接口(公司)
            // 公司：{"codeType":"1","actionType":1,"hasImg":1,"userAgentId":"1","nx":"2","storeData":"http://weixin.jinjixiadan.com/redirect1","addressUrl":"http://weixin.jinjixiadan.com/index"}
            String storeData = h5NetworkUrl+"/redirect1";
            int codeType = 1;
            int actionType = 1;
            int userAgentId = agentId;
            int nx = 2;
            String addressUrl = h5NetworkUrl+"/index";
            boolean hasImg = true;
            String otherData = null;
            Response<TenantCodeStoreResponse> response = Ibarcode.addBarCode(
                    storeData, codeType, actionType, userAgentId, nx,
                    addressUrl, hasImg, otherData);
            //Ibarcode.addBarCode(storeData, codeType, actionType, userAgentId, nx, addressUrl, hasImg, otherData);
            String codeKey = response.getValue().getEntity().getCodeKey();
            String barcodeUrl = h5NetworkUrl+"/webchat/getImgCache?key="
                    + codeKey;

            // 增加二维码地址
            agentDao.updateBarcodeurl(agentId, barcodeUrl);
        }

        return res;
    }

    /**
     * @Title:addChildAgent
     * @Description:增加店面,后台使用
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int addChildAgent(AgentRequest request) throws ServiceException {
        AgentEntity entity = new AgentEntity();
        entity.setAgentName(request.getAgentName());
        entity.setAgentCode(getAgentCode(request.getParentId()));
        entity.setAddress(request.getAddress());
        entity.setParentId(request.getParentId());

       
        int res = agentDao.addAgent(entity);
        if (res >= 0) {

            AgentResponse agentInfo2 = getAgentInfoByAgentName(request
                    .getAgentName());
            int agentId = agentInfo2.getEntity().getId();
            AgentResponse agentInfoResponse = this.getCompanyInfoByAgentId(agentId);
            AgentEntity companyinfo=agentInfoResponse.getEntity();
            if(companyinfo!=null){
                String companyName=companyinfo.getAgentName();
                Map shopMap=new HashMap();
                shopMap.put("shopId",agentId);
                shopMap.put("shopName",request.getAgentName());
                shopMap.put("companyId",companyinfo.getId());
                shopMap.put("companyName",companyName);
                redisHelper.set("addBarCode:shop:"+agentId, JSON.toJSONString(shopMap).toString());
                redisHelper.expire("addBarCode:shop:"+agentId,600);
            }
            // 调用生成二维码接口(公司)
            // 公司的：{"codeType":"1","actionType":1,"hasImg":1,"userAgentId":"1","nx":"2","storeData":"http://weixin.jinjixiadan.com/redirect1","addressUrl":"http://weixin.jinjixiadan.com/index"}
            // 门店的：{"codeType":"2","actionType":1,"hasImg":1,"userAgentId":"7","nx":"2","storeData":"http://weixin.jinjixiadan.com/redirect1","addressUrl":"http://weixin.jinjixiadan.com/index"}

            String storeData = h5NetworkUrl+"/redirect1";
            int codeType = 2;
            int actionType = 1;
            int userAgentId = agentId;
            int nx = 2;

            String addressUrl = h5NetworkUrl+"/index";
            boolean hasImg = true;
            String otherData = null;
            Response<TenantCodeStoreResponse> response = Ibarcode.addBarCode(
                    storeData, codeType, actionType, userAgentId, nx,
                    addressUrl, hasImg, otherData);
            String codeKey = response.getValue().getEntity().getCodeKey();

            String barcodeUrl = h5NetworkUrl+"/webchat/getImgCache?key="
                    + codeKey;

            // 增加二维码地址
            agentDao.updateBarcodeurl(agentId, barcodeUrl);

        }

        return res;
    }

    /**
     * @Title:updateProduct
     * @Description:修改公司,后台使用
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int updateAgent(AgentRequest request) throws ServiceException {
        AgentEntity entity = new AgentEntity();
        int agentId=request.getId();
        String agentName=request.getAgentName();
        entity.setAgentName(request.getAgentName());
        entity.setAddress(request.getAddress());
        entity.setOperator(request.getOperator());
        entity.setOperatorName(request.getOperatorName());
        entity.setId(request.getId());
       if(request.getCorporation()!=null){
           
           entity.setCorporation(request.getCorporation());
       }
        agentDao.updateAgent(entity);
       agentDao.updateAgentNameByAgentIdOnAgentIncome(agentId,agentName);
       agentDao.updateAgentNameByAgentIdOnAgentRole(agentId,agentName);
       agentDao.updateAgentNameByAgentIdOnAgentWithdraw(agentId,agentName);
       int res= userAgentRoleDao.updateAgentNameByAgentId(agentId,agentName);

        
        return res;
    }

    /**
     * @Title:updateAgentStatus
     * @Description:设置公司合作状态,1合作中--0未合作

     * @return
     */
    @Override
    @Transactional
    public int updateAgentStatusByAgentId(int agentId,int operator, String operatorName,
            int status) throws ServiceException {
        return agentDao.updateAgentStatusByAgentId(agentId,operator,operatorName,
                status);
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
			int operator, String operatorName, int status) throws ServiceException{
		return  agentDao.updateAgentStatusByParentAgentId(parentAgentId, operator,
				operatorName, status);
		
	}

    /**
     * @Title:getProductInfo
     * @Description:根据商品主键或者商品码查询商品详情
     * @param productCode
     * @param productId
     * @return
     */
    /*
     * @Override
     * 
     * @Transactional(readOnly = true) public CompanyDetailResponse
     * getCompanyDetail( int companyId) throws ServiceException {
     * CompanyDetailEntity entity = companyDao.getCompanyDetail( companyId);
     * CompanyDetailResponse response = new CompanyDetailResponse();
     * response.setEntity(entity); return response; }
     */

    /**
     * @Title:getProductList
     * @Description:根据商品名称,状态,创建时间查询商品集合,后台使用
     * @param status
     * @param name
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ParentAgentListResponse getParentAgentList(Integer status,
            String keyword, int pageNum, int pageSize) throws ServiceException {
        // 执行查询数据和分页处理
        ParentAgentListResponse response = new ParentAgentListResponse();
        PageHelper.startPage(pageNum, pageSize);
        List<ParentAgentEntity> list = agentDao.getParentAgentList(status,
                keyword);
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;
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
	public ParentAgentBaseListResponse getParentAgentBaseList(int status){
		 List<ParentAgentBaseEntity> list = agentDao.getParentAgentBaseList(status);
		 ParentAgentBaseListResponse response = new ParentAgentBaseListResponse();
	        
	        response.setList(list);// 返回的数据集合
	        
	        return response;
	}

    /**
     * 根据代理id获取基本信息
     */
    @Override
    @Transactional(readOnly = true)
    public AgentResponse getAgentInfoByAgentId(int agentId)
            throws ServiceException {
        AgentEntity entity = agentDao.getAgentInfoByAgentId(agentId);
        AgentResponse response = new AgentResponse();
        response.setEntity(entity);
        return response;
    }
    /**
     * @Title:getAgentInfobyId
     * @Description:根据组织id获取公司基本资料
     * @param agentId
     * @return
     */
    public AgentResponse getCompanyInfoByAgentId(int agentId)
            throws ServiceException{
        AgentEntity entity=getCompanyInfo(agentId);
        AgentResponse response = new AgentResponse();
        response.setEntity(entity);
        return response;
    }
    private AgentEntity getCompanyInfo(int agentId){
        AgentEntity entity = agentDao.getAgentInfoByAgentId(agentId);
        if(entity!=null){
            int parentId=entity.getParentId();
            if(entity.getParentId()>0){
                AgentEntity entity1 = agentDao.getAgentInfoByAgentId(parentId);
                if(entity1.getParentId()==0){
                    return entity1;
                }
                return null;
            }
            else{
                return entity;
            }
        }
        return null;

    }
    /**
     * 根据代理名称获取基本信息
     */
    @Override
    @Transactional(readOnly = true)
    public AgentResponse getAgentInfoByAgentName(String agentName)
            throws ServiceException {
        AgentEntity entity = agentDao.getAgentInfoByAgentName(agentName);
        AgentResponse response = new AgentResponse();
        response.setEntity(entity);
        return response;
    }

    /**
     * 根据代理编号获取基本信息
     */
    @Override
    @Transactional(readOnly = true)
    public AgentResponse getAgentInfoByAgentCode(String agentCode)
            throws ServiceException {
        AgentEntity entity = agentDao.getAgentInfoByAgentCode(agentCode);
        AgentResponse response = new AgentResponse();
        response.setEntity(entity);
        return response;
    }

    /**
     * @Title:getSalseCount
     * @Description:根据agentId查询员工数量
     * @param companyId
     * @return
     */
    @Override
    @Transactional
    public int getSalseCountByParentAgentId(int parentAgentId)
            throws ServiceException {
        return agentDao.getSalseCountByParentAgentId(parentAgentId);
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
    @Transactional
	public int getUserCountByParentAgentId(int parentAgentId)
			throws ServiceException {
		return agentDao.getUserCountByParentAgentId(parentAgentId);

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
    @Transactional
	public int getUserCountByAgentId(int agentId) throws ServiceException {
    	return agentDao.getUserCountByAgentId(agentId);
    }

    /**
     * @Title:getSalseCount
     * @Description:根据agentId查询员工数量
     * @param companyId
     * @return
     */
    @Override
    @Transactional
    public int getSalseCountTodayByParentAgentId(int parentAgentId)
            throws ServiceException {
        String date = com.weichuang.ecommerce.tenant.constants.DateUtil
                .getDate_str();
        return agentDao.getSalseCountTodayByParentAgentId(parentAgentId, date);
    }

    /**
     * 生成代理编号
     * 
     * @throws ServiceException
     */

    // 代理code生成规则,按照代理级别生成,公司还是门店
    public String getAgentCode(int parentId) throws ServiceException {
        String code = "";
        if (parentId == 0) {
            do {
                code = "JSGS" + parentId + codeNumber();
            }
            while (getAgentInfoByAgentCode(code).getEntity() != null);

        }
        else {
            do {
                code = "JSF" + parentId + codeNumber();
            }
            while (getAgentInfoByAgentCode(code).getEntity() != null);
        }
        return code;
    }

    // 随机生成4位数字
    public static int codeNumber() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

    /**
     * @Title:getShopInfoList
     * @Description:根据店面名称,所属公司 ，状态,编号获取店面集合,后台使用
     * @param status
     * @param name
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ChildAgentListResponse getChildAgentInfoList(int status,
            int parentId, String keyWord, int pageNum, int pageSize)
            throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<ChildAgentEntity> list = agentDao.getChildAgentInfoList(status,
                parentId, keyWord);
        ChildAgentListResponse response = new ChildAgentListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;
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
    @Transactional(readOnly = true)
    public ChildAgentListResponse getChildAgentSalesCountInfoList(int status,
            int agentId, String keyWord, int pageNum, int pageSize)
            throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<ChildAgentEntity> list = agentDao.getChildAgentSalesCountInfoList(status,
                agentId, keyWord);
        ChildAgentListResponse response = new ChildAgentListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;
    }
    /**
     * 根据公司id获取基本信息（包含店面资料）
     */
    @Override
    @Transactional(readOnly = true)
    public ParentAgentResponse getParentAgentInfoByParentAgentId(
            int parentAgentId) throws ServiceException {
        ParentAgentEntity entity = agentDao
                .getParentAgentInfoByParentAgentId(parentAgentId);
        ParentAgentResponse response = new ParentAgentResponse();
        response.setEntity(entity);
        return response;
    }

    /**
     * 根据公司店面获取店面信息（包含员工资料）
     */
    @Override
    @Transactional(readOnly = true)
    public ChildAgentResponse getChildAgentInfoByChildAgentId(int childAgentId)
            throws ServiceException {
        ChildAgentEntity entity = agentDao
                .getChildAgentInfoByChildAgentId(childAgentId);
        ChildAgentResponse response = new ChildAgentResponse();
        response.setEntity(entity);
        return response;
    }
    
    /**
     * @Title: ChildAgentSalesCountInfoByChildAgentId
     * @Description:根据主键查询店面资料
     * @param id
     *            主键
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public ChildAgentResponse getChildAgentSalesCountInfoByChildAgentId(
            int childAgentId){
        ChildAgentEntity entity = agentDao
                .getChildAgentSalesCountInfoByChildAgentId(childAgentId);
        ChildAgentResponse response = new ChildAgentResponse();
        response.setEntity(entity);
        return response;
    }

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
    @Override
    @Transactional(readOnly = true)
    public SalesListResponse getSalesList(int roleId, int agentId,int status,
            String keyWord, int pageNum, int pageSize) throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<SalesEntity> list = agentDao
                .getSalesList(roleId, agentId,status, keyWord);
        SalesListResponse response = new SalesListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;
    }

    public SaleListAppResponse getSalesAppList(int companyId,int roleId, int agentId,
                                               String keyWord, int pageNum, int pageSize) throws ServiceException,Exception{
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<SalesEntity> list = agentDao
                .getSalesAppList(companyId,roleId, agentId, keyWord);
        List<SalesAppEntity> applist=null;
        SaleListAppResponse response = new SaleListAppResponse();
        PageInfo pageInfo = new PageInfo(list);
        if(list!=null &&!list.isEmpty()){
            applist=new ArrayList<SalesAppEntity>();
            for(SalesEntity entity:list){
                SalesAppEntity appEntity=new SalesAppEntity();
                BeanUtils.copyProperties(entity,appEntity);
                int userid=appEntity.getId();
                MyCustomerResponse mresponse= userService.selectUserCountByUserId(1,userid);
                MyCustomerEntity entity1=mresponse.getEntity();
                if(entity1==null){
                    throw new ServiceException(60001,"getSalesAppList ,selectUserCountByUserId MyCustomerEntity is null");
                }
                appEntity.setInviteDayNum(entity1.getDayInviteNum());
                appEntity.setCouponDayNum(entity1.getDayCouponNum());
                applist.add(appEntity);
            }
        }
        response.setList(applist);
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;
    }
    /**
     * @Title: salesInfo
     * @Description:根据主键查询员工资料
     * @param id
     *            主键
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public SalesResponse getSalesInfoBySalesId(int salesId)
            throws ServiceException {
        SalesEntity entity = agentDao.getSalesInfoBySalesId(salesId);
        SalesResponse response = new SalesResponse();
        response.setEntity(entity);
        return response;
    }

    /**
     * @Title:companyAgentSalesList
     * @Description:公司列表（含店面资料，员工资料）
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ParentAgentChildAgentSalesListResponse getParentAgentChildAgentSalesList()
            throws ServiceException {
        // 执行查询数据和分页处理

        List<ParentAgentChildAgentSalesEntity> list = agentDao
                .getParentAgentChildAgentSalesList();
        ParentAgentChildAgentSalesListResponse response = new ParentAgentChildAgentSalesListResponse();

        response.setList(list);// 返回的数据集合

        return response;
    }

    /**
     * @Title:updateSalesStatus
     * @Description:修改员工状态
     * @param 员工Id
     * @param status
     *            1正常2冻结3逻辑移除
     * @return
     * @throws Exception
     */

    @Override
    @Transactional
    public int updateSalesStatusBySalesId(int salesId, int loginStatus)
            throws ServiceException {
        return agentDao.updateSalesStatusBySalesId(salesId, loginStatus);
    }

    /**
     * @Title:updateSalesAgentBySalseId
     * @Description:修改员工所属店面
     * @param 员工Id
     * @param 店面Id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public int updateSalesAgentBySalseId(int salesId, int agentId,String agentName)
            throws ServiceException {
        
        return agentDao.updateSalesAgentBySalseId(salesId, agentId,agentName);
    }

    /**
     * @Title:updateSalesAgentBySalseId
     * @Description:修改会员所属员工
     * @param 员工Id
     * @param 店面Id
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public int updateUserSalesBySalseId(int oldSalesId, int salesId,
            String salesName, int agentId, String agentName) throws ServiceException {
        return agentDao.updateUserSalesBySalseId(oldSalesId, salesId,salesName, agentId,agentName);

    }

    /**
     * @Title:pullSetList
     * @Description:拉新设置表
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public SalesPullNewSetListResponse getPullSetList() throws ServiceException {

        List<SalesPullNewSetEntity> list = agentDao.getPullSetList();
        SalesPullNewSetListResponse response = new SalesPullNewSetListResponse();

        response.setList(list);// 返回的数据集合

        return response;

    }

    /**
     * @Title: salesBsaeInfo
     * @Description:根据主键查询员工基本资料
     * @param id
     *            主键
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(readOnly = true)
    public SalesBaseResponse getSalesBaseInfoBySalesId(int salesId)
            throws ServiceException {
        SalesBaseEntity entity = agentDao.getSalesBaseInfoBySalesId(salesId);
        SalesBaseResponse response = new SalesBaseResponse();
        response.setId(entity.getId());
        response.setUserName(entity.getUserName());
        response.setAgentId(entity.getAgentId());
        response.setAgentName(entity.getAgentName());
        response.setParentAgentId(entity.getParentAgentId());
        response.setParentAgentName(entity.getParentAgentName());
        return response;
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
    @Transactional(readOnly = true)
    public SalesListResponse getSalesDetailByAgentId(int agentId,int pageNum,int pageSize) {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<SalesEntity> list = agentDao
                .getSalesDetailByAgentId(agentId);
        SalesListResponse response = new SalesListResponse();
        PageInfo pageInfo = new PageInfo(list);
        
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;



    }
    /**
     *<p>Description:通过公司id查询会员信息</p>
     *<p>param companyId:公司id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/27 13:55</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public MyShopListResponse getShopListByAgentId(int companyId)throws ServiceException,Exception{
        MyShopListResponse response=new MyShopListResponse();
        List<MyShopEntity> list=agentDao.getShopListByAgentId(companyId);
        if(list!=null &&!list.isEmpty()){
            for(MyShopEntity entity:list){
                int agentId=entity.getAgentId();
                MyCustomerResponse mresponse= userService.selectUserCountByUserId(2,agentId);
                MyCustomerEntity entity1=mresponse.getEntity();
                if(entity1==null){
                    throw new ServiceException(60001,"getShopListByAgentId ,MyCustomerEntity is null");
                }
                entity.setInviteNum(entity1.getDayInviteNum());
                //entity.setCouponNum(entity1.getno);
                entity.setCouponNum(entity1.getDayCouponNum());
            }
        }
        response.setList(list);
        return response;
    }
    /**
     *<p>Description:查询公司列表</p>
     *<p>param status:状态</p>
     *<p>param agentName:公司名称</p>
     *<p>param type:1 员工 2：会员</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/23 14:16</p>
     */
    public AgentCouponEntityResponse selectCompanyByPostCounpon(Integer status, String agentName, Integer type) throws ServiceException,Exception{

        if(type==null || type==0){
            throw new ServiceException(81001,"type value is error");
        }
        if(status==null ){
            throw new ServiceException(81002,"status value is error");
        }
        List<AgentCouponEntity> list=agentDao.selectCompanyByPostCounpon(status,agentName,type);
        AgentCouponEntityResponse response=new AgentCouponEntityResponse();
        response.setList(list);
        return response;
    }

}
