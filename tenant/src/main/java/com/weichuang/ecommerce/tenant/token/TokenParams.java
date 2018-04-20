package com.weichuang.ecommerce.tenant.token;

import java.io.Serializable;

/**
 * <p>ClassName: TokenParams</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: TokenParmas</p>
 * <p>author zhouhe</p>
 * <p>date 2017/1/19 11:07</p>
 */
public class TokenParams implements Serializable {
	private static final long serialVersionUID = 7644685658600691355L;
	private String token;
	private String deviceId;
	private String userId;
	private String userName;
	private String createDate;
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
}
