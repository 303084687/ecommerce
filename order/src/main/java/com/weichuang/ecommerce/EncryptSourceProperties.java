package com.weichuang.ecommerce;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>ClassName: EncryptSourceProperties.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.zhidianwuxian.cn</p>
 * <p>Description: 加密参数配置文件</p>
 * <p>author: wanggongliang</p>
 * <p>2016年11月23日 下午1:39:23</p>
 */
@Component("EncryptSourceProperties")
@ConfigurationProperties(prefix = "encrypt")
public class EncryptSourceProperties {
    private String globalKey;

    private String passSalt;

    private String key;

    public String getGlobalKey() {
        return globalKey;
    }

    public void setGlobalKey(String globalKey) {
        this.globalKey = globalKey;
    }

    public String getPassSalt() {
        return passSalt;
    }

    public void setPassSalt(String passSalt) {
        this.passSalt = passSalt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
