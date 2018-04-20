package com.weichuang.ecommerce.barcode.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.barcode.constants.NameSpaceConstant;
import com.weichuang.ecommerce.barcode.entity.TenantCodeStoreEntity;
import com.weichuang.ecommerce.barcode.entity.UserCountEntity;
import com.weichuang.ecommerce.barcode.entity.UserRoleEntity;
import com.weichuang.ecommerce.barcode.repository.ITenantCodeStoreDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class TenantCodeStoreDaoImpl implements ITenantCodeStoreDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     *<p>Description:增加二维码信息</p>
     *<p>param entity:实体</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/10/19 16:22</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int addTenantCodeStore(TenantCodeStoreEntity entity) {

        return sqlSessionTemplate.insert(NameSpaceConstant.TENANT_CODE_STORE+".insertSelective",entity);
    }

    @Override
    public TenantCodeStoreEntity selectByIdTypeStateOne(TenantCodeStoreEntity entity) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.TENANT_CODE_STORE+".selectByIdTypeStateOne",entity);
    }

    @Override
    public List<TenantCodeStoreEntity> selectByIdTypeStateList(TenantCodeStoreEntity entity) {
        return sqlSessionTemplate.selectList(NameSpaceConstant.TENANT_CODE_STORE+".selectByIdTypeStateList",entity);
    }

    @Override
    public void updateByPrimaryKeySelective(TenantCodeStoreEntity entity) {
        sqlSessionTemplate.update(NameSpaceConstant.TENANT_CODE_STORE+".updateByPrimaryKeySelective",entity);
    }

    /**
     *<p>Description:通过手机号查询用户个数</p>
     *<p>param mobile:手机号</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 11:17</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int selectUserCountByMobile(String mobile) {

        return sqlSessionTemplate.selectOne(NameSpaceConstant.TENANT_CODE_STORE+".selectUserCountByMobile",mobile);
    }
    /**
     *<p>Description:通过用户id获取用户角色id信息</p>
     *<p>param id:用户id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 11:45</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public UserRoleEntity selectUserRoleDetailById(int id){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.TENANT_CODE_STORE+".selectUserRoleDetailById",id);
    }
    /**
     *<p>Description:通过id查询发送邀请数量</p>
     *<p>param type:1 店员  2 门店</p>
     *<p>param userId:店员或门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 15:13</p>
     *<p>return:</p>
     */
    public UserCountEntity selectInviteCountByUserId(int type,int userId){
        Map param=new HashMap();
        param.put("type",type);
        param.put("userId",userId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.TENANT_CODE_STORE+".selectInviteCountByUserId",param);
    }
}
