package com.weichuang.ecommerce.tenant.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import com.weichuang.ecommerce.tenant.entity.request.AdminRequest;
import com.weichuang.ecommerce.tenant.entity.response.AdminInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.AdminListResponse;
import com.weichuang.ecommerce.tenant.entity.response.AdminLoginResponse;
import com.weichuang.ecommerce.tenant.service.IAdminService;

/**
 * <p>ClassName: AdminResource.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台用户管理对外接口实现 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月21日 下午1:42:41</p>
 */
@Path("/admin")
@Api(value = "/adminResource", description = "description")
@SuppressWarnings("all")
public class AdminResource extends BaseResource {
	private static final Logger log = LoggerFactory.getLogger(AdminResource.class);
	@Autowired
	private IAdminService adminService;
	
	//增加后台用户
	@ApiOperation(value = "/addAdmin", notes = "add")
	@PUT
	@Path("/add")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> addAdmin(AdminRequest request) throws Exception {
		adminService.addAdmin(request);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//修改后台用户
	@ApiOperation(value = "/updateAdmin", notes = "updateAdmin")
	@POST
	@Path("/update")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateAdmin(AdminRequest request) throws Exception {
		adminService.updateAdmin(request);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//删除后台用户
	@ApiOperation(value = "/deleteAdmin", notes = "deleteAdmin")
	@DELETE
	@Path("/delete")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> deleteAdmin(@QueryParam("userId") Integer userId) throws Exception {
		adminService.deleteAdmin(userId);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//查询带分页和模糊查询
	@ApiOperation(value = "/page/{pageNum}/{pageSize}", notes = "get")
	@GET
	@Path("/list")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AdminListResponse> getAdminList(@QueryParam("status") Integer status,
			@QueryParam("adminName") String adminName,@QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
		AdminListResponse list = adminService.getAdminList(status, adminName, pageNum, pageSize);
		Response<AdminListResponse> response = new Response<>(Result.SUCCESS, list);
		return response;
	}
	
	//根据主键或者用户名查询详情
	@ApiOperation(value = "/id/{id}", notes = "get")
	@GET
	@Path("/info")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AdminInfoResponse> getAdminInfo(@QueryParam("adminName") String adminName,
			@QueryParam("userId") Integer userId) throws Exception {
		AdminInfoResponse info = adminService.getAdmin(adminName, userId);
		Response<AdminInfoResponse> response = new Response<>(Result.SUCCESS, info);
		return response;
	}
	
	//设置启用或者停用
	@ApiOperation(value = "/updateAdminStatus", notes = "batch update status")
	@POST
	@Path("/update/status")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateAdminStatus(@QueryParam("status") Integer status,
			@QueryParam("userId") Integer userId, @QueryParam("type") int type) throws Exception {
		adminService.updateAdminStatus(status, userId, type);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//重置密码
	@ApiOperation(value = "/resetPass", notes = "resetPass")
	@POST
	@Path("/reset/pass")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> resetPass(@QueryParam("userId") Integer userId, @QueryParam("pass") String pass,
			@QueryParam("appKey") String appKey) throws Exception {
		adminService.resetPass(userId, pass, appKey);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//修改密码
	@ApiOperation(value = "/updatePass", notes = "updatePass")
	@POST
	@Path("/update/pass")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updatePass(@QueryParam("userId") Integer userId, @QueryParam("oldPass") String oldPass,
			@QueryParam("newPass") String newPass, @QueryParam("appKey") String appKey) throws Exception {
		adminService.updatePass(userId, oldPass, newPass, appKey);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//修改后台用户头像
	@ApiOperation(value = "/AdminHeadImg", notes = "AdminHeadImg")
	@POST
	@Path("/update/headImg")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateAdminHeadImg(@QueryParam("imageUrl") String imageUrl,
			@QueryParam("adminId") int adminId) throws Exception {
		adminService.updateAdminImg(imageUrl, adminId);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//根据用户名和密码登录
	@ApiOperation(value = "/login", notes = "login")
	@GET
	@Path("/login")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AdminLoginResponse> login(@QueryParam("userName") String userName,
			@QueryParam("passWord") String passWord) throws Exception {
		AdminLoginResponse login = adminService.login(userName, passWord);
		Response<AdminLoginResponse> response = new Response<>(Result.SUCCESS, login);
		return response;
	}
}
