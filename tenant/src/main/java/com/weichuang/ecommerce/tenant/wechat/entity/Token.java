package com.weichuang.ecommerce.tenant.wechat.entity;
/**
 * @describe 用户凭证
* @author towards.liu@163.com
* @version 1.0
* @date  2017年9月16日
* 
*/
public class Token {
	 // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
