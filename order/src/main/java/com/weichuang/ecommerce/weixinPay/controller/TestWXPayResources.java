package com.weichuang.ecommerce.weixinPay.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.OrderNumberUtil;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.Transfers.TransfersUtils;
import com.weichuang.ecommerce.weixinPay.hb.ReadPackUtils;
import com.weichuang.ecommerce.weixinPay.payment.PayUtil;

/**
 * <p>ClassName: TestWXPayResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:微信支付测试api </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月9日 下午5:06:27</p>
 */
@Path("/testWXPayResources")
@Api(value = "/testWXPayResources", description = "微信支付测试api")
@SuppressWarnings("all")
public class TestWXPayResources extends BaseResource {
    @Autowired
    private WeiXinProperties weiXinProperties;

    @Context
    private HttpServletRequest request;

    /*
     * // 微信下单
     * 
     * @POST
     * 
     * @Path("/orderRefund")
     * 
     * @ApiOperation(value = "/orderRefund", notes = "orderRefund")
     * 
     * @Produces({ Constant.APPLICATION_JSON_UTF8 })
     * 
     * @Consumes({ Constant.APPLICATION_JSON_UTF8 }) public Response<String>
     * OrderRefund(@QueryParam("amount") String amount, @QueryParam("desc")
     * String desc,
     * 
     * @QueryParam("notifyUrl") String notifyUrl, @QueryParam("openid") String
     * openid) throws Exception { String result = PayUtil.UnifiedOrder(request,
     * weiXinProperties.getAppId(), weiXinProperties.getMchId(), amount, desc,
     * notifyUrl, weiXinProperties.getPaternerKey(), openid); Response<String>
     * response = new Response(Result.SUCCESS.getCode(), result); return
     * response; }
     */

    // 微信退款
    @POST
    @Path("/unifiedOrder")
    @ApiOperation(value = "/unifiedOrder", notes = "unifiedOrder")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Boolean UnifiedOrder(@QueryParam("tradeNo") String tradeNo, @QueryParam("totalAmount") String totalAmount,
            @QueryParam("refundAmount") String refundAmount) throws Exception {
        boolean result = PayUtil.orderRefund(weiXinProperties.getAppId(), weiXinProperties.getMchId(), tradeNo,
                weiXinProperties.getPaternerKey(), weiXinProperties.getCertPath(), totalAmount, refundAmount);
        return result;
    }

    // 查询微信退款
    @GET
    @Path("/orderRefundQuery")
    @ApiOperation(value = "/orderRefundQuery", notes = "orderRefundQuery")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> orderRefundQuery(@QueryParam("outRefundNo") String outRefundNo) throws Exception {
        String result = PayUtil.orderRefundQuery(weiXinProperties.getAppId(), weiXinProperties.getMchId(),
                weiXinProperties.getPaternerKey(), outRefundNo);
        Response<String> response = new Response<>(Result.SUCCESS, result);
        return response;
    }

    // 企业付款
    @POST
    @Path("/sendTransfers")
    @ApiOperation(value = "/sendTransfers", notes = "sendTransfers")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> SendTransfers(@QueryParam("amount") String amount, @QueryParam("remark") String remark)
            throws Exception {
        String result = TransfersUtils.sendTransfers(request, amount, remark, "ofdoIwzEASy6tfqItCXWEczIHTcA",
                OrderNumberUtil.getWeiXinNo(), weiXinProperties.getMchId(), weiXinProperties.getAppId(),
                weiXinProperties.getPaternerKey(), weiXinProperties.getCertPath());
        Response<String> response = new Response(Result.SUCCESS.getCode(), result);
        return response;
    }

    // 查询企业付款信息
    @GET
    @Path("/query")
    @ApiOperation(value = "/query", notes = "query")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> query(@QueryParam("tradeNo") String tradeNo) throws Exception {
        String result = TransfersUtils.query(tradeNo, weiXinProperties.getMchId(), weiXinProperties.getAppId(),
                weiXinProperties.getPaternerKey(), weiXinProperties.getCertPath());
        Response<String> response = new Response<>(Result.SUCCESS, result);
        return response;
    }

    // 微信普通红包
    @POST
    @Path("/sendredpack")
    @ApiOperation(value = "/sendredpack", notes = "sendredpack")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> Sendredpack(@QueryParam("totalAmount") String totalAmount) throws Exception {
        boolean result = ReadPackUtils.sendredpack(request, "10", "1", "感谢您参加猜灯谜活动，祝您元宵节快乐！", "猜灯谜抢红包活动",
                "猜越多得越多，快来抢！", "ofdoIwzEASy6tfqItCXWEczIHTcA", weiXinProperties.getMchId(),
                weiXinProperties.getAppId(), weiXinProperties.getSendName(), weiXinProperties.getPaternerKey(),
                weiXinProperties.getCertPath());
        Response<String> response = new Response(Result.SUCCESS.getCode(), result);
        return response;
    }

    // 查询红包
    @GET
    @Path("/queryHb")
    @ApiOperation(value = "/queryHb", notes = "queryHb")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> queryHb(@QueryParam("mchBillno") String mchBillno) throws Exception {
        String result = ReadPackUtils.query(mchBillno, weiXinProperties.getMchId(), weiXinProperties.getAppId(),
                weiXinProperties.getPaternerKey(), weiXinProperties.getCertPath());
        Response<String> response = new Response<>(Result.SUCCESS, result);
        return response;
    }
}
