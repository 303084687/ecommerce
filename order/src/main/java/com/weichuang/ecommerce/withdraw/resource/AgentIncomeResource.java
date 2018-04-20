package com.weichuang.ecommerce.withdraw.resource;

import com.weichuang.commons.*;
import com.weichuang.ecommerce.withdraw.entity.request.WithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.response.AgentIncomeListResponse;
import com.weichuang.ecommerce.withdraw.entity.response.WithdrawResponse;
import com.weichuang.ecommerce.withdraw.service.IAgentIncomeService;
import com.weichuang.ecommerce.withdraw.service.IWithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.time.LocalDate;
import java.util.Date;

/**
 * <p>ClassName: AgentIncomeResource.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 代理商预计收入api接口</p>
 * <p>author jiangkesen</p>
 * <p>2017年10月08日 下午17:32:59</p>
 */
@Path("/agentIncome")
@Api(value = "/AgentIncomeResource", description = "代理商预计收入 Api接口")
@SuppressWarnings("all")
public class AgentIncomeResource extends BaseResource {
    private static final Logger log = LoggerFactory.getLogger(AgentIncomeResource.class);

    @Autowired
    private IAgentIncomeService agentIncomeService;

    //查询代码商预计收入列表
    @ApiOperation(value = "/list", notes = "get")
    @GET
    @Path("/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AgentIncomeListResponse> getAgentIncomeList(
            @QueryParam("agentId") int agentId,
            @QueryParam("startTime") Date startTime,
            @QueryParam("endTime") Date endTime,
            @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws ResourceException {

        Response<AgentIncomeListResponse> result = null;
        try {
            AgentIncomeListResponse agentIncomeList = agentIncomeService.getAgentIncomeList(agentId,  startTime, endTime, pageNum, pageSize);
            result = new Response(Result.SUCCESS, agentIncomeList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
            throw new ResourceException(e);
        }
        return result;
    }
}
