package com.weichuang.ecommerce.website.resource;

import com.weichuang.commons.*;
import com.weichuang.ecommerce.website.entity.ContentEntity;
import com.weichuang.ecommerce.website.service.IContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;

/**
 * author :han 2018-01-18 15:45
 */
@Path("/website/content")
@Api(value = "/ContentResources", description = "content Api接口")
@SuppressWarnings("all")
public class ContentResources extends BaseResource{

    @Autowired
    private IContentService contentService;

    /**
     * 添加
     * @param request
     * @return
     * @throws Exception
     */
    @PUT
    @Path("/addontent")
    @ApiOperation(value = "/add", notes = "add")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addContent(ContentEntity request) throws Exception {
        contentService.addContent(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * 修改
     * @param request
     * @return
     * @throws Exception
     */
    @POST
    @Path("/modifyContent")
    @ApiOperation(value = "/update", notes = "update")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyContent(ContentEntity request) throws Exception {
        contentService.updateContent(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * 列表
     * @param title
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GET
    @Path("/contentList")
    @ApiOperation(value = "/contentList", notes = "contentList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<PageListResult<ContentEntity>> contentList(@QueryParam("title") String title,
                                                     @QueryParam("status") int status, @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize)
            throws Exception {
        PageListResult<ContentEntity> list = contentService.contentList(title, status, pageNum, pageSize);
        Response<PageListResult<ContentEntity>> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    @POST
    @Path("/updateHotById")
    @ApiOperation(value = "/updateHotById", notes = "updateHotById")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateHotById(@QueryParam("id") int id) throws Exception {
        contentService.updateHotById(id);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    @GET
    @Path("/selectById")
    @ApiOperation(value = "/selectById", notes = "selectById")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ContentEntity> selectById(@QueryParam("id") int id) throws Exception {
        ContentEntity info = contentService.selectById(id);
        Response<ContentEntity> response = new Response<>(Result.SUCCESS, info);
        return response;
    }

}
