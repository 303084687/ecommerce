package com.weichuang.ecommerce.appVersion.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.appVersion.entity.AppVersionEntity;
import com.weichuang.ecommerce.appVersion.entity.response.AppVersionListResponse;
import com.weichuang.ecommerce.appVersion.entity.response.AppVersionResponse;
import com.weichuang.ecommerce.appVersion.service.IAppVersionService;

/**
 * <p>ClassName: AppVersionResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:版本管理api接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月22日 下午3:32:48</p>
 */
@Path("/appVersion")
@Api(value = "/AppVersionResources", description = "app版本接口Api")
@SuppressWarnings("all")
public class AppVersionResources extends BaseResource {
    @Autowired
    private IAppVersionService appVersionService;

    /**
     * @Title:增加版本更新  
     * @Description:增加
     * @param appVersionEntity 请求参数
     * @return
     */
    @PUT
    @Path("/addAppVersion")
    @ApiOperation(value = "/addAppVersion", notes = "addProduct")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addAppVersion(AppVersionEntity appVersionEntity) throws Exception {
        appVersionService.addAppVersion(appVersionEntity);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: modifyAppVersion
     * @Description: 修改
     * @param appVersionEntity 请求的参数
     * @return
     */
    @POST
    @Path("/modifyAppVersion")
    @ApiOperation(value = "/modifyAppVersion", notes = "modifyAppVersion")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyAppVersion(AppVersionEntity appVersionEntity) throws Exception {
        appVersionService.updateAppVersion(appVersionEntity);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:productList  
     * @Description:后台版本管理列表数据
     * @param name 标题
     * @param status 状态1正常2禁止
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GET
    @Path("/appVersionList")
    @ApiOperation(value = "/appVersionList", notes = "appVersionList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<AppVersionListResponse> appVersionList(@QueryParam("platForm") int platForm,
            @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
        AppVersionListResponse list = appVersionService.getList(platForm, pageNum, pageSize);
        Response<AppVersionListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title: queryAppVersion
     * @Description:根据客户端查询版本号,提供给app使用
     * @param id 主键
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/queryAppVersion")
    @ApiOperation(value = "/queryAppVersion", notes = "queryAppVersion")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<AppVersionResponse> queryAppVersion(@QueryParam("platForm") int platForm,
            @QueryParam("type") int type) throws Exception {
        AppVersionResponse info = appVersionService.getAppVersion(platForm, type);
        Response<AppVersionResponse> response = new Response<>(Result.SUCCESS, info);
        return response;
    }

    /**
     * @Title: queryAppVersionById
     * @Description:根据客户端查询版本号,提供给app使用
     * @param id 主键
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/queryAppVersionById")
    @ApiOperation(value = "/queryAppVersionById", notes = "queryAppVersionById")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<AppVersionResponse> queryAppVersionById(@QueryParam("id") int id) throws Exception {
        AppVersionResponse info = appVersionService.getAppVersionById(id);
        Response<AppVersionResponse> response = new Response<>(Result.SUCCESS, info);
        return response;
    }
}
