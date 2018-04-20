package com.weichuang.ecommerce.pay.dao.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.pay.constants.NameSpaceConstant;
import com.weichuang.ecommerce.pay.dao.IAgentDao;
import com.weichuang.ecommerce.pay.entity.AgentEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
* <p>ClassName:AgentDaoImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/10/10 11:31</p>
**/
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class AgentDaoImpl implements IAgentDao{
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    public AgentEntity selectBySalesId(int salesid) {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT+".selectBySalesId",salesid);
    }
}
