package com.weichuang.ecommerce.tenant.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.weichuang.commons.BaseResource;
import com.weichuang.commons.Constant;
import com.weichuang.commons.ResourceException;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.CountryEntity;
import com.weichuang.ecommerce.tenant.service.ICountryService;

/**
 * <p>ClassName: AddressResource</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 这里用一句话描述这个方法的作用</p>
 * <p>author: licheng</p>
 * <p>date: 2016/11/17 10:04 </p>
 */
@Path("/address")
@Api(value = "/addressResource", description = "description")
public class CountryResource extends BaseResource {
	private static final Logger log = LoggerFactory.getLogger(CountryResource.class);
	@Autowired
	private ICountryService addressService;
	
	//通过父id查询
	@ApiOperation(value = "/children/{parentId}", notes = "get")
	@GET
	@Path("/children/{parentId}")
	@Produces({ Constant.APPLICATION_JSON_UTF8 })
	public Response<List<CountryEntity>> getChildren(@PathParam("parentId") Long parentId) throws ResourceException {
		Response<List<CountryEntity>> response = null;
		try {
			//业务逻辑
			response = new Response<>(Result.SUCCESS, addressService.getChildren(parentId));
		}
		catch (ServiceException e) {
			log.error(e.getMessage(), e);
			response = new Response<>(Result.FAIL);
			throw new ResourceException(e);
		}
		return response;
	}
}
