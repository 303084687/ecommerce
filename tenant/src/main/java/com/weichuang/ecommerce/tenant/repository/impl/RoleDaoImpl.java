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
import com.weichuang.ecommerce.tenant.entity.AdminRoleEntity;
import com.weichuang.ecommerce.tenant.entity.RoleEntity;
import com.weichuang.ecommerce.tenant.repository.IRoleDao;

/**
 * @Title: 后台角色表
 * @Package: com.weichuang.ecommerce.tenant.repository
 * @Description:
 * @author: licheng
 * @date: 2016/10/11 11:44
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class RoleDaoImpl implements IRoleDao {
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;
	
	//增加角色
	@Override
	public int addRole(RoleEntity entity) {
		return sqlSessionTemplate.insert(NameSpaceConstant.ROLE + ".addRole", entity);
	}
	
	//修改
	@Override
	public int updateRole(RoleEntity entity) {
		return sqlSessionTemplate.update(NameSpaceConstant.ROLE + ".updateRole", entity);
	}
	
	//角色列表
	@Override
	public List<RoleEntity> getRoleList() {
		return sqlSessionTemplate.selectList(NameSpaceConstant.ROLE + ".getRoleList");
	}
	
	//查询角色详情
	@Override
	public RoleEntity findByRoleId(Integer roleId, String roleName) {
		Map<String, Object> param = new HashMap<>();
		param.put("roleId", roleId);
		param.put("roleName", roleName);
		return sqlSessionTemplate.selectOne(NameSpaceConstant.ROLE + ".findByRoleId", param);
	}
	
	//用户-角色增加
	@Override
	public int addAdminRole(AdminRoleEntity entity) {
		return sqlSessionTemplate.insert(NameSpaceConstant.ROLE + ".addAdminRole", entity);
	}
	
	//用户-角色修改(给用户修改角色)
	@Override
	public int updateAdminRole(AdminRoleEntity entity) {
		return sqlSessionTemplate.insert(NameSpaceConstant.ROLE + ".updateAdminRole", entity);
	}
	
	//用户-角色删除(当删除用户时候要删除对应的角色)
	@Override
	public int deleteAdminRole(Integer adminId) {
		Map<String, Object> param = new HashMap<>();
		param.put("adminId", adminId);
		return sqlSessionTemplate.delete(NameSpaceConstant.ROLE + ".deleteAdminRole", param);
	}
}
