package com.weichuang.ecommerce.barcode.repository;

import com.weichuang.ecommerce.barcode.entity.SalesRedEnvelopeRece;

/**
* <p>ClassName:ISalesRedEnvelopeRece</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/27 11:39</p>
**/
public interface ISalesRedEnvelopeReceDao {

    public int insertSelective(SalesRedEnvelopeRece envelopeRece);

    public int selectCountBySalesAndSet(int salesId,int rid);

    public int updateReceState(int salesId,int rid,int state);

    public SalesRedEnvelopeRece selectBySalesAndSet(int salesId,int rid);
}
