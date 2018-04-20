package com.weichuang.ecommerce.tenant.service;

import java.util.List;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.tenant.entity.CountryEntity;

/**
 * @Title: 地址级联菜单
 * @Package: com.weichuang.ecommerce.tenant.service
 * @Description:
 * @author: licheng
 * @date: 2016/10/20 11:37
 */
public interface ICountryService {
	//通过父id查询
	List<CountryEntity> getChildren(Long parentId) throws ServiceException;
	
	//查询全部国家
	List<CountryEntity> getAllCountry() throws ServiceException;
}
