package com.weichuang.ecommerce.product.responsitory;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.product.entity.ProductEntity;

/**
 * <p>ClassName: IProductDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品类接口定义 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月14日 上午11:27:04</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IProductDao {
    /**
     * @Title:addProduct  
     * @Description:增加商品,后台使用
     * @param entity
     * @return
     */
    public int addProduct(ProductEntity entity);

    /**
     * @Title:updateProduct  
     * @Description:修改商品,后台使用
     * @param entity
     * @return
     */
    public int updateProduct(ProductEntity entity);

    /**
     * @Title:updateProductStatus  
     * @Description:批量或者单个设置商品上下架状态,1上架2下架3物理删除
     * @param productId
     * @return
     */
    public int updateProductStatus(String productIds, int status);

    /**
     * @Title:getProductInfo  
     * @Description:根据商品主键或者商品码查询商品详情
     * @param productCode
     * @param productId
     * @return
     */
    public ProductEntity getProductInfo(String productCode, int productId);

    /**
     * @Title:getProductList  
     * @Description:根据商品名称,状态,创建时间查询商品集合,后台使用
     * @param status
     * @param name
     * @param startTime
     * @param endTime
     * @return
     */
    public List<ProductEntity> getProductList(Integer status, String name, String startTime, String endTime);

    /**
     * @Title:updateHotById  
     * @Description:根据商品主键设置排序,越大放在最前面
     * @param productId
     * @param maxSort
     * @return
     */
    public int updateHotById(int productId, int maxSort);

    /**
     * @Title: maxSort
     * @Description:查询出最大的排序参数
     * @return
     */
    public int maxSort();

    /**
     * @Title:getProductList  
     * @Description:查询状态为上架的商品集合提供给前端使用
     * @return
     */
    public List<ProductEntity> getProductList(String productIds);

    /**
     * @Title:getProductSingleList  
     * @Description:查询出上架的单品留给生成套餐时候使用
     * @return
     */
    public List<ProductEntity> getProductSingleList();
}
