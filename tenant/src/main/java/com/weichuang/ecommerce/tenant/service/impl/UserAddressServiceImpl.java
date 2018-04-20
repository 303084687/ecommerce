package com.weichuang.ecommerce.tenant.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.UserAddressEntity;
import com.weichuang.ecommerce.tenant.entity.UserAddressTemplate;
import com.weichuang.ecommerce.tenant.entity.request.UserAddressRequest;
import com.weichuang.ecommerce.tenant.entity.response.UserAddressEntityResponse;
import com.weichuang.ecommerce.tenant.entity.response.UserAddressListResponse;
import com.weichuang.ecommerce.tenant.repository.IUserAddressDao;
import com.weichuang.ecommerce.tenant.service.IUserAddressService;

/**
 * @Title: 用户表_收货地址
 * @Package: com.weichuang.ecommerce.tenant.service.impl
 * @Description:
 * @author: wgl
 * @date: 2016/10/11 12:15
 */
@Service("userAddressService")
@SuppressWarnings("all")
public class UserAddressServiceImpl implements IUserAddressService {
    @Autowired
    private IUserAddressDao userAddressDao;

    // 根据用户主键查询所有收货地址
    @Override
    @Transactional(readOnly = true)
    public UserAddressListResponse getUserAddressList(int userId, int page, int size) throws ServiceException {
        // 使用分页插件
        PageHelper.startPage(page, size);
        List<UserAddressTemplate> list = userAddressDao.getUserAddressList(userId);
        // 执行分页并封装参数
        PageInfo pages = new PageInfo(list);
        UserAddressListResponse response = new UserAddressListResponse();
        response.setList(list);
        response.setCount(pages.getTotal());
        return response;
    }

    // 根据主键查询详细信息
    @Override
    @Transactional(readOnly = true)
    public UserAddressEntityResponse getUserAddressInfo(int id) throws ServiceException {
        UserAddressTemplate entity = userAddressDao.getUserAddressInfo(id);
        UserAddressEntityResponse response = new UserAddressEntityResponse();
        response.setEntity(entity);
        return response;
    }

    // 增加收货地址
    @Override
    @Transactional
    public int addUserAddress(UserAddressRequest request) throws ServiceException {
        UserAddressEntity entity = new UserAddressEntity();
        entity.setCityId(request.getCityId());
        entity.setConutyId(request.getConutyId());
        entity.setProvinceId(request.getProvinceId());
        entity.setCreateTime(new Date());
        entity.setDetail(request.getDetail());
        entity.setReceiveName(request.getReceiveName());
        entity.setMobile(request.getMobile());
        entity.setUserId(request.getUserId());
        userAddressDao.addUserAddress(entity);
        return entity.getId();
    }

    // 修改收货地址
    @Override
    @Transactional
    public int updateUserAddress(UserAddressRequest request) throws ServiceException {
        UserAddressEntity entity = new UserAddressEntity();
        entity.setId(request.getId());
        entity.setCityId(request.getCityId());
        entity.setConutyId(request.getConutyId());
        entity.setProvinceId(request.getProvinceId());
        entity.setDetail(request.getDetail());
        entity.setReceiveName(request.getReceiveName());
        entity.setMobile(request.getMobile());
        return userAddressDao.updateUserAddress(entity);
    }

    // 根据主键删除收货地址
    @Override
    @Transactional
    public int deleteUserAddress(int id, int userId) throws ServiceException {
        return userAddressDao.deleteUserAddress(id, userId);
    }

    // 根据主键和用户设置默认地址
    @Override
    @Transactional
    public int defaultAddress(int id, int userId) throws ServiceException {
        return userAddressDao.defaultAddress(id, userId);
    }

    //根据用户id获取用户默认收货地址
    public UserAddressEntityResponse getUserAddressInfoDefault(int userId){
        UserAddressTemplate entity = userAddressDao.getUserAddressInfoDefault(userId);
        UserAddressEntityResponse response = new UserAddressEntityResponse();
        response.setEntity(entity);
        return response;
    }
}
