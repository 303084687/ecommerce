package com.weichuang.ecommerce.barcode.repository.impl;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.barcode.constants.NameSpaceConstant;
import com.weichuang.ecommerce.barcode.entity.SalesRedEnvelopeRece;
import com.weichuang.ecommerce.barcode.repository.ISalesRedEnvelopeReceDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>ClassName:SalesRedEnvelopRece</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:</p>
 * <p>author:zhanghongsheng</p>
 * <p>2017/11/27 11:38</p>
 **/
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class SalesRedEnvelopReceDaoImpl implements ISalesRedEnvelopeReceDao {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    public int insertSelective(SalesRedEnvelopeRece envelopeRece) {
        return sqlSessionTemplate.insert(NameSpaceConstant.SALES_RED_ENVELP_RECE+".insertSelective",envelopeRece);
    }
    /**
     *<p>Description:通过销售id和设置id查询个数</p>
     *<p>param salesId:销售id</p>
     *<p>param rid:设置id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/28 16:27</p>
     */
    public int selectCountBySalesAndSet(int salesId,int rid){
        SalesRedEnvelopeRece envelopeRece=new SalesRedEnvelopeRece();
        envelopeRece.setSalesId(salesId);
        envelopeRece.setRid(rid);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_RED_ENVELP_RECE+".selectCountBySalesAndSet",envelopeRece);
    }
    /**
     *<p>Description:更新红包状态</p>
     *<p>param salesId:</p>
     *<p>param rid:</p>
     *<p>param state:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/28 16:37</p>
     */
    public int updateReceState(int salesId,int rid,int state){
        SalesRedEnvelopeRece envelopeRece=new SalesRedEnvelopeRece();
        envelopeRece.setSalesId(salesId);
        envelopeRece.setRid(rid);
        envelopeRece.setState(state);
        return sqlSessionTemplate.update(NameSpaceConstant.SALES_RED_ENVELP_RECE+".updateReceState",envelopeRece);
    }
    /**
     *<p>Description:查询用户达到的红包</p>
     *<p>param salesId:</p>
     *<p>param rid:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/28 17:05</p>
     */
    public SalesRedEnvelopeRece selectBySalesAndSet(int salesId,int rid){
        SalesRedEnvelopeRece envelopeRece=new SalesRedEnvelopeRece();
        envelopeRece.setSalesId(salesId);
        envelopeRece.setRid(rid);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_RED_ENVELP_RECE+".selectBySalesAndSet",envelopeRece);

    }
}
