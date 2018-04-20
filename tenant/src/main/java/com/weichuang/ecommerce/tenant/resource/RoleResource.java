package com.weichuang.ecommerce.tenant.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.tenant.entity.request.AdminRoleRequest;
import com.weichuang.ecommerce.tenant.entity.request.RoleRequest;
import com.weichuang.ecommerce.tenant.entity.response.RoleInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.RoleListResponse;
import com.weichuang.ecommerce.tenant.service.IRoleService;

/**
 * <p>ClassName: RoleResource.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:角色操作 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月23日 上午9:53:58</p>
 */
@Path("/role")
@Api(value = "/roleResource", description = "description")
@SuppressWarnings("all")
public class RoleResource extends BaseResource {
	private static final Logger log = LoggerFactory.getLogger(RoleResource.class);
	@Autowired
	private IRoleService roleService;
	
	//创建角色
	@ApiOperation(value = "/addRole", notes = "addRole")
	@PUT
	@Path("/add")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> insertRole(RoleRequest request) throws Exception {
		roleService.addRole(request);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//修改角色
	@ApiOperation(value = "/updateRole", notes = "updateRole")
	@POST
	@Path("/update")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateRole(RoleRequest request) throws Exception {
		roleService.updateRole(request);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//角色列表(带分页)
	@ApiOperation(value = "/list", notes = "get")
	@Path("/list")
	@GET
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<RoleListResponse> getRoleList(@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize) throws Exception {
		RoleListResponse list = roleService.getRoleList(pageNum, pageSize);
		Response<RoleListResponse> response = new Response<>(Result.SUCCESS, list);
		return response;
	}
	
	//查询角色详情
	@ApiOperation(value = "/info", notes = "get")
	@Path("/info")
	@GET
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<RoleInfoResponse> findByRoleId(@QueryParam("roleId") int roleId,
			@QueryParam("roleName") String roleName) throws Exception {
		RoleInfoResponse info = roleService.findByRoleId(roleId, roleName);
		Response<RoleInfoResponse> response = new Response<>(Result.SUCCESS, info);
		return response;
	}
	
	//用户-角色增加
	@ApiOperation(value = "/addAdminRole", notes = "addAdminRole")
	@PUT
	@Path("/add/admin/role")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> addAdminRole(AdminRoleRequest request) throws Exception {
		roleService.addAdminRole(request);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
}
