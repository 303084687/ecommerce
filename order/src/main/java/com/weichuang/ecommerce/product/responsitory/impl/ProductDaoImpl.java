package com.weichuang.ecommerce.product.responsitory.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.product.constants.NameSpaceConstant;
import com.weichuang.ecommerce.product.entity.ProductEntity;
import com.weichuang.ecommerce.product.responsitory.IProductDao;

/**
 * <p>ClassName: ProductDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品接口的实现类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月14日 下午1:42:37</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class ProductDaoImpl implements IProductDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Title:addProduct  
     * @Description:增加商品,后台使用
     * @param entity
     * @return
     */
    @Override
    public int addProduct(ProductEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.PRODUCT + ".addProduct", entity);
    }

    /**
     * @Title:updateProduct  
     * @Description:修改商品,后台使用
     * @param entity
     * @return
     */
    @Override
    public int updateProduct(ProductEntity entity) {
        return sqlSessionTemplate.update(NameSpaceConstant.PRODUCT + ".updateProduct", entity);
    }

    /**
     * @Title:updateProductStatus  
     * @Description:批量设置商品上下架状态,1上架2下架3物理删除
     * @param productId
     * @return
     */
    @Override
    public int updateProductStatus(String productIds, int status) {
        Map<String, Object> map = new HashMap<>();
        // 处理传过来的商品id字符串
        List<String> pids = new ArrayList<>();
        for (String pid : productIds.split(",")) {
            pids.add(pid);
        }
        map.put("productIds", pids);
        map.put("status", status);
        return sqlSessionTemplate.update(NameSpaceConstant.PRODUCT + ".updateProductStatus", map);
    }

    /**
     * @Title:getProductInfo  
     * @Description:根据商品主键或者商品码查询商品详情
     * @param productCode
     * @param productId
     * @return
     */
    @Override
    public ProductEntity getProductInfo(String productCode, int productId) {
        Map<String, Object> map = new HashMap<>();
        map.put("productCode", productCode);
        map.put("productId", productId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.PRODUCT + ".getProductInfo", map);
    }

    /**
     * @Title:getProductList  
     * @Description:根据商品名称,状态,创建时间查询商品集合,后台使用
     * @param status
     * @param name
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public List<ProductEntity> getProductList(Integer status, String name, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("name", name);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.PRODUCT + ".getProductList", map);
    }

    /**
     * @Title:updateHotById  
     * @Description:根据商品主键设置排序,越大放在最前面
     * @param productId
     * @param maxSort
     * @return
     */
    @Override
    public int updateHotById(int productId, int maxSort) {
        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);
        map.put("maxSort", maxSort);
        return sqlSessionTemplate.update(NameSpaceConstant.PRODUCT + ".updateHotById", map);
    }

    /**
     * @Title: maxSort
     * @Description:
     * @param查询出最大的排序参数
     * @return
     */
    @Override
    public int maxSort() {
        return sqlSessionTemplate.selectOne(NameSpaceConstant.PRODUCT + ".maxSort");
    }

    /**
     * @Title:getProductList  
     * @Description:查询状态为上架的商品集合提供给前端使用
     * @return
     */
    @Override
    public List<ProductEntity> getProductList(String productIds) {
        Map<String, Object> map = new HashMap<>();
        map.put("productIds", productIds);
        return sqlSessionTemplate.selectList(NameSpaceConstant.PRODUCT + ".productList", map);
    }

    /**
     * @Title:getProductSingleList  
     * @Description:查询出上架的单品留给生成套餐时候使用
     * @return
     */
    @Override
    public List<ProductEntity> getProductSingleList() {
        return sqlSessionTemplate.selectList(NameSpaceConstant.PRODUCT + ".getProductSingleList");
    }
}
