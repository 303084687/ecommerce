package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.AdminEntity;
import com.weichuang.ecommerce.tenant.entity.AdminMenuTemplate;

/**
 * <p>ClassName: AdminLoginResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台用户登录返回实体 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月27日 上午11:00:21</p>
 */
@SuppressWarnings("all")
public class AdminLoginResponse {
	//用户信息
	private AdminEntity entity;
	//权限集合
	private List<AdminMenuTemplate> menuList;
	
	public AdminEntity getEntity() {
		return entity;
	}
	
	public void setEntity(AdminEntity entity) {
		this.entity = entity;
	}
	
	public List<AdminMenuTemplate> getMenuList() {
		return menuList;
	}
	
	public void setMenuList(List<AdminMenuTemplate> menuList) {
		this.menuList = menuList;
	}
}
