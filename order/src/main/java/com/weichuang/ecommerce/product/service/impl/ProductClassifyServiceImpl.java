package com.weichuang.ecommerce.product.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.product.entity.ProductClassifyEntity;
import com.weichuang.ecommerce.product.entity.request.ProductClassifyRequest;
import com.weichuang.ecommerce.product.entity.response.ProductClassifyInfoResponse;
import com.weichuang.ecommerce.product.entity.response.ProductClassifyListResponse;
import com.weichuang.ecommerce.product.responsitory.IProductClassifyDao;
import com.weichuang.ecommerce.product.service.IProductClassifyService;

/**
 * <p>ClassName: ProductClassifyServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 商品类型service实现类</p>
 * <p>author wanggongliang</p>
 * <p>2018年1月10日 上午10:06:24</p>
 */
@Service
@SuppressWarnings("all")
public class ProductClassifyServiceImpl implements IProductClassifyService {
    // 注册商品类型服务接口
    @Autowired
    private IProductClassifyDao productClassifyDao;

    /**
     * @Title:addProductClassify 
     * @Description:增加商品类型,后台使用
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int addProductClassify(ProductClassifyRequest request) throws ServiceException {
        ProductClassifyEntity entity = new ProductClassifyEntity();
        BeanUtils.copyProperties(request, entity);
        return productClassifyDao.addProductClassify(entity);
    }

    /**
     * @Title:updateProductClassify  
     * @Description:修改商品类型,后台使用
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int updateProductClassify(ProductClassifyRequest request) throws ServiceException {
        ProductClassifyEntity entity = new ProductClassifyEntity();
        BeanUtils.copyProperties(request, entity);
        return productClassifyDao.updateProductClassify(entity);
    }

    /**
     * @Title:getProductClassifyList  
     * @Description:根据商品类型主键查询商品类型详情
     * @param productCode
     * @param productId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductClassifyInfoResponse getProductClassifyInfo(int classifyId) throws ServiceException {
        ProductClassifyEntity entity = productClassifyDao.getProductClassifyInfo(classifyId);
        ProductClassifyInfoResponse response = new ProductClassifyInfoResponse();
        response.setClassify(entity);
        return response;
    }

    /**
     * @Title:getProductClassifyList  
     * @Description:查询类型集合后台使用
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductClassifyListResponse getProductClassifyList(int pageNum, int pageSize) throws ServiceException {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductClassifyEntity> list = productClassifyDao.getProductClassifyList();
        ProductClassifyListResponse response = new ProductClassifyListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 总的数据
        return response;
    }

    /**
     * @Title:updateHotById  
     * @Description:根据商品类型主键设置排序,越大放在最前面
     * @param productId
     * @param maxSort
     * @return
     */
    @Override
    @Transactional
    public int updateHotById(int classifyId) throws ServiceException {
        // 根据类型的不同查询最大的排序数
        int maxSort = productClassifyDao.maxSort();
        // 更新排序置顶
        return productClassifyDao.updateHotById(classifyId, maxSort);
    }

    /**
     * @Title:getProductClassifyList 
     * @Description:查询状态为正常的商品类型集合提供给商品使用
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductClassifyListResponse getProductClassifyWebList() throws ServiceException {
        List<ProductClassifyEntity> list = productClassifyDao.getProductClassifyWebList();
        ProductClassifyListResponse response = new ProductClassifyListResponse();
        response.setList(list);
        return response;
    }

}
