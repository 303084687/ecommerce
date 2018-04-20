package com.weichuang.ecommerce.tenant.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.entity.AdminRoleEntity;
import com.weichuang.ecommerce.tenant.entity.RoleEntity;

/**
 * <p>ClassName: IRoleDao.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 角色以及用户-角色接口</p>
 * <p>author: wanggongliang</p>
 * <p>2017年2月20日 下午5:09:16</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IRoleDao {
	//增加角色
	public int addRole(RoleEntity entity);
	
	//修改
	public int updateRole(RoleEntity entity);
	
	//角色列表
	public List<RoleEntity> getRoleList();
	
	//查询角色详情
	public RoleEntity findByRoleId(Integer roleId, String roleName);
	
	//用户-角色增加(给用户分配角色)
	public int addAdminRole(AdminRoleEntity entity);
	
	//用户-角色修改(给用户修改角色)
	public int updateAdminRole(AdminRoleEntity entity);
	
	//用户-角色删除(当删除用户时候要删除对应的角色)
	public int deleteAdminRole(Integer adminId);
}
