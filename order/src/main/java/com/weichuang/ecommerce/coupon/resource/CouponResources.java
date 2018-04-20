package com.weichuang.ecommerce.coupon.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.math.BigDecimal;

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
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.coupon.entity.request.CouponAddRequest;
import com.weichuang.ecommerce.coupon.entity.response.CouponListResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponNumResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponUsedListResponse;
import com.weichuang.ecommerce.coupon.service.ICouponService;

/**
 * <p>ClassName: CouponResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券api接口 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月26日 上午11:27:52</p>
 */
@Path("/coupon")
@Api(value = "/CouponResources", description = "优惠券 Api接口")
@SuppressWarnings("all")
public class CouponResources extends BaseResource {
    // 优惠券service
    @Autowired
    private ICouponService couponService;

    /**
     * @Title:addCoupon  
     * @Description:批量生成优惠券,使用场景 固定日期，实体优惠券
     * @param  请求参数
     * @return
     */
    @PUT
    @Path("/addCoupon")
    @ApiOperation(value = "/addCoupon", notes = "addCoupon")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addCoupon(@QueryParam("typeCode") String typeCode, @QueryParam("hasSend") int hasSend,
            @QueryParam("creator") String creator, @QueryParam("remark") String remark,
            @QueryParam("createNum") int createNum) throws Exception {
        // 封装请求参数
        CouponAddRequest request = new CouponAddRequest();
        request.setTypeCode(typeCode);
        request.setHasSend(hasSend);
        request.setCreator(creator);
        request.setCreateNum(createNum);
        request.setRemark(remark);
        // 调取接口
        couponService.addCoupon(request);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:oldUserCoupon  
     * @Description:根据销售给特定的健身房老用户发放体验券
     * @param typeCode 发放的优惠券批次多个用,隔开
     * @param userId 领取人的主键
     * @param userOpenId 领取人的openId
     * @param companyId 销售所属的公司主键
     * @param saleId 销售的主键
     * @return
     */
    @PUT
    @Path("/oldUserCoupon")
    @ApiOperation(value = "/oldUserCoupon", notes = "oldUserCoupon")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> oldUserCoupon(@QueryParam("companyId") int companyId,
            @QueryParam("typeCode") String typeCode, @QueryParam("userId") int userId,
            @QueryParam("userOpenId") String userOpenId, @QueryParam("saleId") int saleId) {
        Response<String> response = null;
        try {
            // 调取接口
            couponService.oldUserCoupon(userId, userOpenId, saleId, typeCode, companyId);
            response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        } catch (ServiceException ex) {
            response = new Response(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            response = new Response(Result.FAIL.getCode(), Result.FAIL.getMessage());
        }
        return response;
    }

    /**
     * @Title:bindCoupon  
     * @Description:b-c,c-c新人推荐赠送优惠券,规则B-C:C必须是新用户且只能领取一次,下单成功后B获取推荐奖
     * @param openId 被推荐人的openId
     * @param typeCode 发放的优惠券批次多个用,隔开
     * @param referId 推荐人的主键
     * @param referOpenId 推荐人的openId
     * @param exceptTypeCode 非公共部分批次优惠券号
     * @param type 1C-C 2B-C
     * @return
     */
    @PUT
    @Path("/bindCoupon")
    @ApiOperation(value = "/bindCoupon", notes = "bindCoupon")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> bindCoupon(@QueryParam("openId") String openId, @QueryParam("typeCode") String typeCode,
            @QueryParam("referId") int referId, @QueryParam("referOpenId") String referOpenId,
            @QueryParam("exceptTypeCode") String exceptTypeCode, @QueryParam("type") int type) {
        Response<String> response = null;
        try {
            // 调取接口
            couponService.bindCoupon(openId, referId, referOpenId, typeCode, exceptTypeCode, type);
            response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        } catch (ServiceException ex) {
            response = new Response(ex.getCode(), ex.getMessage());
        } catch (Exception ex) {
            response = new Response(Result.FAIL.getCode(), Result.FAIL.getMessage());
        }

        return response;
    }

    /**
     * @Title:getCouponList  
     * @Description:根据状态,是否使用,使用者,券号等查询优惠券列表后台接口使用
     * @param isUsed 1未使用2已使用
     * @param status 状态1正常2禁止
     * @param typeCode 优惠券类型
     * @param couponCode 券码
     * @return
     */
    @GET
    @Path("/couponList")
    @ApiOperation(value = "/couponList", notes = "couponList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponListResponse> getCouponList(@QueryParam("status") int status,
            @QueryParam("isUsed") int isUsed, @QueryParam("typeCode") String typeCode,
            @QueryParam("couponCode") String couponCode, @QueryParam("userName") String userName,
            @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
        CouponListResponse list = couponService.getCouponList(typeCode, couponCode, userName, isUsed, status, pageNum,
                pageSize);
        Response<CouponListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title: getCouponByCode
     * @Description:根据券号查询优惠券详情
     * @param couponCode 优惠券号
     * @return
     * @throws Exception 
     */
    @GET
    @Path("/couponByCode")
    @ApiOperation(value = "/couponByCode", notes = "couponByCode")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponResponse> getCouponByCode(@QueryParam("couponCode") String couponCode) throws Exception {
        CouponResponse info = couponService.getCouponByCode(couponCode);
        Response<CouponResponse> response = new Response<>(Result.SUCCESS, info);
        return response;
    }

    /**
     * @Title:couponNotUsedLists  
     * @Description:根据用户主键和时间查询可用优惠券列表(删选条件：未过期 已到使用日期 状态有效的 未使用的)
     * @return
     */
    @GET
    @Path("/couponNotUsedList")
    @ApiOperation(value = "/couponNotUsedList", notes = "couponNotUsedList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponUsedListResponse> couponNotUsedList(@QueryParam("openId") String openId,
            @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
        CouponUsedListResponse list = couponService.couponNotUsedList(openId, pageNum, pageSize);
        Response<CouponUsedListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title:couponNotUsedLists  
     * @Description:根据用户主键和时间查询不可用优惠券列表总数量（删选条件：已过期 未到使用日期 状态无效的 已使用的）
     * @return
     */
    @GET
    @Path("/couponUsedList")
    @ApiOperation(value = "/couponUsedList", notes = "couponUsedList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponUsedListResponse> couponUsedList(@QueryParam("openId") String openId,
            @QueryParam("pageNum") int pageNum, @QueryParam("pageSize") int pageSize) throws Exception {
        CouponUsedListResponse list = couponService.couponUsedList(openId, pageNum, pageSize);
        Response<CouponUsedListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title:couponNum  
     * @Description:根据用户openId和时间查询优惠券可用和不可用的数量
     * @return
     */
    @GET
    @Path("/couponNum")
    @ApiOperation(value = "/couponNum", notes = "couponNum")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponNumResponse> couponNum(@QueryParam("openId") String openId) throws Exception {
        CouponNumResponse list = couponService.couponNum(openId);
        Response<CouponNumResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title: referCouponOrder
     * @Description: 根据券号将优惠券标为已经使用提交订单
     * @return
     */
    @POST
    @Path("/referCouponOrder")
    @ApiOperation(value = "/referCouponOrder", notes = "referCouponOrder")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> referCouponOrder(@QueryParam("orderId") String orderId,
            @QueryParam("userName") String userName, @QueryParam("couponCode") String couponCode,
            @QueryParam("usedPlat") int usedPlat) throws Exception {
        couponService.referCouponOrder(userName, orderId, couponCode, usedPlat);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:cancelCouponOrder  
     * @Description:根据券号将优惠券标为已经使用取消订单
     * @param couponCode 券的号码
     * @return
     * @throws Exception
     */
    @POST
    @Path("/cancelCouponOrder")
    @ApiOperation(value = "/cancelCouponOrder", notes = "updateProductStatus")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> cancelCouponOrder(@QueryParam("couponCode") String couponCode) throws Exception {
        couponService.cancelCouponOrder(couponCode);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:updateCouponStatus  
     * @Description:批量修改优惠券状态为禁止使用
     * @param ids 券的主键
     * @return
     * @throws Exception
     */
    @POST
    @Path("/updateCouponStatus")
    @ApiOperation(value = "/updateCouponStatus", notes = "updateCouponStatus")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> updateCouponStatus(@QueryParam("ids") String ids) throws Exception {
        couponService.updateCouponStatus(ids);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:couponNotUsedLists  
     * @Description:* 根据用户,未使用,未过期,渠道,最低订单金额,筛选符合订单的优惠券列表，用于订单页面的可使用列表
     * openId 用户openId,platForm限制使用平台,1web,2h5,3安卓4ios,orderMoney最低订单金额,productIds 限制使用商品字符串
     * @return
     */
    @GET
    @Path("/choseOrderCoupon")
    @ApiOperation(value = "/choseOrderCoupon", notes = "choseOrderCoupon")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CouponUsedListResponse> choseOrderCoupon(@QueryParam("openId") String openId,
            @QueryParam("platForm") int platForm, @QueryParam("orderMoney") BigDecimal orderMoney,
            @QueryParam("productIds") String productIds) throws Exception {
        // 验证参数不能为空
        this.validParamsNotNull(openId, platForm, orderMoney, productIds);
        CouponUsedListResponse list = couponService.choseCoupon(openId, platForm, orderMoney, productIds);
        Response<CouponUsedListResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

    /**
     * @Title:batchAppointCoupon  
     * @Description:指定批量发送优惠券 
     * @param typeCode 类型批次号
     * @param type 1: 注册30天以内会员（含30天） 2：注册30天以上会员   3：所在公司的销售 4：所在公司的会员
     * @return
     * @throws Exception
     */
    @POST
    @Path("/batchAppointCoupon")
    @ApiOperation(value = "/batchAppointCoupon", notes = "batchAppointCoupon")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> batchAppointCoupon(@QueryParam("typeCode") String typeCode, @QueryParam("type") int type,
            @QueryParam("creator") String creator, @QueryParam("companyIds") String companyIds) throws Exception {
        couponService.batchAppointCoupon(typeCode, type, creator, companyIds);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title:singleBindCoupon  
     * @Description:批量修改优惠券状态为禁止使用
     * @param ids 券的主键
     * @return
     * @throws Exception
     */
    @POST
    @Path("/singleBindCoupon")
    @ApiOperation(value = "/singleBindCoupon", notes = "singleBindCoupon")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> singleBindCoupon(@QueryParam("typeCode") String typeCode, @QueryParam("userId") int userId,
            @QueryParam("openId") String openId, @QueryParam("creator") String creator) throws Exception {
        couponService.singleBindCoupon(typeCode, userId, openId, creator);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }

    /**
     * @Title: deleteCoupon
     * @Description: 根据券号删除优惠券
     * @return
     */
    @POST
    @Path("/deleteCoupon")
    @ApiOperation(value = "/deleteCoupon", notes = "deleteCoupon")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> deleteCoupon(@QueryParam("couponCode") String couponCode) throws Exception {
        couponService.deleteCoupon(couponCode);
        Response<String> response = new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
        return response;
    }
}
