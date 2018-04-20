package com.weichuang.ecommerce.tenant.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.MenuRequest;
import com.weichuang.ecommerce.tenant.entity.response.AdminMenuListResponse;
import com.weichuang.ecommerce.tenant.entity.response.MenuInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.MenuListResponse;

/**
 * <p>ClassName: IMenuService.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台菜单列表 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月23日 上午10:53:34</p>
 */
public interface IMenuService {
	//增加菜单
	public int addMenu(MenuRequest request) throws ServiceException;
	
	//修改菜单
	public int updateMenu(MenuRequest request) throws ServiceException;
	
	//菜单列表(带分页)
	public MenuListResponse getMenuList(int pageNum, int pageSize, Integer type) throws ServiceException;
	
	//根据菜单主键查询详细信息
	public MenuInfoResponse getMenuById(Integer menuId, String menuName) throws ServiceException;
	
	//菜单-角色增加
	public int addMenuRole(String menuIds, Integer roleId) throws ServiceException;
	
	//根据角色主键查询所有菜单（关联菜单和菜单-角色表查询）
	public AdminMenuListResponse getUserMenu(Integer roleId, Integer status) throws ServiceException;
}
