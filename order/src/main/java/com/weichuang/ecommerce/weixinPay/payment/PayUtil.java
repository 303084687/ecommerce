package com.weichuang.ecommerce.weixinPay.payment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.JsonKit;
import com.weichuang.commons.OrderNumberUtil;
import com.weichuang.ecommerce.weixinPay.api.WxPayApi;
import com.weichuang.ecommerce.weixinPay.util.IpKit;
import com.weichuang.ecommerce.weixinPay.util.PaymentKit;
import com.weichuang.ecommerce.weixinPay.util.StrKit;

/**
 * <p>ClassName: PayUtil.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:微信支付封装类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月27日 下午1:58:21</p>
 */
public class PayUtil {
    private static final Logger log = LoggerFactory.getLogger(PayUtil.class);

    /**
     * @Title:UnifiedOrder  
     * @Description: 微信下单工具类
     * @param request
     * @param appId 公众账号ID
     * @param mchId 商户号
     * @param amount标价金额int类型单位为分
     * @param desc商品描述
     * @param paternerKey商户签名key
     * @param notifyUrl异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
     * @return
     */
    public static String UnifiedOrder(HttpServletRequest request, String appId, String mchId, String amount,
            String desc, String notifyUrl, String paternerKey, String openid, String orderNo) {
        // 获取ip地址要是没有默认为本机地址
        String ip = IpKit.getRealIp(request);
        if (StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }
        Map<String, String> params = new HashMap<String, String>();
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        // 终端IP
        params.put("spbill_create_ip", ip);
        // 商户订单号
        params.put("out_trade_no", orderNo);
        // 币种
        params.put("fee_type", "CNY");
        // 交易类型
        params.put("trade_type", TradeType.JSAPI.name());
        // 标价金额int类型单位为分
        int price = ((int) (Float.valueOf(amount) * 100));
        params.put("total_fee", price + "");
        // 公众账号ID
        params.put("appid", appId);
        // trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识
        params.put("openid", openid);
        // 商户号
        params.put("mch_id", mchId);
        // 商品描述
        params.put("body", desc);
        // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
        params.put("notify_url", notifyUrl);
        // 生成签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        // 调取统一下单接口 isSandbox=true时候仿真测试环境 false真实环境
        String xmlResult = WxPayApi.pushOrder(false, params);
        // 获取返回结果
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.warn(JsonKit.toJson(result));
        // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        String return_code = result.get("return_code");
        if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
            // 返回错误信息
            return JsonKit.toJson(result);
        }
        // 业务结果
        String result_code = result.get("result_code");
        if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
            // 返回错误信息
            return JsonKit.toJson(result);
        }
        // 当return_code和result_code都为success时候返回正确信息
        if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
            // 封装返回参数
            // 微信生成的预支付会话标识，用于后续接口调用中使用，该值有效期为2小时
            String prepay_id = result.get("prepay_id");
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("appId", appId);
            packageParams.put("timeStamp", System.currentTimeMillis() / 1000 + "");
            packageParams.put("nonceStr", System.currentTimeMillis() / 1000 + "");
            packageParams.put("package", "prepay_id=" + prepay_id);
            packageParams.put("signType", "MD5");
            String packageSign = PaymentKit.createSign(packageParams, paternerKey);
            packageParams.put("paySign", packageSign);
            return JsonKit.toJson(packageParams);
        }
        // 什么都不执行返回null
        return null;
    }

    /**
     *调取支付Map获取
     * @param appId 公众号id
     * @param prepayId 预付单号
     * @return 调取支付Map
     */
    /*
     * public static Map<String,String> jsApiPayRequest(String appId,String
     * prepayId,String paternerKey) { Map<String, String> params = new
     * HashMap<String, String>(); // 公众账号ID params.put("appid", appId); // 时间戳
     * params.put("timeStamp", System.currentTimeMillis() / 1000 + ""); //随机字符串
     * params.put("nonceStr", PaymentKit.generateNonceStr()); //订单详情扩展字符串
     * params.put("package", "prepay_id="+prepayId); //签名方式
     * params.put("signType", "MD5"); String packageSign =
     * PaymentKit.createJsApiSign(params, paternerKey); //签名
     * params.put("paySign",packageSign); return params; }
     */
    /**
     * @Title:orderQuery  
     * @Description:根据商户订单号查询一个订单
     * @param request
     * @param appId 公众号appId
     * @param mchId 商户号
     * @param paternerKey 商户签名key
     * @param tradeNo 商户订单号
     * @return
     */
    public static String orderQuery(String appId, String mchId, String tradeNo, String paternerKey) {
        Map<String, String> params = new HashMap<String, String>();
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        // 公众账号ID
        params.put("appid", appId);
        // 商户号
        params.put("mch_id", mchId);
        // 商户订单号
        params.put("out_trade_no", tradeNo);
        // 生成签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        // 调取查询接口
        String xmlResult = WxPayApi.orderQuery(false, params);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.warn(JsonKit.toJson(result));
        // 返回的是微信结果,还需要对内容解析
        return JsonKit.toJson(result);
    }

    /**
     * @Title:orderRefund  
     * @Description:申请退款
     * @param appId 公众账号ID
     * @param mchId商户号
     * @param tradeNo商户订单号
     * @param paternerKey商户签名key
     * @param certPath证书路径
     * @param totalAmount订单金额
     * @param refundAmount退款金额
     * @return
     */
    public static Boolean orderRefund(String appId, String mchId, String tradeNo, String paternerKey, String certPath,
            String totalAmount, String refundAmount) {
        Map<String, String> params = new HashMap<String, String>();
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        // 公众账号ID
        params.put("appid", appId);
        // 商户号
        params.put("mch_id", mchId);
        // 商户订单号
        params.put("out_trade_no", tradeNo);
        // 退款订单号
        params.put("out_refund_no", OrderNumberUtil.getRefundNo());
        // 订单总金额(分)
        int totalPrice = ((int) (Float.valueOf(totalAmount) * 100));
        params.put("total_fee", totalPrice + "");
        // 退款金额(分)
        int refundPrice = ((int) (Float.valueOf(refundAmount) * 100));
        params.put("refund_fee", refundPrice + "");
        // 生成签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        // 调取申请退款的接口
        String xmlResult = WxPayApi.orderRefund(false, params, certPath, mchId);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.warn(JsonKit.toJson(result));
        // 需要根据return_code和result_code解析返回结果
        String return_code = result.get("return_code");
        String result_code = result.get("result_code");
        if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
            return true;
        }
        return false;
    }

    /**
     * @Title:orderRefundQuery  
     * @Description:退款订单号查询退款的方法
     * @param appId公众账号ID
     * @param mchId商户号
     * @param paternerKey
     * @param outRefundNo 商户退款单号 refund_id > out_refund_no商户退款单号 > transaction_id微信订单号 > out_trade_no商户订单号
     * @return
     */
    public static String orderRefundQuery(String appId, String mchId, String paternerKey, String outRefundNo) {
        Map<String, String> params = new HashMap<String, String>();
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        // 公众账号ID
        params.put("appid", appId);
        // 商户号
        params.put("mch_id", mchId);
        // 商户退款单号
        params.put("out_refund_no", outRefundNo);
        // 生成签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        // 调取查询对款的接口
        String xmlResult = WxPayApi.orderRefundQuery(false, params);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.warn(JsonKit.toJson(result));
        // 需要根据return_code和result_code解析返回结果
        return JsonKit.toJson(result);
    }
}
