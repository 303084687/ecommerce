package com.weichuang.ecommerce.withdraw.resource;

import com.weichuang.commons.*;

import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawEntity;

import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawEntity;
import com.weichuang.ecommerce.withdraw.service.IAdminWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/adminWithdraw")
@Api(value = "/adminWithdrawResources", description = "后台销售分红 Api接口")
@SuppressWarnings("all")
public class AdminWithdrawResources {

    private static final Logger log = LoggerFactory
            .getLogger(AdminWithdrawResources.class);

    @Autowired
    private IAdminWithdrawService adminWithdrawService;

    @GET

    @Path("/salesWithdrawList")
    @ApiOperation(value = "/salesWithdrawList", notes = "salesWithdrawList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<PageListResult<SalesWithdrawEntity>> salesWithdrawList(@QueryParam("salesId") int salesId,
                                                               @QueryParam("status") int status,@QueryParam("keyWords") String keyWords, @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize)
            throws Exception {
        PageListResult<SalesWithdrawEntity> list = adminWithdrawService.salesWithdrawList(salesId, status,keyWords, pageNum, pageSize);

        Response<PageListResult<SalesWithdrawEntity>> response = new Response<>(Result.SUCCESS, list);
        return response;
    }


    @GET
    @Path("/agentWithdrawList")
    @ApiOperation(value = "/agentWithdrawList", notes = "agentWithdrawList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<PageListResult<AgentWithdrawEntity>> agentWithdrawList(@QueryParam("agentId") int agentId,
                                                                                          @QueryParam("status") int status, @QueryParam("keyWords") String keyWords,@QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize)
            throws Exception {
        PageListResult<AgentWithdrawEntity> list = adminWithdrawService.agentWithdrawList(agentId, status,keyWords, pageNum, pageSize);
        Response<PageListResult<AgentWithdrawEntity>> response = new Response<>(Result.SUCCESS, list);
        return response;
    }


}
