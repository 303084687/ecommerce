package com.weichuang.ecommerce.barcode.service;

import com.weichuang.ecommerce.barcode.entity.response.InviteListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesInvteNewCountResponse;

/**
* <p>ClassName:ISalesPullNewcService</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/22 10:17</p>
**/
public interface ISalesPullNewcService {
    public InviteListResponse selectInviteList(Integer pageNum,Integer pageSize,Integer saleId) throws Exception;
    public SalesInvteNewCountResponse selectSalesInvteNewCount(int type,int id);
}
