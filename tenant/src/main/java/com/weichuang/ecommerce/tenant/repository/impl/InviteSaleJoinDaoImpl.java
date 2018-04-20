package com.weichuang.ecommerce.tenant.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.InviteSaleJoinEntity;
import com.weichuang.ecommerce.tenant.repository.InviteSaleJoinDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class InviteSaleJoinDaoImpl implements InviteSaleJoinDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     *<p>Description:保存邀请店员信息</p>
     *<p>param entity:实体</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/5 9:58</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public int insertInviteSaleJoin(InviteSaleJoinEntity entity){
        return  sqlSessionTemplate.insert(NameSpaceConstant.INVITE_SALE_JOIN+".insertSelective",entity);
    }
    public int getCountByCodekey(String codekey){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.INVITE_SALE_JOIN+".getCountByCodekey",codekey);
    }
}
