package com.weichuang.ecommerce.tenant.entity.request;

/**
 * <p>ClassName: UserRegister</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 这里用一句话描述这个方法的作用</p>
 * <p>author: licheng</p>
 * <p>date: 2016/10/17 14:11 </p>
 */
public class UserRegisterRequest {
	private String password;//经过算法加密过的
	private String userName;
	private String account;
	private Long platformId;
	private String appKey;
	private Long userId;
	private String code;//邮箱验证码
	private Integer status;
	//注册类型
	private Integer registerType;
	
	public Integer getRegisterType() {
		return registerType;
	}
	
	public void setRegisterType(Integer registerType) {
		this.registerType = registerType;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getAppKey() {
		return appKey;
	}
	
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public Long getPlatformId() {
		return platformId;
	}
	
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
}
