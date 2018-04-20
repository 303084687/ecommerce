package com.weichuang.ecommerce.barcode.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.barcode.constants.NameSpaceConstant;
import com.weichuang.ecommerce.barcode.entity.SalesRedEnvelopActiviti;
import com.weichuang.ecommerce.barcode.repository.ISalesRedEnvelopActiviDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
/**
 * <p>ClassName:SalesRedEnvelpActiviDaoImpl</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:</p>
 * <p>author:zhanghongsheng</p>
 * <p>2017/11/27 11:38</p>
 **/
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class SalesRedEnvelpActiviDaoImpl implements ISalesRedEnvelopActiviDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    /**
     *<p>Description:通过状态查询活动</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 16:03</p>
     */
    public SalesRedEnvelopActiviti selectByState(int state) {

        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_RED_ENVELP_ACTIVI+".selectByState",state);
    }
}
