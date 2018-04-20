package com.weichuang.ecommerce.barcode.repository;

import com.weichuang.ecommerce.barcode.entity.SalesRedEnvelopActiviti;

/**
* <p>ClassName:ISalesRedEnvelopActivi</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/27 11:39</p>
**/
public interface ISalesRedEnvelopActiviDao {
    /**
     *<p>Description:通过状态查询活动</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 16:03</p>
     */
    public SalesRedEnvelopActiviti selectByState(int state);
}
