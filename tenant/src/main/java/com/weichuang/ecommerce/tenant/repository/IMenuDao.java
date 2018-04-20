package com.weichuang.ecommerce.tenant.repository;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.AdminMenuTemplate;
import com.weichuang.ecommerce.tenant.entity.MenuEntity;
import com.weichuang.ecommerce.tenant.entity.RoleMenuEntity;

/**
 * <p>ClassName: IMenuDao.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 菜单接口</p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午5:52:53</p>
 */
public interface IMenuDao {
	//增加菜单
	public int addMenu(MenuEntity entity);
	
	//修改菜单
	public int updateMenu(MenuEntity entity);
	
	//菜单列表,type用在添加菜单的时候根据(目录1菜单2按钮3)
	public List<MenuEntity> getMenuList(Integer type);
	
	//根据菜单主键查询详细信息
	public MenuEntity getMenuById(Integer menuId, String menuName);
	
	//菜单-角色增加
	public int addMenuRole(List<RoleMenuEntity> list);
	
	//菜单-角色删除(删除角色时候要对应删除角色菜单)
	public int deleteMenuRole(Integer roleId);
	
	//根据角色主键查询所有菜单（关联菜单和菜单-角色表查询）
	public List<AdminMenuTemplate> getUserMenu(Integer roleId, Integer status);
}
