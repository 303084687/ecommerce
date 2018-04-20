package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.UserAddressTemplate;

/**
 * <p>ClassName: UserAddressListResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户收货地址列表返回</p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月7日 下午1:43:40</p>
 */
public class UserAddressListResponse {
	private List<UserAddressTemplate> list;
	private Long count;
	
	public List<UserAddressTemplate> getList() {
		return list;
	}
	
	public void setList(List<UserAddressTemplate> list) {
		this.list = list;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
