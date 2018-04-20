package com.weichuang.ecommerce.tenant.service;

/**
 * Created by licheng on 2016/10/11.
 */
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.UserAddressEntity;
import com.weichuang.ecommerce.tenant.entity.request.UserAddressRequest;
import com.weichuang.ecommerce.tenant.entity.response.UserAddressEntityResponse;
import com.weichuang.ecommerce.tenant.entity.response.UserAddressListResponse;

/**
 * @Title: 用户表_收货地址
 * @Package: com.weichuang.ecommerce.tenant.service
 * @Description:
 * @author: wgl
 * @date: 2016/10/11 11:58
 */
public interface IUserAddressService {
    // 根据用户主键查询所有收货地址
    public UserAddressListResponse getUserAddressList(int userId, int page, int size) throws ServiceException;

    // 根据主键查询详细信息
    public UserAddressEntityResponse getUserAddressInfo(int id) throws ServiceException;

    // 增加收货地址
    public int addUserAddress(UserAddressRequest request) throws ServiceException;

    // 修改收货地址
    public int updateUserAddress(UserAddressRequest request) throws ServiceException;

    // 根据主键删除收货地址
    public int deleteUserAddress(int id, int userId) throws ServiceException;

    // 根据主键和用户设置默认地址
    public int defaultAddress(int id, int userId) throws ServiceException;
    //根据用户id获取用户默认收货地址
    public UserAddressEntityResponse getUserAddressInfoDefault(int userId);
}
