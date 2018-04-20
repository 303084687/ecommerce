package com.weichuang.ecommerce.barcode.resource;

import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.response.BalanceWithdrawResponse;
import com.weichuang.ecommerce.barcode.entity.response.InviteListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesIncomeDetailResponse;
import com.weichuang.ecommerce.barcode.service.ISalesShareEnvelopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;

/**
* <p>ClassName:SalesShareEnvelopResource</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 10:28</p>
**/
@Path("/salesShareEnvelop")
@Api(value = "/salesShareEnvelopResource", description = "description")
public class SalesShareEnvelopResource {
    private static final Logger log = LoggerFactory
            .getLogger(SalesShareEnvelopResource.class);
    @Autowired
    private ISalesShareEnvelopService envelopService;
    @Context
    private HttpServletRequest request;
    @ApiOperation(value = "/shareEnvelopCount", notes = "shareEnvelopCount")
    @GET
    @Path("/shareEnvelopCount")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    /**
     *<p>Description:查询销售可用的分享的红包次数</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:33</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public Response<Integer> getShareEnvelopCount(@QueryParam("saleId")int saleId) {
        Response<Integer> response =null;
        int count=0;
        try{
            response=envelopService.getShareEnvelopCount(saleId);
        }catch (ServiceException ex){
            log.error("SalesShareEnvelopResource getShareEnvelopCount error:{}",ex);
            response=new Response<>(ex.getCode(),ex.getMessage());
            return response;
        }
        catch(Exception ex)
        {
            log.error("SalesShareEnvelopResource getShareEnvelopCount error:{}",ex);
            response=new Response<>(Result.FAIL,0);
            return response;
        }
        return response;
    }

    @ApiOperation(value = "/shareEnvelop", notes = "shareEnvelop")
    @GET
    @Path("/shareEnvelop")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    /**
     *<p>Description:分享领红包</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:33</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public Response<String> getShareEnvelop(@QueryParam("saleId") int saleId,@QueryParam("codekey")String codekey) {
        Response<String> response =null;
        int count=0;
        try{
            response=envelopService.getShareEnvelop(request,saleId,codekey);
        }catch (ServiceException ex){
            log.error("SalesShareEnvelopResource getShareEnvelop error:{}",ex);
            response=new Response(ex.getCode(),ex.getMessage());
            return response;
        }
        catch(Exception ex)
        {
            log.error("SalesShareEnvelopResource getShareEnvelop error:{}",ex);
            response=new Response<>(Result.FAIL,"0");
            return response;
        }
        return response;
    }
    /**
     *<p>Description:分享红包提现</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:33</p>
     *<p>return:</p>
     */
    @ApiOperation(value = "/shareEnvelopWithdraw", notes = "shareEnvelopWithdraw")
    @GET
    @Path("/shareEnvelopWithdraw")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<Integer> getShareEnvelopWithdraw(@QueryParam("saleId") int saleId){
        Response<Integer> response =null;
        int count=0;
        try{
            response=envelopService.getShareEnvelopWithdraw(request,saleId);
        }catch (ServiceException ex){
            log.error("SalesShareEnvelopResource getShareEnvelopWithdraw error:{}",ex);
            response=new Response<>(ex.getCode(),ex.getMessage());
            return response;
        }
        catch(Exception ex)
        {
            log.error("SalesShareEnvelopResource getShareEnvelopWithdraw error:{}",ex);
            response=new Response<>(Result.FAIL,0);
            return response;
        }
        return response;
    }
    /**
     *<p>Description:分享红包提现列表</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:33</p>
     *<p>return:</p>
     */
    @ApiOperation(value = "/IncomeDetailList", notes = "IncomeDetailList")
    @GET
    @Path("/IncomeDetailList")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<SalesIncomeDetailResponse> getIncomeDetailList(@QueryParam("saleId") int saleId){
        Response<SalesIncomeDetailResponse> response =null;
        int count=0;
        try{
            response=envelopService.selectIncomeDetailList(saleId);
        }catch (ServiceException ex){
            log.error("SalesShareEnvelopResource getIncomeDetailList error:{}",ex);
            response=new Response<>(ex.getCode(),ex.getMessage());
            return response;
        }
        catch(Exception ex)
        {
            log.error("SalesShareEnvelopResource getIncomeDetailList error:{}",ex);
            response=new Response(Result.FAIL,ex.getMessage());
            return response;
        }
        return response;
    }
    /**
     *<p>Description:可提现得余额</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:33</p>
     *<p>return:</p>
     */
    @ApiOperation(value = "/balanceWithdraw", notes = "balanceWithdraw")
    @GET
    @Path("/balanceWithdraw")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<BalanceWithdrawResponse> getBalanceWithdraw(@QueryParam("saleId") int saleId){
        Response<BalanceWithdrawResponse> response =null;
        int count=0;
        try{
            response=envelopService.selectBalanceWithdraw(saleId);
        }catch (ServiceException ex){
            log.error("SalesShareEnvelopResource getBalanceWithdraw error:{}",ex);
            response=new Response<>(ex.getCode(),ex.getMessage());
            return response;
        }
        catch(Exception ex)
        {
            log.error("SalesShareEnvelopResource getBalanceWithdraw error:{}",ex);
            response=new Response(Result.FAIL,ex.getMessage());
            return response;
        }
        return response;
    }
}
