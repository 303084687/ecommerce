package com.weichuang.ecommerce.tenant.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.entity.CountryEntity;

/**
 * @Title: 地址级联表
 * @Package: com.weichuang.ecommerce.tenant.repository
 * @Description:
 * @author: licheng
 * @date: 2016/10/20 11:40
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface ICountryDao {
	//根据父id查询对应的信息
	List<CountryEntity> getChildren(Long parentId);
	
	//查询所有国家
	List<CountryEntity> getAllCountry();
}
