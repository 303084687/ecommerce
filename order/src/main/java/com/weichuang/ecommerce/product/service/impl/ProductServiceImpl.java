package com.weichuang.ecommerce.product.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.DateUtil;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.product.constants.ProductUtil;
import com.weichuang.ecommerce.product.entity.ProductEntity;
import com.weichuang.ecommerce.product.entity.ProductStockEntity;
import com.weichuang.ecommerce.product.entity.request.ProductRequest;
import com.weichuang.ecommerce.product.entity.response.ProductInfoResponse;
import com.weichuang.ecommerce.product.entity.response.ProductListResponse;
import com.weichuang.ecommerce.product.entity.response.ProductResponse;
import com.weichuang.ecommerce.product.responsitory.IProductDao;
import com.weichuang.ecommerce.product.responsitory.IProductStockDao;
import com.weichuang.ecommerce.product.service.IProductService;

/**
 * <p>ClassName: BannserServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:product服务接口实现类 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月7日 下午2:47:03</p>
 */
@Service
@SuppressWarnings("all")
public class ProductServiceImpl implements IProductService {
    // 注册商品服务接口
    @Autowired
    private IProductDao productDao;

    // 分享的地址
    @Value("${webchat.h5NetworkUrl}")
    private String h5NetworkUrl;

    // 商品sku信息
    @Autowired
    private IProductStockDao productStockDao;

    /**
     * @Title:addProduct  
     * @Description:增加商品,后台使用
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int addProduct(ProductRequest request) throws ServiceException {
        String productCode = ProductUtil.productCode(request.getType());
        ProductEntity entity = new ProductEntity();
        entity.setActions(request.getActions());
        entity.setBrand(request.getBrand());
        entity.setMonth(request.getMonth());
        entity.setCode(productCode);
        entity.setCostPrice(request.getCostPrice() == null ? new BigDecimal(0) : request.getCostPrice().setScale(2, 1));
        entity.setDeploy(request.getDeploy());
        entity.setDescription(request.getDescription());
        if (request.getDiscountEnabled() == 2) {
            entity.setDiscountPrice(request.getDiscountPrice() == null ? new BigDecimal(0) : request.getDiscountPrice()
                    .setScale(2, 1));
            // 假设传递过来的优惠开始和结束时间为空，默认为当天
            entity.setDiscountStart(StringUtils.isBlank(request.getDiscountStart()) ? LocalDate.now().toString()
                    : request.getDiscountStart());
            entity.setDiscountEnd(StringUtils.isBlank(request.getDiscountEnd()) ? LocalDate.now().toString() : request
                    .getDiscountEnd());
        }
        // 商品的小图
        entity.setSmallPicture(request.getSmallPicture());
        entity.setImages(request.getImages());
        entity.setIntroduction(request.getIntroduction());
        entity.setName(request.getName());
        entity.setNorms(request.getNorms());
        entity.setDiscountEnabled(request.getDiscountEnabled());
        entity.setShareContent(request.getShareContent());
        entity.setBuyExplain(request.getBuyExplain());
        entity.setSellingPrice(request.getSellingPrice() == null ? new BigDecimal(0) : request.getSellingPrice()
                .setScale(2, 1));
        entity.setShelfLife(request.getShelfLife());
        entity.setStorage(request.getStorage());
        entity.setType(request.getType());
        entity.setWeight(request.getWeight());
        entity.setIsPresell(request.getIsPresell());
        entity.setIslimitCoupon(request.getIslimitCoupon());
        // 执行增加主商品
        productDao.addProduct(entity);
        // 增加商品对应sku信息,要是单品sku信息则是本身，套餐包含所有单品的sku信息
        List<ProductStockEntity> list = request.getList();
        for (int i = 0; i < list.size(); i++) {
            // 单品
            if (request.getType() == 1) {
                list.get(i).setGoodId(entity.getId());
                list.get(i).setGoodCode(productCode);
                list.get(i).setGoodName(request.getName());
            }
            list.get(i).setProductName(request.getName());
            list.get(i).setProductId(entity.getId());
            list.get(i).setProductCode(productCode);
        }
        return productStockDao.addProductStock(list);
    }

    /**
     * @Title:updateProduct  
     * @Description:修改商品,后台使用
     * @param entity
     * @return
     */
    @Override
    @Transactional
    public int updateProduct(ProductRequest request) throws ServiceException {
        ProductEntity entity = new ProductEntity();
        entity.setId(request.getId());
        entity.setActions(request.getActions());
        entity.setBrand(request.getBrand());
        entity.setMonth(request.getMonth());
        entity.setCostPrice(request.getCostPrice() == null ? new BigDecimal(0) : request.getCostPrice().setScale(2, 1));
        entity.setDeploy(request.getDeploy());
        entity.setDescription(request.getDescription());
        if (request.getDiscountEnabled() == 2) {
            entity.setDiscountPrice(request.getDiscountPrice() == null ? new BigDecimal(0) : request.getDiscountPrice()
                    .setScale(2, 1));
            // 假设传递过来的优惠开始和结束时间为空，默认为当天
            entity.setDiscountStart(StringUtils.isBlank(request.getDiscountStart()) ? LocalDate.now().toString()
                    : request.getDiscountStart());
            entity.setDiscountEnd(StringUtils.isBlank(request.getDiscountEnd()) ? LocalDate.now().toString() : request
                    .getDiscountEnd());
        } else {
            entity.setDiscountPrice(null);
            entity.setDiscountStart(null);
            entity.setDiscountEnd(null);
        }
        // 商品的小图
        entity.setSmallPicture(request.getSmallPicture());
        entity.setImages(request.getImages());
        entity.setIntroduction(request.getIntroduction());
        entity.setName(request.getName());
        entity.setNorms(request.getNorms());
        entity.setDiscountEnabled(request.getDiscountEnabled());
        entity.setShareContent(request.getShareContent());
        entity.setBuyExplain(request.getBuyExplain());
        entity.setSellingPrice(request.getSellingPrice() == null ? new BigDecimal(0) : request.getSellingPrice()
                .setScale(2, 1));
        entity.setShelfLife(request.getShelfLife());
        entity.setStorage(request.getStorage());
        entity.setType(request.getType());
        entity.setWeight(request.getWeight());
        entity.setIsPresell(request.getIsPresell());
        entity.setIslimitCoupon(request.getIslimitCoupon());
        // 执行修改信息
        productDao.updateProduct(entity);
        // 删除对应的sku
        productStockDao.deleteProductStock(request.getId());
        // 增加对应的sku信息
        List<ProductStockEntity> list = request.getList();
        for (int i = 0; i < list.size(); i++) {
            // 单品
            if (request.getType() == 1) {
                list.get(i).setGoodId(entity.getId());
                list.get(i).setGoodCode(request.getCode());
                list.get(i).setGoodName(request.getName());
            }
            list.get(i).setProductName(request.getName());
            list.get(i).setProductId(request.getId());
            list.get(i).setProductCode(request.getCode());
        }
        return productStockDao.addProductStock(list);
    }

    /**
     * @Title:updateProductStatus  
     * @Description:设置商品上下架状态,1上架2下架
     * @param productId
     * @return
     */
    @Override
    @Transactional
    public int updateProductStatus(String productIds, int status) throws ServiceException {
        return productDao.updateProductStatus(productIds, status);
    }

    /**
     * @Title:getProductInfo  
     * @Description:根据商品主键或者商品码查询商品详情
     * @param productCode
     * @param productId
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductInfoResponse getProductInfo(String productCode, int productId) throws ServiceException {
        ProductEntity entity = productDao.getProductInfo(productCode, productId);
        // 查询对应的sku信息返回
        List<ProductStockEntity> list = productStockDao.getProductStockList(productId, productCode);
        ProductInfoResponse response = new ProductInfoResponse();
        response.setEntity(entity);
        response.setList(list);
        return response;
    }

    /**
     * @Title:getProductWebList  
     * @Description:查询状态为上架的商品集合提供给前端使用
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductListResponse getProductWebList(int pageNum, int pageSize, String productIds) throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<ProductEntity> list = productDao.getProductList(productIds);
        // 需要根据优惠开启以及时间范围判断显示优惠价格,未开启显示的是list的数据
        for (int i = 0; i < list.size(); i++) {
            // 开启优惠
            if (list.get(i).getDiscountEnabled() == 2) {
                // 需要判断优惠时间的区间范围
                if (DateUtil.betweenDate(list.get(i).getDiscountStart(), list.get(i).getDiscountEnd())) {
                    // 把销售价修改为优惠价
                    list.get(i).setSellingPrice(list.get(i).getDiscountPrice());
                }
            }
            list.get(i).setAppProductUrl(h5NetworkUrl + "/product/app/info?productCode=" + list.get(i).getCode());
            list.get(i).setAppShareProductUrl(
                    h5NetworkUrl + "/product/info?productId=" + list.get(i).getId() + "&productCode="
                            + list.get(i).getCode());
        }
        ProductListResponse response = new ProductListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());
        return response;
    }

    /**
     * @Title:queryProductInfo  
     * @Description:根据商品主键或者商品编码查询商品详情,因为涉及到优惠是否开启,只供前端使用
     * @param productCode
     * @param productId
     * @return
     * @throws ServiceException
     */
    @Override
    public ProductResponse queryProductInfo(String productCode, int productId) throws ServiceException {
        ProductEntity entity = productDao.getProductInfo(productCode, productId);
        // 需要根据优惠开启以及时间范围判断显示优惠价格,当前时间在优惠时间内，销售价格=优惠价格
        if (null != entity) {
            // 开启优惠判断
            if (entity.getDiscountEnabled() == 2) {
                // 需要判断优惠时间的区间范围
                if (DateUtil.betweenDate(entity.getDiscountStart(), entity.getDiscountEnd())) {
                    // 把销售价修改为优惠价
                    entity.setSellingPrice(entity.getDiscountPrice());
                }
            }
        }
        ProductResponse response = new ProductResponse();
        response.setEntity(entity);
        return response;
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
    @Transactional(readOnly = true)
    public ProductListResponse getProductList(Integer status, String name, String startTime, String endTime,
            int pageNum, int pageSize) throws ServiceException {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<ProductEntity> list = productDao.getProductList(status, name, startTime, endTime);
        ProductListResponse response = new ProductListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据集合
        response.setPages(pageInfo.getPages());// 返回的总页数，用于分页使用
        response.setTotal(pageInfo.getTotal());// 返回的数据总个数
        return response;
    }

    /**
     * @Title:updateHotById  
     * @Description:根据商品主键设置排序,越大放在最前面
     * @param productId
     * @param maxSort
     * @return
     */
    @Override
    @Transactional
    public int updateHotById(int productId) throws ServiceException {
        // 根据类型的不同查询最大的排序数
        int maxSort = productDao.maxSort();
        // 更新排序置顶
        return productDao.updateHotById(productId, maxSort);
    }

    /**
     * @Title:getProductSingleList  
     * @Description:查询出上架的单品留给生成套餐时候使用
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ProductListResponse getProductSingleList() {
        List<ProductEntity> list = productDao.getProductSingleList();
        ProductListResponse response = new ProductListResponse();
        response.setList(list);
        return response;
    }
}
