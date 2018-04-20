package com.weichuang.ecommerce.tenant.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>ClassName: MenuEntity.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:菜单表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午3:59:50</p>
 */
public class MenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;//名称
	private Integer parentId;//父菜单id
	private String resKey;//英文描述
	private int type;//根目录1一级菜单2二级菜单
	private String resUrl;//菜单地址
	private int status;//1正常2禁用
	private String description;//描述
	private Date createTime;//创建时间
	private String parentName;
	private String icon;//图标
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getResKey() {
		return resKey;
	}
	
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getResUrl() {
		return resUrl;
	}
	
	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}