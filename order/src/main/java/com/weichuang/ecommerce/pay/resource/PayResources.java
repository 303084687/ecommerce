package com.weichuang.ecommerce.pay.resource;

import com.weichuang.ecommerce.order.constants.OrderUtil;
import com.weichuang.ecommerce.order.entity.response.AdminOrderDetailResponse;
import com.weichuang.ecommerce.order.service.IAdminOrderService;
import com.weichuang.ecommerce.task.constants.MessageConstant;
import com.weichuang.ecommerce.weixin.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.pay.entity.UnifiedOrder;
import com.weichuang.ecommerce.pay.entity.response.UnifiedOrderResponse;
import com.weichuang.ecommerce.pay.service.IPayService;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.payment.PayUtil;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import com.weichuang.ecommerce.weixinPay.util.XMLUtil;

@Path("/pay")
@Api(value = "/payResource", description = "description")
@SuppressWarnings("all")
public class PayResources extends BaseResource {
    private static final Logger log = LoggerFactory.getLogger(PayResources.class);

    @Autowired
    private IPayService payService;

    @Autowired
    private WeiXinProperties weiXinProperties;

    @Context
    private HttpServletRequest request;

    // 微信发送接口
    @Autowired
    private MessageService messageService;

    @Autowired
    private IAdminOrderService orderService;
    /**
     * 微信支付
     * @param tradeNo
     * @param totalAmount
     * @param refundAmount
     * @return
     * @throws Exception
     */
    @POST
    @Path("/orderPay")
    @ApiOperation(value = "/orderPay", notes = "orderPay")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<UnifiedOrderResponse> orderPay(@QueryParam("amount") String amount,
            @QueryParam("desc") String desc, @QueryParam("notifyUrl") String notifyUrl,
            @QueryParam("openid") String openid, @QueryParam("orderNo") String orderNo) throws Exception {
        String result = PayUtil.UnifiedOrder(request, weiXinProperties.getAppId(), weiXinProperties.getMchId(), amount,
                desc, notifyUrl, weiXinProperties.getPaternerKey(), openid, orderNo);
        // 解析result
        JSONObject json = JSONObject.fromObject(result);
        Response<UnifiedOrderResponse> response = null;
        if (json.containsKey("result_code") && json.getString("result_code").equals("FAIL")) {
            UnifiedOrderResponse info = new UnifiedOrderResponse();
            UnifiedOrder unifiedOrder = new UnifiedOrder();
            unifiedOrder.setErrorDes(json.getString("err_code_des"));
            info.setUnifiedOrder(unifiedOrder);
            response = new Response<>(Result.FAIL, info);
        } else {
            UnifiedOrderResponse info = new UnifiedOrderResponse();
            UnifiedOrder unifiedOrder = new UnifiedOrder();
            unifiedOrder.setAppId(json.getString("appId"));
            unifiedOrder.setNonceStr(json.getString("nonceStr"));
            unifiedOrder.setPackages(json.getString("package"));
            unifiedOrder.setPaySign(json.getString("paySign"));
            unifiedOrder.setSignType(json.getString("signType"));
            unifiedOrder.setTimeStamp(json.getString("timeStamp"));
            info.setUnifiedOrder(unifiedOrder);
            response = new Response<>(Result.SUCCESS, info);
        }
        return response;
    }

    // 微信支付回调
    @ApiOperation(value = "/invoker", notes = "new")
    @Path("/invoker")
    @POST
    // 便于测试这里用POST，正式环境改为GET
    @Produces("application/xml")
    public void weixin_invoker(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        // 读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        try {
            inputStream = request.getInputStream();
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            while ((s = in.readLine()) != null) {
                sb.append(s);
            }
            in.close();
            inputStream.close();
            if (!StringUtils.isEmpty(sb.toString())) {
                // 解析xml成map
                Map<String, String> m = new HashMap<>();
                m = XMLUtil.doXMLParse(sb.toString());

                // 过滤空 设置 TreeMap
                SortedMap<Object, Object> packageParams = new TreeMap<>();
                Iterator it = m.keySet().iterator();
                while (it.hasNext()) {
                    String parameter = (String) it.next();
                    String parameterValue = m.get(parameter);
                    String v = "";
                    if (null != parameterValue) {
                        v = parameterValue.trim();
                    }
                    packageParams.put(parameter, v);
                }
                String resXml = "";
                String orderNo = packageParams.get("out_trade_no") == null ? "" : packageParams.get("out_trade_no")
                        .toString();
                log.info("订单号：" + orderNo + ",开始回调。。。。。。");
                if ("SUCCESS".equals(packageParams.get("result_code"))) {
                    boolean flag = payService.isPayed(packageParams);
                    if (flag) {
                        // 通知微信.异步确认成功
                        resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                                + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                        log.info("订单号：" + orderNo + ",微信回调成功！");

                        //微信提醒
                       AdminOrderDetailResponse adminOrderDetailResponse = orderService.getOrderByOrderNo(orderNo);
                       Properties p = new Properties();
                       p.put("productName", adminOrderDetailResponse.getOrderDetails().get(0).getProductName());
                       String str = "";
                        if(adminOrderDetailResponse.getSendTimes() > 1){
                            str = OrderUtil.composeMessage(MessageConstant.MULTIPLE_SHIPPING,p);
                        }else{
                            str = OrderUtil.composeMessage(MessageConstant.SINGLE_SHIPPING,p);
                        }
                        messageService.sendMessage(adminOrderDetailResponse.getUserName(), str);
                        log.info("订单号：" + orderNo + ",微信通知成功！");
                    } else {
                        log.error("订单号：" + orderNo + " 微信回调，本地处理异常！");
                        resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                                + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    }

                } else {
                    log.error("订单号：" + orderNo + "回调，微信抛异常：" + packageParams.get("err_code"));
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }
                // 处理业务完毕
                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
            } else {
                log.error("微信回调，解析获取的参数为空！");
            }
        }
        catch (Exception ex) {
            log.error("微信回调异常：" + ex.getMessage(), ex);
        }
    }
}
