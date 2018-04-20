package com.weichuang.ecommerce.product.responsitory;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.product.entity.ProductStockEntity;

/**
 * <p>ClassName: IProductStockDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品库存接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月13日 下午3:24:19</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IProductStockDao {
    /**
     * @Title:addProductStock  
     * @Description:批量增加
     * @param list
     * @return
     */
    public int addProductStock(List<ProductStockEntity> list);

    /**
     * @Title:deleteProductStock  
     * @Description:删除
     * @param 
     * @return
     */
    public int deleteProductStock(Integer productId);

    /**
     * @Title:getProductStockList
     * @Description:根据商品主键或者code查询商品对用的sku信息
     * @param productId 商品主键
     * @param productCode 商品code
     * @return
     */
    public List<ProductStockEntity> getProductStockList(Integer productId, String productCode);

}
