package com.weichuang.ecommerce.tenant.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: AdminTemplate.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台用户返回实体 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月21日 下午3:07:02</p>
 */
public class AdminTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;//主键自增长
	private String adminName;//用户名
	private String name;//姓名
	private String mobile;//手机号 加密
	private int sex;//性别：2-女；1-男,3保密
	private String headingUrl;//头像地址
	private int status;//状态：1-启用，2-禁用
	private Date createTime;//创建时间
	private String roleName;//角色名
	private int roleId;//角色主键
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
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
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}