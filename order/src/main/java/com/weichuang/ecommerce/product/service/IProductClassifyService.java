package com.weichuang.ecommerce.product.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.product.entity.request.ProductClassifyRequest;
import com.weichuang.ecommerce.product.entity.response.ProductClassifyInfoResponse;
import com.weichuang.ecommerce.product.entity.response.ProductClassifyListResponse;

/**
 * <p>ClassName: IProductClassifyService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品类型service类 </p>
 * <p>author wanggongliang</p>
 * <p>2018年1月10日 上午9:54:11</p>
 */
public interface IProductClassifyService {
    /**
     * @Title:addProductClassify 
     * @Description:增加商品类型,后台使用
     * @param entity
     * @return
     */
    public int addProductClassify(ProductClassifyRequest request) throws ServiceException;

    /**
     * @Title:updateProductClassify  
     * @Description:修改商品类型,后台使用
     * @param entity
     * @return
     */
    public int updateProductClassify(ProductClassifyRequest request) throws ServiceException;

    /**
     * @Title:getProductClassifyList  
     * @Description:根据商品类型主键查询商品类型详情
     * @param productCode
     * @param productId
     * @return
     */
    public ProductClassifyInfoResponse getProductClassifyInfo(int classifyId) throws ServiceException;

    /**
     * @Title:getProductClassifyList  
     * @Description:查询类型集合后台使用
     * @return
     */
    public ProductClassifyListResponse getProductClassifyList(int pageNum, int pageSize) throws ServiceException;

    /**
     * @Title:updateHotById  
     * @Description:根据商品类型主键设置排序,越大放在最前面
     * @param productId
     * @param maxSort
     * @return
     */
    public int updateHotById(int classifyId) throws ServiceException;

    /**
     * @Title:getProductClassifyList 
     * @Description:查询状态为正常的商品类型集合提供给商品使用
     * @return
     */
    public ProductClassifyListResponse getProductClassifyWebList() throws ServiceException;

}
