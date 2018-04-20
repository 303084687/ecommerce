package com.weichuang.ecommerce.order.feign;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.feign.SalesBaseResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "tenant")
@SuppressWarnings("all")
public interface IAgent {
	@RequestMapping(value = "/api/agent/get/salesBaseInfo/salesId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public Response<SalesBaseResponse> getAgentInfo(@RequestParam("salesId") Integer salesId)
			throws ServiceException;
}
