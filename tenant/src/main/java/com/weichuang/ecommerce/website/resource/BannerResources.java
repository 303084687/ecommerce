package com.weichuang.ecommerce.website.resource;

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
import com.weichuang.ecommerce.website.entity.request.WebsiteBannerRequest;
import com.weichuang.ecommerce.website.entity.response.WebsiteBannerListResponse;
import com.weichuang.ecommerce.website.entity.response.WebsiteBannerResponse;
import com.weichuang.ecommerce.website.service.IBannerService;

/**
 * <p>ClassName: BannerResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 网站推荐接口api</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月7日 下午3:33:18</p>
 */
@Path("/website/banner")
@Api(value = "/BannerResources", description = "网站轮播图 Api接口")
@SuppressWarnings("all")
public class BannerResources extends BaseResource {
    // 注册banner管理服务接口
    @Autowired
    private IBannerService bannerService;

    /**
     * @Title:addBanner  
     * @Description:增加
     * @param WebsiteBannerRequest 请求参数
     * @return
     */
    @PUT
    @Path("/addBanner")
    @ApiOperation(value = "/add", notes = "add")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addBanner(WebsiteBannerRequest request) throws Exception {
        bannerService.addBanner(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: modifyBanner
     * @Description: 修改
     * @param WebsiteBannerRequest 请求的参数
     * @return
     */
    @POST
    @Path("/modifyBanner")
    @ApiOperation(value = "/update", notes = "update")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyBanner(WebsiteBannerRequest request) throws Exception {
        bannerService.updateBanner(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:bannerList  
     * @Description:管理列表显示
     * @param title 标题
     * @param type 类型 目前默认为1
     * @param status 状态1正常2禁止
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GET
    @Path("/bannerList")
    @ApiOperation(value = "/bannerList", notes = "bannerList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<WebsiteBannerListResponse> bannerList(@QueryParam("title") String title,
            @QueryParam("type") int type, @QueryParam("status") int status, @QueryParam("startTime") String startTime,
            @QueryParam("endTime") String endTime, @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize, @QueryParam("classify") int classify) throws Exception {
        WebsiteBannerListResponse list = bannerService.bannerList(title, type, status, startTime, endTime, pageNum,
                pageSize, classify);
        Response<WebsiteBannerListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title:channelList  
     * @Description:根据渠道和类型列表显示(提供给前端使用)
     * @param channe 1web 2h5 3安卓 4ios
     * @param type 类型 目前默认为1
     * @return
     */
    @GET
    @Path("/channelList")
    @ApiOperation(value = "/channelList", notes = "channelList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<WebsiteBannerListResponse> channelList(@QueryParam("channel") int channel,
            @QueryParam("type") String type, @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize)
            throws Exception {
        WebsiteBannerListResponse list = bannerService.channelList(channel, type, pageNum, pageSize);
        Response<WebsiteBannerListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title: updateHotById
     * @Description: 置顶
     * @param type 类型目前只有一个默认为1
     * @return
     */
    @POST
    @Path("/updateHotById")
    @ApiOperation(value = "/updateHotById", notes = "updateHotById")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateHotById(@QueryParam("id") int id, @QueryParam("type") Integer type) throws Exception {
        bannerService.updateHotById(id, type);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: selectById
     * @Description:根据主键查询详情信息
     * @param id 主键
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/selectById")
    @ApiOperation(value = "/selectById", notes = "selectById")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<WebsiteBannerResponse> selectById(@QueryParam("id") int id) throws Exception {
        WebsiteBannerResponse info = bannerService.selectById(id);
        Response<WebsiteBannerResponse> response = new Response<>(Result.SUCCESS, info);
        return response;
    }

}
