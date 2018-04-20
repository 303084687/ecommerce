package com.weichuang.ecommerce.order.resource;


import com.weichuang.commons.*;

import com.weichuang.ecommerce.order.entity.request.OrderRequest;
import com.weichuang.ecommerce.order.entity.response.*;
import com.weichuang.ecommerce.order.service.IOrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

/**
 * <p>ClassName: OrderResource.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单数据资源接口 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月14日 下午2:09:24</p>
 */
@Path("/order")
@Api(value = "/orderResource", description = "description")
@SuppressWarnings("all")
public class OrderResource extends BaseResource {

    private static final Logger log = LoggerFactory.getLogger(OrderResource.class);

    @Autowired
    private IOrderService orderService;

    //增加订单
    @ApiOperation(value = "/add", notes = "new")
    @PUT
    @Path("/add")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<OrderAddResponse> addOrder(OrderRequest request) throws ResourceException {
        Response<OrderAddResponse> result;
        try {
            OrderAddResponse orderResponse = orderService.addOrder(request);
            result = new Response(Result.SUCCESS, orderResponse);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
        }
        return result;
    }

    //根据主键查询详细信息
    @ApiOperation(value = "/get/id", notes = "get")
    @GET
    @Path("/get/id")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<UserOrderDetailResponse> getOrderById(@QueryParam("id") int id) throws ResourceException {
        Response<UserOrderDetailResponse> response = null;
        try {
            UserOrderDetailResponse orderResponse = orderService.getOrderById(id);
            response = new Response(Result.SUCCESS, orderResponse);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    //根据订单编号查询详细信息
    @ApiOperation(value = "/get/orderNo", notes = "get")
    @GET
    @Path("/get/orderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<UserOrderDetailResponse> getOrderByOrderNo(@QueryParam("orderNo") String orderNo) throws ResourceException {
        Response<UserOrderDetailResponse> response = null;
        try {
            UserOrderDetailResponse orderResponse = orderService.getOrderByNo(orderNo);
            response = new Response(Result.SUCCESS, orderResponse);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    //根据用户主键查询订单
    @ApiOperation(value = "/get/list", notes = "get")
    @GET
    @Path("/get/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<UserOrderListResponse> getUserOrderList(
            @QueryParam("userId") int userId,
            @QueryParam("status") int status,
            @QueryParam("startTime") String startTime,
            @QueryParam("endTime") String endTime,
            @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws ResourceException {
        Response<UserOrderListResponse> result = null;
        try {
            //业务逻辑
            UserOrderListResponse userOrderList = orderService.getOrderList(userId, status, startTime, endTime, pageNum, pageSize);
            result = new Response(Result.SUCCESS, userOrderList);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
        }
        return result;
    }

    //根据用户id进行会员详情-交易统计查询
    @ApiOperation(value = "/order/getTransactionStatistics", notes = "get")
    @GET
    @Path("/order/getTransactionStatistics")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<TransactionStatisticsResponse> getTransactionStatistics(@QueryParam("userid") int userid) throws ResourceException {
        Response<TransactionStatisticsResponse> response = null;
        try {
            TransactionStatisticsResponse res = orderService.getTransactionStatistics(userid);
            response = new Response(Result.SUCCESS, res);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }


    //根据订单编号，更新订单发货记录的为已收货
    @ApiOperation(value = "/update/sentSatusToReceived/orderNo", notes = "update order sent's satus to receive")
    @POST
    @Path("/update/sentSatusToReceived/orderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<String> updateOrderSentStatusToReceivedByOrderNo(@QueryParam("orderNo") String orderNo) throws ResourceException {
        Response<String> result = null;
        try {
            //业务逻辑
            orderService.updateOrderSentStatusToReceivedByOrderNo(orderNo);
            result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
        }
        return result;
    }


    //根据用户id查询用户订单各个状态订单的数量
    @ApiOperation(value = "/get/orderStatusList", notes = "get")
    @GET
    @Path("/get/orderStatusList")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<OrderStatusCountListResponse> getOrderStatusCountByUserId(
            @QueryParam("userId") int userId) throws ResourceException {
        Response<OrderStatusCountListResponse> result = null;
        try {
            //业务逻辑
            OrderStatusCountListResponse orderStatusCountList = orderService.getOrderStatusCountByUserId(userId);
            result = new Response(Result.SUCCESS, orderStatusCountList);
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
