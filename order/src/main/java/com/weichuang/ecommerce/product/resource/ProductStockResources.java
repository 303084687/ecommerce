package com.weichuang.ecommerce.product.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.product.entity.response.ProductStockListResponse;
import com.weichuang.ecommerce.product.service.IProductStockService;

/**
 * <p>ClassName: ProductStockResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品库存api </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月13日 下午5:23:04</p>
 */
@Path("/productStock")
@Api(value = "/ProductStockResources", description = "商品库存类 Api接口")
@SuppressWarnings("all")
public class ProductStockResources extends BaseResource {
    @Autowired
    private IProductStockService productStockService;

    /**
     * @Title:getProductStockList  
     * @Description:根据商品主键或者code查询商品对用的sku信息
     * @param productId 商品主键
     * @param productCode 商品code
     * @return
     */
    @GET
    @Path("/productStockList")
    @ApiOperation(value = "/productStockList", notes = "productStockList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductStockListResponse> getProductStockList(@QueryParam("productId") Integer productId,
            @QueryParam("productCode") String productCode) throws Exception {
        ProductStockListResponse list = productStockService.getProductStockList(productId, productCode);
        Response<ProductStockListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }
}
