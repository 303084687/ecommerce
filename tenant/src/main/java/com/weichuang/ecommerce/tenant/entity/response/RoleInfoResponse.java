package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.RoleEntity;

/**
 * <p>ClassName: RoleInfoResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 角色详情返回</p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月22日 下午7:10:57</p>
 */
public class RoleInfoResponse {
	private RoleEntity roleEntity;
	
	public RoleEntity getRoleEntity() {
		return roleEntity;
	}
	
	public void setRoleEntity(RoleEntity roleEntity) {
		this.roleEntity = roleEntity;
	}
}
