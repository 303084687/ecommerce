package com.weichuang.ecommerce.order.resource;


import com.weichuang.commons.*;
import com.weichuang.ecommerce.order.constants.OrderUtil;
import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.entity.response.AdminOrderDetailResponse;
import com.weichuang.ecommerce.order.entity.response.AdminOrderListResponse;
import com.weichuang.ecommerce.order.entity.response.OrderSentResponse;
import com.weichuang.ecommerce.order.service.IAdminOrderService;
import com.weichuang.ecommerce.task.constants.MessageConstant;
import com.weichuang.ecommerce.weixin.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

/**
 * <p>ClassName: AdminOrderResource.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台管理订单数据资源接口 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月14日 下午2:09:24</p>
 */
@Path("/order/admin")
@Api(value = "/adminOrderResource", description = "description")
@SuppressWarnings("all")
public class AdminOrderResource extends BaseResource {

    private static final Logger log = LoggerFactory.getLogger(AdminOrderResource.class);

    @Autowired
    private IAdminOrderService orderService;

    // 微信发送接口
    @Autowired
    private MessageService messageService;

    //根据订单编号查询详细信息
    @ApiOperation(value = "/get/orderNo", notes = "get by orderNo")
    @GET
    @Path("/get/orderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AdminOrderDetailResponse> getOrderByOrderNo(@QueryParam("orderNo") String orderNo) throws ResourceException {
        Response<AdminOrderDetailResponse> response = null;
        try {
            AdminOrderDetailResponse orderDetailResponse = orderService.getOrderByOrderNo(orderNo);
            response = new Response(Result.SUCCESS, orderDetailResponse);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            response = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = new Response(Result.FAIL, e.getMessage());
        }
        return response;
    }

    //根据条件查询订单管理用户订单列表,用户已发货的订单列表的查询(配送中的订单)
    @ApiOperation(value = "/sent/list", notes = "admin sent order list")
    @GET
    @Path("/sent/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AdminOrderListResponse> getSentOrderList(
            @QueryParam("createStartTime") String createStartTime,
            @QueryParam("createEndTime") String createEndTime,
            @QueryParam("sendStartTime") String sendStartTime,
            @QueryParam("sendEndTime") String sendEndTime,
            @QueryParam("orderNo") String orderNo,
            @QueryParam("mobile") String mobile,
            @QueryParam("receiverName") String receiverName,
            @QueryParam("receiverMobile") String receiverMobile,
            @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws ResourceException {
        Response<AdminOrderListResponse> result = null;
        try {
            //业务逻辑
            AdminOrderListResponse userOrderList = orderService.getSentOrderList(createStartTime,
                    createEndTime,
                    sendStartTime,
                    sendEndTime,
                    orderNo,
                    mobile,
                    receiverName,
                    receiverMobile,
                    pageNum,
                    pageSize);
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

    //根据条件查询订单管理订单列表,用户未发货的订单列表的查询（配送中的订单）
    @ApiOperation(value = "/unsent/list", notes = "admin unsent order list")
    @GET
    @Path("/unsent/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AdminOrderListResponse> getUnsentOrderList(
            @QueryParam("createStartTime") String createStartTime,
            @QueryParam("createEndTime") String createEndTime,
            @QueryParam("sendStartTime") String sendStartTime,
            @QueryParam("sendEndTime") String sendEndTime,
            @QueryParam("orderNo") String orderNo,
            @QueryParam("mobile") String mobile,
            @QueryParam("receiverName") String receiverName,
            @QueryParam("receiverMobile") String receiverMobile,
            @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws ResourceException {
        Response<AdminOrderListResponse> result = null;
        try {
            //业务逻辑
            AdminOrderListResponse userOrderList = orderService.getUnsentOrderList(createStartTime,
                    createEndTime,
                    sendStartTime,
                    sendEndTime,
                    orderNo,
                    mobile,
                    receiverName,
                    receiverMobile,
                    pageNum,
                    pageSize);
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

    //根据条件查询订单管理用户订单列表
    @ApiOperation(value = "/get/list", notes = "admin order list")
    @GET
    @Path("/get/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AdminOrderListResponse> getAdminOrderList(
            @QueryParam("createStartTime") String createStartTime,
            @QueryParam("createEndTime") String createEndTime,
            @QueryParam("orderNo") String orderNo,
            @QueryParam("mobile") String mobile,
            @QueryParam("status") int status,
            @QueryParam("receiverName") String receiverName,
            @QueryParam("receiverMobile") String receiverMobile,
            @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws ResourceException {
        Response<AdminOrderListResponse> result = null;
        try {
            //业务逻辑
            AdminOrderListResponse userOrderList = orderService.getAdminOrderList(createStartTime,
                    createEndTime,
                    orderNo,
                    mobile,
                    status,
                    receiverName,
                    receiverMobile,
                    pageNum,
                    pageSize);
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

    // 订单回收站
    @ApiOperation(value = "/recycle/list", notes = "admin order recycle list")
    @GET
    @Path("/recycle/list")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<AdminOrderListResponse> getAdminOrderRecycle(
            @QueryParam("createStartTime") String createStartTime,
            @QueryParam("createEndTime") String createEndTime,
            @QueryParam("orderNo") String orderNo,
            @QueryParam("mobile") String mobile,
            @QueryParam("status") int status,
            @QueryParam("receiverName") String receiverName,
            @QueryParam("receiverMobile") String receiverMobile,
            @QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws ResourceException {
        Response<AdminOrderListResponse> result = null;
        try {
            //业务逻辑
            AdminOrderListResponse userOrderList = orderService.getAdminOrderRecycle(createStartTime,
                    createEndTime,
                    orderNo,
                    mobile,
                    status,
                    receiverName,
                    receiverMobile,
                    pageNum,
                    pageSize);
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
    //添加退款记录并退款
    @ApiOperation(value = "/add/refund", notes = "add order refund")
    @PUT
    @Path("/add/refund")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<String> addOrderRefund(
            @QueryParam("orderNo") String orderNo,
            @QueryParam("refundType") int refundType,
            @QueryParam("refundMoney") BigDecimal refundMoney,
            @QueryParam("operator") int operator,
            @QueryParam("operatorName") String operatorName,
            @QueryParam("refundReason") String refundReason) throws ResourceException {
        Response<String> result = null;
        try {
            //业务逻辑
            boolean refundResult = orderService.addOrderRefund(orderNo, refundType, refundMoney, operator, operatorName, refundReason);
            if (refundResult) {
                result = new Response(Result.SUCCESS, Result.SUCCESS.getMessage());
            } else {
                result = new Response(Result.FAIL, "退款失败");
            }
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
        }
        return result;
    }

    //根据发货id，订单编号更新发货状态,更新为侍收货
    @ApiOperation(value = "/update/sentSatusToShipping/orderNo", notes = "update order sent")
    @POST
    @Path("/update/sentSatusToShipping/orderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<String> updateOrderSentStatusToShipping(
            @QueryParam("orderNo") String orderNo,
            @QueryParam("sendingTime") String sendingTime,
            @QueryParam("trackingNum") String trackingNum,
            @QueryParam("operator") int operator,
            @QueryParam("operatorName") String operatorName) throws ResourceException {
        Response<String> result = null;
        try {
            //业务逻辑
            orderService.updateOrderSentStatusToShippingByOrderNo(orderNo, sendingTime, trackingNum, operator, operatorName);

            //微信提醒
            AdminOrderDetailResponse adminOrderDetailResponse = orderService.getOrderByOrderNo(orderNo);
            Properties p = new Properties();
            p.put("productName", adminOrderDetailResponse.getOrderDetails().get(0).getProductName());
            String str = "";
            if (adminOrderDetailResponse.getSendTimes() > 1) {
                str = OrderUtil.composeMessage(MessageConstant.ADMIN_MULTIPLE, p);
            } else {
                str = OrderUtil.composeMessage(MessageConstant.ADMIN_SINGLE, p);
            }
            messageService.sendMessage(adminOrderDetailResponse.getUserName(), str);

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

    //根据发货id，订单编号更新发货状态,更新为侍收货
    @ApiOperation(value = "/update/orderSendTime/orderNo", notes = "update order sent")
    @POST
    @Path("/update/orderSendTime/orderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<String> updateOrderSendTimeByOrderNo(
            @QueryParam("orderNo") String orderNo,
            @QueryParam("sendTime") int sendTime,
            @QueryParam("operator") int operator,
            @QueryParam("operatorName") String operatorName) throws ResourceException {
        Response<String> result = null;
        try {
            //业务逻辑
            orderService.updateOrderSendTimeByOrderNo(orderNo, sendTime, operator, operatorName);
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

    /**
     * <p>Description: 根据订单编号和发货记录id，更新未发货记录的收货地址</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>param receiverName 收货人名称 </p>
     * <p>param provinceId 省id </p>
     * <p>param cityId 市id </p>
     * <p>param conutyId 县id </p>
     * <p>param address 详情地址 </p>
     * <p>param mobile 收货人手机号 </p>
     * <p>param operator 操作人id </p>
     * <p>param operatorName 操作人姓名 </p>
     * <p>param operateTime 操作时间 </p>
     * <p>param usedNewAddress 是否使用新的地址替换后续所有未发货的地址 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/25 </p>
     * <p>return int</p>
     */
    @ApiOperation(value = "/update/orderSendAddress/orderNo", notes = "update order sent address")
    @POST
    @Path("/update/orderSendAddress/orderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<String> updateOrderSendAddressByOrderNo(
            @QueryParam("id") int id,
            @QueryParam("orderNo") String orderNo,
            @QueryParam("receiverName") String receiverName,
            @QueryParam("provinceId") int provinceId,
            @QueryParam("cityId") int cityId,
            @QueryParam("countyId") int countyId,
            @QueryParam("address") String address,
            @QueryParam("mobile") String mobile,
            @QueryParam("operator") int operator,
            @QueryParam("operatorName") String operatorName,
            @QueryParam("useNewAddress") int useNewAddress) throws ResourceException {
        Response<String> result = null;
        try {
            //业务逻辑
            orderService.updateOrderSendAddressByIdAndOrderNo(
                    id,
                    orderNo,
                    receiverName,
                    provinceId,
                    cityId,
                    countyId,
                    address,
                    mobile,
                    operator,
                    operatorName,
                    useNewAddress);
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

    /**
     * <p>Description: 根据订单编号和发货记录id，更新未发货记录的收货地址</p>
     * <p>param id orderSentId </p>
     * <p>param orderNo 订单编号 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/12/25 </p>
     * <p>return int</p>
     */
    @ApiOperation(value = "/get/orderSend/idAndOrderNo", notes = "update order sent address")
    @POST
    @Path("/get/orderSend/idAndOrderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<OrderSentResponse> getOrderSentByIdAndOrderNo(
            @QueryParam("id") int id,
            @QueryParam("orderNo") String orderNo) throws ResourceException {
        Response<OrderSentResponse> result = null;
        try {
            //业务逻辑
            OrderSentResponse response = orderService.getOrderSentByIdAndOrderNo(id, orderNo);
            result = new Response(Result.SUCCESS, response);
        } catch (ServiceException e) {
            log.error(e.getMessage(), e);
            result = new Response(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result = new Response(Result.FAIL, e.getMessage());
        }
        return result;
    }

    //删除订单
    @ApiOperation(value = "/update/del/orderNo", notes = "update order del")
    @POST
    @Path("/update/del/orderNo")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<String> updateOrderStatusToDel(
            @QueryParam("orderNo") String orderNo
             ) throws ResourceException {
        Response<String> result = null;
        try {
            //业务逻辑
            orderService.updateOrdeStatusToDel(orderNo);
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

    //订单批量发货
    @ApiOperation(value = "/update/import", notes = "update import ")
    @POST
    @Path("/update/import")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<String>  OrderDelivery(
            @QueryParam("orderCsvUrl") String orderCsvUrl
    ) throws ResourceException {
        Response<String> result = null;
        try {
            //业务逻辑
            int operator = 46;
            String operatorName = "admin@qq.com";
            SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sendingTime = tempDate.format(new java.util.Date());
            try {
                List<String> dataList = CSVUtils.importCsv(orderCsvUrl);
                if (dataList != null && !dataList.isEmpty()) {
                    for (String data : dataList) {

                        String[] pills = data.split(",");
                        //System.out.print(pills[0]);
                        orderService.updateOrderSentStatusToShippingByOrderNo(pills[1],sendingTime,pills[0],operator,operatorName);
                    }
                }
           } catch (Exception e) {
            e.printStackTrace();
           }


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

}
