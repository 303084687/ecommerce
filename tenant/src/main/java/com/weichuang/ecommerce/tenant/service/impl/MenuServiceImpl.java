package com.weichuang.ecommerce.tenant.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.constants.Util;
import com.weichuang.ecommerce.tenant.entity.AdminMenuTemplate;
import com.weichuang.ecommerce.tenant.entity.MenuEntity;
import com.weichuang.ecommerce.tenant.entity.MenuRequest;
import com.weichuang.ecommerce.tenant.entity.RoleMenuEntity;
import com.weichuang.ecommerce.tenant.entity.response.AdminMenuListResponse;
import com.weichuang.ecommerce.tenant.entity.response.MenuInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.MenuListResponse;
import com.weichuang.ecommerce.tenant.repository.IMenuDao;
import com.weichuang.ecommerce.tenant.service.IMenuService;

/**
 * <p>ClassName: MenuServiceImpl.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户菜单操作 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月23日 下午4:16:16</p>
 */
@Service("menuService")
public class MenuServiceImpl implements IMenuService {
	@Autowired
	private IMenuDao menuDao;
	
	//增加菜单
	@Override
	@Transactional
	public int addMenu(MenuRequest request) throws ServiceException {
		MenuEntity entity = new MenuEntity();
		entity.setName(request.getName());
		entity.setType(request.getType());
		entity.setIcon(request.getIcon());
		if (request.getParentId() == 0) {
			entity.setParentId(null);
		} else {
			entity.setParentId(request.getParentId());
		}
		entity.setResKey(request.getResKey());
		entity.setResUrl(request.getResUrl());
		entity.setDescription(request.getDescription());
		entity.setCreateTime(new Date());
		return menuDao.addMenu(entity);
	}
	
	//修改菜单
	@Override
	@Transactional
	public int updateMenu(MenuRequest request) throws ServiceException {
		MenuEntity entity = new MenuEntity();
		entity.setId(request.getId());
		entity.setName(request.getName());
		if (request.getParentId() == 0) {
			entity.setParentId(null);
		} else {
			entity.setParentId(request.getParentId());
		}
		entity.setResUrl(request.getResUrl());
		entity.setStatus(request.getStatus());
		entity.setDescription(request.getDescription());
		entity.setIcon(request.getIcon());
		return menuDao.updateMenu(entity);
	}
	
	//菜单列表(带分页)type用于添加菜单时候根据类型不同加载不同数据1目录2菜单
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("all")
	public MenuListResponse getMenuList(int pageNum, int pageSize, Integer type) throws ServiceException {
		//执行查询数据和分页处理
		PageHelper.startPage(pageNum, pageSize);
		MenuListResponse response = new MenuListResponse();
		List<MenuEntity> list = menuDao.getMenuList(type);
		PageInfo page = new PageInfo(list);
		response.setList(list);
		response.setCount(page.getTotal());
		return response;
	}
	
	//根据菜单主键查询详细信息
	@Override
	@Transactional(readOnly = true)
	public MenuInfoResponse getMenuById(Integer menuId, String menuName) throws ServiceException {
		MenuEntity entity = menuDao.getMenuById(menuId, menuName);
		MenuInfoResponse response = new MenuInfoResponse();
		response.setEntity(entity);
		return response;
	}
	
	//菜单-角色增加
	@Override
	@Transactional
	public int addMenuRole(String menuIds, Integer roleId) throws ServiceException {
		//1:先删掉角色对应的菜单
		menuDao.deleteMenuRole(roleId);
		//2:新增角色的菜单
		//去掉传入的菜单集合中的相同项
		List<Integer> menuIdList = Util.removeSameItem(Arrays.asList(menuIds.split(",")));
		List<RoleMenuEntity> list = new ArrayList<RoleMenuEntity>();
		for (int i = 0; i < menuIdList.size(); i++) {
			RoleMenuEntity roleMenuEntity = new RoleMenuEntity();
			roleMenuEntity.setMeunId(menuIdList.get(i));
			roleMenuEntity.setRoleId(roleId);
			list.add(roleMenuEntity);
		}
		//执行增加的操作
		return menuDao.addMenuRole(list);
	}
	
	//根据角色主键查询所有菜单（关联菜单和菜单-角色表查询）
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("all")
	public AdminMenuListResponse getUserMenu(Integer roleId, Integer status) throws ServiceException {
		AdminMenuListResponse response = new AdminMenuListResponse();
		//查询出角色对应的菜单列表
		List<AdminMenuTemplate> list = menuDao.getUserMenu(roleId, status);
		//查询出所有的菜单列表(修改为直接用sql语句查询出来,然后再根据tree组织所需要的数据)
		//List<MenuEntity>allMenuList=menuDao.getMenuList();
		//组织角色菜单返回json树形数据结构
		//	List userMenuList=new  ArrayList();
		//	for (MenuEntity r : allMenuList) {
		//		boolean flag = false;
		//		for (AdminMenuTemplate ur : list) {
		//			//用户拥有的权限
		//			if (ur.getId()==r.getId()) {
		//				Map<String, Object>map=new HashMap<String, Object>();
		//				map.put("fid",r.getId());
		//				map.put("pfid",r.getParentId());
		//				map.put("fname",r.getName());
		//				map.put("status", r.getStatus());
		//				map.put("isChecked", true);
		//				flag = true;
		//				userMenuList.add(map);
		//			}
		//		}
		//		//非用户菜单权限集合
		//		if (!flag) {
		//			Map<String, Object>map=new HashMap<String, Object>();
		//			map.put("fid",r.getId());
		//			map.put("pfid",r.getParentId());
		//			map.put("fname",r.getName());
		//			map.put("status", r.getStatus());
		//			map.put("isChecked", false);
		//			userMenuList.add(map);
		//		}
		//	}
		response.setList(list);
		return response;
	}
}
