package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.AdminTemplate;

/**
 * <p>ClassName: AdminInfoResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台用户返回列表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月22日 上午11:19:02</p>
 */
public class AdminListResponse {
	private List<AdminTemplate> list;
	private Long count;
	
	public List<AdminTemplate> getList() {
		return list;
	}
	
	public void setList(List<AdminTemplate> list) {
		this.list = list;
	}
	
	public Long getCount() {
		return count;
	}
	
	public void setCount(Long count) {
		this.count = count;
	}
}
