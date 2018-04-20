package com.weichuang.ecommerce.tenant.wechat.service;

import org.springframework.stereotype.Service;

/**
 * @describe 获取token
 * @author towards.liu@163.com
 * @version 1.0
 * @date 2017年10月11日
 */
@Service
public interface ITokenService {

	public String getToken(String token_url, String appId, String appSecret);
	
}
