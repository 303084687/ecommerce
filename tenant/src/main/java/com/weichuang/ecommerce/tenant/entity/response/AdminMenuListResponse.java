package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.AdminMenuTemplate;
import com.weichuang.ecommerce.tenant.entity.MenuEntity;

/**
 * <p>ClassName: AdminMenuListResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 根据用户主键返回的集合</p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月23日 上午11:40:05</p>
 */
@SuppressWarnings("all")
public class AdminMenuListResponse {
	private List<AdminMenuTemplate> list;
	private List<MenuEntity> allMenuList;
	private List userMenuList;
	
	public List getUserMenuList() {
		return userMenuList;
	}
	
	public void setUserMenuList(List userMenuList) {
		this.userMenuList = userMenuList;
	}
	
	public List<AdminMenuTemplate> getList() {
		return list;
	}
	
	public void setList(List<AdminMenuTemplate> list) {
		this.list = list;
	}
	
	public List<MenuEntity> getAllMenuList() {
		return allMenuList;
	}
	
	public void setAllMenuList(List<MenuEntity> allMenuList) {
		this.allMenuList = allMenuList;
	}
}
