package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.AdminEntity;

/**
 * <p>ClassName: AdminEntity.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台用户表详情返回 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午3:48:17</p>
 */
public class AdminInfoResponse {
	private AdminEntity adminEntity;
	
	public AdminEntity getAdminEntity() {
		return adminEntity;
	}
	
	public void setAdminEntity(AdminEntity adminEntity) {
		this.adminEntity = adminEntity;
	}
}