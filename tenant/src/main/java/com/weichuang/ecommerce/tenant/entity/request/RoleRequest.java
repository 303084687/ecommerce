package com.weichuang.ecommerce.tenant.entity.request;

import java.io.Serializable;

/**
 * <p>ClassName: RoleEntity.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:角色表请求类 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午3:53:58</p>
 */
public class RoleRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;//角色主键
	private String name;//角色名称
	private String roleKey;//英文描述
	private String description;//描述
	private int status;//状态：1-启用(默认)，2-禁用
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRoleKey() {
		return roleKey;
	}
	
	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
}