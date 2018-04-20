package com.weichuang.ecommerce.weixinPay.Transfers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.weichuang.ecommerce.weixinPay.util.PaymentKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.kit.JsonKit;
import com.weichuang.ecommerce.weixinPay.api.WxPayApi;
import com.weichuang.ecommerce.weixinPay.util.IpKit;
import com.weichuang.ecommerce.weixinPay.util.StrKit;

/**
 * <p>ClassName: ReadPackUtils.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:企业付款封装类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月26日 下午2:54:49</p>
 */
public class TransfersUtils {
    private static final Logger log = LoggerFactory.getLogger(TransfersUtils.class);

    /**
     * 发送企业付款
     * @param request 获取IP
     * @param total_amount  付款现金(单位分)
     * @param remark 备注
     * @param reOpenid 用户openid
     * @param mchId 商户号
     * @param tradeNo 第三方订单号
     * @param wxappid 商户账号appid
     * @param paternerKey 商户签名key
     * @param certPath 证书路径
     * @return
     */
    public static String sendTransfers(HttpServletRequest request, String amount, String remark, String reOpenid,
            String tradeNo, String mchId, String wxappid, String paternerKey, String certPath) {
        // 获取ip地址要是没有默认为本机地址
        String ip = IpKit.getRealIp(request);
        if (StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }
        Map<String, String> params = new HashMap<String, String>();
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        // 商户订单号
        params.put("partner_trade_no", tradeNo);
        // 商户号
        params.put("mchid", mchId);
        // 公众账号ID
        params.put("mch_appid", wxappid);
        // 用户OPENID
        params.put("openid", reOpenid);
        // 付款现金(单位分)
        int price = ((int) (Float.valueOf(amount) * 100));
        params.put("amount", price + "");
        // 终端IP
        params.put("spbill_create_ip", ip);
        // 校验用户姓名选项NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
        params.put("check_name", "NO_CHECK");
        // 备注
        params.put("desc", remark);
        // 创建签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        // 调取微信企业转账的接口
        String xmlResult = WxPayApi.transfers(params, certPath, mchId);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.warn(JsonKit.toJson(result));
        // 此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        String return_code = result.get("return_code");
        // 业务结果
        String result_code = result.get("result_code");

        if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
            // 返回错误信息
            return JsonKit.toJson(result);
        }
        // 业务结果失败时候返回
        if (StrKit.isBlank(result_code) || !"SUCCESS".equals(result_code)) {
            // 返回错误信息
            return JsonKit.toJson(result);
        }
        // 执行成功返回
        if ("SUCCESS".equals(return_code) && "SUCCESS".equals(result_code)) {
            // 封装返回信息
            Map<String, String> packageParams = new HashMap<String, String>();
            packageParams.put("tradeNo", result.get("partner_trade_no"));// 商户订单号
            packageParams.put("paymentNo", result.get("payment_no"));// 微信订单号
            packageParams.put("paymentTime", result.get("payment_time"));// 付款成功时间
            return JsonKit.toJson(packageParams);
        }
        return null;
    }

    /**
     * 根据商户订单号查询企业付款信息
     * @param partner_trade_no 商户订单号
     * @param mchId 商户号
     * @param wxappid 公众账号ID
     * @param paternerKey 商户签名Key
     * @param certPath 证书路径
     * @return
     */
    public static String query(String tradeNo, String mchId, String wxappid, String paternerKey, String certPath) {
        Map<String, String> params = new HashMap<String, String>();
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        // 商户订单号
        params.put("partner_trade_no", tradeNo);
        // 商户号
        params.put("mch_id", mchId);
        // 公众账号ID
        params.put("appid", wxappid);
        // 创建签名
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        // 调取查询企业转账的接口
        String xmlResult = WxPayApi.getTransferInfo(params, certPath, mchId);
        Map<String, String> result = PaymentKit.xmlToMap(xmlResult);
        log.warn(JsonKit.toJson(result));
        return JsonKit.toJson(result);
    }
}
