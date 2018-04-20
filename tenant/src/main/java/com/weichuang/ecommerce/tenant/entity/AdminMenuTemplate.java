package com.weichuang.ecommerce.tenant.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p>ClassName: MenuEntity.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台用户权限列表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午3:59:50</p>
 */
public class AdminMenuTemplate implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;//名称
	private Integer parentId;//父菜单id
	private String parentName;
	private String resKey;//英文描述
	private Integer type;//0根目录1一级菜单2二级菜单
	private String resUrl;//菜单地址
	private int status;//1正常2禁用
	private String description;//描述
	private int selected;//是否有权限的标志
	private String icon;
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	private List<AdminMenuTemplate> childrens = new ArrayList<AdminMenuTemplate>();//菜单的子集和
	
	public List<AdminMenuTemplate> getChildrens() {
		return childrens;
	}
	
	public void setChildrens(List<AdminMenuTemplate> childrens) {
		this.childrens = childrens;
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
	
	public String getParentName() {
		return parentName;
	}
	
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	public String getResKey() {
		return resKey;
	}
	
	public void setResKey(String resKey) {
		this.resKey = resKey;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
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
	
	public int getSelected() {
		return selected;
	}
	
	public void setSelected(int selected) {
		this.selected = selected;
	}
	
	/** 
	  * 孩子节点列表 
	  */
	private Children children = new Children();
	
	// 先序遍历，拼接JSON字符串  
	public String toString() {
		String result = "{" + "id : '" + this.id + "'" + ", name : '" + this.name + "'" + ",parentId : '"
				+ this.parentId + "'" + ", parentName : '" + this.parentName + "'" + ",resKey : '" + this.resKey + "'"
				+ ",type : '" + this.type + "'" + ",resUrl : '" + this.resUrl + "'" + ",selected : '" + this.selected
				+ "'";
		if (children != null && children.getSize() != 0) {
			result += ", children : " + children.toString();
		}
		return result + "}";
	}
	
	// 添加孩子节点  
	public void addChild(AdminMenuTemplate node) {
		this.children.addChild(node);
	}
}


/** 
* 孩子列表类 
*/
@SuppressWarnings("all")
class Children {
	private List list = new ArrayList();
	
	public int getSize() {
		return list.size();
	}
	
	public void addChild(AdminMenuTemplate node) {
		list.add(node);
	}
	
	// 拼接孩子节点的JSON字符串  
	public String toString() {
		String result = "[";
		for (Iterator it = list.iterator(); it.hasNext();) {
			result += ((AdminMenuTemplate) it.next()).toString();
			result += ",";
		}
		result = result.substring(0, result.length() - 1);
		result += "]";
		return result;
	}
}
