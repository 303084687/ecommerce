package com.weichuang.ecommerce.barcode.entity.request;

import com.weichuang.ecommerce.barcode.entity.TenantCodeStoreEntity;

import java.io.Serializable;
/**
 * <p>ClassName:TenantCodeStoreRequest</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:</p>
 * <p>author:zhanghongsheng</p>
 * <p>2017/10/19 18:36</p>
 **/
public class TenantCodeStoreRequest  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer codeType;//二位码类型 1：公司 2：健身房 3:销售 4:客户

    private Integer userAgentId; //根据类型对应id

    private boolean hasImg=false;//是否有二维码


    private String storeData; //二维码存储url信息

    private Integer actionType; //动作类型

    private String addressUrl; //最终导航地址

    private Integer nx;//1:存在返回原来的，不存在才添加，返回新的，2：存在把以前状态修改为无效，在添加新的，返回新的

    private String otherData;//其他数据


    public String getOtherData() {
        return otherData;
    }

    public void setOtherData(String otherData) {
        this.otherData = otherData;
    }

    public boolean isHasImg() {
        return hasImg;
    }

    public void setHasImg(boolean hasImg) {
        this.hasImg = hasImg;
    }


    public Integer getActionType() {
        return actionType;
    }

    public void setActionType(Integer actionType) {
        this.actionType = actionType;
    }

    public String getAddressUrl() {
        return addressUrl;
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }


    public Integer getNx() {
        return nx;
    }

    public void setNx(Integer nx) {
        this.nx = nx;
    }

    public Integer getCodeType() {
        return codeType;
    }

    public void setCodeType(Integer codeType) {
        this.codeType = codeType;
    }

    public Integer getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(Integer userAgentId) {
        this.userAgentId = userAgentId;
    }

    public String getStoreData() {
        return storeData;
    }

    public void setStoreData(String storeData) {
        this.storeData = storeData;
    }
}
