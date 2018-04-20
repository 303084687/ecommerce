package com.weichuang.ecommerce.tenant.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.request.AdminRequest;
import com.weichuang.ecommerce.tenant.entity.response.AdminInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.AdminListResponse;
import com.weichuang.ecommerce.tenant.entity.response.AdminLoginResponse;

/**
 * <p>ClassName: IAdminService.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台管理用户 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月21日 上午10:05:25</p>
 */
public interface IAdminService {
	//增加
	public void addAdmin(AdminRequest request) throws Exception, ServiceException;
	
	//修改用户信息
	public void updateAdmin(AdminRequest request) throws ServiceException;
	
	//修改用户头像
	public void updateAdminImg(String imageUrl,int adminId) throws ServiceException;
	
	//根据用户主键删除用户
	public int deleteAdmin(Integer userId);
	
	//根据主键或者用户名查询详情
	public AdminInfoResponse getAdmin(String adminName, Integer userId) throws ServiceException;
	
	//列表查询带分页和模糊查询
	public AdminListResponse getAdminList(Integer status, String adminName, int pageNum, int pageSize)
			throws ServiceException;
	
	//设置启用或者停用
	public void updateAdminStatus(Integer status, Integer userId, int type) throws ServiceException;
	
	//重置
	public int resetPass(Integer userId, String pass, String appKey) throws Exception, ServiceException;
	
	//修改密码
	public int updatePass(Integer userId, String oldPass, String newPass, String appKey) throws Exception,
			ServiceException;
	
	//后台用户登录
	public AdminLoginResponse login(String userName, String passWord) throws Exception, ServiceException;
}
