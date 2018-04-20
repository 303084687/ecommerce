package com.weichuang.ecommerce.product.service;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.product.entity.request.ProductRequest;
import com.weichuang.ecommerce.product.entity.response.ProductInfoResponse;
import com.weichuang.ecommerce.product.entity.response.ProductListResponse;
import com.weichuang.ecommerce.product.entity.response.ProductResponse;

/**
 * <p>ClassName: IProductService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品服务接口类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月14日 下午4:28:46</p>
 */
public interface IProductService {
    /**
     * @Title:addProduct  
     * @Description:增加商品,后台使用
     * @param entity
     * @return
     */
    public int addProduct(ProductRequest request) throws ServiceException;

    /**
     * @Title:updateProduct  
     * @Description:修改商品,后台使用
     * @param entity
     * @return
     */
    public int updateProduct(ProductRequest request) throws ServiceException;

    /**
     * @Title:updateProductStatus  
     * @Description:设置商品上下架状态,1上架2下架3物理删除
     * @param productId
     * @return
     */
    public int updateProductStatus(String productIds, int status) throws ServiceException;

    /**
     * @Title:getProductInfo  
     * @Description:根据商品主键或者商品码查询商品详情,后台管理使用
     * @param productCode
     * @param productId
     * @return
     */
    public ProductInfoResponse getProductInfo(String productCode, int productId) throws ServiceException;

    /**
     * @Title:queryProductInfo  
     * @Description:根据商品主键或者商品编码查询商品详情,因为涉及到优惠是否开启,只供前端使用
     * @param productCode
     * @param productId
     * @return
     * @throws ServiceException
     */
    public ProductResponse queryProductInfo(String productCode, int productId) throws ServiceException;

    /**
     * @Title:getProductList  
     * @Description:根据商品名称,状态,创建时间查询商品集合,后台使用
     * @param status
     * @param name
     * @param startTime
     * @param endTime
     * @return
     */
    public ProductListResponse getProductList(Integer status, String name, String startTime, String endTime,
            int pageNum, int pageSize) throws ServiceException;

    /**
     * @Title:updateHotById  
     * @Description:根据商品主键设置排序,越大放在最前面
     * @param productId
     * @return
     */
    public int updateHotById(int productId) throws ServiceException;

    /**
     * @Title:getProductWebList  
     * @Description:查询状态为上架的商品集合提供给前端使用
     * @return
     */
    public ProductListResponse getProductWebList(int pageNum, int pageSize, String productIds) throws ServiceException;

    /**
     * @Title:getProductSingleList  
     * @Description:查询出上架的单品留给生成套餐时候使用
     * @return
     */
    public ProductListResponse getProductSingleList();
}
