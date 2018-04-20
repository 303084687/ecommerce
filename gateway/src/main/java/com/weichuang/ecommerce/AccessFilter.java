package com.weichuang.ecommerce;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.weichuang.commons.Constant;
import com.weichuang.commons.sign.partner.Partner;
import com.weichuang.commons.sign.partner.PartnerConfig;
import com.weichuang.commons.sign.partner.ValidateAvailable;
import com.weichuang.commons.sign.response.SignResponse;
import com.weichuang.commons.sign.response.SignResult;

@SuppressWarnings("all")
public class AccessFilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        System.out.println("AccessFilter开始 ===============================");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        // 1.对appKey进行非空判断
        String appKey = request.getHeader(Constant.HEADER_APPKEY_KEY);// 公钥
        System.out.println("appKey ==============================: " + appKey);

        if (StringUtils.isEmpty(appKey)) {
            SignResponse resp = new SignResponse(SignResult.MISSING_APIKEY);
            ctx.setSendZuulResponse(false); // zuul过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(SignResult.MISSING_APIKEY.getCode());
            // 返回的错误码
            ctx.setResponseBody(JSON.toJSONString(resp)); // 返回body内容
            return ctx;
        }
        // 2.取得signature 并对其进行非空判断
        String signature = request.getHeader(Constant.HEADER_SIGNATURE_KEY);// 签名
        System.out.println("signature ========================: " + signature);
        if (StringUtils.isEmpty(signature)) {
            SignResponse resp = new SignResponse(SignResult.MISSING_SIGNATURE);
            ctx.setSendZuulResponse(false); // zuul过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(SignResult.MISSING_SIGNATURE.getCode()); // 返回的错误码
            ctx.setResponseBody(JSON.toJSONString(resp)); // 返回body内容
            return ctx;
        }

        // 3.对appKey的有效性进行判断（根据不同的客户端，从配置文件中取相应信息）
        Partner partner = PartnerConfig.getPartner(appKey);
        if (StringUtils.isEmpty(partner)) {
            SignResponse resp = new SignResponse(SignResult.BAD_APIKEY);
            ctx.setSendZuulResponse(false); // zuul过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(SignResult.BAD_APIKEY.getCode()); // 返回的错误码
            ctx.setResponseBody(JSON.toJSONString(resp)); // 返回body内容
            return ctx;
        }

        // 4.对签名的有效性进行判断
        if (!ValidateAvailable.isAvailableSignature(request, signature, partner)) {
            SignResponse resp = new SignResponse(SignResult.BAD_SIGNATURE);
            ctx.setSendZuulResponse(false); // zuul过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(SignResult.BAD_SIGNATURE.getCode()); // 返回的错误码
            ctx.setResponseBody(JSON.toJSONString(resp)); // 返回body内容
            return ctx;
        }
        return ctx;
    }

}