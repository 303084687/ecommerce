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
import com.weichuang.ecommerce.tenant.entity.request.UserAddressRequest;
import com.weichuang.ecommerce.tenant.entity.response.UserAddressEntityResponse;
import com.weichuang.ecommerce.tenant.entity.response.UserAddressListResponse;
import com.weichuang.ecommerce.tenant.service.IUserAddressService;

/**
 * <p>ClassName: UserAddressResource.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:收货地址api </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月19日 下午5:26:31</p>
 */
@Path("/userAddress")
@Api(value = "/userAddressResource", description = "description")
@SuppressWarnings("all")
public class UserAddressResource extends BaseResource {
    private static final Logger log = LoggerFactory.getLogger(UserAddressResource.class);

    @Autowired
    private IUserAddressService userAddressService;

    // 根据主键和用户删除收货地址
    @ApiOperation(value = "/delete", notes = "delete")
    @Path("/delete")
    @DELETE
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<String> delete(@QueryParam("id") int id, @QueryParam("userId") int userId) throws Exception {
        // 业务逻辑
        userAddressService.deleteUserAddress(id, userId);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    // 修改收货地址
    @ApiOperation(value = "/update", notes = "update")
    @POST
    @Path("/update")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<String> updateUserAddress(UserAddressRequest request) throws Exception {
        // 业务逻辑
        userAddressService.updateUserAddress(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    // 根据主键查询详细信息
    @ApiOperation(value = "/id", notes = "get")
    @GET
    @Path("/id")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<UserAddressEntityResponse> getUserAddressById(@QueryParam("id") int id) throws Exception {
        UserAddressEntityResponse info = userAddressService.getUserAddressInfo(id);
        Response<UserAddressEntityResponse> response = new Response(Result.SUCCESS, info);
        return response;
    }

    // 增加收货地址
    @ApiOperation(value = "/add", notes = "add")
    @PUT
    @Path("/add")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<String> insertUserAddress(UserAddressRequest request) throws Exception {
        int id = userAddressService.addUserAddress(request);
        Response<String> response = new Response(Result.SUCCESS, id);
        return response;
    }

    // 根据用户主键查询所有收货地址
    @ApiOperation(value = "/list", notes = "get")
    @GET
    @Path("/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<UserAddressListResponse> getUserAddressAllByUserId(@QueryParam("userId") int userId,
                                                                       @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
        // 业务逻辑
        UserAddressListResponse list = userAddressService.getUserAddressList(userId, pageNum, pageSize);
        Response<UserAddressListResponse> response = new Response(Result.SUCCESS, list);
        return response;
    }

    // 根据主键和用户设置/取消默认地址 type=1设置=2取消
    @ApiOperation(value = "/default", notes = "update")
    @POST
    @Path("/default")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<String> defaultAddress(@QueryParam("id") int id, @QueryParam("userId") int userId) throws Exception {
        userAddressService.defaultAddress(id, userId);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    // 根据主键查询详细信息
    @ApiOperation(value = "/default", notes = "get")
    @GET
    @Path("/default")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<UserAddressEntityResponse> getUserDefaultAddress(@QueryParam("userid") int userid) throws Exception {
        UserAddressEntityResponse info = userAddressService.getUserAddressInfoDefault(userid);
        Response<UserAddressEntityResponse> response = new Response(Result.SUCCESS, info);
        return response;
    }
}
