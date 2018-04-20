package com.weichuang.ecommerce.barcode.resource;

import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.barcode.entity.request.SalesPullNewSetRequest;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewSetListResponse;
import com.weichuang.ecommerce.barcode.service.ISalesPullNewSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;

/**
 * <p>ClassName:SalePullNewSetResource</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:</p>
 * <p>author:liuzhanchao</p>
 * <p>2017/12/22 10:37</p>
 **/
@Path("/salePullNewSet")
@Api(value = "/salePullNewSetResource", description = "description")
public class SalePullNewSetResource {
    private static final Logger log = LoggerFactory
            .getLogger(SalePullNewSetResource.class);

    @Autowired
    private ISalesPullNewSetService pullNewSetService;

    @GET
    @Path("/get/pullSet/list")
    @ApiOperation(value = "/get/pullSet/list")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<SalesPullNewSetListResponse> getAllPullNewSetList()
            throws Exception {
        Response<SalesPullNewSetListResponse> res = null;
        try {
            SalesPullNewSetListResponse list = this.pullNewSetService
                    .getAllPullNewSetList();
            res = new Response<>(Result.SUCCESS, list);
        }
        catch (Exception ex) {
            res = new Response<SalesPullNewSetListResponse>(
                    Result.FAIL.getCode(), "getPullSetList查询异常："
                            + ex.getMessage());
            log.error("getPullSetList查询异常：{}", ex);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     *<p>Description:更新红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 22:00</p>
     */
    @POST
    @Path("/update/pullNewSet")
    @ApiOperation(value = "/update/pullNewSet")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updatePullNewSet(SalesPullNewSetRequest request)
            throws Exception {
        Response<String> response = null;
        this.pullNewSetService.updatePullNewSet(request);

        response = new Response(Result.SUCCESS.getCode(),
                Result.SUCCESS.getMessage());

        return response;
    }

    /**
     *<p>Description:增加红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */

    @PUT
    @Path("/add/pullNewSet")
    @ApiOperation(value = "/add/pullNewSet")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addPullNewSet(SalesPullNewSetRequest request)
            throws Exception {
        Response<String> response = null;
        this.pullNewSetService.addPullNewSet(request);

        response = new Response(Result.SUCCESS.getCode(),
                Result.SUCCESS.getMessage());

        return response;

    }
}
