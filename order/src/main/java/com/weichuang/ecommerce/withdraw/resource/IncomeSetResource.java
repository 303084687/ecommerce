package com.weichuang.ecommerce.withdraw.resource;

import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.barcode.entity.request.SalesPullNewSetRequest;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewSetListResponse;
import com.weichuang.ecommerce.barcode.service.ISalesPullNewSetService;
import com.weichuang.ecommerce.withdraw.entity.request.IncomeSetRequest;
import com.weichuang.ecommerce.withdraw.entity.response.IncomeSetListResponse;
import com.weichuang.ecommerce.withdraw.service.IIncomeSetService;
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
 * <p>2017/12/27 10:37</p>
 **/
@Path("/incomeSet")
@Api(value = "/incomeSet", description = "description")
public class IncomeSetResource {
    private static final Logger log = LoggerFactory
            .getLogger(IncomeSetResource.class);

    @Autowired
    private IIncomeSetService incomeSetService;

    /**
     * 获取提成设置参数
     * @return
     * @throws Exception
     */
    @GET
    @Path("/get/incomeSet/list")
    @ApiOperation(value = "/get/incomeSet/list")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<IncomeSetListResponse> getIncomeSetList()
            throws Exception {
        Response<IncomeSetListResponse> res = null;
        try {
            IncomeSetListResponse list = this.incomeSetService.getAllIncomeSetList();
            res = new Response<>(Result.SUCCESS, list);
        }
        catch (Exception ex) {
            res = new Response<IncomeSetListResponse>(
                    Result.FAIL.getCode(), "getIncomeSetList查询异常："
                            + ex.getMessage());
            log.error("getIncomeSetList查询异常：{}", ex);
            ex.printStackTrace();
        }
        return res;
    }

    /**
     *<p>Description:更新提成参数设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/27 11:00</p>
     */
    @POST
    @Path("/update/incomeSet")
    @ApiOperation(value = "/update/incomeSet")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateIncomeSet(IncomeSetRequest request)
            throws Exception {
        Response<String> response = null;
        this.incomeSetService.updateIncomeSet(request);

        response = new Response(Result.SUCCESS.getCode(),
                Result.SUCCESS.getMessage());

        return response;
    }

    /**
     *<p>Description:增加提成设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/27 11:40</p>
     */

    @PUT
    @Path("/add/incomeSet")
    @ApiOperation(value = "/add/incomeSet")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addIncomeSet(IncomeSetRequest request)
            throws Exception {
        Response<String> response = null;
        this.incomeSetService.addIncomeSet(request);

        response = new Response(Result.SUCCESS.getCode(),
                Result.SUCCESS.getMessage());

        return response;

    }

    /**
     *<p>Description:更新提成参数状态0：失效；1：正常</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/27 11:00</p>
     */
    @POST
    @Path("/update/incomeSet/status")
    @ApiOperation(value = "/update/incomeSet/status")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateIncomeSetStatus(IncomeSetRequest request)
            throws Exception {
        Response<String> response = null;
        this.incomeSetService.updateIncomeSetStatus(request);

        response = new Response(Result.SUCCESS.getCode(),
                Result.SUCCESS.getMessage());

        return response;
    }
}
