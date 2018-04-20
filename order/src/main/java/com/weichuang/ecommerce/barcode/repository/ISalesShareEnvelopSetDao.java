package com.weichuang.ecommerce.barcode.repository;

import com.weichuang.ecommerce.barcode.entity.SalesShareEnvelopeSet;

/**
* <p>ClassName:ISalesShareEnvelopSetDao</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 10:11</p>
**/
public interface ISalesShareEnvelopSetDao {
    public SalesShareEnvelopeSet selectUserSetByState(int state);

}
