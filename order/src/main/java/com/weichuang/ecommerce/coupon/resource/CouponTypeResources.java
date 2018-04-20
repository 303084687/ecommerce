package com.weichuang.ecommerce.coupon.resource;

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
import com.weichuang.ecommerce.coupon.entity.request.CouponTypeAddRequest;
import com.weichuang.ecommerce.coupon.entity.response.CouponTypeListResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponTypeResponse;
import com.weichuang.ecommerce.coupon.service.ICouponTypeService;

/**
 * <p>ClassName: CouponResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券类型api接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月26日 上午11:27:52</p>
 */
@Path("/couponType")
@Api(value = "/CouponTypeResources", description = "优惠券类型 Api接口")
@SuppressWarnings("all")
public class CouponTypeResources extends BaseResource {
    // 优惠券service
    @Autowired
    private ICouponTypeService couponTypeService;

    /**
     * @Title:addCouponType  
     * @Description:增加
     * @param  请求参数
     * @return
     */
    @PUT
    @Path("/addCouponType")
    @ApiOperation(value = "/addCouponType", notes = "addCouponType")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addCouponType(CouponTypeAddRequest typeAddRequest) throws Exception {
        // 调取接口
        couponTypeService.addCouponType(typeAddRequest);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: updateCouponType
     * @Description: 修改
     * @param couponType 请求的参数
     * @return
     */
    @POST
    @Path("/updateCouponType")
    @ApiOperation(value = "/updateCouponType", notes = "updateCouponType")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> modifyCouponType(CouponTypeAddRequest typeAddRequest) throws Exception {
        couponTypeService.updateCouponType(typeAddRequest);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:deleteCouponType  
     * @Description:根据主键删除(物理删除)
     * @param id 主键
     * @return
     * @throws Exception
     */
    @POST
    @Path("/deleteCouponType")
    @ApiOperation(value = "/deleteCouponType", notes = "deleteCouponType")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> deleteCouponType(@QueryParam("id") int id, @QueryParam("status") int status)
            throws Exception {
        couponTypeService.deleteCouponType(id, status);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:getCouponTypeList  
     * @Description:根据券号类型状态以及创建时间查询列表后台管理接口
     * @param category 类型
     * @param status 状态1正常2禁止
     * @param typeCode 优惠券类型
     * @return
     */
    @GET
    @Path("/couponTypeList")
    @ApiOperation(value = "/couponTypeList", notes = "couponTypeList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponTypeListResponse> getCouponTypeList(@QueryParam("status") int status,
            @QueryParam("category") int category, @QueryParam("typeCode") String typeCode,
            @QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime,
            @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
        CouponTypeListResponse list = couponTypeService.getCouponTypeList(typeCode, category, status, startTime,
                endTime, pageNum, pageSize);
        Response<CouponTypeListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title: getCouponByCode
     * @Description:根据类型号查询详细信息(单个)
     * @param couponCode 优惠券号
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/couponTypeById")
    @ApiOperation(value = "/couponTypeById", notes = "couponTypeById")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponTypeResponse> getCouponTypeById(@QueryParam("typeCode") String typeCode,
            @QueryParam("id") int id) throws Exception {
        CouponTypeResponse info = couponTypeService.getCouponTypeById(typeCode, id);
        Response<CouponTypeResponse> response = new Response<>(Result.SUCCESS, info);
        return response;
    }

    /**
     * @Title:getTypeList 
     * @Description:类型集合(用于下拉框)只查询正常状态下的,用于生成优惠券
     * @return
     */
    @GET
    @Path("/typeList")
    @ApiOperation(value = "/typeList", notes = "typeList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponTypeListResponse> getTypeList() throws Exception {
        CouponTypeListResponse list = couponTypeService.getTypeList();
        Response<CouponTypeListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title:getLimitPlatList  
     * @Description:根据限制使用渠道或者类型查询优惠券列表
     * @param platLimitId 限制使用平台id
     * @param category 用于查询优惠券类型
     * @return
     * @throws Exception
     */
    @GET
    @Path("/limitPlatList")
    @ApiOperation(value = "/limitPlatList", notes = "limitPlatList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponTypeListResponse> getLimitPlatList(@QueryParam("platLimitId") int platLimitId,
            @QueryParam("category") int category) throws Exception {
        CouponTypeListResponse list = couponTypeService.getLimitPlatId(platLimitId, category);
        Response<CouponTypeListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }
}
