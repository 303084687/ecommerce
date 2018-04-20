package com.weichuang.ecommerce.barcode.feign;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.barcode.entity.response.SalesListResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.QueryParam;

/**
* <p>ClassName:IAgent</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/26 14:13</p>
**/
@FeignClient(name = "tenant")
public interface IAgent {
    @RequestMapping(value = "/api/agent/get/agentInfo/agentId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Response<AgentResponse> getAgentInfoByAgentId(@RequestParam("agentId") int agentId) throws ServiceException;

    @RequestMapping(value = "/api/agent/get/companyInfo/agentId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Response<AgentResponse> getCompanyInfoByAgentId(@RequestParam("agentId") int agentId) throws ServiceException;
    @RequestMapping(value = "/api/agent/get/sales/list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Response<SalesListResponse> getSalesList(@RequestParam("roleId") int roleId,
                                                     @RequestParam("agentId") int agentId,

                                                     @RequestParam("keyWord") String keyWord,

                                                     @RequestParam("pageNum") int pageNum,
                                                     @RequestParam("pageSize") int pageSize)throws ServiceException;
}
