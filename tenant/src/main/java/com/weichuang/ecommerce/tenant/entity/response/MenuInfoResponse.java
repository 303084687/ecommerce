package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.MenuEntity;

/**
 * <p>ClassName: MenuInfoResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:菜单详情返回 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月23日 上午11:21:38</p>
 */
public class MenuInfoResponse {
	private MenuEntity entity;
	
	public MenuEntity getEntity() {
		return entity;
	}
	
	public void setEntity(MenuEntity entity) {
		this.entity = entity;
	}
}
