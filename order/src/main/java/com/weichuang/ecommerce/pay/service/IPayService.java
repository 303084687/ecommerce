package com.weichuang.ecommerce.pay.service;

import java.util.Map;
import java.util.SortedMap;

/**
* <p>ClassName:IPayService</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/10/10 9:20</p>
**/
public interface IPayService {
    public boolean isPayed(SortedMap map) throws Exception;
}
