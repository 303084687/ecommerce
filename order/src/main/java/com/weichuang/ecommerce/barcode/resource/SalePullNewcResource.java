package com.weichuang.ecommerce.barcode.resource;

import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.response.InviteListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesInvteNewCountResponse;
import com.weichuang.ecommerce.barcode.entity.response.TenantCodeStoreResponse;
import com.weichuang.ecommerce.barcode.service.ISalesPullNewcService;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
/**
 * <p>ClassName:SalePullNewcResource</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:</p>
 * <p>author:zhanghongsheng</p>
 * <p>2017/12/22 10:37</p>
 **/
@Path("/salePullNew")
@Api(value = "/salePullNewResource", description = "description")

public class SalePullNewcResource {
    private static final Logger log = LoggerFactory
            .getLogger(SalePullNewcResource.class);
    @Autowired
    private ISalesPullNewcService pullNewcService;
    @ApiOperation(value = "/inviteList", notes = "inviteList")
    @GET
    @Path("/inviteList")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    /**
     *<p>Description:分页查询邀新list</p>
     *<p>param pageNum:当前页</p>
     *<p>param pageSize:每页大小</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:33</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public Response<InviteListResponse> getInviteList(@QueryParam("pageNum") int pageNum,@QueryParam("pageSize") int pageSize,@QueryParam("saleId") int saleId) {
        Response<InviteListResponse> response = null;
        if (pageNum<=0) {
            response = new Response(Result.SALESPULLNEW_PAGENUM_NOT_EXIST);
        }
        else if(pageSize<=0){
            response = new Response(Result.SALESPULLNEW_PAGESIZE_NOT_EXIST);
        }
        else if(saleId<=0){
            response = new Response(Result.SALESPULLNEW_SALEID_NOT_EXIST);
        }
        else{
            try {
                InviteListResponse res=pullNewcService.selectInviteList(pageNum,pageSize,saleId);
                response=new Response<>(Result.SUCCESS,res);
            } catch (Exception e) {
                log.error("SalePullNewcResource getInviteList error:{}",e);
                response = new Response(Result.SALESPULLNEW_EXCEPTION);
            }
        }
        return response;
    }
    /**
     *<p>Description:邀新数量统计</p>
     *<p>param type:1 公司  2 门店 3 总计 4销售</p>
     *<p>param id: 公司或者门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/9 17:56</p>
     *<p>return:</p>
     */
    @ApiOperation(value = "/salesInvteNewCount", notes = "salesInvteNewCount")
    @GET
    @Path("/salesInvteNewCount")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<SalesInvteNewCountResponse> getSalesInvteNewCount(@QueryParam("type") int type,@QueryParam("id") int id ){
        Response<SalesInvteNewCountResponse> response = null;
        if (type<=0) {
            response = new Response(70001,"type value is error");
            return response;
        }
         else if(type!=3){
            if(id<=0){
                response =  new Response(70002,"id value is error");
                return response;
            }
        }
        try {
            SalesInvteNewCountResponse res=pullNewcService.selectSalesInvteNewCount(type,id);
            response=new Response<>(Result.SUCCESS,res);
        } catch (Exception e) {
            log.error("SalePullNewcResource getSalesInvteNewCount error:{}",e);
            response = new Response(Result.FAIL.getCode(),e.getMessage());
        }
        return response;
    }
}
