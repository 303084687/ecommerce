package com.weichuang.ecommerce.barcode.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.barcode.constants.NameSpaceConstant;
import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelopeSet;
import com.weichuang.ecommerce.barcode.repository.ISalesShareEnvelopSetDao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureAfter(MyBatisConfig.class)
/**
* <p>ClassName:SalesShareEnvelopSetDaoImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 10:16</p>
**/
public class SalesShareEnvelopSetDaoImpl implements ISalesShareEnvelopSetDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    public SalesShareEnvelopeSet selectUserSetByState(int state){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_SHARE_ENVELP_SET+".selectUserSetByState",state);
    }

}
