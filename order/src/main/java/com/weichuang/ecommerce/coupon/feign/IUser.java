package com.weichuang.ecommerce.coupon.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.weichuang.commons.Response;
import com.weichuang.commons.ServiceException;

/**
 * <p>ClassName: IUser.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 用户api</p>
 * <p>author wanggongliang</p>
 * <p>2017年11月1日 上午10:28:12</p>
 */
@FeignClient(name = "tenant")
@SuppressWarnings("all")
public interface IUser {
    @RequestMapping(value = "/api/user/get/openId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Response<UserResponse> getUserByOpenId(@RequestParam("openId") String openId,
            @RequestParam("registerType") int registerType) throws ServiceException;

    @RequestMapping(value = "/api/user/get/detail/userId", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Response<UserDetailResponse> getUserDetailByUserId(@RequestParam("userId") int userId)
            throws ServiceException;

    // 获取所有B端用户数据
    @RequestMapping(value = "/api/user/get/salesAll", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Response<UserDetailListResponse> getSalesAll() throws Exception;

    // 根据条件查询用户
    @RequestMapping(value = "/api/user/get/detail/couponUserList", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
    public Response<UserDetailListResponse> getCounponUserList(@RequestParam("type") int type) throws Exception;

    // 根据公司主键查询所属的员工
    @RequestMapping(value = "/api/user/get/userDetail/byCompanyIds", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
    public Response<UserDetailListResponse> getUserDetailByCompanyIds(@RequestParam("type") int type,
            @RequestParam("ids") String ids) throws Exception;
}
