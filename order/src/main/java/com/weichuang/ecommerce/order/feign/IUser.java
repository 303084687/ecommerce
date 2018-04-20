package com.weichuang.ecommerce.order.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.response.UserDetailResponse;


@FeignClient(name = "tenant")
@SuppressWarnings("all")
public interface IUser {
	//@GetMapping(value = "/user/{id}")
	@RequestMapping(value = "/api/user/get/detail/userId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Response<UserDetailResponse> getUserDetailByUserId(@RequestParam("userId")Integer userId)
			throws ServiceException;
}
