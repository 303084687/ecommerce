package com.weichuang.ecommerce.weixinPay.controller;

import java.util.HashMap;
import java.util.Map;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.weichuang.commons.OrderNumberUtil;
import com.weichuang.ecommerce.weixinPay.Transfers.TransfersUtils;
import com.weichuang.ecommerce.weixinPay.util.HttpUtils;
import com.weichuang.ecommerce.weixinPay.util.IpKit;
import com.weichuang.ecommerce.weixinPay.util.PaymentKit;

/**
 * <p>ClassName: WeixinTransfersController.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:企业付款测试 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月26日 下午4:25:34</p>
 */
public class WeixinTransfersController extends Controller {

    // 微信证书路径
    private static String certPath = "/Users/Javen/Downloads/cert/apiclient_cert.p12";

    // 商户相关资料
    private static String appid = "";

    // 微信支付分配的商户号
    private static String partner = "";

    // API密钥
    private String paternerKey = "";

    private static String transfer_url = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    public void sendTransfers() throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        // 收款用户在wxappid下的openid
        String openid = "oyPBJ0Rafl9008FDzqzHtX_HaBCQ";
        // 真实姓名（可选）
        // String reUserName = "Javen205";
        // 金额 单位：分
        params.put("amount", "1");
        // 是否验证姓名
        // NO_CHECK：不校验真实姓名
        // FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）
        // OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
        params.put("check_name", "NO_CHECK");
        // 描述
        params.put("desc", "测试企业付款");
        params.put("mch_appid", appid);
        params.put("mchid", partner);
        // 随机字符串
        params.put("nonce_str", System.currentTimeMillis() / 1000 + "");
        params.put("openid", openid);
        params.put("partner_trade_no", OrderNumberUtil.getWeiXinNo());
        // 收款用户真实姓名。
        // 如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
        // params.put("re_user_name", reUserName);
        String ip = IpKit.getRealIp(getRequest());
        if (StrKit.isBlank(ip)) {
            ip = "127.0.0.1";
        }
        params.put("spbill_create_ip", ip);
        String sign = PaymentKit.createSign(params, paternerKey);
        params.put("sign", sign);
        String xml = PaymentKit.toXml(params);
        System.out.println(xml);
        String xmlResult = HttpUtils.postSSL(transfer_url, xml, certPath, partner);
        System.out.println(xmlResult);
        Map<String, String> resultXML = PaymentKit.xmlToMap(xmlResult.toString());
        // 返回状态码此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
        String return_code = resultXML.get("return_code");
        String return_msg = resultXML.get("return_msg");

        if (StrKit.isBlank(return_code) || !"SUCCESS".equals(return_code)) {
            renderText(return_msg);
        }
        // 业务结果成功
        String result_code = resultXML.get("result_code");
        if (StrKit.notBlank(result_code) && "SUCCESS".equals(result_code)) {
            renderText(return_msg);
        }
    }

    // 请求企业退款
    public void sendredpack() throws Exception {
        String json = TransfersUtils.sendTransfers(getRequest(), "1", "测试企业退款", "oyPBJ0Rafl9008FDzqzHtX_HaBCQ",
                OrderNumberUtil.getWeiXinNo(), partner, appid, paternerKey, certPath);

        renderJson(json);
    }

    // 查询企业退款
    public void query() throws Exception {
        String query = TransfersUtils.query("10000098201411111234567890", partner, appid, paternerKey, certPath);
        renderJson(query);
    }
}
