package com.weichuang.ecommerce.tenant.repository;

import com.weichuang.ecommerce.tenant.entity.UserRoleEntity;

/**
 * <p>ClassName: IUserRoleDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户角色数据 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月22日 下午16:00:13</p>
 */
public interface IUserRoleDao {
    /**
     * <p>Description: 增加用户角色</p>
     * <p>param entity 用户角色实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/22 16:00 </p>
     * <p>return </p>
     */
    public int addUserRole(UserRoleEntity entity);

    /**
     * <p>Description: 根据角色id查询角色信息</p>
     * <p>roleId 角色id</p>
     * <p>date 2017/9/22 16:00 </p>
     * <p>return UserRoleEntity</p>
     */
    public UserRoleEntity getUserRoleById(int roleId);

}