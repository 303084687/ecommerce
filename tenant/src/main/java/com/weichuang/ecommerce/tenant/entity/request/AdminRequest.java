package com.weichuang.ecommerce.tenant.entity.request;

import java.io.Serializable;

/**
 * <p>ClassName: AdminEntity.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台用户表请求表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午3:48:17</p>
 */
public class AdminRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;//主键自增长
	private String adminName;//用户名
	private String password;//密码 加密-单向
	private String name;//姓名
	private String mobile;//手机号 加密
	private int sex;//性别：2-女；1-男,3保密
	private String headingUrl;//头像地址
	private int status;//状态：1-启用，2-禁用
	private String appKey;//密码加密key
	private int roleId;//角色主键
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getAppKey() {
		return appKey;
	}
	
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAdminName() {
		return adminName;
	}
	
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public int getSex() {
		return sex;
	}
	
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public String getHeadingUrl() {
		return headingUrl;
	}
	
	public void setHeadingUrl(String headingUrl) {
		this.headingUrl = headingUrl;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}