package com.weichuang.ecommerce.barcode.service;

import java.util.List;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.SalesPullNewSetEntity;
import com.weichuang.ecommerce.barcode.entity.request.SalesPullNewSetRequest;
import com.weichuang.ecommerce.barcode.entity.response.InviteListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewSetListResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewSetResponse;

/**
* <p>ClassName:ISalesPullNewcService</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:liuzhanchao</p>
* <p>date:2017/12/26 18:00</p>
**/
public interface ISalesPullNewSetService  {
    /**
     *<p>Description:查询红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public SalesPullNewSetListResponse getAllPullNewSetList() throws ServiceException;
    
    /**
     *<p>Description:更新红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public int updatePullNewSet(SalesPullNewSetRequest request) throws ServiceException;
    
    /**
     *<p>Description:增加红包拉新设置</p>
     *<p>author:liuzhanchao</p>
     *<p>date:2017/12/26 18:00</p>
     */
    public int addPullNewSet(SalesPullNewSetRequest request) throws ServiceException;
}
