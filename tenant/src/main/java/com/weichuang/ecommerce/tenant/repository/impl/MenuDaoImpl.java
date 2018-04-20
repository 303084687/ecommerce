package com.weichuang.ecommerce.tenant.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.AdminMenuTemplate;
import com.weichuang.ecommerce.tenant.entity.MenuEntity;
import com.weichuang.ecommerce.tenant.entity.RoleMenuEntity;
import com.weichuang.ecommerce.tenant.repository.IMenuDao;

/**
 * <p>ClassName: MenuDaoImpl.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 角色-菜单接口实现</p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午5:54:43</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class MenuDaoImpl implements IMenuDao {
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;
	
	//增加菜单
	@Override
	public int addMenu(MenuEntity entity) {
		return sqlSessionTemplate.insert(NameSpaceConstant.MENU + ".addMenu", entity);
	}
	
	//修改菜单
	@Override
	public int updateMenu(MenuEntity entity) {
		return sqlSessionTemplate.update(NameSpaceConstant.MENU + ".updateMenu", entity);
	}
	
	//菜单列表
	@Override
	public List<MenuEntity> getMenuList(Integer type) {
		Map<String, Object> param = new HashMap<>();
		param.put("type", type);
		return sqlSessionTemplate.selectList(NameSpaceConstant.MENU + ".getMenuList", param);
	}
	
	//根据菜单主键查询详细信息
	@Override
	public MenuEntity getMenuById(Integer menuId, String menuName) {
		Map<String, Object> param = new HashMap<>();
		param.put("menuId", menuId);
		param.put("menuName", menuName);
		return sqlSessionTemplate.selectOne(NameSpaceConstant.MENU + ".getMenuById", param);
	}
	
	//菜单-角色增加
	@Override
	public int addMenuRole(List<RoleMenuEntity> list) {
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		return sqlSessionTemplate.insert(NameSpaceConstant.MENU + ".addMenuRole", param);
	}
	
	//菜单-角色删除(删除用户时候要对应删除用户菜单)
	@Override
	public int deleteMenuRole(Integer roleId) {
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", roleId);
		return sqlSessionTemplate.delete(NameSpaceConstant.MENU + ".deleteMenuRole", param);
	}
	
	//根据角色主键查询所有菜单（关联菜单和菜单-角色表查询）
	@Override
	public List<AdminMenuTemplate> getUserMenu(Integer roleId, Integer status) {
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", roleId);
		param.put("status", status);
		return sqlSessionTemplate.selectList(NameSpaceConstant.MENU + ".getUserMenu", param);
	}
}
