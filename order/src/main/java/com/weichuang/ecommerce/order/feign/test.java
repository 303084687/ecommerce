package com.weichuang.ecommerce.order.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.response.UserDetailResponse;


/**
 * @ClassName: test.java
 * @Company: 伟创科技(北京)科技有限公司
 * @Description: 
 * @author towards.liu@163.com
 * @2017年10月20日 下午8:17:26
 */
@Controller
public class test {
	
	@Autowired
	private IUser user;
	@RequestMapping("/test/test")
	public int getid(Integer userId ) throws ServiceException{
		UserDetailResponse r = user.getUserDetailByUserId(7).getValue();
		
		return 0;
	}
}
