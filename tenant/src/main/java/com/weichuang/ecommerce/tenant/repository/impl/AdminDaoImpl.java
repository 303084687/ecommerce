package com.weichuang.ecommerce.tenant.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.AdminEntity;
import com.weichuang.ecommerce.tenant.entity.AdminTemplate;
import com.weichuang.ecommerce.tenant.repository.IAdminDao;

/**
 * <p>ClassName: AdminDaoImpl.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台管理用户 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月20日 下午5:21:24</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
@ConfigurationProperties(prefix = "keyencode")
public class AdminDaoImpl implements IAdminDao {
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;
	private String DESede;
	
	public String getDESede() {
		return DESede;
	}
	
	public void setDESede(String DESede) {
		this.DESede = DESede;
	}
	
	//增加
	@Override
	public int addAdmin(AdminEntity entity) {
		entity.setKeyCode(this.getDESede());
		return sqlSessionTemplate.insert(NameSpaceConstant.ADMIN + ".addAdmin", entity);
	}
	
	//修改
	@Override
	public int updateAdmin(AdminEntity entity) {
		entity.setKeyCode(this.getDESede());
		return sqlSessionTemplate.update(NameSpaceConstant.ADMIN + ".updateAdmin", entity);
	}
	
	//根据用户主键删除用户
	@Override
	public int deleteAdmin(Integer userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		return sqlSessionTemplate.delete(NameSpaceConstant.ADMIN + ".deleteAdmin", param);
	}
	
	//列表查询带分页和模糊查询
	@Override
	public List<AdminTemplate> getAdminList(Integer status, String adminName) {
		Map<String, Object> param = new HashMap<>();
		param.put("status", status);
		param.put("adminName", adminName);
		param.put("keyCode", this.getDESede());
		return sqlSessionTemplate.selectList(NameSpaceConstant.ADMIN + ".getAdminList", param);
	}
	
	//设置启用或者停用
	@Override
	public int updateAdminStatus(Integer status, Integer userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("status", status);
		param.put("userId", userId);
		return sqlSessionTemplate.update(NameSpaceConstant.ADMIN + ".updateAdminStatus", param);
	}
	
	//根据主键或者用户名查询详情
	@Override
	public AdminEntity getAdmin(String adminName, Integer userId) {
		Map<String, Object> param = new HashMap<>();
		param.put("keyCode", this.getDESede());
		param.put("adminName", adminName);
		param.put("userId", userId);
		return sqlSessionTemplate.selectOne(NameSpaceConstant.ADMIN + ".getAdmin", param);
	}
	
	//根据主键重置or修改密码
	@Override
	public int resetPass(Integer userId, String pass) {
		Map<String, Object> param = new HashMap<>();
		param.put("userId", userId);
		param.put("pass", pass);
		return sqlSessionTemplate.update(NameSpaceConstant.ADMIN + ".resetPass", param);
	}
}
