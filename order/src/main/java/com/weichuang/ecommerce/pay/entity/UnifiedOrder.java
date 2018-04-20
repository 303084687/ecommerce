package com.weichuang.ecommerce.pay.entity;

import java.io.Serializable;

/**
 * <p>ClassName: UnifiedOrder.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:支付返回参数 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月22日 下午2:07:14</p>
 */
public class UnifiedOrder implements Serializable {
    private static final long serialVersionUID = -142514382782598235L;

    private String appId;

    private String timeStamp;

    private String nonceStr;

    private String packages;

    private String signType;

    private String paySign;

    private String errorDes;

    public String getErrorDes() {
        return errorDes;
    }

    public void setErrorDes(String errorDes) {
        this.errorDes = errorDes;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

}
