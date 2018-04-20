package com.weichuang.ecommerce.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.RedisHelper;
import com.weichuang.ecommerce.WeixinConfig;
import com.weichuang.ecommerce.barcode.entity.response.SalesEntity;
import com.weichuang.ecommerce.barcode.entity.response.SalesListResponse;
import com.weichuang.ecommerce.barcode.feign.IAgent;
import com.weichuang.ecommerce.order.resource.OrderResource;
import com.weichuang.ecommerce.weixin.entity.Token;
import com.weichuang.ecommerce.weixin.entity.response.SendMessageResponse;
import com.weichuang.ecommerce.weixin.service.MessageService;
import com.weichuang.ecommerce.weixin.util.HttpUtil;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
* <p>ClassName:MessageServiceImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/19 11:50</p>
**/
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private WeixinConfig weixinConfig;
    @Autowired
    RedisHelper redisHelper;
    @Autowired
    private IAgent agentFeign;
    /**
     *<p>Description:向微信用户发消息  </p>
     *<p>param openId:微信用户openId</p>
     *<p>param message:发送的消息</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/19 13:11</p>
     *<p>return:  SendMessageResponse{errcode：0 正常返回，其他数字表示有错误，errmsg:错误信息}</p>
     *<p>throws: </p>
     */
    public SendMessageResponse sendMessage(String openId, String message) throws ServiceException {

        if(StringUtils.isEmpty(openId)){
            throw new ServiceException(3001,"openId can not empty");
        }
        if(StringUtils.isEmpty(message)){
            throw new ServiceException(3002,"message can not empty");
        }
        String message_token_key="SEND_MESSAGE_TOKEN";
        String tokenStr="";
        if(redisHelper.exists(message_token_key)){
             tokenStr=redisHelper.get(message_token_key);
             boolean flag=HttpUtil.checkToken(tokenStr);
             if(!flag){
                 Token token=null;
                 token=HttpUtil.getToken(weixinConfig.getAppId(),weixinConfig.getAppSecret());
                 tokenStr=token.getAccessToken();
             }
        }
        else{
            Token token=null;
            token=HttpUtil.getToken(weixinConfig.getAppId(),weixinConfig.getAppSecret());
            tokenStr=token.getAccessToken();
            redisHelper.set(message_token_key,tokenStr);
            redisHelper.expire(message_token_key,10*60);
        }

        if(StringUtils.isEmpty(tokenStr)){
            throw new ServiceException(3003,"can not get token");
        }
        JSONObject resultJson=HttpUtil.sendTextMsg(openId,message,tokenStr);
        SendMessageResponse res=new SendMessageResponse();
        int errcode=resultJson.getInteger("errcode");
        String errmsg=resultJson.get("errmsg")==null?"":resultJson.getString("errmsg");
        res.setErrcode(errcode);
        res.setErrmsg(errmsg);
        return res;
    }
    /**
     *<p>Description:给店员发消息</p>
     *<p>param message:消息内容</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/13 14:06</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public Response<Integer> sendSalesMessages(String message) throws ServiceException{
        int roleId=5;
        int pageSize=1000;
        ExecutorService executorService=Executors.newFixedThreadPool(10);
        Response<SalesListResponse> salesListResponse=agentFeign.getSalesList(roleId,0,null,1,pageSize);
        int salecode=salesListResponse.getCode();
        if(salecode!=0){
            throw new ServiceException(90001,"MessageServiceImpl sendSalesMessages agentFeign.getSalesList error:code="+salecode);
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(message)){
            throw new ServiceException(90002,"MessageServiceImpl sendSalesMessages agentFeign.getSalesList message is null");
        }
        SalesListResponse response=salesListResponse.getValue();
        int pages=response.getPages();
        List<SalesEntity> salelist=response.getList();
        if(pages>0){
            for(int i=1;i<=pages;i++){
                if(i==1){
                    if(salelist!=null&&!salelist.isEmpty()){
                        for(SalesEntity salesEntity:salelist ){
                           String openId= salesEntity.getUserName();
                           if(org.apache.commons.lang.StringUtils.isNotEmpty(openId))
                                executorService.execute(new SendSaleMessageThread(openId,message));
                        }
                    }
                }
                else{
                    Response<SalesListResponse> salesListResponse1=agentFeign.getSalesList(roleId,0,null,i,pageSize);
                    if(salesListResponse1.getCode()==0){
                        salelist=salesListResponse1.getValue().getList();
                        if(salelist!=null&&!salelist.isEmpty()){
                            for(SalesEntity salesEntity:salelist ){
                                String openId= salesEntity.getUserName();
                                if(org.apache.commons.lang.StringUtils.isNotEmpty(openId))
                                    executorService.execute(new SendSaleMessageThread(openId,message));
                            }
                        }
                    }
                }
            }
        }
        executorService.shutdown();
        Response<Integer> res=new Response<>(Result.SUCCESS);
        return res;
    }
    protected class SendSaleMessageThread extends Thread{
        private String openId;
        private String message;
        private  final Logger log = LoggerFactory.getLogger(OrderResource.class);
        public SendSaleMessageThread(String openId, String message){
            this.setMessage(message);
            this.setOpenId(openId);
        }
        public void run(){
            log.info("MessageServiceImpl sendSalesMessages 发送消息:openId="+openId+"，开始发送");
            SendMessageResponse response= sendMessage(openId,message);
            log.info("MessageServiceImpl sendSalesMessages 发送消息:openId="+openId+"，发送消息，结果：code="+response.getErrcode()+",message="+response.getErrmsg());
        }
        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}
