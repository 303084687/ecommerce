package com.weichuang.ecommerce.order.resource;

import com.weichuang.commons.*;
import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.service.IRevenueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * income author han 2018-1-30
 */
@Path("/revenue/admin")
@Api(value = "/adminRevenueResource", description = "description")
@SuppressWarnings("all")
public class RevenueResource extends BaseResource{

    private static final Logger log = LoggerFactory.getLogger(RevenueResource.class);

    @Autowired
    private IRevenueService revenueService;

    //根据条件查询订单管理订单列表,用户未发货的订单列表的查询（配送中的订单）
    @ApiOperation(value = "/revenue/list", notes = "revenue list")
    @GET
    @Path("/revenue/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<PageListResult<OrderEntity>> getRevenueList(
            @QueryParam("startTime") String startTime,
            @QueryParam("endTime") String endTime,
            @QueryParam("status") int status,
            @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws ResourceException {
        Response<PageListResult<OrderEntity>> result = null;
        try {
            PageListResult<OrderEntity> pageListResult = revenueService.orderList(startTime,endTime,status,pageNum,pageSize);
            result = new Response(Result.SUCCESS, pageListResult);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
        }
        return result;
    }

}
