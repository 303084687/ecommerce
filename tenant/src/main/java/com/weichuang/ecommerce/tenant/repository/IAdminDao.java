package com.weichuang.ecommerce.tenant.repository;

import java.util.List;

import com.weichuang.ecommerce.tenant.entity.AdminEntity;
import com.weichuang.ecommerce.tenant.entity.AdminTemplate;

/**
 * <p>ClassName: IAdminDao.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台管理人员表 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月19日 下午5:51:13</p>
 */
public interface IAdminDao {
	//增加
	public int addAdmin(AdminEntity entity);
	
	//修改
	public int updateAdmin(AdminEntity entity);
	
	//根据用户主键删除用户
	public int deleteAdmin(Integer userId);
	
	//列表查询带分页和模糊查询
	public List<AdminTemplate> getAdminList(Integer status, String adminName);
	
	//设置启用或者停用
	public int updateAdminStatus(Integer status, Integer userId);
	
	//根据主键或者用户名查询详情
	public AdminEntity getAdmin(String adminName, Integer userId);
	
	//根据主键重置or修改密码
	public int resetPass(Integer userId, String pass);
}
