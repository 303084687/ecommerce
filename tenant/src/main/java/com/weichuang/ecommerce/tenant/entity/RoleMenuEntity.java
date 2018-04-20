package com.weichuang.ecommerce.tenant.entity;

import java.io.Serializable;

/**
 * <p>ClassName: RoleMenuEntity.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:角色-菜单关联表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午4:01:30</p>
 */
public class RoleMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int meunId;
	private int roleId;
	
	public int getMeunId() {
		return meunId;
	}
	
	public void setMeunId(int meunId) {
		this.meunId = meunId;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}