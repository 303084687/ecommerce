package com.weichuang.ecommerce.tenant.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.EncryptUtil;
import com.weichuang.ecommerce.tenant.entity.AdminEntity;
import com.weichuang.ecommerce.tenant.entity.AdminMenuTemplate;
import com.weichuang.ecommerce.tenant.entity.AdminRoleEntity;
import com.weichuang.ecommerce.tenant.entity.AdminTemplate;
import com.weichuang.ecommerce.tenant.entity.request.AdminRequest;
import com.weichuang.ecommerce.tenant.entity.response.AdminInfoResponse;
import com.weichuang.ecommerce.tenant.entity.response.AdminListResponse;
import com.weichuang.ecommerce.tenant.entity.response.AdminLoginResponse;
import com.weichuang.ecommerce.tenant.repository.IAdminDao;
import com.weichuang.ecommerce.tenant.repository.IMenuDao;
import com.weichuang.ecommerce.tenant.repository.IRoleDao;
import com.weichuang.ecommerce.tenant.service.IAdminService;

/**
 * <p>ClassName: AdminServiceImpl.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:后台管理用户 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月21日 上午10:13:09</p>
 */
@Service("adminService")
public class AdminServiceImpl implements IAdminService {
	//后台用户接口
	@Autowired
	private IAdminDao adminDao;
	//角色接口
	@Autowired
	private IRoleDao roleDao;
	//加密类
	@Autowired
	private EncryptUtil encryptUtil;
	//菜单接口
	@Autowired
	private IMenuDao menuDao;
	
	//增加
	@Override
	@Transactional
	public void addAdmin(AdminRequest request) throws Exception, ServiceException {
		//1:首先判断用户是否存在
		AdminEntity adminEntity = adminDao.getAdmin(request.getAdminName(), null);
		if (adminEntity == null) {
			AdminEntity entity = new AdminEntity();
			entity.setAdminName(request.getAdminName());
			entity.setPassword(request.getPassword());//密码先存传递过来的
			entity.setMobile(request.getMobile());
			entity.setSex(request.getSex());
			entity.setName(request.getName());
			entity.setCreateTime(new Date());
			entity.setHeadingUrl(request.getHeadingUrl());
			adminDao.addAdmin(entity);
			//用户密码处理,根据加密规则
			String passWord = encryptUtil.registerEncrypt(request.getAppKey(), Long.valueOf(entity.getId()),
					request.getPassword());
			//2:更新用户密码
			adminDao.resetPass(entity.getId(), passWord);
			if (request.getRoleId() > 0) {
				//3:分配用户角色
				AdminRoleEntity roleEntity = new AdminRoleEntity();
				roleEntity.setAdminId(entity.getId());
				roleEntity.setRoleId(request.getRoleId());
				roleDao.addAdminRole(roleEntity);
			}
		} else {
			//提示用户已经存在
			throw new ServiceException(Result.USER_EXIST.getCode(), Result.USER_EXIST.getMessage());
		}
	}
	
	//修改
	@Override
	@Transactional
	public void updateAdmin(AdminRequest request) throws ServiceException {
		AdminEntity entity = new AdminEntity();
		entity.setId(request.getId());
		entity.setHeadingUrl(request.getHeadingUrl());
		entity.setSex(request.getSex());
		entity.setMobile(request.getMobile());
		entity.setName(request.getName());
		//1:修改用户主表信息
		adminDao.updateAdmin(entity);
		//2:修改用户角色
		AdminRoleEntity roleEntity = new AdminRoleEntity();
		roleEntity.setAdminId(entity.getId());
		roleEntity.setRoleId(request.getRoleId());
		int count = roleDao.updateAdminRole(roleEntity);
		//如果角色-用户不存在则执行新增操作
		if (count == 0) {
			roleDao.addAdminRole(roleEntity);
		}
	}
	//修改用户头像
	@Override
	@Transactional
	public void updateAdminImg(String imageUrl,int adminId) throws ServiceException {
		AdminEntity entity = new AdminEntity();
		entity.setId(adminId);
		entity.setHeadingUrl(imageUrl);
		//修改用户头像
		adminDao.updateAdmin(entity);
	}
	//根据用户主键删除用户
	@Override
	@Transactional
	public int deleteAdmin(Integer userId) {
		//1:先删除用户
		adminDao.deleteAdmin(userId);
		//2:删除用户对应的角色
		return roleDao.deleteAdminRole(userId);
	}
	
	//根据主键或者用户名查询详情
	@Override
	@Transactional(readOnly = true)
	public AdminInfoResponse getAdmin(String adminName, Integer userId) throws ServiceException {
		AdminEntity entity = adminDao.getAdmin(adminName, userId);
		AdminInfoResponse response = new AdminInfoResponse();
		response.setAdminEntity(entity);
		return response;
	}
	
	//列表查询带分页和模糊查询
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("all")
	public AdminListResponse getAdminList(Integer status, String adminName, int pageNum, int pageSize)
			throws ServiceException {
		AdminListResponse response = new AdminListResponse();
		//执行查询数据和分页处理
		PageHelper.startPage(pageNum, pageSize);
		List<AdminTemplate> list = adminDao.getAdminList(status, adminName);
		PageInfo pageInfo = new PageInfo(list);
		response.setList(list);
		response.setCount(pageInfo.getTotal());//总数量
		return response;
	}
	
	//设置启用或者停用
	@Override
	@Transactional
	public void updateAdminStatus(Integer status, Integer userId, int type) throws ServiceException {
		//type 为1的时候 启用，为2的时候禁用
		if (type == 1) {
			adminDao.updateAdminStatus(1, userId);
		}
		if (type == 2) {
			adminDao.updateAdminStatus(2, userId);
		}
	}
	
	//重置密码
	@Override
	@Transactional
	public int resetPass(Integer userId, String pass, String appKey) throws Exception, ServiceException {
		//重置原始密码,默认密码是888888
		String passWord = encryptUtil.registerEncrypt(appKey, Long.valueOf(userId), pass);
		return adminDao.resetPass(userId, passWord);
	}
	
	//修改密码
	@Override
	@Transactional
	public int updatePass(Integer userId, String oldPass, String newPass, String appKey) throws Exception,
			ServiceException {
		//1:先判断用户是否存在
		AdminEntity adminEntity = adminDao.getAdmin("", userId);
		if (adminEntity != null) {
			//2:先判断用户原密码是否正确,oldPass按照登录规则加密传递过来
			String pass = encryptUtil.validateLoginPass(Long.valueOf(userId), oldPass);
			if (!pass.equals(adminEntity.getPassword())) {
				throw new ServiceException(Result.USER_PASS_ERROR.getCode(), Result.USER_PASS_ERROR.getMessage());
			} else {
				//3:更新用户新密码
				//根据算法解密得到原始密码存到数据库
				String passWord = encryptUtil.registerEncrypt(appKey, Long.valueOf(userId), newPass);
				return adminDao.resetPass(userId, passWord);
			}
		} else {
			//用户不存在提示
			throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
		}
	}
	
	//根据用户名和密码登录,passWord 根据密码登录生成规则
	@Override
	@Transactional(readOnly = true)
	@SuppressWarnings("all")
	public AdminLoginResponse login(String userName, String passWord) throws Exception, ServiceException {
		//1:判断用户是否存在
		AdminEntity entity = adminDao.getAdmin(userName, null);
		if (entity != null) {
			//2:判断密码是否正确
			String pass = encryptUtil.validateLoginPass(Long.valueOf(entity.getId()), passWord);
			if (!entity.getPassword().equals(pass)) {
				throw new ServiceException(Result.USER_PASS_ERROR.getCode(), Result.USER_PASS_ERROR.getMessage());
			}
			//3:判断用户状态是否被禁止
			if (entity.getStatus() == 2) {
				throw new ServiceException(Result.USER_NOT_ALLOW.getCode(), Result.USER_NOT_ALLOW.getMessage());
			}
			//4:满足1、2、3条件返回用户信息和权限集合
			AdminLoginResponse response = new AdminLoginResponse();
			response.setEntity(entity);
			if (response.getEntity().getRoleId() != null) {
				//查询出角色对应的菜单列表,status为1代表查询未被禁用的菜单选项
				List<AdminMenuTemplate> list = menuDao.getUserMenu(entity.getRoleId(), 1);
				//过滤出用户的权限
				List<AdminMenuTemplate> roleList = new ArrayList<AdminMenuTemplate>();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getSelected() == 1) {
						roleList.add(list.get(i));
					}
				}
				response.setMenuList(roleList);
				/*	以下注释代码是生成json格式的权限树形菜单
									//根据结果组合用户权限菜单(按照层级数据结构)
									// 节点列表（散列表，用于临时存储节点对象）  
									HashMap nodeList = new HashMap();
									// 根据结果集构造节点列表（存入散列表）  
									for (int i = 0; i < roleList.size(); i++) {
										AdminMenuTemplate node = new AdminMenuTemplate();
										node.setId(roleList.get(i).getId());
										node.setName(roleList.get(i).getName());
										node.setParentId(roleList.get(i).getParentId());
										node.setParentName(roleList.get(i).getParentName());
										node.setResKey(roleList.get(i).getResKey());
										node.setResUrl(roleList.get(i).getResUrl());
										node.setType(roleList.get(i).getType());
										node.setSelected(roleList.get(i).getSelected());
										nodeList.put(roleList.get(i).getId(), node);
									}
									List<AdminMenuTemplate> rootList = new ArrayList<AdminMenuTemplate>();
									// 构造无序的多叉树  
									Set entrySet = nodeList.entrySet();
									for (Iterator it = entrySet.iterator(); it.hasNext();) {
										// 根节点,如果只有一个父节点秩序把  AdminMenuTemplate root = null;放到集合外面即可
										AdminMenuTemplate root = null;
										AdminMenuTemplate node = (AdminMenuTemplate) ((Map.Entry) it.next()).getValue();
										//一级目录的父id必须是null,否则页面树形菜单显示不出来还报错，jstree要求的
										if (node.getParentId() == null) {
											root = node;
											rootList.add(root);
										} else {
											//根据parentId添加子节点
											((AdminMenuTemplate) nodeList.get(node.getParentId())).addChild(node);
										}
									}*/
			}
			return response;
		} else {
			//用户不存在给出提示
			throw new ServiceException(Result.USER_NOT_EXIST.getCode(), Result.USER_NOT_EXIST.getMessage());
		}
	}
}
