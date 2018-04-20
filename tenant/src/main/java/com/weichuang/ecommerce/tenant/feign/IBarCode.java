package com.weichuang.ecommerce.tenant.feign;

import com.weichuang.ecommerce.tenant.entity.response.CompanyNumResponse;
import com.weichuang.ecommerce.tenant.entity.response.UserCountResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.response.TenantCodeStoreResponse;

@FeignClient(name = "order")
@SuppressWarnings("all")
public interface IBarCode {

	@RequestMapping(value = "/api/barcode/addBarCode", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded;charset=utf-8")
	public Response<TenantCodeStoreResponse> addBarCode(
			@RequestParam("storeData") String storeData,

			@RequestParam("codeType") int codeType,

			@RequestParam("actionType") int actionType,
			@RequestParam("userAgentId") int userAgentId,

			@RequestParam("nx") int nx,
			@RequestParam("addressUrl") String addressUrl,

			@RequestParam("hasImg") boolean hasImg,

			@RequestParam("otherData") String otherData)
			throws ServiceException;

	@RequestMapping(value = "/api/barcode/getInviteCount", method = RequestMethod.GET, consumes = "application/json;charset=utf-8")
	public Response<UserCountResponse> getInviteCount(
			@RequestParam("type") int type,
			@RequestParam("userId") int userId
	);

	@RequestMapping(value = "/api/refer/coupon/referList", method = RequestMethod.GET, consumes = "application/json;charset=utf-8")
	public Response<CompanyNumResponse> getReferList(
			@RequestParam("queryType") int queryType,
			@RequestParam("queryId") int queryId
	);
}
