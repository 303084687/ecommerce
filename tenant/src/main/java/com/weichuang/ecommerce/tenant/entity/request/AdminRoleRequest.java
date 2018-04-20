package com.weichuang.ecommerce.tenant.entity.request;

/**
 * <p>ClassName: AdminRoleEntity.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户-角色关联表</p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午3:55:23</p>
 */
public class AdminRoleRequest {
	private int adminId;
	//外键-角色表id：role表id
	private int roleId;
	
	public int getAdminId() {
		return adminId;
	}
	
	public void setAdminId(int adminId) {
		this.adminId = adminId;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
}