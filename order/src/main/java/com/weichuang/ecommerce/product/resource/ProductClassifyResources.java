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
import com.weichuang.ecommerce.product.entity.request.ProductClassifyRequest;
import com.weichuang.ecommerce.product.entity.response.ProductClassifyInfoResponse;
import com.weichuang.ecommerce.product.entity.response.ProductClassifyListResponse;
import com.weichuang.ecommerce.product.service.IProductClassifyService;

/**
 * <p>ClassName: ProductClassifyResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:商品类型实现api类 </p>
 * <p>author wanggongliang</p>
 * <p>2018年1月10日 上午11:05:42</p>
 */
@Path("/productClassify")
@Api(value = "/ProductClassifyResources", description = "商品类型 Api接口")
@SuppressWarnings("all")
public class ProductClassifyResources extends BaseResource {
    @Autowired
    private IProductClassifyService productClassifyService;

    /**
     * @Title:addProductClassify  
     * @Description:增加商品类型
     * @param request 请求参数
     * @return
     */
    @PUT
    @Path("/addProductClassify")
    @ApiOperation(value = "/addProductClassify", notes = "addProductClassify")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addProductClassify(ProductClassifyRequest request) throws Exception {
        productClassifyService.addProductClassify(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: modifyProductClassify
     * @Description: 修改商品类型
     * @param request 请求的参数
     * @return
     */
    @POST
    @Path("/modifyProductClassify")
    @ApiOperation(value = "/modifyProductClassify", notes = "modifyProductClassify")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyProductClassify(ProductClassifyRequest request) throws Exception {
        productClassifyService.updateProductClassify(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:getProductClassifyList  
     * @Description:管理列表显示
     * @return
     */
    @GET
    @Path("/productClassifyList")
    @ApiOperation(value = "/productClassifyList", notes = "productClassifyList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductClassifyListResponse> productClassifyList(@QueryParam("pageNum") int pageNum,
            @QueryParam("pageSize") int pageSize) throws Exception {
        ProductClassifyListResponse list = productClassifyService.getProductClassifyList(pageNum, pageSize);
        Response<ProductClassifyListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title:productClassifyWebList  
     * @Description:查询状态为正常的商品类型集合提供给商品使用
     * @return
     */
    @GET
    @Path("/productClassifyWebList")
    @ApiOperation(value = "/productClassifyWebList", notes = "productClassifyWebList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductClassifyListResponse> productClassifyWebList() throws Exception {
        ProductClassifyListResponse list = productClassifyService.getProductClassifyWebList();
        Response<ProductClassifyListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title: productClassifyInfo
     * @Description:根据商品类型主键查询商品类型详情
     * @param id 主键
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/productClassifyInfo")
    @ApiOperation(value = "/productClassifyInfo", notes = "productClassifyInfo")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<ProductClassifyInfoResponse> productClassifyInfo(@QueryParam("classifyId") int classifyId)
            throws Exception {
        ProductClassifyInfoResponse info = productClassifyService.getProductClassifyInfo(classifyId);
        Response<ProductClassifyInfoResponse> response = new Response<>(Result.SUCCESS, info);
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
    public Response<String> modifyHotById(@QueryParam("classifyId") int classifyId) throws Exception {
        productClassifyService.updateHotById(classifyId);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

}
