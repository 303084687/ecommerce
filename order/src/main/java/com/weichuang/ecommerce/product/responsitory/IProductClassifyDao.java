package com.weichuang.ecommerce.product.responsitory;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.product.entity.ProductClassifyEntity;

/**
 * <p>ClassName: IProductClassifyDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品类型接口 </p>
 * <p>author wanggongliang</p>
 * <p>2018年1月9日 下午4:23:46</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IProductClassifyDao {
    /**
     * @Title:addProductClassify 
     * @Description:增加商品类型,后台使用
     * @param entity
     * @return
     */
    public int addProductClassify(ProductClassifyEntity entity);

    /**
     * @Title:updateProductClassify  
     * @Description:修改商品类型,后台使用
     * @param entity
     * @return
     */
    public int updateProductClassify(ProductClassifyEntity entity);

    /**
     * @Title:getProductClassifyList  
     * @Description:根据商品类型主键查询商品类型详情
     * @param productCode
     * @param productId
     * @return
     */
    public ProductClassifyEntity getProductClassifyInfo(int classifyId);

    /**
     * @Title:getProductClassifyList  
     * @Description:查询类型集合后台使用
     * @return
     */
    public List<ProductClassifyEntity> getProductClassifyList();

    /**
     * @Title:updateHotById  
     * @Description:根据商品类型主键设置排序,越大放在最前面
     * @param productId
     * @param maxSort
     * @return
     */
    public int updateHotById(int classifyId, int maxSort);

    /**
     * @Title: maxSort
     * @Description:查询出最大的排序参数
     * @return
     */
    public int maxSort();

    /**
     * @Title:getProductClassifyList 
     * @Description:查询状态为正常的商品类型集合提供给商品使用
     * @return
     */
    public List<ProductClassifyEntity> getProductClassifyWebList();

}
