package com.weichuang.ecommerce.tenant.repository.impl;


import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.UserDetailEntity;
import com.weichuang.ecommerce.tenant.entity.UserEntity;
import com.weichuang.ecommerce.tenant.entity.UserRoleEntity;
import com.weichuang.ecommerce.tenant.repository.IUserDao;
import com.weichuang.ecommerce.tenant.repository.IUserRoleDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>ClassName: UserRoleDaoImpl.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户角色数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月22日 下午16:49:13</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class UserRoleDaoImpl implements IUserRoleDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 增加用户角色</p>
     * <p>param entity 用户角色实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/22 16:00 </p>
     * <p>return </p>
     */
    @Override
    public int addUserRole(UserRoleEntity entity) {
        return 0;
    }

    /**
     * <p>Description: 根据角色id查询角色信息</p>
     * <p>roleId 角色id</p>
     * <p>date 2017/9/22 16:00 </p>
     * <p>return UserRoleEntity</p>
     */
    @Override
    public UserRoleEntity getUserRoleById(int roleId) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER_ROLE + ".getUserRoleById", roleId);
    }
}
