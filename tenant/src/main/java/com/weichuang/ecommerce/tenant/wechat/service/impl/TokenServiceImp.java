package com.weichuang.ecommerce.tenant.wechat.service.impl;
import org.springframework.stereotype.Service;
import com.weichuang.ecommerce.tenant.wechat.entity.Token;
import com.weichuang.ecommerce.tenant.wechat.service.ITokenService;
import com.weichuang.ecommerce.tenant.wechat.service.util.CommonUtil;



/**
 * @describe	
 * @author towards.liu@163.com
 * @version 1.0
 * @date  2017年9月26日
 */
@Service
public class TokenServiceImp implements ITokenService {
	
	
	/**
	 * 获取access_token
	 * @param appid
	 * @param appsecret
	 * 
	 * 
	 */
	@Override
	public  String getToken(String token_url,String appId,String appSecret) {
		
			Token token=CommonUtil.getToken(token_url,appId, appSecret);
			String accessToken=token.getAccessToken();
			
				
		return accessToken;
	}
	
	
	
	
	
			
		
	
	
}
