package com.weichuang.ecommerce.tenant.entity.response;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.ParentAgentChildAgentSalesEntity;
import com.weichuang.ecommerce.tenant.entity.ParentAgentEntity;

public class ParentAgentChildAgentSalesListResponse {
	private List<ParentAgentChildAgentSalesEntity> list;

	private int pages;

	private Long total;// 返回的总条数

	public List<ParentAgentChildAgentSalesEntity> getList() {
		return list;
	}

	public void setList(List<ParentAgentChildAgentSalesEntity> list) {
		this.list = list;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
