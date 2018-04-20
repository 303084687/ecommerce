package com.weichuang.ecommerce.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.weixin.entity.response.SendMessageResponse;

/**
* <p>ClassName:MessageService</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/19 11:35</p>
**/
public interface MessageService {
    public SendMessageResponse sendMessage(String openId, String message)throws ServiceException;
    public Response<Integer> sendSalesMessages(String message) throws ServiceException;
}
