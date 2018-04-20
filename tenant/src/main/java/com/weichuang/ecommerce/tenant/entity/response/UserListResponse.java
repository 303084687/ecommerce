package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.UserEntity;

/**
 * <p>ClassName: UserListResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户集合返回 C端使用 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月2日 下午2:10:37</p>
 */
public class UserListResponse {
	private List<UserEntity> list;
	private Long count;
	
	public List<UserEntity> getList() {
		return list;
	}
	
	public void setList(List<UserEntity> list) {
		this.list = list;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
