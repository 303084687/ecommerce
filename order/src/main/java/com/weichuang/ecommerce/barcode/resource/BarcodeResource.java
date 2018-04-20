package com.weichuang.ecommerce.barcode.resource;

import com.weichuang.commons.Constant;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.InviteNewEnvelopResponse;
import com.weichuang.ecommerce.barcode.entity.SalesPullNewcEntity;
import com.weichuang.ecommerce.barcode.entity.TenantCodeStoreEntity;
import com.weichuang.ecommerce.barcode.entity.UserCountEntity;
import com.weichuang.ecommerce.barcode.entity.request.CodeAndTextRequest;
import com.weichuang.ecommerce.barcode.entity.request.TenantCodeStoreRequest;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewcResponse;
import com.weichuang.ecommerce.barcode.entity.response.TenantCodeStoreResponse;
import com.weichuang.ecommerce.barcode.entity.response.UserCountResponse;
import com.weichuang.ecommerce.barcode.service.BarcodeService;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.lang.reflect.Field;
import java.rmi.ServerException;

@Path("/barcode")
@Api(value = "/BarcodeResource", description = "description")

@SuppressWarnings("all")
public class BarcodeResource {
    private static final Logger log = LoggerFactory
            .getLogger(BarcodeResource.class);
    @Autowired
    private WeiXinProperties weiXinProperties;
    @Context
    private HttpServletRequest request;
    @Autowired
    private BarcodeService barcodeService;
    //生成二维码带文本信息
    @ApiOperation(value = "/codeAndText", notes = "new")
    @GET
    @Path("/codeAndText")
    public String codeAndText(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            CodeAndTextRequest codeAndTextRequest = new CodeAndTextRequest();
            String codeUrl = request.getParameter("codeUrl") == null ? "" : request.getParameter("codeUrl").toString();
            Integer codeImageSize = request.getParameter("codeImageSize") == null ? null : Integer.parseInt(request.getParameter("codeImageSize").toString());
            String textColor = request.getParameter("textColor") == null ? "" : request.getParameter("textColor").toString();
            String text = request.getParameter("text") == null ? "" : request.getParameter("text").toString();
            Integer bgImageMarginTop = request.getParameter("bgImageMarginTop") == null ? null : Integer.parseInt(request.getParameter("bgImageMarginTop").toString());
            String textFontName = request.getParameter("textFontName") == null ? "" : request.getParameter("textFontName").toString();
            Integer textFontSize = request.getParameter("textFontSize") == null ? null : Integer.parseInt(request.getParameter("textFontSize").toString());
            Boolean textIsBold = request.getParameter("textIsBold") == null ? false : Boolean.parseBoolean(request.getParameter("textIsBold").toString());

            if (StringUtils.isEmpty(codeUrl)) {

                response.sendError(404, "codeUrl参数不能为空！");
                return "";
            }
            if (codeImageSize == null || codeImageSize == 0) {
                response.sendError(404, "codeImageSize参数必须大于0！");
                return "";
            }
            if (!StringUtils.isEmpty(textColor)) {
                codeAndTextRequest.setTextColor(textColor);
            }
            if (StringUtils.isEmpty(text)) {
                response.sendError(404, "text参数不能为空！");
                return "";
            }
            if (!StringUtils.isEmpty(textFontName)) {
                codeAndTextRequest.setTextFontName(textFontName);
            }
            if (textFontSize != null && textFontSize > 0) {
                codeAndTextRequest.setTextFontSize(textFontSize);
            }
            codeAndTextRequest.setTextIsBold(textIsBold);
            codeAndTextRequest.setCodeImageSize(codeImageSize);
            codeAndTextRequest.setText(text);
            codeAndTextRequest.setCodeUrl(codeUrl);
            codeAndTextRequest.setBgImageMarginTop(bgImageMarginTop);
            String color = codeAndTextRequest.getTextColor();
            Class<?> clazz = Class.forName("java.awt.Color");
            Field f = clazz.getDeclaredField(color);
            if (f == null) {
                response.sendError(404, "在java.awt.Color找不到" + color + "这个属性");
                return "";
            }
            //合并二维码及文本信息以图片输出
            barcodeService.mergeImage(codeAndTextRequest, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /**
     *<p>Description:邀新二维码生成</p>
     *<p>param userAgentId:销售id</p>
     *<p>param mobile:客户手机号</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 10:12</p>
     *<p>return:</p>
     */
    @ApiOperation(value = "/addInviteNewBarCode", notes = "addInviteNewBarCode")
    @POST
    @Path("/addInviteNewBarCode")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<TenantCodeStoreResponse> addInviteNewBarCode( @FormParam("userAgentId") int userAgentId,
                                                         @FormParam("mobile") String mobile) {
        Response<TenantCodeStoreResponse> response = null;
        if (userAgentId <= 0) {
            response = new Response(Result.FAIL, "userAgentId参数不能为空！");
            return response;
        }
        if (StringUtils.isEmpty(mobile)) {
            response = new Response(Result.FAIL, "mobile参数不能为空！");
            return response;
        }
        TenantCodeStoreRequest req = new TenantCodeStoreRequest();
        req.setCodeType(3);
        req.setActionType(4);
        req.setUserAgentId(userAgentId);
        req.setNx(2);
        req.setHasImg(true);
        try {
            TenantCodeStoreResponse entity = barcodeService.addInviteBarCode(req,mobile);
            response = new Response<>(Result.SUCCESS, entity);
        } catch (ServiceException e) {
            response = new Response(e.getCode(),e.getMessage());
        }catch (Exception e){
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }

    /**
     *<p>Description:分享商品二维码生成</p>
     *<p>param userAgentId:销售id</p>
     *<p>param productId:商品id</p>
     *<p>param productCode:商品编码</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/23 10:12</p>
     *<p>return:</p>
     */
    @ApiOperation(value = "/addShareBarCode", notes = "addShareBarCode")
    @POST
    @Path("/addShareBarCode")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<TenantCodeStoreResponse> addShareBarCode( @FormParam("userAgentId") int userAgentId, @FormParam("productId") int productId,
                                                              @FormParam("productCode") String productCode) {
        Response<TenantCodeStoreResponse> response = null;
        try {
            TenantCodeStoreResponse entity = barcodeService.addShareBarCode(userAgentId,productId,productCode);
            response = new Response<>(Result.SUCCESS, entity);
        } catch (ServiceException e) {
            response = new Response(e.getCode(),e.getMessage());
        }catch (Exception e){
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }
    /**
     *<p>Description:绑定收款人二维码生成</p>
     *<p>param userId:健身房管理员id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 18:20</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @ApiOperation(value = "/addReceiverBarCode", notes = "addReceiverBarCode")
    @GET
    @Path("/addReceiverBarCode")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<TenantCodeStoreResponse> addReceiverBarCode( @QueryParam("userId") int userId) {

        Response<TenantCodeStoreResponse> response = null;
        try {
            TenantCodeStoreResponse entity = barcodeService.addReceiverBarCode(userId);
            response = new Response<>(Result.SUCCESS, entity);
        } catch (ServiceException e) {
            response = new Response(e.getCode(),e.getMessage());
        }catch (Exception e){
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }
    /**
     *<p>Description:店员邀请店员二维码</p>
     *<p>param userId:店员id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 18:20</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @ApiOperation(value = "/addInviteSaleBarCode", notes = "addInviteSaleBarCode")
    @GET
    @Path("/addInviteSaleBarCode")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    public Response<TenantCodeStoreResponse> addInviteSaleBarCode( @QueryParam("userId") int userId) {

        Response<TenantCodeStoreResponse> response = null;
        try {
            TenantCodeStoreResponse entity = barcodeService.addInviteSaleBarCode(userId);
            response = new Response<>(Result.SUCCESS, entity);
        } catch (ServiceException e) {
            response = new Response(e.getCode(),e.getMessage());
        }catch (Exception e){
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }
    /**
     *<p>Description:获取邀新信息</p>
     *<p>param codekey:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 10:12</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @ApiOperation(value = "/getInviteNewByCodekey", notes = "getInviteNewByCodekey")
    @POST
    @Path("/getInviteNewByCodekey")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<SalesPullNewcResponse> getInviteNewByCodekey(@FormParam("codekey") String codekey) {
        Response<SalesPullNewcResponse> response=null;
        try{
            SalesPullNewcResponse entity=barcodeService.getInviteNewBycode(codekey);
            response = new Response<>(Result.SUCCESS, entity);
        }catch(Exception ex)
        {
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }
    @ApiOperation(value = "/insertInviteNew", notes = "insertInviteNew")
    @POST
    @Path("/insertInviteNew")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<Integer> insertInviteNew(@FormParam("codekey") String codekey,@FormParam("state") int state,
                                             @FormParam("userId") int userId,@FormParam("saleId") int saleId,
                                             @FormParam("inviteType") int inviteType) {
        Response<Integer> response=null;
        try{
            if (StringUtils.isEmpty(codekey)) {
                response = new Response(Result.FAIL, "codekey参数无效！");
                return response;
            }
            if (state<0) {
                response = new Response(Result.FAIL, "state参数无效！");
                return response;
            }
            if (userId<=0) {
                response = new Response(Result.FAIL, "userId参数无效！");
                return response;
            }
            if (inviteType<=0) {
                response = new Response(Result.FAIL, "inviteType参数无效！");
                return response;
            }
            if (saleId<=0) {
                response = new Response(Result.FAIL, "saleId参数无效！");
                return response;
            }

            SalesPullNewcEntity entity=new SalesPullNewcEntity();
            entity.setSaleId(saleId);
            entity.setState(state);
            entity.setUserId(userId);
            entity.setInvitetype(inviteType);
            entity.setCodeKey(codekey);
            int flag=barcodeService.insertNew(entity);
            response = new Response<>(Result.SUCCESS, flag);
        }catch (ServiceException ex) {
            response = new Response(Result.FAIL, ex.getMessage());
        }catch(Exception ex)
        {
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }
    /**
     *<p>Description:更新邀新信息</p>
     *<p>param codekey:键值</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 10:12</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @ApiOperation(value = "/updateInviteNew", notes = "updateInviteNew")
    @POST
    @Path("/updateInviteNew")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<Integer> updateInviteNew(@FormParam("codekey") String codekey,@FormParam("state") int state,@FormParam("userId") int userId) {
        Response<Integer> response=null;
        try{
            if (state <= 0) {
                response = new Response(Result.FAIL, "state参数无效！");
                return response;
            }
            if (StringUtils.isEmpty(codekey)) {
                response = new Response(Result.FAIL, "codekey参数不能为空！");
                return response;
            }
            if (userId<=0) {
                response = new Response(Result.FAIL, "userId参数无效！");
                return response;
            }
            int flag=barcodeService.updateInviteNew(codekey,state,userId);
            response = new Response<>(Result.SUCCESS, flag);
        }catch (ServiceException ex) {
            response = new Response(Result.FAIL, ex.getMessage());
        }catch(Exception ex)
        {
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }
    /**
     *<p>Description:领取红包</p>
     *<p>param salesId:销售id</p>
     *<p>param rid:设置id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/28 16:43</p>
     */
    @ApiOperation(value = "/redEnvelopRece", notes = "redEnvelopRece")
    @POST
    @Path("/redEnvelopRece")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<Integer> redEnvelopRece(@FormParam("salesId") int salesId,@FormParam("rid") int rid) {
        Response<Integer> response=null;
        try{
            if (rid <= 0) {
                response = new Response(Result.FAIL, 0);
                log.error("redEnvelopRece rid参数无效！");
                return response;
            }
            if (salesId <= 0) {
                response = new Response(Result.FAIL, 0);
                log.error("redEnvelopRece salesId参数无效！");
                return response;
            }
            int flag=barcodeService.RedEnvelopRece(request,salesId,rid);
            if(flag==1){
                log.info("redEnvelopRece salesId="+salesId+",rid="+rid+" 红包领取成功！");
                response = new Response<>(Result.SUCCESS, 1);
            }
            else{
                log.info("redEnvelopRece salesId="+salesId+",rid="+rid+" 红包领取失败！");
                response = new Response(Result.FAIL, 0);
            }
        }catch (ServiceException ex) {
            log.info("redEnvelopRece salesId="+salesId+",rid="+rid+" 红包领取失败！error:"+ex.getMessage());
            response = new Response(Result.FAIL, 0);
        }catch(Exception ex)
        {
            log.info("redEnvelopRece salesId="+salesId+",rid="+rid+" 红包领取失败！error:"+ex.getMessage());
            response = new Response(Result.FAIL, 0);
        }
        return response;
    }
    /**
     * <p>Description:增加二维码信息</p>
     * <p>param entity:</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/10/19 16:30</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    //修改后台用户
    @ApiOperation(value = "/addBarCode", notes = "addBarCode")
    @POST
    @Path("/addBarCode")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response<TenantCodeStoreResponse> addBarCode(@FormParam("storeData") String storeData, @FormParam("codeType") int codeType,
                                                        @FormParam("actionType") int actionType, @FormParam("userAgentId") int userAgentId,
                                                        @FormParam("nx") int nx, @FormParam("addressUrl") String addressUrl, @FormParam("hasImg") boolean hasImg,
                                                        @FormParam("otherData") String otherData) {
        Response<TenantCodeStoreResponse> response = null;
        if (StringUtils.isEmpty(storeData)) {
            response = new Response(Result.FAIL, "storeData参数不能为空！");
            return response;
        }
        if (codeType <= 0) {
            response = new Response(Result.FAIL, "codeType参数不正确！");
            return response;
        }
        if (actionType <= 0) {
            response = new Response(Result.FAIL, "actionType参数不正确！");
            return response;
        }
        if (userAgentId <= 0) {
            response = new Response(Result.FAIL, "userAgentId参数不能为空！");
            return response;
        }
        if (nx != 1 && nx != 2) {
            response = new Response(Result.FAIL, "nx参数不正确！");
            return response;
        }
        TenantCodeStoreRequest req = new TenantCodeStoreRequest();
        req.setStoreData(storeData);
        req.setCodeType(codeType);
        req.setActionType(actionType);
        req.setUserAgentId(userAgentId);
        req.setNx(nx);
        req.setHasImg(hasImg);
        if (StringUtils.isNotEmpty(addressUrl)) {
            req.setAddressUrl(addressUrl);
        }
        if(StringUtils.isNotEmpty(otherData))
        {
            req.setOtherData(otherData);
        }
        try {

            TenantCodeStoreResponse entity = barcodeService.addBarCode(req, req.isHasImg());
            response = new Response<>(Result.SUCCESS, entity);
        } catch (ServiceException e) {
            response = new Response(Result.FAIL, "操作失败");
        }
        return response;
    }

    /**
     * <p>Description:获取扫描详细信息</p>
     * <p>param codeKey:主键uuid</p>
     * <p>author:zhanghongsheng</p>
     * <p>date:2017/10/26 15:58</p>
     * <p>return:</p>
     * <p>throws: </p>
     */
    @ApiOperation(value = "/getTenantCodeStore", notes = "getTenantCodeStore")
    @GET
    @Path("/getTenantCodeStore")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<TenantCodeStoreResponse> getTenantCodeStore(@QueryParam("codekey") String codekey) {
        Response<TenantCodeStoreResponse> response = null;
        if (StringUtils.isEmpty(codekey)) {
            response = new Response(Result.BARCODE_CODEKEY_NOT_EXIST);
        }
        try {
            TenantCodeStoreResponse entity = barcodeService.getByCodeKey(codekey);
            response = new Response<>(Result.SUCCESS, entity);
        } catch (ServiceException e) {
            response = new Response(Result.BARCODE_GET_TENANT_CODESTRORE_EXCEPTION);
        }
        return response;
    }
    /**
     *<p>Description:获取邀新红包图</p>
     *<p>param userId:店员id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:44</p>
     */
    @ApiOperation(value = "/getInviteNewList", notes = "getInviteNewList")
    @GET
    @Path("/getInviteNewList")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<InviteNewEnvelopResponse> getInviteNewList(@QueryParam("userId") int userId) {
        Response<InviteNewEnvelopResponse> response = null;
        try {
            InviteNewEnvelopResponse  res=barcodeService.getByUserId(userId);
            response = new Response<>(Result.SUCCESS, res);
        } catch (ServiceException e) {
            response = new Response(e.getCode(),e.getMessage());
        }catch (Exception e){
            response = new Response(Result.FAIL,Result.FAIL.getMessage());
        }
        return response;
    }
    /**
     *<p>Description:通过id查询发送邀请数量</p>
     *<p>param type:1 店员  2 门店 3 公司</p>
     *<p>param userId:店员或门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 15:13</p>
     *<p>return:</p>
     */
    @ApiOperation(value = "/getInviteCount", notes = "getInviteCount")
    @GET
    @Path("/getInviteCount")
    @Produces({Constant.APPLICATION_JSON_UTF8})
    @Consumes({Constant.APPLICATION_JSON_UTF8})
    public Response<UserCountResponse> getInviteCount(@QueryParam("type") int type, @QueryParam("userId") int userId) {
        Response<UserCountResponse> response = null;
        try {
            UserCountResponse res=barcodeService.getInviteCount(type,userId);
            response = new Response<>(Result.SUCCESS, res);
        } catch (ServiceException e) {
            response = new Response(e.getCode(),e.getMessage());
        }catch (Exception e){
            response = new Response(Result.FAIL,Result.FAIL.getMessage());
        }
        return response;
    }


}
