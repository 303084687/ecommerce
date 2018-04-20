package com.weichuang.ecommerce.product.responsitory.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.product.constants.NameSpaceConstant;
import com.weichuang.ecommerce.product.entity.ProductStockEntity;
import com.weichuang.ecommerce.product.responsitory.IProductStockDao;

/**
 * <p>ClassName: ProductStockDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 商品库存实现</p>
 * <p>author wanggongliang</p>
 * <p>2017年11月13日 下午3:43:38</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class ProductStockDaoImpl implements IProductStockDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Title:addProductStock  
     * @Description:批量增加
     * @param list
     * @return
     */
    @Override
    public int addProductStock(List<ProductStockEntity> list) {
        return sqlSessionTemplate.insert(NameSpaceConstant.PRODUCT_STOCK + ".addProductStock", list);
    }

    /**
     * @Title:deleteProductStock  
     * @Description:删除
     * @param 
     * @return
     */
    @Override
    public int deleteProductStock(Integer productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        return sqlSessionTemplate.delete(NameSpaceConstant.PRODUCT_STOCK + ".deleteProductStock", map);
    }

    /**
     * @Title:getProductStockList
     * @Description:根据商品主键或者code查询商品对用的sku信息
     * @param productId 商品主键
     * @param productCode 商品code
     * @return
     */
    @Override
    public List<ProductStockEntity> getProductStockList(Integer productId, String productCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("productCode", productCode);
        return sqlSessionTemplate.selectList(NameSpaceConstant.PRODUCT_STOCK + ".getProductStockList", map);
    }
}
