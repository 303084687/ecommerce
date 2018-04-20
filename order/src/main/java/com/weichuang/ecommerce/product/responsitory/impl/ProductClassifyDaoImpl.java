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
import com.weichuang.ecommerce.product.entity.ProductClassifyEntity;
import com.weichuang.ecommerce.product.responsitory.IProductClassifyDao;

/**
 * <p>ClassName: ProductClassifyDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品类型接口实现 </p>
 * <p>author wanggongliang</p>
 * <p>2018年1月9日 下午4:34:04</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class ProductClassifyDaoImpl implements IProductClassifyDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Title:addProductClassify 
     * @Description:增加商品类型,后台使用
     * @param entity
     * @return
     */
    @Override
    public int addProductClassify(ProductClassifyEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.PRODUCT_CLASSIFY + ".addProductClassify", entity);
    }

    /**
     * @Title:updateProductClassify  
     * @Description:修改商品类型,后台使用
     * @param entity
     * @return
     */
    @Override
    public int updateProductClassify(ProductClassifyEntity entity) {
        return sqlSessionTemplate.update(NameSpaceConstant.PRODUCT_CLASSIFY + ".updateProductClassify", entity);
    }

    /**
     * @Title:getProductClassifyInfo  
     * @Description:根据商品类型主键查询商品类型详情
     * @param classifyId 主键
     * @return
     */
    @Override
    public ProductClassifyEntity getProductClassifyInfo(int classifyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("classifyId", classifyId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.PRODUCT_CLASSIFY + ".getProductClassifyInfo", map);
    }

    /**
     * @Title:getProductClassifyList  
     * @Description:查询类型集合后台使用
     * @return
     */
    @Override
    public List<ProductClassifyEntity> getProductClassifyList() {
        return sqlSessionTemplate.selectList(NameSpaceConstant.PRODUCT_CLASSIFY + ".getProductClassifyList");
    }

    /**
     * @Title:updateHotById  
     * @Description:根据商品类型主键设置排序,越大放在最前面
     * @param classifyId 类型的主键
     * @param maxSort 最大的排序数
     * @return
     */
    @Override
    public int updateHotById(int classifyId, int maxSort) {
        Map<String, Object> map = new HashMap<>();
        map.put("classifyId", classifyId);
        map.put("maxSort", maxSort);
        return sqlSessionTemplate.update(NameSpaceConstant.PRODUCT_CLASSIFY + ".updateHotById", map);
    }

    /**
     * @Title: maxSort
     * @Description:查询出最大的排序参数
     * @return
     */
    @Override
    public int maxSort() {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.PRODUCT_CLASSIFY + ".maxSort");
    }

    /**
     * @Title:getProductClassifyWebList 
     * @Description:查询状态为正常的商品类型集合
     * @return
     */
    @Override
    public List<ProductClassifyEntity> getProductClassifyWebList() {
        return sqlSessionTemplate.selectList(NameSpaceConstant.PRODUCT_CLASSIFY + ".getProductClassifyWebList");
    }
}
