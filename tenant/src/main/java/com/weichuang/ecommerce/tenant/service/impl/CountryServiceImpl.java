package com.weichuang.ecommerce.tenant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.CountryEntity;
import com.weichuang.ecommerce.tenant.repository.ICountryDao;
import com.weichuang.ecommerce.tenant.service.ICountryService;

/**
 * @Title: 地址级联表
 * @Package: com.weichuang.ecommerce.tenant.service.impl
 * @Description:
 * @author: licheng
 * @date: 2016/10/11 12:05
 */
@Service("addressService")
@Transactional(readOnly = true)
public class CountryServiceImpl implements ICountryService {
    @Autowired
    private ICountryDao addressDao;

    // 通过父id查询
    @Override
    @Cacheable(value = "country", keyGenerator = "customKeyGenerator")
    public List<CountryEntity> getChildren(Long parentId) throws ServiceException {
        return addressDao.getChildren(parentId);
    }

    // 查询全部国家
    @Override
    public List<CountryEntity> getAllCountry() throws ServiceException {
        return addressDao.getAllCountry();
    }
}
