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
import com.weichuang.ecommerce.tenant.entity.MenuRequest;
import com.weichuang.ecommerce.tenant.entity.response.AdminMenuListResponse;
import com.weichuang.ecommerce.tenant.entity.response.MenuInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.MenuListResponse;
import com.weichuang.ecommerce.tenant.service.IMenuService;

/**
 * <p>ClassName: MenuResource.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台菜单操作接口 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月24日 上午10:29:17</p>
 */
@Path("/menu")
@Api(value = "/menuResource", description = "description")
@SuppressWarnings("all")
public class MenuResource extends BaseResource {
	private static final Logger log = LoggerFactory.getLogger(MenuResource.class);
	@Autowired
	private IMenuService menuService;
	
	//新增菜单
	@ApiOperation(value = "/addMenu", notes = "addMenu")
	@PUT
	@Path("/add")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> addMenu(MenuRequest request) throws Exception {
		menuService.addMenu(request);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//修改菜单
	@ApiOperation(value = "/updateMenu", notes = "addMenu")
	@POST
	@Path("/update")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> updateMenu(MenuRequest request) throws Exception {
		menuService.updateMenu(request);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//菜单列表(带分页)
	@ApiOperation(value = "/menuList", notes = "menuList")
	@Path("/list")
	@GET
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<MenuListResponse> getMenuList(@QueryParam("pageNum") int pageNum,
			@QueryParam("pageSize") int pageSize, @QueryParam("type") Integer type) throws Exception {
		MenuListResponse list = menuService.getMenuList(pageNum, pageSize, type);
		Response<MenuListResponse> response = new Response(Result.SUCCESS, list);
		return response;
	}
	
	//根据菜单主键查询详细信息
	@ApiOperation(value = "/getMenuById", notes = "getMenuById")
	@Path("/info")
	@GET
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<MenuInfoResponse> getMenuById(@QueryParam("menuId") int menuId,
			@QueryParam("menuName") String menuName) throws Exception {
		MenuInfoResponse info = menuService.getMenuById(menuId, menuName);
		Response<MenuInfoResponse> response = new Response(Result.SUCCESS, info);
		return response;
	}
	
	//菜单-角色增加
	@ApiOperation(value = "/addMenuRole", notes = "addMenuRole")
	@PUT
	@Path("/add/menu/role")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<String> addMenuRole(@QueryParam("menuIds") String menuIds, @QueryParam("roleId") Integer roleId)
			throws Exception {
		menuService.addMenuRole(menuIds, roleId);
		Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
		return response;
	}
	
	//根据角色主键查询所有菜单（关联菜单和菜单-角色表查询）
	@ApiOperation(value = "/getUserMenu", notes = "getUserMenu")
	@Path("/by/roleId")
	@GET
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	@Consumes({ Constant.APPLICATION_JSON_UTF8 })
	public Response<AdminMenuListResponse> getUserMenu(@QueryParam("roleId") Integer roleId,
			@QueryParam("status") Integer status) throws Exception {
		AdminMenuListResponse list = menuService.getUserMenu(roleId, status);
		Response<AdminMenuListResponse> response = new Response<>(Result.SUCCESS, list);
		return response;
	}
}
