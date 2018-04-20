package com.weichuang.ecommerce.tenant.entity;

public class CountryEntity {
	/**
	 * <pre>
	 * 
	 * 表字段 : t_address.id
	 * </pre>
	 */
	private Long id;
	/**
	 * <pre>
	 * 
	 * 表字段 : t_address.parent_id
	 * </pre>
	 */
	private Long parentId;
	/**
	 * <pre>
	 * 
	 * 表字段 : t_address.foreign_name
	 * </pre>
	 */
	private String foreignName;
	
	/**
	 * <pre>
	 * 获取：
	 * 表字段：t_address.id
	 * </pre>
	 *
	 * @return t_address.id：
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * <pre>
	 * 设置：
	 * 表字段：t_address.id
	 * </pre>
	 *
	 * @param id
	 *            t_address.id：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * <pre>
	 * 获取：
	 * 表字段：t_address.parent_id
	 * </pre>
	 *
	 * @return t_address.parent_id：
	 */
	public Long getParentId() {
		return parentId;
	}
	
	/**
	 * <pre>
	 * 设置：
	 * 表字段：t_address.parent_id
	 * </pre>
	 *
	 * @param parentId
	 *            t_address.parent_id：
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * <pre>
	 * 获取：
	 * 表字段：t_address.foreign_name
	 * </pre>
	 *
	 * @return t_address.foreign_name：
	 */
	public String getForeignName() {
		return foreignName;
	}
	
	/**
	 * <pre>
	 * 设置：
	 * 表字段：t_address.foreign_name
	 * </pre>
	 *
	 * @param foreignName
	 *            t_address.foreign_name：
	 */
	public void setForeignName(String foreignName) {
		this.foreignName = foreignName == null ? null : foreignName.trim();
	}
}