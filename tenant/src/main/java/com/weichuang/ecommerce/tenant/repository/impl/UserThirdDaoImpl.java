package com.weichuang.ecommerce.tenant.repository.impl;

import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.UserThirdEntity;
import com.weichuang.ecommerce.tenant.repository.IUserThirdDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;

/**
 * <p>ClassName: IOrderDao.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单数据访层 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年09月19日 下午20:12:13</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class UserThirdDaoImpl implements IUserThirdDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 增加第三方用户授权的信息</p>
     * <p>param entity 第三方用户授权实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/9/19 20:00 </p>
     * <p>return </p>
     */
    @Override
    public int addUserThird(UserThirdEntity entity){
        return sqlSessionTemplate.insert(NameSpaceConstant.USER_THIRD + ".addUserThird", entity);
    }
    /**
     *<p>Description:</p>
     *<p>param unionId:</p>
     *<p>param appOpenId:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/1 16:20</p>
     *<p>return:int</p>
     */
    public int updateUserThird(String openId,String unionId,String appOpenId){
        UserThirdEntity entity=new UserThirdEntity();
        entity.setOpenId(openId);
        entity.setAppOpenId(appOpenId);
        entity.setUnionId(unionId);
        return sqlSessionTemplate.update(NameSpaceConstant.USER_THIRD + ".updateUserThird",entity);
    }
    public UserThirdEntity selectByUserId(int userId){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER_THIRD + ".selectByUserId",userId);

    }
}