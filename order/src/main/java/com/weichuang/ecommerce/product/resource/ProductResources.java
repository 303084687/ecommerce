package com.weichuang.ecommerce.product.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.product.entity.request.ProductRequest;
import com.weichuang.ecommerce.product.entity.request.TestRequest;
import com.weichuang.ecommerce.product.entity.response.ProductInfoResponse;
import com.weichuang.ecommerce.product.entity.response.ProductListResponse;
import com.weichuang.ecommerce.product.entity.response.ProductResponse;
import com.weichuang.ecommerce.product.service.IProductService;

/**
 * <p>ClassName: ProductResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品服务类api接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月14日 下午5:49:54</p>
 */
@Path("/product")
@Api(value = "/ProductResources", description = "商品类 Api接口")
@SuppressWarnings("all")
public class ProductResources extends BaseResource {
    @Autowired
    private IProductService productService;

    /**
     * @Title:addProduct  
     * @Description:增加
     * @param productRequest 请求参数
     * @return
     */
    @PUT
    @Path("/addProduct")
    @ApiOperation(value = "/addProduct", notes = "addProduct")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addProduct(ProductRequest productRequest) throws Exception {
        productService.addProduct(productRequest);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: modifyBanner
     * @Description: 修改
     * @param productRequest 请求的参数
     * @return
     */
    @POST
    @Path("/modifyProduct")
    @ApiOperation(value = "/modifyProduct", notes = "modifyProduct")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyProduct(ProductRequest productRequest) throws Exception {
        productService.updateProduct(productRequest);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:productList  
     * @Description:管理列表显示
     * @param name 标题
     * @param status 状态1正常2禁止
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @GET
    @Path("/productList")
    @ApiOperation(value = "/productList", notes = "productList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductListResponse> productList(@QueryParam("status") int status, @QueryParam("name") String name,
            @QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime,
            @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
        ProductListResponse list = productService.getProductList(status, name, startTime, endTime, pageNum, pageSize);
        Response<ProductListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title:productWebList  
     * @Description:根据上架状态列表显示(提供给前端使用)
     * @return
     */
    @GET
    @Path("/productWebList")
    @ApiOperation(value = "/productWebList", notes = "productWebList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductListResponse> productWebList(@QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize, @QueryParam("productIds") String productIds) throws Exception {
        ProductListResponse list = productService.getProductWebList(pageNum, pageSize, productIds);
        Response<ProductListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title: productInfo
     * @Description:根据主键查询详情信息因为涉及到优惠是否开启,只供前端使用
     * @param id 主键
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/queryProductInfo")
    @ApiOperation(value = "/queryProductInfo", notes = "productInfo")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductResponse> queryProductInfo(@QueryParam("productCode") String productCode,
            @QueryParam("productId") int productId) throws Exception {
        ProductResponse info = productService.queryProductInfo(productCode, productId);
        Response<ProductResponse> response = new Response<>(Result.SUCCESS, info);
        return response;
    }

    /**
     * @Title: updateHotById
     * @Description: 置顶
     * @return
     */
    @POST
    @Path("/modifyHotById")
    @ApiOperation(value = "/modifyHotById", notes = "updateHotById")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyHotById(@QueryParam("productId") int productId) throws Exception {
        productService.updateHotById(productId);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: productInfo
     * @Description:根据主键查询详情信息
     * @param id 主键
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/productInfo")
    @ApiOperation(value = "/productInfo", notes = "productInfo")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductInfoResponse> queryById(@QueryParam("productCode") String productCode,
            @QueryParam("productId") int productId) throws Exception {
        ProductInfoResponse info = productService.getProductInfo(productCode, productId);
        Response<ProductInfoResponse> response = new Response<>(Result.SUCCESS, info);
        return response;
    }

    /**
     * @Title:updateProductStatus  
     * @Description:修改上下架状态
     * @param productId 商品主键
     * @param status 1上架2下架3物理删除
     * @return
     * @throws Exception
     */
    @POST
    @Path("/modifyProductStatus")
    @ApiOperation(value = "/modifyProductStatus", notes = "updateProductStatus")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyProductStatus(@QueryParam("productIds") String productIds,
            @QueryParam("status") int status) throws Exception {
        productService.updateProductStatus(productIds, status);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    @POST
    @Path("/testWeb3")
    @ApiOperation(value = "/testWeb3", notes = "testWeb3")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> testWeb3(@QueryParam("testRequest") TestRequest testRequest) throws Exception {
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:getProductSingleList  
     * @Description:查询出上架的单品留给生成套餐时候使用
     * @return
     */
    @GET
    @Path("/productSingleList")
    @ApiOperation(value = "/productSingleList", notes = "productSingleList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductListResponse> getProductSingleList() throws Exception {
        ProductListResponse list = productService.getProductSingleList();
        Response<ProductListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }
}
