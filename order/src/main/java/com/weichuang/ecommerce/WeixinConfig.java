package com.weichuang.ecommerce;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
* <p>ClassName:WeixinConfig</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:微信配置实体类</p>
* <p>author:zhanghongsheng</p>
* <p>2017/10/27 13:24</p>
**/
@Component
@ConfigurationProperties(prefix = "weixin.config")
public class WeixinConfig {

    private String appId;

    private String appSecret;

    private String h5NetworkUrl;


    public String getH5NetworkUrl() {
        return h5NetworkUrl;
    }

    public void setH5NetworkUrl(String h5NetworkUrl) {
        this.h5NetworkUrl = h5NetworkUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
    

}
