package com.weichuang.ecommerce.tenant.resource;

import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.AgentEntity;
import com.weichuang.ecommerce.tenant.entity.request.AgentRequest;
import com.weichuang.ecommerce.tenant.entity.response.*;
import com.weichuang.ecommerce.tenant.service.IAgentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.xml.ws.Service;

/**
 * <p>
 * ClassName:AgentResource
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
 * 2017/11/2 10:21
 * </p>
 **/
@Path("/agent")
@Api(value = "/agentResource", description = "description")
public class AgentResource {
	private static final Logger log = LoggerFactory
			.getLogger(AgentResource.class);

	@Autowired
	private IAgentService agentService;

	// 根据主键或者名称查询详情

	/**
	 * <p>
	 * Description:
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
	 * date:2017/11/2 13:45
	 * </p>
	 * <p>
	 * return:
	 * </p>
	 * <p>
	 * throws:
	 * </p>
	 */
	@ApiOperation(value = "/get/agentInfo", notes = "getAgentInfo")
	@GET
	@Path("/get/agentInfo/")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AgentListResponse> getAgentInfo(
			@QueryParam("parentId") Integer parentId,
			@QueryParam("agentName") String agentName) {
		Response<AgentListResponse> response = null;
		try {
			response = new Response<>(Result.SUCCESS,
					agentService.getAgentList(parentId, agentName));
		} catch (Exception ex) {
			response = new Response<>(Result.FAIL);
			log.error("查询代理商getAgentInfo方法异常:{}", ex);
			ex.printStackTrace();
		}
		return response;
	}
	@ApiOperation(value = "/get/app/agentInfo", notes = "getAppAgentInfo")
	@GET
	@Path("/get/app/agentInfo/")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AgentAppListResponse> getAppAgentInfo(
			@QueryParam("parentId") Integer parentId,
			@QueryParam("agentName") String agentName,
			@QueryParam("pageNum") Integer pageNum,
			@QueryParam("pageSize") Integer pageSize) {
		Response<AgentAppListResponse> response = null;
		try {
			response = new Response<AgentAppListResponse>(Result.SUCCESS,
					agentService.getAppAgentList(pageNum,pageSize,parentId, agentName));
		} catch (Exception ex) {
			response = new Response<>(Result.FAIL);
			log.error("查询代理商getAppAgentInfo方法异常:{}", ex);
			ex.printStackTrace();
		}
		return response;
	}
	/**
	 * 
	 * <p>
	 * Company: 伟创科技(北京)科技有限公司
	 * </p>
	 * <p>
	 * Description:公司类api接口
	 * </p>
	 * <p>
	 * author liuzhanchao
	 * </p>
	 * <p>
	 * 2017年11月2日 下午2:00
	 * </p>
	 */

	/**
	 * @Title:addParentAgent
	 * @Description:增加
	 * @param productRequest
	 *            请求参数
	 * @return
	 */
	@PUT
	@Path("/add/parentAgent")
	@ApiOperation(value = "/add/parentAgent")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> addParentAgent(AgentRequest agentRequest)
			throws Exception {
		Response<String> response = null;
		AgentResponse agentInfo = agentService
				.getAgentInfoByAgentName(agentRequest.getAgentName());
		AgentEntity info = agentInfo.getEntity();
		if (info != null) {
			response = new Response(Result.AGENT_EXIST.getCode(),
					Result.AGENT_EXIST.getMessage());
		} else {

			agentService.addParentAgent(agentRequest);
			response = new Response(Result.SUCCESS.getCode(),
					Result.SUCCESS.getMessage());
		}
		return response;

	}

	/**
	 * @Title:addChildAgent
	 * @Description:增加
	 * @param productRequest
	 *            请求参数
	 * @return
	 */
	@PUT
	@Path("/add/childAgent")
	@ApiOperation(value = "/add/childAgent")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> addChildAgent(AgentRequest agentRequest)
			throws Exception {
		Response<String> response = null;
		AgentResponse agentInfo = agentService
				.getAgentInfoByAgentName(agentRequest.getAgentName());
		AgentEntity info = agentInfo.getEntity();
		if (info != null) {
			response = new Response(Result.AGENT_EXIST.getCode(),
					Result.AGENT_EXIST.getMessage());
		} else {
			agentService.addChildAgent(agentRequest);
			response = new Response(Result.SUCCESS.getCode(),
					Result.SUCCESS.getMessage());
		}
		return response;

	}

	/**
	 * @Title: updateParentAgent
	 * @Description: 修改
	 * @param ParentAgentRequest
	 *            请求的参数
	 * @return
	 */
	@POST
	@Path("/update/Agent")
	@ApiOperation(value = "/update/Agent")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updatetAgent(AgentRequest agentRequest)
			throws Exception {
		Response<String> response = null;
		AgentResponse agentInfo = agentService
				.getAgentInfoByAgentName(agentRequest.getAgentName());
		AgentEntity info = agentInfo.getEntity();
		if (info != null && info.getId()!=agentRequest.getId()) {
			response = new Response(Result.AGENT_EXIST.getCode(),
					Result.AGENT_EXIST.getMessage());
		} else {
		agentService.updateAgent(agentRequest);
		 response = new Response(Result.SUCCESS.getCode(),
				Result.SUCCESS.getMessage());
		}
		return response;
	}

	/**
	 * @Title:parentAgentList
	 * @Description:管理列表显示
	 * @param name
	 *            公司，法人，或公司编号
	 * @param status
	 *            状态1正常2禁止
	 * @return
	 */
	@GET
	@Path("/get/parentAgent/list")
	@ApiOperation(value = "/get/parentAgent/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ParentAgentListResponse> getParentAgentList(
			@QueryParam("status") int status,
			@QueryParam("keyWord") String keyWord,

			@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize) throws Exception {

		Response<ParentAgentListResponse> res = null;
		try {
			ParentAgentListResponse list = this.agentService
					.getParentAgentList(status, keyWord, pageNum, pageSize);
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<ParentAgentListResponse>(Result.FAIL.getCode(),
					"getParentAgentList查询异常：" + ex.getMessage());
			log.error("getParentAgentList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
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
	@GET
	@Path("/get/parentAgent/base/list")
	@ApiOperation(value = "/get/parentAgent/base/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ParentAgentBaseListResponse> getParentAgentBaseList(
			@QueryParam("status") int status) throws Exception {

		Response<ParentAgentBaseListResponse> res = null;
		try {
			ParentAgentBaseListResponse list = this.agentService
					.getParentAgentBaseList(status);
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<ParentAgentBaseListResponse>(Result.FAIL.getCode(),
					"getParentAgentBaseList查询异常：" + ex.getMessage());
			log.error("getParentAgentBaseList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title: parentAgentInfo
	 * @Description:根据主键查询详情信息
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/parentAgentInfo/parentAgentId")
	@ApiOperation(value = "/get/parentAgentInfo/parentAgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ParentAgentResponse> getParentAgentInfoByParentAgentId(
			@QueryParam("parentAgentId") int parentAgentId) throws Exception {

		Response<ParentAgentResponse> res = null;
		try {
			ParentAgentResponse info = this.agentService
					.getParentAgentInfoByParentAgentId(parentAgentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<ParentAgentResponse>(Result.FAIL.getCode(),
					"getParentAgent查询异常：" + ex.getMessage());
			log.error("getParentAgent查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title:updateParentAgentStatusByparentAgentId
	 * @Description:修改合作状态
	 * @param companyId
	 *            公司主键
	 * @param status
	 *            1合作2未合作
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/update/agentStatus/agentId")
	@ApiOperation(value = "/update/agentStatus/agentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateAgentStatusByAgentId(
			@QueryParam("agentId") int agentId,
			@QueryParam("operator") int operator,
			@QueryParam("operatorName") String operatorName,
			@QueryParam("status") int status) throws Exception {
		agentService.updateAgentStatusByAgentId(agentId, operator,
				operatorName, status);
		Response<String> response = new Response(Result.SUCCESS.getCode(),
				Result.SUCCESS.getMessage());
		return response;
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
	@POST
	@Path("/update/agentStatus/parentAgentId")
	@ApiOperation(value = "/update/agentStatus/parentAgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateAgentStatusByParentAgentId(
			@QueryParam("parentAgentId") int parentAgentId,
			@QueryParam("operator") int operator,
			@QueryParam("operatorName") String operatorName,
			@QueryParam("status") int status) throws Exception {
		
		agentService.updateAgentStatusByParentAgentId(parentAgentId, operator,
				operatorName, status);
		
		agentService.updateAgentStatusByAgentId(parentAgentId, operator,
				operatorName, status);
		Response<String> response = new Response(Result.SUCCESS.getCode(),
				Result.SUCCESS.getMessage());
		return response;
	}

	/**
	 * @Title: agentInfo
	 * @Description:根据主键查询公司基本资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/agentInfo/agentId")
	@ApiOperation(value = "/get/agentInfo/agentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AgentResponse> getAgentInfoByAgentId(

	@QueryParam("agentId") int agentId) throws Exception {
		Response<AgentResponse> res = null;
		try {
			AgentResponse info = this.agentService
					.getAgentInfoByAgentId(agentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<AgentResponse>(Result.FAIL.getCode(),
					"getAgentInfo查询异常：" + ex.getMessage());
			log.error("getAgentInfo查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	@GET
	@Path("/get/companyInfo/agentId")
	@ApiOperation(value = "/get/companyInfo/agentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AgentResponse> getCompanyInfoByAgentId(@QueryParam("agentId") int agentId) throws Exception {
		Response<AgentResponse> res = null;
		try {
			AgentResponse info = this.agentService.getCompanyInfoByAgentId(agentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<AgentResponse>(Result.FAIL.getCode(),
					"getAgentInfo查询异常：" + ex.getMessage());
			log.error("getAgentInfo查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	/**
	 * @Title: agentInfo
	 * @Description:根据公司名称查询公司基本资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/agentInfo/agentName")
	@ApiOperation(value = "/get/agentInfo/agentName")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AgentResponse> getAgentInfoByAgentName(

	@QueryParam("agentName") String agentName) throws Exception {
		Response<AgentResponse> res = null;
		try {
			AgentResponse info = this.agentService
					.getAgentInfoByAgentName(agentName);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<AgentResponse>(Result.FAIL.getCode(),
					"getAgentInfo查询异常：" + ex.getMessage());
			log.error("getAgentInfo查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title: shopInfo
	 * @Description:根据主键查询店面资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/childAgentInfo/childAgentId")
	@ApiOperation(value = "/get/childAgentInfo/childAgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ChildAgentResponse> getChildAgentInfoByChildAgentId(

	@QueryParam("childAgentId") int childAgentId) throws Exception {
		Response<ChildAgentResponse> res = null;
		try {
			ChildAgentResponse info = this.agentService
					.getChildAgentInfoByChildAgentId(childAgentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<ChildAgentResponse>(Result.FAIL.getCode(),
					"getChildAgentInfo查询异常：" + ex.getMessage());
			log.error("getChildAgentInfo查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title: ChildAgentSalesCountInfoByChildAgentId
	 * @Description:根据主键查询店面资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/childAgentSalesCountInfo/childAgentId")
	@ApiOperation(value = "/get/childAgentSalesCountInfo/childAgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ChildAgentResponse> getChildAgentSalesCountInfoByChildAgentId(

	@QueryParam("childAgentId") int childAgentId) throws Exception {
		Response<ChildAgentResponse> res = null;
		try {
			ChildAgentResponse info = this.agentService
					.getChildAgentSalesCountInfoByChildAgentId(childAgentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<ChildAgentResponse>(Result.FAIL.getCode(),
					"getChildAgentInfo查询异常：" + ex.getMessage());
			log.error("getChildAgentInfo查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title:productList
	 * @Description:管理列表显示
	 * @param name
	 *            门店，公司，或编号
	 * @param status
	 *            状态1合作中2未合作
	 * @return
	 */
	@GET
	@Path("/get/childAgentInfo/list")
	@ApiOperation(value = "/get/childAgentInfo/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ChildAgentListResponse> getChildAgentInfoList(
			@QueryParam("status") int status,
			@QueryParam("parentId") int parentId,

			@QueryParam("keyWord") String keyWord,

			@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize) throws Exception {
		Response<ChildAgentListResponse> res = null;
		try {
			ChildAgentListResponse list = this.agentService
					.getChildAgentInfoList(status, parentId, keyWord, pageNum,
							pageSize);
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<ChildAgentListResponse>(Result.FAIL.getCode(),
					"getChildAgentInfoList查询异常：" + ex.getMessage());
			log.error("getChildAgentInfoList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
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
	@GET
	@Path("/get/childAgentSalesCountInfo/list")
	@ApiOperation(value = "/get/childAgentInfo/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ChildAgentListResponse> getChildAgentSalesCountInfoList(
			@QueryParam("status") int status,
			@QueryParam("agentId") int agentId,

			@QueryParam("keyWord") String keyWord,

			@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize) throws Exception {
		Response<ChildAgentListResponse> res = null;
		try {
			ChildAgentListResponse list = this.agentService
					.getChildAgentSalesCountInfoList(status, agentId, keyWord,
							pageNum, pageSize);
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<ChildAgentListResponse>(Result.FAIL.getCode(),
					"getChildAgentSalesCountInfoList查询异常：" + ex.getMessage());
			log.error("getChildAgentSalesCountInfoList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
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
	@GET
	@Path("/get/salesDetailByAgentId/list")
	@ApiOperation(value = "/get/childAgentInfo/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<SalesListResponse> getSalesDetailByAgentId(
			@QueryParam("agentId") int agentId,
			@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize) throws Exception {
		Response<SalesListResponse> res = null;
		try {
			SalesListResponse list = this.agentService
					.getSalesDetailByAgentId(agentId,pageNum,pageSize);
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<SalesListResponse>(Result.FAIL.getCode(),
					"getSalesDetailByAgentId查询异常：" + ex.getMessage());
			log.error("getSalesDetailByAgentId查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title: salseCount
	 * @Description:根据公司主键查询员工数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/salseCount/parentAgentId")
	@ApiOperation(value = "/get/salseCount/parentAgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<Integer> getSalseCountByParentAgentId(

	@QueryParam("parentAgentId") int parentAgentId) throws Exception {
		Response<Integer> res = null;
		try {
			int info = this.agentService
					.getSalseCountByParentAgentId(parentAgentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<Integer>(Result.FAIL.getCode(),
					"getSalseCountByParentAgentId查询异常：" + ex.getMessage());
			log.error("getSalseCountByParentAgentId查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	
	/**
	 * @Title: getUserCountByParentAgentId
	 * @Description:根据公司主键查询员工数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/userCount/parentAgentId")
	@ApiOperation(value = "/get/userCount/parentAgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<Integer> getUserCountByParentAgentId(

	@QueryParam("parentAgentId") int parentAgentId) throws Exception {
		Response<Integer> res = null;
		try {
			int info = this.agentService
					.getUserCountByParentAgentId(parentAgentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<Integer>(Result.FAIL.getCode(),
					"getSalseCountByParentAgentId查询异常：" + ex.getMessage());
			log.error("getSalseCountByParentAgentId查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	
	/**
	 * @Title: getUserCountByAgentId
	 * @Description:根据门店主键查询员工数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/userCount/AgentId")
	@ApiOperation(value = "/get/userCount/AgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<Integer> getUserCountByAgentId(

	@QueryParam("agentId") int agentId) throws Exception {
		Response<Integer> res = null;
		try {
			int info = this.agentService
					.getUserCountByAgentId(agentId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<Integer>(Result.FAIL.getCode(),
					"getSalseCountByAgentId查询异常：" + ex.getMessage());
			log.error("getSalseCountByAgentId查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}


	/**
	 * @Title: getSalseCountToday
	 * @Description:根据主键查询当天员工数量
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/salseCount/today/parentAgentId")
	@ApiOperation(value = "/get/salseCount/today/parentAgentId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<Integer> getSalseCountTodayByParentAgentId(

	@QueryParam("parentAgentId") int parentAgentId) throws Exception {
		int info = agentService
				.getSalseCountTodayByParentAgentId(parentAgentId);
		Response<Integer> response = new Response<>(Result.SUCCESS, info);
		return response;

	}

	/**
	 * @Title:salesList
	 * @Description:管理列表显示
	 * @param name
	 *            门店，公司，或编号
	 * @param status
	 *            状态1合作中2未合作
	 * @return
	 */
	@GET
	@Path("/get/sales/list")
	@ApiOperation(value = "/get/sales/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<SalesListResponse> getSalesList(
			@QueryParam("roleId") int roleId,
			@QueryParam("agentId") int agentId,
			@QueryParam("status") int status,

			@QueryParam("keyWord") String keyWord,

			@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize) throws Exception {
		Response<SalesListResponse> res = null;
		try {
			SalesListResponse list = this.agentService.getSalesList(roleId,
					agentId,status, keyWord, pageNum, pageSize);
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<SalesListResponse>(Result.FAIL.getCode(),
					"getSalesList查询异常：" + ex.getMessage());
			log.error("getSalesList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	/**
	 *<p>Description:查询店员</p>

	 *<p>param agentId:</p>
	 *<p>param pageNum:</p>
	 *<p>param pageSize:</p>
	 *<p>author:zhanghongsheng</p>
	 *<p>date:2017/12/27 15:29</p>
	 *<p>return:</p>
	 *<p>throws: </p>
	 */
	@GET
	@Path("/get/sales/applist")
	@ApiOperation(value = "/get/sales/applist")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<SaleListAppResponse> getSalesAppList(
			@QueryParam("companyId") int companyId,
			@QueryParam("agentId") int agentId,
			@QueryParam("keyWord") String keyWord,
			@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize) throws Exception {

		Response<SaleListAppResponse> res = null;
		try {
			SaleListAppResponse list = this.agentService.getSalesAppList(companyId,5,
					agentId, keyWord, pageNum, pageSize);
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<SaleListAppResponse>(Result.FAIL.getCode(),
					"getSalesList查询异常：" + ex.getMessage());
			log.error("getSalesList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	/**
	 * @Title: salesInfo
	 * @Description:根据主键查询员工资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/salesInfo/salesId")
	@ApiOperation(value = "/get/salesInfo/salesId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<SalesResponse> getSalesInfoBySalesId(

	@QueryParam("salesId") int salesId) throws Exception {
		Response<SalesResponse> res = null;
		try {
			SalesResponse info = this.agentService
					.getSalesInfoBySalesId(salesId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<SalesResponse>(Result.FAIL.getCode(),
					"getSalesInfoBySalesId查询异常：" + ex.getMessage());
			log.error("getSalesInfoBySalesId查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title:companyAgentSalesList
	 * @Description:公司列表（含店面资料，员工资料）
	 * @return
	 */
	@GET
	@Path("/get/parentAgentChildAgentSales/list")
	@ApiOperation(value = "/get/parentAgentChildAgentSales/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<ParentAgentChildAgentSalesListResponse> getParentAgentChildAgentSalesList()
			throws Exception {
		Response<ParentAgentChildAgentSalesListResponse> res = null;
		try {
			ParentAgentChildAgentSalesListResponse list = agentService
					.getParentAgentChildAgentSalesList();
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<ParentAgentChildAgentSalesListResponse>(
					Result.FAIL.getCode(),
					"getParentAgentChildAgentSalesList查询异常：" + ex.getMessage());
			log.error("getParentAgentChildAgentSalesList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
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
	@POST
	@Path("/update/salesStatus/salesId")
	@ApiOperation(value = "/update/salesStatus/salesId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateSalesStatusBySalesId(
			@QueryParam("salesId") int salesId,
			@QueryParam("loginStatus") int loginStatus) throws Exception {
		agentService.updateSalesStatusBySalesId(salesId, loginStatus);
		Response<String> response = new Response(Result.SUCCESS.getCode(),
				Result.SUCCESS.getMessage());
		return response;
	}

	/**
	 * @Title:updateSalesAgentBySalseId
	 * @Description:修改员工所属店面
	 * @param 员工Id
	 * @param 店面Id
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/update/salesAgent/salseId")
	@ApiOperation(value = "/update/salesAgent/salseId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateSalesAgentBySalseId(
			@QueryParam("salesId") int salesId,
			@QueryParam("agentId") int agentId,
			@QueryParam("agentName") String agentName) throws Exception {
		agentService.updateSalesAgentBySalseId(salesId, agentId, agentName);
		Response<String> response = new Response(Result.SUCCESS.getCode(),
				Result.SUCCESS.getMessage());
		return response;
	}

	/**
	 * @Title:updateUserSalesBySalseId
	 * @Description:修改会员所属员工
	 * @param 员工Id
	 * @param 店面Id
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("/update/userSales/salseId")
	@ApiOperation(value = "/update/userSales/salseId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateUserSalesBySalseId(
			@QueryParam("oldSalesId") int oldSalesId,
			@QueryParam("salesId") int salesId,
			@QueryParam("salesName") String salesName,
			@QueryParam("agentId") int agentId,
			@QueryParam("agentName") String agentName) throws Exception {
		agentService.updateUserSalesBySalseId(oldSalesId, salesId, salesName,
				agentId, agentName);
		Response<String> response = new Response(Result.SUCCESS.getCode(),
				Result.SUCCESS.getMessage());
		return response;
	}

	/**
	 * @Title:pullSetList
	 * @Description:拉新设置表
	 * @return
	 */
	@GET
	@Path("/get/pullSet/list")
	@ApiOperation(value = "/get/pullSet/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<SalesPullNewSetListResponse> getPullSetList()
			throws Exception {
		Response<SalesPullNewSetListResponse> res = null;
		try {
			SalesPullNewSetListResponse list = this.agentService
					.getPullSetList();
			res = new Response<>(Result.SUCCESS, list);
		} catch (Exception ex) {
			res = new Response<SalesPullNewSetListResponse>(
					Result.FAIL.getCode(), "getPullSetList查询异常："
							+ ex.getMessage());
			log.error("getPullSetList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}

	/**
	 * @Title: salesBaseInfo
	 * @Description:根据主键查询员工基本资料
	 * @param id
	 *            主键
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("/get/salesBaseInfo/salesId")
	@ApiOperation(value = "/get/salesBaseInfo/salesId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<SalesBaseResponse> getSalesBaseInfoBySalesId(

	@QueryParam("salesId") int salesId) throws Exception {
		Response<SalesBaseResponse> res = null;
		try {
			SalesBaseResponse info = this.agentService
					.getSalesBaseInfoBySalesId(salesId);
			res = new Response<>(Result.SUCCESS, info);
		} catch (Exception ex) {
			res = new Response<SalesBaseResponse>(Result.FAIL.getCode(),
					"getSalesBaseInfoBySalesId查询异常：" + ex.getMessage());
			log.error("getSalesBaseInfoBySalesId查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	@GET
	@Path("/get/myshopList/companyId")
	@ApiOperation(value = "/get/myshopList/companyId")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<MyShopListResponse> getMyshopList(
			@QueryParam("companyId") int companyId) throws Exception {
		Response<MyShopListResponse> res = null;
		try {
			if(companyId<=0){
				res = new Response<MyShopListResponse>(60003,
						"companyId is error");
				return res;
			}
			else{
				MyShopListResponse response=agentService.getShopListByAgentId(companyId);
				res = new Response<MyShopListResponse>(Result.SUCCESS,response);
			}

		} catch (Exception ex) {
			res = new Response<MyShopListResponse>(Result.FAIL.getCode(),
					"getMyshopList查询异常：" + ex.getMessage());
			log.error("getMyshopList查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
	/**
	 *<p>Description:按公司发送优惠券，查询公司列表</p>
	 *<p>param status:状态</p>
	 *<p>param agentName:公司名称</p>
	 *<p>param type:1 员工 2：会员</p>
	 *<p>author:zhanghongsheng</p>
	 *<p>date:2018/1/23 14:16</p>
	 */
	@GET
	@Path("/get/companys/postCounpon")
	@ApiOperation(value = "/get/companys/postCounpon")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AgentCouponEntityResponse> getPostCounponCompanys(@QueryParam("status") int status,
																	  @QueryParam("type") int type,
																	  @QueryParam("agentName") String agentName){
		Response<AgentCouponEntityResponse> res = null;
		try {
			AgentCouponEntityResponse agentResponse=agentService.selectCompanyByPostCounpon(status, agentName, type);
			res=new Response<>(Result.SUCCESS,agentResponse);
		}catch (ServiceException ex){
			res = new Response<AgentCouponEntityResponse>(ex.getCode(),ex.getMessage());
			log.error("getPostCounponCompanys查询异常：code={},message={}", ex.getCode(),ex.getMessage());
		}catch (Exception ex) {
			res = new Response<AgentCouponEntityResponse>(Result.FAIL.getCode(),
					"getPostCounponCompanys查询异常：" + ex.getMessage());
			log.error("getPostCounponCompanys查询异常：{}", ex);
			ex.printStackTrace();
		}
		return res;
	}
}
