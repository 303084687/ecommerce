package com.weichuang.ecommerce.weixin.resource;

import com.weichuang.commons.*;
import com.weichuang.commons.message.SendMessage;
import com.weichuang.ecommerce.coupon.entity.response.CouponListResponse;
import com.weichuang.ecommerce.weixin.entity.response.SendMessageResponse;
import com.weichuang.ecommerce.weixin.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
* <p>ClassName:MessageResource</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/19 10:56</p>
**/
@Path("/weixin")
@Api(value = "/weixin", description = "微信 Api接口")
public class MessageResource extends BaseResource{
    @Autowired
    private MessageService messageService;
    /**
     *<p>Description:向公众号用户发送消息</p>
     *<p>param openId:用户微信openId</p>
     *<p>param message:发送的消息</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/19 11:03</p>
     */
    @GET
    @Path("/sendMessage")
    @ApiOperation(value = "/sendMessage", notes = "sendMessage")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<SendMessageResponse> sendMessage(@QueryParam("openId") String openId,@QueryParam("message") String message){
        SendMessageResponse res=null;
        try{
            res=messageService.sendMessage(openId, message);
        }catch(ServiceException ex)
        {
            Response<SendMessageResponse> response = new Response<>(ex.getCode(),ex.getMessage());
            return response;
        }
        catch (Exception ex){
            Response<SendMessageResponse> response = new Response<>(Result.FAIL);
            return response;
        }
        Response<SendMessageResponse> response = new Response<>(Result.SUCCESS,res);
        return response;
    }
    @GET
    @Path("/sendSalesMessages")
    @ApiOperation(value = "/sendSalesMessages", notes = "sendSalesMessages")
    @Produces({ Constant.APPLICATION_JSON_UTF8 })
    public Response<Integer> sendSalesMessages(@QueryParam("message") String message){
        Response<Integer> res=null;
        try{
            res=messageService.sendSalesMessages(message);
        }catch(ServiceException ex)
        {
            res = new Response<>(ex.getCode(),ex.getMessage());
        }
        catch (Exception ex){
          res = new Response<>(Result.FAIL);
        }
        return res;
    }
}
