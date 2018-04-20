package com.weichuang.ecommerce.product.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.product.entity.ProductStockEntity;
import com.weichuang.ecommerce.product.entity.response.ProductStockListResponse;
import com.weichuang.ecommerce.product.responsitory.IProductStockDao;
import com.weichuang.ecommerce.product.service.IProductStockService;

/**
 * <p>ClassName: ProductStockServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品库存接口实现类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月13日 下午5:17:04</p>
 */
@Service
@SuppressWarnings("all")
public class ProductStockServiceImpl implements IProductStockService {
    // 注册商品库存服务接口
    @Autowired
    private IProductStockDao productStockDao;

    /**
     * @Title:getProductStockList  
     * @Description:根据商品主键或者code查询商品对用的sku信息
     * @param productId 商品主键
     * @param productCode 商品code
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductStockListResponse getProductStockList(Integer productId, String productCode) throws ServiceException {
        ProductStockListResponse response = new ProductStockListResponse();
        List<ProductStockEntity> list = productStockDao.getProductStockList(productId, productCode);
        response.setList(list);
        return response;
    }

}
