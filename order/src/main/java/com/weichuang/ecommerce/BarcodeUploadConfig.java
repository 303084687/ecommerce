package com.weichuang.ecommerce;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
* <p>ClassName:BarcodeUploadConfig</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/21 16:41</p>
**/
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class BarcodeUploadConfig {
    private String barcodePath;

    public String getBarcodePath() {
        return barcodePath;
    }

    public void setBarcodePath(String barcodePath) {
        this.barcodePath = barcodePath;
    }
}
