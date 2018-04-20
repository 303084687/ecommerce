package com.weichuang.ecommerce.weixin.entity.response;
/**
* <p>ClassName:SendMessageResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/19 13:26</p>
**/
public class SendMessageResponse {
    
    private int errcode; //错误码
    private String errmsg;//错误信息

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
