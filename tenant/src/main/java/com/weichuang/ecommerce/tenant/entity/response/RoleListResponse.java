package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.RoleEntity;

/**
 * <p>ClassName: RoleListResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:角色返回列表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月22日 下午7:08:13</p>
 */
public class RoleListResponse {
	private List<RoleEntity> list;
	private Long count;
	
	public List<RoleEntity> getList() {
		return list;
	}
	
	public void setList(List<RoleEntity> list) {
		this.list = list;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
