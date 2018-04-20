package com.weichuang.ecommerce.tenant.wechat.entity;
/**
* @describe 返回的结果对象 
* @author towards.liu@163.com
* @version 1.0
* @date  2017年9月20日
*/
  
public class WeiXinResult {  
      
    public static final int NEWSMSG = 1;            //图文消息  
    private boolean isSuccess;  
    private Object obj;  
    private int type;  
    private String msg;  
  
    public String getMsg() {  
        return msg;  
    }  
  
    public void setMsg(String msg) {  
        this.msg = msg;  
    }  
  
    public int getType() {  
        return type;  
    }  
  
    public void setType(int type) {  
        this.type = type;  
    }  
  
    public boolean isSuccess() {  
        return isSuccess;  
    }  
  
    public void setSuccess(boolean isSuccess) {  
        this.isSuccess = isSuccess;  
    }  
  
    public Object getObj() {  
        return obj;  
    }  
  
    public void setObj(Object obj) {  
        this.obj = obj;  
    }  
}  