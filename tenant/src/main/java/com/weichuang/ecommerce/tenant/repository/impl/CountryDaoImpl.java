package com.weichuang.ecommerce.tenant.repository.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.CountryEntity;
import com.weichuang.ecommerce.tenant.repository.ICountryDao;

/**
 * @Title: 地址级联表
 * @Package: com.weichuang.ecommerce.tenant.repository
 * @Description:
 * @author: licheng
 * @date: 2016/10/20 11:40
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
@SuppressWarnings("all")
public class CountryDaoImpl implements ICountryDao {
	@Autowired
	public SqlSessionTemplate sqlSessionTemplate;
	
	//查询所有国家
	@Override
	public List<CountryEntity> getAllCountry() {
		return sqlSessionTemplate.selectList(NameSpaceConstant.COUNTRY + ".selectAllCountry");
	}
	
	//根据父id查询对应的信息
	@Override
	public List<CountryEntity> getChildren(Long parentId) {
		return sqlSessionTemplate.selectList(NameSpaceConstant.COUNTRY + ".selectChildren", parentId);
	}
}
