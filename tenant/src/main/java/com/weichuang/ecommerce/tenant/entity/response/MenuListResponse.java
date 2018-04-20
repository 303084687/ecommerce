package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.MenuEntity;

/**
 * <p>ClassName: MenuListResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:菜单列表返回列表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月23日 上午11:18:30</p>
 */
public class MenuListResponse {
	private List<MenuEntity> list;
	private Long count;
	
	public List<MenuEntity> getList() {
		return list;
	}
	
	public void setList(List<MenuEntity> list) {
		this.list = list;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
