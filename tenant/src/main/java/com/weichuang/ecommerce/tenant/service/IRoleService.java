package com.weichuang.ecommerce.tenant.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.request.AdminRoleRequest;
import com.weichuang.ecommerce.tenant.entity.request.RoleRequest;
import com.weichuang.ecommerce.tenant.entity.response.RoleInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.RoleListResponse;

/**
 * <p>ClassName: IRoleService.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:角色接口操作 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月22日 下午7:04:01</p>
 */
public interface IRoleService {
	//增加角色
	public int addRole(RoleRequest request) throws ServiceException;
	
	//修改
	public int updateRole(RoleRequest request) throws ServiceException;
	
	//角色列表(带分页)
	public RoleListResponse getRoleList(int pageNum, int pageSize) throws ServiceException;
	
	//查询角色详情
	public RoleInfoResponse findByRoleId(Integer roleId, String roleName) throws ServiceException;
	
	//用户-角色增加
	public int addAdminRole(AdminRoleRequest request) throws ServiceException;
}
