package com.weichuang.ecommerce.tenant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.AdminRoleEntity;
import com.weichuang.ecommerce.tenant.entity.RoleEntity;
import com.weichuang.ecommerce.tenant.entity.request.AdminRoleRequest;
import com.weichuang.ecommerce.tenant.entity.request.RoleRequest;
import com.weichuang.ecommerce.tenant.entity.response.RoleInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.RoleListResponse;
import com.weichuang.ecommerce.tenant.repository.IRoleDao;
import com.weichuang.ecommerce.tenant.service.IRoleService;

/**
 * <p>ClassName: RoleServiceImpl.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 角色操作</p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月22日 下午7:16:27</p>
 */
@Service("roleService")
public class RoleServiceImpl implements IRoleService {
	@Autowired
	private IRoleDao roleDao;
	
	//增加角色
	@Override
	@Transactional
	public int addRole(RoleRequest request) throws ServiceException {
		//1:先查询角色是否存在
		RoleEntity entity = roleDao.findByRoleId(null, request.getName());
		if (entity == null) {
			RoleEntity roleEntity = new RoleEntity();
			roleEntity.setName(request.getName());
			roleEntity.setRoleKey(request.getRoleKey());
			roleEntity.setDescription(request.getDescription());
			roleEntity.setCreateTime(new Date());
			return roleDao.addRole(roleEntity);
		} else {
			//提示角色已经存在
			throw new ServiceException(Result.ROLE_EXIST.getCode(), Result.ROLE_EXIST.getMessage());
		}
	}
	
	//修改
	@Override
	@Transactional
	public int updateRole(RoleRequest request) throws ServiceException {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setId(request.getId());
		roleEntity.setStatus(request.getStatus());
		roleEntity.setDescription(request.getDescription());
		return roleDao.updateRole(roleEntity);
	}
	
	//角色列表(带分页)
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("all")
	public RoleListResponse getRoleList(int pageNum, int pageSize) throws ServiceException {
		RoleListResponse response = new RoleListResponse();
		//执行查询数据和分页处理
		PageHelper.startPage(pageNum, pageSize);
		List<RoleEntity> list = roleDao.getRoleList();
		PageInfo page = new PageInfo(list);
		response.setList(list);
		response.setCount(page.getTotal());
		return response;
	}
	
	//查询角色详情
	@Override
	@Transactional(readOnly = true)
	public RoleInfoResponse findByRoleId(Integer roleId, String roleName) throws ServiceException {
		RoleInfoResponse response = new RoleInfoResponse();
		RoleEntity entity = roleDao.findByRoleId(roleId, roleName);
		response.setRoleEntity(entity);
		return response;
	}
	
	//用户-角色增加
	@Override
	@Transactional
	public int addAdminRole(AdminRoleRequest request) throws ServiceException {
		//1:先删除用户对用的角色
		roleDao.deleteAdminRole(request.getAdminId());
		//2:新增用户对应的角色
		AdminRoleEntity entity = new AdminRoleEntity();
		entity.setAdminId(request.getAdminId());
		entity.setRoleId(request.getRoleId());
		return roleDao.addAdminRole(entity);
	}
}
