package com.weichuang.ecommerce.tenant.repository;


import com.weichuang.ecommerce.tenant.entity.UserThirdEntity;

/**
 * <p>ClassName: IUserThirdDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午19:49:13</p>
 */
public interface IUserThirdDao {
	/**
	 * <p>Description: 增加第三方用户授权的信息</p>
	 * <p>param entity 第三方用户授权实体 </p>
	 * <p>author jiangkesen </p>
	 * <p>date 2017/9/19 20:00 </p>
	 * <p>return </p>
	 */
	public int addUserThird(UserThirdEntity entity);


	//修改信息
	public int updateUserThird(String openId,String unionId,String appOpenId);
	/*
	//修改用户头像
	public int updateUserHeadImg(Long userId, String imageUrl);
	
	//增加用户详情
	public int addUserDetail(UserThirdEntity entity);
	*/
	public UserThirdEntity selectByUserId(int userId);
}
