package com.weichuang.ecommerce.tenant.entity;

/**
 * <p>
 * ClassName: MenuEntity.java
 * </p>
 * <p>
 * Company: 指点无限(北京)科技有限公司
 * http://www.weichuangwuxian.cn
 * </p>
 * <p>
 * Description:菜单表 请求类
 * </p>
 * <p>
 * author: wanggongliang
 * </p>
 * <p>
 * 2017年2月20日 下午3:59:50
 * </p>
 */
/**
 * <p>ClassName: MenuRequest.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: </p>
 * <p>author: wanggongliang</p>
 * <p>2017年3月16日 下午6:50:09</p>
 */
public class MenuRequest {
	private int id;
	private String name;//名称
	private int parentId;//父菜单id
	private String resKey;//英文描述
	private int type;//0根目录1一级菜单2二级菜单
	private String resUrl;//菜单地址
	private int status;//1正常2禁用
	private String description;//描述
	private String icon;//图标
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
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
	
	public int getParentId() {
		return parentId;
	}
	
	public void setParentId(int parentId) {
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
}