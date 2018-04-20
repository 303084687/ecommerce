package com.weichuang.ecommerce.weixinPay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: WeiXinProperties.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:微信配置文件 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月26日 下午3:53:27</p>
 */
@Component("weiXinProperties")
@ConfigurationProperties(prefix = "WxPay")
public class WeiXinProperties {
    private String mchId;// 商户号

    private String appId;// 商户账号appid

    private String certPath;// 证书路径类似"/Users/Javen/Downloads/cert/apiclient_cert.p12"

    private String paternerKey;// 商户平台设置的密钥key

    private String sendName;// 商户名

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getPaternerKey() {
        return paternerKey;
    }

    public void setPaternerKey(String paternerKey) {
        this.paternerKey = paternerKey;
    }

}
