package com.weichuang.ecommerce.weixinPay.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.jfinal.core.Controller;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.hb.ReadPackUtils;

/**
 * <p>ClassName: RedPackApiController.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:微信红包 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月26日 下午4:22:38</p>
 */
public class RedPackApiController extends Controller {

    @Autowired
    private WeiXinProperties weiXinProperties;

    /**
     * 发送普通红包
     */
    public void sendredpack() throws Exception {

        boolean isSend = ReadPackUtils.sendredpack(getRequest(), "10", "1", "感谢您参加猜灯谜活动，祝您元宵节快乐！", "猜灯谜抢红包活动",
                "猜越多得越多，快来抢！", "oyPBJ0Rafl9008FDzqzHtX_HaBCQ", weiXinProperties.getMchId(),
                weiXinProperties.getAppId(), weiXinProperties.getSendName(), weiXinProperties.getPaternerKey(),
                weiXinProperties.getCertPath());

        renderJson(isSend);
    }

    /**
     * 发送裂变红包
     */
    public void sendGroupRedPack() throws Exception {

        boolean isSend = ReadPackUtils.sendGroupRedPack(weiXinProperties.getMchId(), weiXinProperties.getAppId(),
                weiXinProperties.getSendName(), "oyPBJ0Rafl9008FDzqzHtX_HaBCQ", "50", "3", "感谢您参加猜灯谜活动，祝您元宵节快乐！",
                "猜灯谜抢红包活动", "猜越多得越多，快来抢", weiXinProperties.getPaternerKey(), weiXinProperties.getCertPath());

        renderJson(isSend);
    }

    public void query() throws Exception {
        String query = ReadPackUtils.query("10000098201411111234567890", weiXinProperties.getMchId(),
                weiXinProperties.getAppId(), weiXinProperties.getPaternerKey(), weiXinProperties.getCertPath());
        renderJson(query);
    }

}