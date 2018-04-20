package com.weichuang.ecommerce.tenant.token;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.weichuang.commons.ResourceException;
import com.weichuang.commons.sign.Base64;
import com.weichuang.ecommerce.RedisHelper;

;
/**
 * <p>ClassName: TokenCreateAndValidate</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: token创建和验证</p>
 * <p>author zhouhe</p>
 * <p>date 2017/1/19 11:09</p>
 */
@Component("tokenCreateAndValidate")
public class TokenCreateAndValidate {
	private static final String INSERT_TOKEN = "insertToken"; // 注册
	@Autowired
	private RedisHelper redisHelper;
	@Autowired
	private TokenProperty tokenProperty;
	
	//TODO 1、token生成规则：userid+username+时间，使用秘钥加密，算法待确认
	//TODO 2、token存储规则：key（token）:value（用户信息json）
	/**
	 * <p>Description: token生成规则：userid+username+时间，使用秘钥加密，算法待确认</p>
	 * <p>param  </p>
	 * <p>author zhouhe </p>
	 * <p>date 2017/1/19 13:55 </p>
	 * <p>return </p>
	 */
	public String createToken(String userId, String userName) throws ResourceException {
		String encryptString = userId + userName + new Date();
		//加密字符串
		String result = Base64.encode(DigestUtils.sha1(encryptString.getBytes()));
		//向Redis中追加
		addTokenToRedis(userId, userName, result, tokenProperty.getDuration());
		return result;
	}
	
	/**
	 * <p>Description: token存储规则：key（token）:value（用户信息json）</p>
	 * <p>param </p>
	 * <p>author zhouhe </p>
	 * <p>date 2017/5/23 16:11 </p>
	 * <p>return </p>
	 */
	private String addTokenToRedis(String userId, String userName, String token, Long time) {
		TokenParams param = new TokenParams();
		param.setUserId(userId);
		param.setUserName(userName);
		param.setCreateDate(new Date().toString());
		String jSession = JSON.toJSONString(param);
		//TODO 2、token存储规则：key（token）:value（用户信息json）
		String res = redisHelper.setToken(INSERT_TOKEN, token, jSession, time);
		return res;
	}
	
	/**
	 * <p>Description: 判断Token是否有效 ||| Redis中不存在token记录则表明token无效、否则有效</p>
	 * <p>param </p>
	 * <p>author zhouhe </p>
	 * <p>date 2017/5/23 16:19 </p>
	 * <p>return </p>
	 */
	public Boolean isTokenValidate(String token) {
		//1.根据appKey以及deviceId获取缓存中的Session
		String res = redisHelper.getToken(INSERT_TOKEN, token);
		//1.token是否存在判断
		if (StringUtils.isEmpty(res) || StringUtils.isEmpty(token)) {
			return false;
		}
		return true;
	}
	
	/**
	 * <p>Description: 删除token</p>
	 * <p>param  </p>
	 * <p>author ningxiaoling </p>
	 * <p>date 2017/1/19 13:55 </p>
	 * <p>return </p>
	 */
	public boolean deleteToken(String userId, String deviceId) throws ResourceException {
		boolean flag = false;
		Long result = null;
		result = redisHelper.del(INSERT_TOKEN + ":" + userId + deviceId);
		if (result == 1) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * <p>Description: 根据token获取用户信息userId、userName</p>
	 * <p>param </p>
	 * <p>author zhouhe </p>
	 * <p>date 2017/5/23 16:14 </p>
	 * <p>return </p>
	 */
	public String getUserIdAndUserName(String key) {
		return redisHelper.getToken(INSERT_TOKEN, key);
	}
}
