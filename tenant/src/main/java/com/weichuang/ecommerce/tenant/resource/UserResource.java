package com.weichuang.ecommerce.tenant.resource;

import com.weichuang.ecommerce.tenant.entity.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.commons.message.SendMessage;
import com.weichuang.ecommerce.RedisHelper;
import com.weichuang.ecommerce.tenant.constants.CreateCode;
import com.weichuang.ecommerce.tenant.constants.RedisStatus;
import com.weichuang.ecommerce.tenant.service.IUserService;

/**
 * <p>ClassName: UserResource.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:UserResource对外接口实现 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月23日 下午17:42:41</p>
 */
@Path("/user")
@Api(value = "/userResource", description = "description")
@SuppressWarnings("all")
public class UserResource extends BaseResource {
    private static final Logger log = LoggerFactory.getLogger(UserResource.class);

    // 邮件服务
    // @Autowired
    // private IEmail emailService;
    // 缓存配置
    @Autowired
    RedisHelper redisHelper;

    @Autowired
    private IUserService userService;

    /**
     * <p>Description: 通过第三方登陆方式注册用户,注册成为普通用户</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 姓别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 代理名称，这里是指健身房的id</p>
     * <p>salesId 用户所归属的业务人员id,健身房的员工的id</p>
     * <p>mobile 注册时绑定的手机号，必填</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return String</p>
     */
    @ApiOperation(value = "/add/user/thrid")
    @PUT
    @Path("/add/user/thrid")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    private Response<UserDetailResponse> addUserByThird(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("salesId") int salesId,
            @QueryParam("mobile") String mobile, @QueryParam("registerType") int registerType) throws Exception {
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.addUserByThird(openId, nickName, gender, iconUrl, salesId, mobile,
                    registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "注册用户失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 姓别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>realName 真实姓名</p>
     * <p>mobile 注册时绑定的手机号，必填</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/9/19 19:00 </p>
     * <p>return String</p>
     */
    @ApiOperation(value = "/add/sales/thrid")
    @PUT
    @Path("/add/sales/thrid")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    private Response<UserDetailResponse> addSalesByThird(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("agentId") int agentId,
            @QueryParam("roleId") int roleId, @QueryParam("realName") String realName,
            @QueryParam("mobile") String mobile, @QueryParam("registerType") int registerType) throws Exception {
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.addSalesByThird(openId, nickName, gender, iconUrl, agentId, roleId,
                    realName, mobile, registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "用户注册失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description: 通过用户推荐用户的方式注册用户,注册成为普通用户，需要记录用户的推荐关系</p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 姓别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>referrerId 推荐用户的id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/17 11:00 </p>
     * <p>return String</p>
     */
    @ApiOperation(value = "/add/user/referrer/thrid")
    @PUT
    @Path("/add/user/referrer/thrid")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> addUserReferrerByThird(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("referrerId") int referrerId,
            @QueryParam("registerType") int registerType) throws Exception {
        // String openId, String nickName, int gender, String iconUrl, int
        // referrerId, int registerType
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.addUserReferrerByThird(openId, nickName, gender, iconUrl, referrerId,
                    registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "注册用户失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联。
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 姓别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/09 14:00 </p>
     * <p>return </p>
     */
    @ApiOperation(value = "/add/sales/thrid/neglectUserName")
    @PUT
    @Path("/add/sales/thrid/neglectUserName")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> addSalesByThirdNeglectUserName(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("agentId") int agentId,
            @QueryParam("roleId") int roleId, @QueryParam("registerType") int registerType) throws Exception {
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.addSalesByThird(openId, nickName, gender, iconUrl, agentId, roleId,
                    registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "用户注册失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联。
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 姓别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/09 14:00 </p>
     * <p>return </p>
     */
    @ApiOperation(value = "/add/manager/thrid/neglectUserName")
    @PUT
    @Path("/add/manager/thrid/neglectUserName")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> addManagerByThirdNeglectUserName(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("agentId") int agentId,
            @QueryParam("registerType") int registerType) throws Exception {
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.addManagerByThird(openId, nickName, gender, iconUrl, agentId,
                    registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "用户注册失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description: 通过第三方登陆方式注册用户，健身房员工注册，员工要和健身房及角色关联。
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 姓别</p>
     * <p>iconUrl 第三方头像</p>
     * <p>agentId 所有代理的ID,目前是健身房的id</p>
     * <p>roleId 所属角色id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/09 14:00 </p>
     * <p>return </p>
     */
    @ApiOperation(value = "/add/user/thrid/neglectUserName")
    @PUT
    @Path("/add/user/thrid/neglectUserName")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> addUserByThirdNeglectUserName(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("salesId") int salesId,
            @QueryParam("registerType") int registerType) throws Exception {
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.addUserByThird(openId, nickName, gender, iconUrl, salesId,
                    registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "用户注册失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description:微信授权扫码注册用户</p>
     * <p>param openId:第三方OpenID</p>
     * <p>param nickName:第三方昵称</p>
     * <p>param gender:姓别</p>
     * <p>param iconUrl:第三方头像</p>
     * <p>param realname:姓名</p>
     * <p>param mobile:手机号</p>
     * <p>param password:密码</p>
     * <p>param codekey:二维码id</p>
     * <p>param codeType:1 门店 2 公司</p>
     * <p>param registerType:第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/21 15:36</p>
     * <p>return:</p>
     */
    @ApiOperation(value = "/add/user/thrid/thirdBarcode")
    @POST
    @Path("/add/user/thrid/thirdBarcode")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<UserDetailResponse> addUserByThirdBarcode(@FormParam("unionId") String unionId,
            @FormParam("appOpenId") String appOpenId, @FormParam("nickName") String nickName,
            @FormParam("gender") int gender, @FormParam("iconUrl") String iconUrl,
            @FormParam("realName") String realName, @FormParam("mobile") String mobile,
            @FormParam("password") String password, @FormParam("codekey") String codekey,
            @FormParam("codeType") int codeType, @FormParam("registerType") int registerType) {
        Response<UserDetailResponse> response = null;
        log.info("addUserByThirdBarcode:openId=");
        try {
            UserDetailResponse user = userService.addUserByThirdBarcode(appOpenId, nickName, gender, iconUrl, realName,
                    mobile, password, codekey, codeType, registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "用户注册失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description:微信授权扫码注册用户</p>
     * <p>param openId:第三方OpenID</p>
     * <p>param nickName:第三方昵称</p>
     * <p>param gender:姓别</p>
     * <p>param iconUrl:第三方头像</p>
     * <p>param realname:姓名</p>
     * <p>param mobile:手机号</p>
     * <p>param opassword:原密码</p>
     * <p>param password:密码</p>
     * <p>param registerType:第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/21 15:36</p>
     * <p>return:</p>
     */
    @ApiOperation(value = "/update/user/unionId")
    @POST
    @Path("/update/user/unionId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<UserDetailResponse> updateUserByUnionId(@FormParam("unionId") String unionId,
            @FormParam("appOpenId") String appOpenId, @FormParam("codeKey") String codeKey,
            @FormParam("nickName") String nickName, @FormParam("gender") int gender,
            @FormParam("iconUrl") String iconUrl, @FormParam("realName") String realName,
            @FormParam("mobile") String mobile, @FormParam("opassword") String opassword,
            @FormParam("password") String password, @FormParam("registerType") int registerType) {
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.updateUserByUnionId(unionId, appOpenId, codeKey, nickName, gender,
                    iconUrl, realName, mobile, opassword, password, registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response(Result.FAIL, "用户更新失败");
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description: 通过第三方登陆方式注册用户，普通用户注册。不于业务人员或健身房产生关系
     * 第一次关注微信公众号时，暂时先不需绑定手机或真实姓名，再次进入商城时需要绑定真实姓名和手机号
     * </p>
     * <p>openId 第三方OpenID</p>
     * <p>nickName 第三方昵称</p>
     * <p>gender 性别 0女， 1男</p>
     * <p>iconUrl 第三方头像</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	 </p>
     * <p>date 2017/10/09 14:00 </p>
     * <p>return </p>
     */
    @ApiOperation(value = "/add/user/thrid/withoutSales")
    @POST
    @Path("/add/user/thrid/withoutSales")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> addUserWithoutSalesByThird(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("registerType") int registerType) throws Exception {
        Response<UserDetailResponse> response = null;

        try {
            UserDetailResponse user = userService.addUserWithoutSalesByThird(openId, nickName, gender, iconUrl,
                    registerType);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response<>(Result.FAIL);
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "/add/user/app/withoutSales")
    @POST
    @Path("/add/user/app/withoutSales")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> addUserWithoutSalesByApp(@QueryParam("openId") String openId,
            @QueryParam("nickName") String nickName, @QueryParam("gender") int gender,
            @QueryParam("iconUrl") String iconUrl, @QueryParam("registerType") int registerType,
            @QueryParam("unionId") String unionId) throws Exception {
        Response<UserDetailResponse> response = null;
        try {
            UserDetailResponse user = userService.addUserWithoutSalesByApp(openId, nickName, gender, iconUrl,
                    registerType, unionId);
            if (user != null) {
                response = new Response(Result.SUCCESS, user);
            } else {
                response = new Response<>(Result.FAIL);
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    /**
     * <p>Description:全部用户查询</p>
     * <p>param startTime:开始时间</p>
     * <p>param endTime:结束时间</p>
     * <p>param logStatus:状态</p>
     * <p>param companyId:公司id</p>
     * <p>param agentId:门店id</p>
     * <p>param searchInput:搜索输入</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/2 15:01</p>
     * <p>return:</p>
     * <p>throws: </p>
     */

    @ApiOperation(value = "/get/userAll", notes = "get userAll by search")
    @GET
    @Path("/get/userAll")
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserAllListResponse> getUserAll(@QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize, @QueryParam("startTime") String startTime,
            @QueryParam("endTime") String endTime, @QueryParam("logStatus") Integer logStatus,
            @QueryParam("companyId") Integer companyId, @QueryParam("agentId") Integer agentId,
            @QueryParam("searchInput") String searchInput) throws Exception {

        Response<UserAllListResponse> res = null;
        try {
            UserAllListResponse list = this.userService.getUserAll(pageNum, pageSize, startTime, endTime, logStatus,
                    companyId, agentId, searchInput);
            res = new Response<>(Result.SUCCESS, list);
        }
        catch (Exception ex) {
            res = new Response<UserAllListResponse>(Result.FAIL.getCode(), "getUserAll查询异常：" + ex.getMessage());
            log.error("getUserAll查询异常：{}", ex);
            ex.printStackTrace();
        }

        return res;
    }
    /**
     * <p>Description:全部用户查询</p>
     * <p>param startTime:开始时间</p>
     * <p>param endTime:结束时间</p>
     * <p>param logStatus:状态</p>
     * <p>param companyId:公司id</p>
     * <p>param agentId:门店id</p>
     * <p>param searchInput:搜索输入</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/2 15:01</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    @ApiOperation(value = "/get/userAll/export", notes = "get userAll export")
    @GET
    @Path("/get/userAll/export")
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserAllListResponse> getUserAllExport( @QueryParam("startTime") String startTime,
                                                    @QueryParam("endTime") String endTime, @QueryParam("logStatus") Integer logStatus,
                                                    @QueryParam("companyId") Integer companyId, @QueryParam("agentId") Integer agentId,
                                                    @QueryParam("searchInput") String searchInput) throws Exception {
        Response<UserAllListResponse> res = null;
        try {
            UserAllListResponse list =this.userService.getUserAllExport( startTime, endTime, logStatus,
                    companyId, agentId, searchInput);
            res = new Response<>(Result.SUCCESS, list);
        }
        catch (Exception ex) {
            res = new Response<>(Result.FAIL);
            log.error("/get/userAll/export导出异常：{}", ex);
            ex.printStackTrace();
        }
        return res;
    }
    /**
     * <p>Description:通过用户编号查询用户</p>
     * <p>param userid:用户编号</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/4 18:25</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    @ApiOperation(value = "/get/userAllOne", notes = "get userAllOne by id")
    @GET
    @Path("/get/userAllOne")
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserAllListResponse> getUserAllOne(@QueryParam("userid") int userid) throws Exception {
        Response<UserAllListResponse> res = null;
        try {
            UserAllListResponse list = this.userService.getAllUserOne(userid);
            res = new Response<>(Result.SUCCESS, list);
        }
        catch (Exception ex) {
            res = new Response<UserAllListResponse>(Result.FAIL.getCode(), "getUserAllOne查询异常：" + ex.getMessage());
            log.error("getUserAll查询异常：{}", ex);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     * <p>Description:根据代理商id，及角色id查询用户信息</p>
     * <p>param agentId:代理商id</p>
     * <p>param roleId:角色id</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/11/3 11:27</p>
     * <p>return:用户信息</p>
     * <p>throws: </p>
     */
    @ApiOperation(value = "/get/getUserByAgentIdOrRoleId", notes = "get user by search")
    @GET
    @Path("/get/getUserByAgentIdOrRoleId")
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserListResponse> getUserByAgentIdOrRoleId(@QueryParam("agentId") Integer agentId,
            @QueryParam("roleId") Integer roleId) throws Exception {

        Response<UserListResponse> res = null;
        try {
            res = new Response(Result.SUCCESS, userService.getUserByAgentIdOrRoleId(agentId, roleId));
        }
        catch (Exception ex) {
            res = new Response<UserListResponse>(Result.FAIL.getCode(), "getUserByAgentIdOrRoleId查询异常："
                    + ex.getMessage());
            log.error("getUserByAgentIdOrRoleId查询异常：{}", ex);
            ex.printStackTrace();
        }

        return res;
    }

    /**
     * <p>Description: 根据第三方OpenId及登陆类型查询用户信息</p>
     * <p>openId 第三方OpenId</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     * <p>date 2017/9/21 19:00 </p>
     * <p>return UserResponse</p>
     */
    @ApiOperation(value = "/get/openId", notes = "get userInfo by openId")
    @GET
    @Path("/get/openId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserResponse> getUserByOpenId(@QueryParam("openId") String openId,
            @QueryParam("registerType") int registerType) throws Exception {
        Response<UserResponse> result = null;
        try {
            UserResponse user = userService.getUserByOpenId(openId, registerType);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     *<p>Description:通过unionid查询用户信息</p>
     *<p>param unionId:全局id</p>
     * <p>registerType 第三方登陆类型，1-微信登陆	 2-QQ登陆	 3-支付宝登陆	</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/1 13:51</p>
     *<p>return:Response<UserDetailResponse></p>
     */
    @ApiOperation(value = "/get/unionId", notes = "get userInfo by unionId")
    @GET
    @Path("/get/unionId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> getUserByUnionId(@QueryParam("unionId") String unionId,
            @QueryParam("registerType") int registerType) throws Exception {

        Response<UserDetailResponse> result = null;
        try {
            UserDetailResponse user = userService.getUserByUnionId(unionId, registerType);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }
    @ApiOperation(value = "/get/userBySearch", notes = "get userInfo by search")
    @GET
    @Path("/get/userBySearch")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserNewDetailResponse> getUserBySearch(@QueryParam("pageNum") int pageNum,
                                                         @QueryParam("pageSize") int pageSize,@QueryParam("saleId") int saleId) throws Exception {
        Response<UserNewDetailResponse> response=null;
        try{
            response=userService.getUserBySearch(pageNum, pageSize, saleId);
        }catch (ServiceException ex){
            response=new Response<>(ex.getCode(),ex.getMessage());
        }catch(Exception ex)
        {
            response=new Response<>(Result.FAIL);
        }
        return response;

    }
    /**
     * <p>Description: 根据手机号查询用户信息</p>
     * <p>mobile 用户手机号</p>
     * <p>date 2017/10/18 10:32 </p>
     * <p>return UserResponse</p>
     */
    @ApiOperation(value = "/get/mobile", notes = "get userInfo by mobile")
    @GET
    @Path("/get/mobile")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserResponse> getUserByMobile(@QueryParam("mobile") String mobile) throws Exception {
        Response<UserResponse> result = null;
        try {
            UserResponse user = userService.getUserByMobile(mobile);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据手机号查询用户信息</p>
     * <p>mobile 用户手机号</p>
     * <p>date 2017/10/18 10:32 </p>
     * <p>return UserResponse</p>
     */
    @ApiOperation(value = "/getDetail/mobile", notes = "get userdetail by mobile")
    @GET
    @Path("/getDetail/mobile")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> getUserDetailByMobile(@QueryParam("mobile") String mobile) throws Exception {
        Response<UserDetailResponse> result = null;
        try {
            UserDetailResponse user = userService.getUserDetailByMobile(mobile);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户名查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 16:38 </p>
     * <p>return UserResponse</p>
     */
    @ApiOperation(value = "/get/userName", notes = "get userInfo by userName")
    @GET
    @Path("/get/userName")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserResponse> getUserByUserName(@QueryParam("userName") String userName) throws Exception {
        Response<UserResponse> result = null;
        try {
            UserResponse user = userService.getUserByUserName(userName);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据userId查询用户信息</p>
     * <p>userId 用户ID</p>
     * <p>date 2017/10/11 16:38 </p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/get/detail/userId", notes = "get user detail by userId")
    @GET
    @Path("/get/detail/userId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> getUserDetailByUserId(@QueryParam("userId") int userId) {
        Response<UserDetailResponse> result = null;
        try {
            UserDetailResponse user = userService.getUserDetailByUserId(userId);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据userId查询用户信息</p>
     * <p>userId 用户ID</p>
     * <p>date 2017/10/11 16:38 </p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/get/managerDetail/userId", notes = "get manger detail by userId")
    @GET
    @Path("/get/managerDetail/userId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ManagerDetailResponse> getManagerDetailByUserId(@QueryParam("userId") int userId) {
        Response<ManagerDetailResponse> result = null;
        try {
            ManagerDetailResponse user = userService.getManagerDetailByUserId(userId);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据userName查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/10/11 16:38 </p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/get/detail/userName", notes = "get user detail by userName")
    @GET
    @Path("/get/detail/userName")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> getUserDetailByUserName(@QueryParam("userName") String userName)
            throws Exception {
        Response<UserDetailResponse> result = null;
        try {
            UserDetailResponse user = userService.getUserDetailByUserName(userName);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据userName查询用户信息</p>
     * <p>userName 用户名</p>
     * <p>date 2017/10/11 16:38 </p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/get/salesAll", notes = "get all sales")
    @GET
    @Path("/get/salesAll")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailListResponse> getSalesAll() throws Exception {
        Response<UserDetailListResponse> result = null;
        try {
            UserDetailListResponse user = userService.getAllSales();
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 查询用户信息</p>
     * <p>param type: 1: 注册30天以内会员（含30天） 2：注册30天以上会员  3：所有销售</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/12/13 15:27</p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/get/detail/couponUserList", notes = "get  couponUserList")
    @GET
    @Path("/get/detail/couponUserList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailListResponse> getCounponUserList(@QueryParam("type") int type) throws Exception {
        Response<UserDetailListResponse> result = null;
        try {
            UserDetailListResponse user = userService.getCouponUserList(type);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据第三方注册的openId查询用户信息</p>
     * <p>openId 第三方用户注册openId</p>
     * <p>date 2017/10/11 16:38 </p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/get/detail/openId", notes = "get user detail by userName")
    @GET
    @Path("/get/detail/openId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> getUserDetailByOpenId(@QueryParam("openId") String openId,
            @QueryParam("registerType") int registerType) throws Exception {
        Response<UserDetailResponse> result = null;
        try {
            UserDetailResponse user = userService.getUserDetailByOpenId(openId, registerType);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "/update/third")
    @GET
    @Path("/update/third")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<Integer> updateUserThird(@QueryParam("openId") String openId,
            @QueryParam("unionId") String unionId, @QueryParam("appOpenId") String appOpenId) {
        Response<Integer> result = null;
        try {
            int flag = userService.updateUserThird(openId, unionId, appOpenId);
            result = new Response(Result.SUCCESS, flag);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据手机号获取邀新信息</p>
     * <p>openId 第三方用户注册openId</p>
     * <p>date 2017/10/11 16:38 </p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/get/inviteNew/mobile", notes = "get inviteNew by mobile")
    @GET
    @Path("/get/inviteNew/mobile")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<InviteNewResponse> getInviteNewByMobile(@QueryParam("mobile") String mobile) throws Exception {
        Response<InviteNewResponse> result = null;
        try {
            InviteNewResponse user = userService.getInviteNewByMobile(mobile);
            result = new Response(Result.SUCCESS, user);
        }
        catch (Exception e) {
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户名更新用户最后登陆时间</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return String</p>
     */
    @ApiOperation(value = "/update/loginTime", notes = "update login time")
    @POST
    @Path("/update/loginTime")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateUserLoginTime(@QueryParam("userName") String userName) throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateUserLoginTime(userName);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户名更新用户最后登陆时间</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return String</p>
     */
    @ApiOperation(value = "/update/descripe", notes = "update descripe ")
    @POST
    @Path("/update/descripe")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateUserDescripe(@QueryParam("userid") int userid, @QueryParam("descripe") String descripe)
            throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateUserDescripe(userid, descripe);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * @param mobile 手机号
     * @throws Exception
     * @Title:sendBindCode
     * @Description:发送/重新发送绑定账号手机验证码
     */
    @ApiOperation(value = "/send/bind/code", notes = "send bind code")
    @GET
    @Path("/send/bind/code")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> sendBindCode(@QueryParam("mobile") String mobile) throws Exception {
        Response<String> response = null;
        // 首先判断用户是否存在,未被绑定的情况下才可以发送手机号码
        UserResponse user = userService.getUserByMobile(mobile);
        if (null == user) {
            // 发送手机验证码
            String code = CreateCode.verificationCode(6);
            // 调取发送短信的接口,00位短信商返回的结果
            String result = SendMessage.sendMessage(mobile, code);
            if (result.equals("00")) {
                // 存到缓存服务器
                redisHelper.setDictionary(RedisStatus.TYPE_REGISTER, mobile, code);
                response = new Response<>(Result.SUCCESS, code);
            } else {
                response = new Response(Result.FAIL.getCode(), Result.FAIL.getMessage());
            }
        } else {
            // 提示用户已经绑定
            response = new Response(Result.USER_PHONE_EXIST.getCode(), Result.USER_PHONE_EXIST.getMessage());
        }
        return response;
    }

    /**
     * @param mobile 手机号
     * @throws Exception
     * @Title:sendForgetCode
     * @Description:忘记密码发送手机验证码
     */
    @ApiOperation(value = "/send/forget/code", notes = "send forget code")
    @GET
    @Path("/send/forget/code")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> sendForgetCode(@QueryParam("mobile") String mobile) throws Exception {
        Response<String> response = null;
        // 首先判断用户是否存在,存在的情况下才发送
        UserResponse user = userService.getUserByMobile(mobile);
        if (null != user) {
            // 发送手机验证码
            String code = CreateCode.verificationCode(6);
            // 调取发送短信的接口,00位短信商返回的结果
            String result = SendMessage.sendMessage(mobile, code);
            if (result.equals("00")) {
                // 存到缓存服务器
                redisHelper.setDictionary(RedisStatus.TYPE_BIND, mobile, code);
                response = response = new Response<>(Result.SUCCESS, code);
            } else {
                response = new Response(Result.FAIL.getCode(), Result.FAIL.getMessage());
            }
        } else {
            // 提示用户不存在
            response = new Response(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
        }
        return response;
    }

    /**
     * @Title:ValidateCode  
     * @Description:验证验证码是否正确
     * @param mobile
     * @param type 1是注册 2是绑定
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "/validate/code", notes = "validate code")
    @GET
    @Path("/validate/code")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> ValidateCode(@QueryParam("mobile") String mobile, @QueryParam("code") String code,
            @QueryParam("type") Integer type) throws Exception {
        Response<String> response = null;
        // 根据类型获取redis的值
        String valueCode = "";
        if (type == 1) {
            valueCode = redisHelper.getDictionary(RedisStatus.TYPE_REGISTER, mobile);
        } else {
            valueCode = redisHelper.getDictionary(RedisStatus.TYPE_BIND, mobile);
        }
        // 判断验证码是否正确
        if (valueCode != null && valueCode.equalsIgnoreCase(code)) {
            response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        } else {
            // 提示手机验证码错误
            response = new Response(Result.USER_CODE_ERROR.getCode(), Result.USER_CODE_ERROR.getMessage());
        }
        return response;
    }

    /**
     * 更新电话真实姓名绑定账号
     *
     * @param openId   微信openId
     * @param mobile   手机号码
     * @param realName 真实姓名
     * @param code     手机验证码
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "/update/mobile", notes = "update mobile")
    @POST
    @Path("/update/mobile")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> updateUserMobileByOpenId(@QueryParam("openId") String openId,
            @QueryParam("mobile") String mobile, @QueryParam("realName") String realName,
            @QueryParam("code") String code, @QueryParam("registerType") int registerType) throws Exception {

        // 首先判断短信验证码是否正确(先暂不做判断)
        // String value = redisHelper.getDictionary(RedisStatus.TYPE_BIND,
        // mobile);
        // if(value == null || value.equalsIgnoreCase(code)){
        // // 提示手机验证码错误
        // response = new Response(Result.USER_CODE_ERROR.getCode(),
        // Result.USER_CODE_ERROR.getMessage());
        // }
        Response<UserDetailResponse> response = null;

        Response<UserDetailResponse> result = null;
        try {
            UserDetailResponse userDetail = userService
                    .updateUserMobileByOpenId(openId, mobile, realName, registerType);
            result = new Response(Result.SUCCESS, userDetail);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    /**
     * 更新电话真实姓名绑定账号
     *
     * @param openId   微信openId
     * @param mobile   手机号码
     * @param realName 真实姓名
     * @param code     手机验证码
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "/update/mobile/byid", notes = "update mobile byId")
    @POST
    @Path("/update/mobile/byid")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateUserMobileById(@QueryParam("mobile") String mobile, @QueryParam("id") int id)
            throws Exception {

        Response<String> result = null;
        try {
            int updateResult = userService.updateUserMobileById(mobile, id);
            if (updateResult > 0) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户名更新用户最后登陆时间</p>
     * <p>userName 用户名</p>
     * <p>date 2017/9/21 13:00 </p>
     * <p>return String</p>
     */
    @ApiOperation(value = "/update/password", notes = "update user password")
    @POST
    @Path("/update/password")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateUserPasswordByMobile(@QueryParam("mobile") String mobile,
            @QueryParam("password") String password) throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateUserPasswordByMobile(mobile, password);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户id更新管理人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名id</p>
     * <p>agentId 代理商id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    @ApiOperation(value = "/update/manager/agent", notes = "update manager agent")
    @POST
    @Path("/update/manager/agent")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateManagerAgentRoleByUserId(@QueryParam("userId") int userId,
            @QueryParam("agentId") int agentId) throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateManagerAgentRoleByUserId(userId, agentId);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户id更新业务人员的关系信息,更新角色及所在代理商</p>
     * <p>userId 用户名id</p>
     * <p>agentId 代理商id</p>
     * <p>roleId 业务人员角色id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    @ApiOperation(value = "/update/sales/agent", notes = "update sales agent")
    @GET
    @Path("/update/sales/agent")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateSalesAgentRoleByUserId(@QueryParam("userId") int userId,
            @QueryParam("agentId") int agentId, @QueryParam("roleId") int roleId) throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateSalesAgentRoleByUserId(userId, agentId, roleId);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>salesId 业务人员id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    @ApiOperation(value = "/update/user/sales", notes = "update user sales")
    @POST
    @Path("/update/user/sales")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateUserAgentRoleByUserId(@QueryParam("userId") int userId,
            @QueryParam("salesId") int salesId) throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateUserAgentRoleByUserId(userId, salesId);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>salesId 业务人员id</p>
     * <p>date 2017/10/17 10:00 </p>
     * <p>return boolean</p>
     */
    @ApiOperation(value = "/update/user/loginStatus", notes = "update user sales")
    @GET
    @Path("/update/user/loginStatus")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateUserloginStatus(@QueryParam("id") int id, @QueryParam("status") int status)
            throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateUserStatus(status, id);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户id用户的关系信息,更新角色、推荐人信息、业务人员及所在代理商</p>
     * <p>userId 用户名</p>
     * <p>referrerId 推荐人id</p>
     * <p>date 2017/10/20 10:00 </p>
     * <p>return boolean</p>
     */
    @ApiOperation(value = "/update/user/referrer", notes = "update user referrer")
    @POST
    @Path("/update/user/referrer")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateReferrerAgentRoleByUserId(@QueryParam("userId") int userId,
            @QueryParam("referrerId") int referrerId) throws Exception {
        Response<String> result = null;
        try {
            boolean updateResult = userService.updateReferrerAgentRoleByUserId(userId, referrerId);
            if (updateResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response<>(Result.FAIL, Result.FAIL.getMessage());
            }
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL, e.getMessage());
        }
        return result;
    }

    /**
     * <p>Description: 根据用户通过手机号进行登陆</p>
     * <p>mobile 用户登陆的手机号</p>
     * <p>password 用户登陆的密码</p>
     * <p>date 2017/10/27 11:00 </p>
     * <p>return UserDetailResponse</p>
     */
    @ApiOperation(value = "/login/mobile", notes = "user login by mobile")
    @GET
    @Path("/login/mobile")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UserDetailResponse> userLoginByMobile(@QueryParam("mobile") String mobile,
            @QueryParam("password") String password) throws Exception {
        Response<UserDetailResponse> result = null;
        try {
            UserDetailResponse user = userService.userLoginByMobile(mobile, password);
            result = new Response(Result.SUCCESS, user);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "/get/myCustomer", notes = "get my customer")
    @GET
    @Path("/get/myCustomer")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<MyCustomerResponse> getMyCustomer(@QueryParam("type") int type, @QueryParam("userId") int userId)
            throws Exception {
        Response<MyCustomerResponse> result = null;
        try {
            if (type <= 0) {
                result = new Response(Result.GETMYCUSTOMER_TYPE_NOT_EXIST);
                return result;
            }
            if (userId <= 0) {
                result = new Response(Result.GETMYCUSTOMER_USERID_NOT_EXIST);
                return result;
            }
            MyCustomerResponse mycustomer = userService.selectUserCountByUserId(type, userId);
            result = new Response(Result.SUCCESS, mycustomer);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }
    /**
     *<p>Description:查询未确认的绑定信息</p>
     *<p>param userId:登录人id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/28 13:40</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @ApiOperation(value = "/get/manager/bindMsg", notes = "get manager bind messages")
    @GET
    @Path("/get/manager/bindMsg")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ManagerBindMessageResponse> getBindManagerMessage(@QueryParam("userId") int userId)
            throws Exception {
        Response<ManagerBindMessageResponse> result = null;
        try {
            ManagerBindMessageResponse response=userService.getBindMessage(userId);
            result = new Response(Result.SUCCESS,response);
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), e.getMessage());
        }
        return result;
    }

    @ApiOperation(value = "/update/receiverAccountId/userId", notes = "user receiver Accountid by userid")
    @GET
    @Path("/update/receiverAccountId/userId")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> userLoginByMobile(@QueryParam("receiverAccountId") int receiverAccountId,
                                                          @QueryParam("userId") int userId) throws Exception {
        Response result = null;
        try {
            if(receiverAccountId<=0){
                result = new Response<>(5001,"receiverAccountId is error");
                return result;
            }
            else if(userId<=0){
                result = new Response<>(5002,"userId is error");
                return result;
            }
            int flag= userService.updateReceiverIdByUserId(receiverAccountId, userId);
            result = new Response(Result.SUCCESS,String.valueOf(flag));
        }
        catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response<>(e.getCode(), e.getMessage().toString());
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response<>(Result.FAIL.getCode(), Result.FAIL.getMessage());
        }
        return result;
    }
    /**
     *<p>Description:邀请店员</p>
     *<p>param inviteId:邀请人id</p>
     *<p>param saleId:被邀请人id</p>
     *<p>param codekey:二维码键值</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/5 10:04</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @ApiOperation(value = "/update/inviteSaleJoin/agent", notes = "invite sale join agent")
    @GET
    @Path("/update/inviteSaleJoin/agent")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> inviteSaleJoin(@QueryParam("inviteId") int inviteId,@QueryParam("saleId") int saleId,@QueryParam("codekey") String codekey){

        Response result = null;
        try {
            result = userService.updateInviteSaleJoin(inviteId, saleId, codekey);
        }
        catch (ServiceException e) {
            log.error("inviteSaleJoin error:{}",e);
            result = new Response<>(e.getCode(), e.getMessage().toString());
        }
        catch (Exception e) {
            log.error("inviteSaleJoin error:{}",e);
            result = new Response<>(Result.FAIL.getCode(), Result.FAIL.getMessage());
        }
        return result;
    }
    /**
     *<p>Description:通过公司id查询员工或者会员</p>
     *<p>param type: 1员工  2会员</p>
     *<p>param ids: 公司id串</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/23 15:13</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @ApiOperation(value = "/get/userDetail/byCompanyIds", notes = "get user detail by CompanyIds")
    @POST
    @Path("/get/userDetail/byCompanyIds")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<UserDetailListResponse> getUserDetailByCompanyIds(@FormParam("type") int type,@FormParam("ids") String ids){

        Response<UserDetailListResponse> result = null;
        try {
            UserDetailListResponse  userdetailList = userService.getUserDetailByCompanyIds(type,ids);
            result=new Response<>(Result.SUCCESS,userdetailList);
        }
        catch (ServiceException e) {
            log.error("getUserDetailByCompanyIds error:{}",e);
            result = new Response<>(e.getCode(), e.getMessage().toString());
        }
        catch (Exception e) {
            log.error("getUserDetailByCompanyIds error:{}",e);
            result = new Response<>(Result.FAIL.getCode(), Result.FAIL.getMessage());
        }
        return result;
    }
}
