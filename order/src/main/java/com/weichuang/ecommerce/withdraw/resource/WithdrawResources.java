package com.weichuang.ecommerce.withdraw.resource;

import com.weichuang.ecommerce.product.entity.request.ProductRequest;
import com.weichuang.ecommerce.withdraw.entity.response.*;
import com.weichuang.ecommerce.withdraw.service.IAgentIncomeService;
import com.weichuang.ecommerce.withdraw.service.ISalesIncomeStatisticsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.time.LocalDate;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.DateUtil;
import com.weichuang.commons.ResourceException;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.withdraw.entity.request.WithdrawEntity;
import com.weichuang.ecommerce.withdraw.service.IWithdrawService;

/**
 * <p>ClassName: WithdrawResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 个人中心分红api接口</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月25日 下午2:42:59</p>
 */
@Path("/withdraw")
@Api(value = "/WithdrawResources", description = "个人中心销售分红 Api接口")
@SuppressWarnings("all")
public class WithdrawResources extends BaseResource {

    private static final Logger log = LoggerFactory
            .getLogger(WithdrawResources.class);

    @Autowired
    private IWithdrawService withdrawService;
    @Autowired
    ISalesIncomeStatisticsService salesIncomeStatisticsService;
    @Autowired
    IAgentIncomeService agentIncomeService;

    @Context
    private HttpServletRequest request;

    /**
     * @param saleId销售/代理主键
     * @param type          1代理2销售人员
     * @param money提现金额
     * @param saleName      销售/代理名字
     * @param openId        微信openID
     * @return
     * @throws Exception
     * @Title:extractMoney
     * @Description:提现流程
     */
    @POST
    @Path("/extractMoney")
    @ApiOperation(value = "/extractMoney", notes = "extractMoney")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    //@Consumes({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<String> extractMoney(@FormParam("saleId") int saleId,
    		@FormParam("type") int type, @FormParam("money") String money,
    		@FormParam("saleName") String saleName,
    		@FormParam("openId") String openId) throws Exception {
        LocalDate today = LocalDate.now();
        // 计算15天前
        LocalDate fifteDay = today.plusDays(-15);
        // 生成待提现记录
        WithdrawEntity entity = withdrawService.addWaitExtract(saleId, type,
                money, saleName, openId, fifteDay.toString() + " 00:00:00");
        // 执行体现流程1调取微信接口2更新记录
        
        if (null != entity) {
            withdrawService.extractMoney(request, entity);
            
        }
        Response<String> response = new Response(Result.SUCCESS.getCode(),
                Result.SUCCESS.getMessage());
        return response;
    }



    /**
     *@param
     * @return
     * @throws Exception
     * @Title:extractMoney
     * @Description:重新提现流程
     */
    @POST
    @Path("/againExtractMoney")
    @ApiOperation(value = "/againExtractMoney", notes = "againExtractMoney")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<String> againExtractMoney(  WithdrawEntity withdrawEntity) throws Exception {

        // 执行体现流程1调取微信接口2更新记录

        if (null != withdrawEntity) {
            withdrawService.extractMoney(request, withdrawEntity);

        }
        Response<String> response = new Response(Result.SUCCESS.getCode(),
                Result.SUCCESS.getMessage());
        return response;

    }

    /**
     * @param saleId 代理/销售主键
     * @param type   1代理2销售
     * @return
     * @throws Exception
     * @Title:queryWithdraw
     * @Description:个人中心查询销售分红
     */
    @GET
    @Path("/queryWithdraw")
    @ApiOperation(value = "/queryWithdraw", notes = "queryWithdraw")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<WithdrawResponse> queryWithdraw(
            @QueryParam("saleId") int saleId, @QueryParam("type") int type)
            throws Exception {
        WithdrawResponse entity = withdrawService.queryWithdraw(saleId, type);
        Response<WithdrawResponse> response = new Response<>(Result.SUCCESS,
                entity);
        return response;
    }

    /**
     * @param saleId 代理/销售主键
     * @param type   1代理2销售
     * @return
     * @throws Exception
     * @Title:queryWithdrawInfoList
     * @Description:个人中心查询提现详情
     */
    @GET
    @Path("/queryWithdrawInfoList")
    @ApiOperation(value = "/queryWithdrawInfoList", notes = "queryWithdrawInfoList")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<SalesWithdrawInfoListResponse> queryWithdrawInfoList(
            @QueryParam("saleId") int saleId) throws Exception {

        Response<SalesWithdrawInfoListResponse> result = null;
        try {
            SalesWithdrawInfoListResponse salesWithdrawInfoList = withdrawService
                    .queryWithdrawInfoList(saleId);
            result = new Response(Result.SUCCESS, salesWithdrawInfoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }


    /**
     * @param agentId 代理/销售主键

     * @return
     * @throws Exception
     * @Title:queryWithdrawInfoList
     * @Description:根据公司id查询提现详情
     */
    @GET
    @Path("/queryAgentWithdrawInfoList")
    @ApiOperation(value = "/queryAgentWithdrawInfoList", notes = "queryAgentWithdrawInfoList")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AgentWithdrawInfoListResponse> queryAgentWithdrawInfoList(
            @QueryParam("agentId") int agentId) throws Exception {

        Response<AgentWithdrawInfoListResponse> result = null;
        try {
            AgentWithdrawInfoListResponse agentWithdrawInfoList = withdrawService
                    .queryAgentWithdrawInfoList(agentId);
            result = new Response(Result.SUCCESS, agentWithdrawInfoList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }

    /**
     * @param saleId    代理/销售主键
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Response<SalesDailyIncomeSumListResponse>
     * @throws Exception
     * @Title:getDailyIncomeSumBySalesId
     * @Description:根据业务人员的id查询某一时间段内每天的预计的可提成的汇总
     */
    @GET
    @Path("/get/salesDailyIncome/salesId")
    @ApiOperation(value = "/get/salesDailyIncome/salesId", notes = "/get/salesDailyIncome/salesId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<SalesDailyIncomeSumListResponse> getDailyIncomeSumBySalesId(
            @QueryParam("saleId") int saleId, @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws Exception {
        Response<SalesDailyIncomeSumListResponse> result = null;
        try {
            SalesDailyIncomeSumListResponse response = salesIncomeStatisticsService.getDailyIncomeSumBySalesId(saleId,pageNum, pageSize);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }

    /**
     * @param saleId    代理/销售主键
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据业务人员的id查询某一时间段内每天的预计的可提成的详细信息
     */
    @GET
    @Path("/get/salesDailyDetail/salesId")
    @ApiOperation(value = "/get/salesDailyDetail/salesId", notes = "/get/salesDailyDetail/salesId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<SalesDailyIncomeDetailListResponse> getDailyIncomeDetailBySalesId(
            @QueryParam("saleId") int saleId) throws Exception {
        Response<SalesDailyIncomeDetailListResponse> result = null;
        
        try {
            SalesDailyIncomeDetailListResponse response = salesIncomeStatisticsService.getDailyIncomeDetailBySalesId(saleId);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }
    
    /**
     * @param saleId    代理/销售主键
     * @param oneDay 开始时间
     
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据业务人员的id查询某一天的预计的可提成的详细信息
     */
    @GET
    @Path("/get/salesDailyDetail/salesId/date")
    @ApiOperation(value = "/get/salesDailyDetail/salesId/date", notes = "/get/salesDailyDetail/salesId/date")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<SalesDailyIncomeDetailListResponse> getDailyIncomeDetailBySalesIdDate(
            @QueryParam("saleId") int saleId,@QueryParam("oneDay") String oneDay
            ) throws Exception {
        Response<SalesDailyIncomeDetailListResponse> result = null;
        
        try {
            SalesDailyIncomeDetailListResponse response = salesIncomeStatisticsService.getDailyIncomeDetailBySalesIdDate(saleId,oneDay);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }
    
    /**
     * @Title:addProduct  
     * @Description:增加
     * @param productRequest 请求参数
     * @return
     */
    @PUT
    @Path("/addWithdrawDetail")
    @ApiOperation(value = "/addWithdrawDetail", notes = "addWithdrawDetail")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    
    public Response<String> addWithdrawDetail(@QueryParam("saleId")int saleId, @QueryParam("type")int type, 
    		@QueryParam("oldTime")String oldTime) throws Exception {
    	withdrawService.addWithdrawDetail(saleId,type,oldTime);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @param saleId 销售主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgBySaleId
     * @Description:后台显示员工提成信息
     */
    @GET
    @Path("/queryIncomeMsgBySaleId")
    @ApiOperation(value = "/queryIncomeMsgBySaleId", notes = "queryIncomeMsgBySaleId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<IncomeMsgResponse> queryIncomeMsgBySaleId(
            @QueryParam("saleId") int saleId)
            throws Exception {
        IncomeMsgResponse entity = withdrawService.queryIncomeMsgBySaleId(saleId);
        Response<IncomeMsgResponse> response = new Response<>(Result.SUCCESS,
                entity);
        return response;
    }

    /**
     * @param agentId 门店主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByAgentId
     * @Description:后台显示门店提成信息
     */
    @GET
    @Path("/queryIncomeMsgByAgentId")
    @ApiOperation(value = "/queryIncomeMsgByAgentId", notes = "queryIncomeMsgByAgentId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<IncomeMsgResponse> queryIncomeMsgByAgentId(
            @QueryParam("agentId") int agentId)
            throws Exception {
        IncomeMsgResponse entity = withdrawService.queryIncomeMsgByAgentId(agentId);
        Response<IncomeMsgResponse> response = new Response<>(Result.SUCCESS,
                entity);
        return response;
    }

    /**
     * @param parentAgentId 公司主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByParentAgentId
     * @Description:后台显示公司提成信息
     */
    @GET
    @Path("/queryIncomeMsgByParentAgentId")
    @ApiOperation(value = "/queryIncomeMsgByParentAgentId", notes = "queryIncomeMsgByParentAgentId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<IncomeMsgResponse> queryIncomeMsgByParentAgentId(
            @QueryParam("parentAgentId") int parentAgentId)
            throws Exception {
        IncomeMsgResponse entity = withdrawService.queryIncomeMsgByParentAgentId(parentAgentId);
        Response<IncomeMsgResponse> response = new Response<>(Result.SUCCESS,
                entity);
        return response;
    }
    
    
    /**
     * @param parentAgentId 公司主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByParentAgentId
     * @Description:后台显示公司提成信息
     */
    @GET
    @Path("/queryAllAgentIncomeMsgByParentAgentId")
    @ApiOperation(value = "/queryAllAgentIncomeMsgByParentAgentId", notes = "queryAllAgentIncomeMsgByParentAgentId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<IncomeMsgResponse> queryAllAgentIncomeMsgByParentAgentId(
            @QueryParam("parentAgentId") int parentAgentId)
            throws Exception {
        IncomeMsgResponse entity = withdrawService.queryAllAgentIncomeMsgByParentAgentId(parentAgentId);
        Response<IncomeMsgResponse> response = new Response<>(Result.SUCCESS,
                entity);
        return response;
    }
    
    /**
     * @param
     * @return
     * @throws Exception
     * @Title:queryPullNewMsg
     * @Description:后台显示所有公司提成信息
     */
    @GET
    @Path("/queryAllFinanceMsg")
    @ApiOperation(value = "/queryAllFinanceMsg", notes = "queryAllFinanceMsg")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AllFinanceMsgResponse> queryAllFinanceMsg()
            throws Exception {
        AllFinanceMsgResponse entity = withdrawService.queryAllFinanceMsg();
        Response<AllFinanceMsgResponse> response = new Response<>(Result.SUCCESS,
                entity);
        return response;
    }

    /**
     * @param saleId    代理/销售主键
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Response<SalesDailyIncomeSumListResponse>
     * @throws Exception
     * @Title:getDailyIncomeSumBySalesId
     * @Description:根据公司的id查询提成的汇总（按天显示）
     */
    @GET
    @Path("/get/parentAgentDailyIncomeSum/parentAgentId")
    @ApiOperation(value = "/get/parentAgentDailyIncomeSum/parentAgentId", notes = "/get/parentAgentDailyIncomeSum/parentAgentId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<ParentAgentDailyIncomeSumListResponse> getParentAgentDailyIncomeSumByParentAgentId(
            @QueryParam("parentAgentId") int parentAgentId, @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws Exception {
        Response<ParentAgentDailyIncomeSumListResponse> result = null;
        try {
            ParentAgentDailyIncomeSumListResponse response = agentIncomeService.getParentAgentDailyIncomeSumByParentAgentId(parentAgentId,pageNum, pageSize);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }



    /**
     * @param saleId    代理/销售主键
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据公司的id查询当天的预计的可提成的详细信息（按门店显示）
     */
    @GET
    @Path("/get/ParentAgentDailyIncome/ParentAgentId")
    @ApiOperation(value = "/get/ParentAgentDailyIncome/ParentAgentId", notes = "/get/ParentAgentDailyIncome/ParentAgentId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<ParentAgentDailyIncomeListResponse> getParentAgentDailyIncomeListByParentAgentId(
            @QueryParam("parentAgentId") int parentAgentId,@QueryParam("pageNum") int pageNum,
    @QueryParam("pageSize") int pageSize) throws Exception {
        Response<ParentAgentDailyIncomeListResponse> result = null;

        try {
            ParentAgentDailyIncomeListResponse response = agentIncomeService.getParentAgentDailyIncomeListByParentAgentId(parentAgentId,pageNum, pageSize);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }

    /**
     * @param saleId    代理/销售主键
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据公司的id查询某天的预计的可提成的详细信息（按门店显示）
     */
    @GET
    @Path("/get/ParentAgentDailyIncome/ParentAgentId/date")
    @ApiOperation(value = "/get/ParentAgentDailyIncome/ParentAgentId/date", notes = "/get/ParentAgentDailyIncome/ParentAgentId/date")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<ParentAgentDailyIncomeListResponse> getParentAgentDailyIncomeListByParentAgentIdDate(
            @QueryParam("parentAgentId") int parentAgentId,@QueryParam("oneDay") String oneDay,@QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws Exception {
        Response<ParentAgentDailyIncomeListResponse> result = null;

        try {
            ParentAgentDailyIncomeListResponse response = agentIncomeService.getParentAgentDailyIncomeListByParentAgentIdDate(parentAgentId,oneDay,pageNum, pageSize);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }

    /**
     * @param saleId    代理/销售主键
     * @param oneDay 开始时间

     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据门店的id查询当天的预计的可提成的详细信息
     */
    @GET
    @Path("/get/agentDailyDetail/agentId/")
    @ApiOperation(value = "/get/agentDailyDetail/agentId", notes = "/get/agentDailyDetail/agentId")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AgentDailyIncomeDetailListResponse> getAgentDailyIncomeDetailByAgentId(
            @QueryParam("agentId") int agentId,@QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize
    ) throws Exception {
        Response<AgentDailyIncomeDetailListResponse> result = null;

        try {
            AgentDailyIncomeDetailListResponse response = agentIncomeService.getAgentDailyIncomeDetailByAgentId(agentId,pageNum, pageSize);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }

    /**
     * @param saleId    代理/销售主键
     * @param oneDay 开始时间

     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据门店的id查询某一天的预计的可提成的详细信息
     */
    @GET
    @Path("/get/agentDailyDetail/agentId/date")
    @ApiOperation(value = "/get/agentDailyDetail/agentId/date", notes = "/get/agentDailyDetail/agentId/date")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AgentDailyIncomeDetailListResponse> getAgentDailyIncomeDetailByAgentIdDate(
            @QueryParam("agentId") int agentId,@QueryParam("oneDay") String oneDay,@QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize
    ) throws Exception {
        Response<AgentDailyIncomeDetailListResponse> result = null;

        try {
            AgentDailyIncomeDetailListResponse response = agentIncomeService.getAgentDailyIncomeDetailByAgentIdDate(agentId,oneDay,pageNum, pageSize);
            result = new Response(Result.SUCCESS, response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;

    }

    
    
    
}
