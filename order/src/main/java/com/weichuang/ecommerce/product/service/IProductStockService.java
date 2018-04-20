package com.weichuang.ecommerce.product.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.product.entity.response.ProductStockListResponse;

/**
 * <p>ClassName: IProductStockService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品库存service </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月13日 下午5:09:57</p>
 */
public interface IProductStockService {
    /**
     * @Title:getProductStockList  
     * @Description:根据商品主键或者code查询商品对用的sku信息
     * @param productId 商品主键
     * @param productCode 商品code
     * @return
     */
    public ProductStockListResponse getProductStockList(Integer productId, String productCode) throws ServiceException;

}
