package com.weichuang.ecommerce.coupon.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.ecommerce.coupon.entity.ReferRecomme;
import com.weichuang.ecommerce.coupon.entity.response.CompanyNumResponse;
import com.weichuang.ecommerce.coupon.service.IReferRecommeService;

/**
 * <p>ClassName: ReferRecommeResources.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:记录优惠券领取关系 </p>
 * <p>author wanggongliang</p>
 * <p>2017年12月22日 下午4:51:31</p>
 */
@Path("/refer/coupon")
@Api(value = "/ReferRecommeResources", description = "优惠券领取记录关系 Api接口")
@SuppressWarnings("all")
public class ReferRecommeResources extends BaseResource {
    // 优惠券service
    @Autowired
    private IReferRecommeService recommeService;

    /**
     * @Title:addReferRecomme  
     * @Description:b-c,c-c新人推荐赠送优惠券增加对应关系
     * @param recomme 增加的实体
     * @return
     */
    @PUT
    @Path("/addReferRecomme")
    @ApiOperation(value = "/addReferRecomme", notes = "addReferRecomme")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    @Consumes({ Constant.APPLICATION_JSON_UTF8 })
    public Response<String> addReferRecomme(ReferRecomme recomme) {
        // 调取接口
        recommeService.addReferRecomme(recomme);
        return new Response(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage());
    }

    /**
     * @Title:getReferList 
     * @Description:根据门店或者公司以及员工查询数据统计(包含查询分享次数) query 1公司2门店3个人,queryId 为对用的主键
     * @param isUsed 1未使用2已使用
     * @param status 状态1正常2禁止
     * @param typeCode 优惠券类型
     * @param couponCode 券码
     * @return
     */
    @GET
    @Path("/referList")
    @ApiOperation(value = "/referList", notes = "couponList")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<CompanyNumResponse> getReferList(@QueryParam("queryType") int queryType,
            @QueryParam("queryId") int queryId) throws Exception {
        CompanyNumResponse list = recommeService.getReferList(queryType, queryId);
        Response<CompanyNumResponse> response = new Response<>(Result.SUCCESS, list);
        return response;
    }

}
