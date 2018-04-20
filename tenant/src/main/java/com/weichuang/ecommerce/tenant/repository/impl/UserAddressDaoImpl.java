package com.weichuang.ecommerce.tenant.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.constants.NameSpaceConstant;
import com.weichuang.ecommerce.tenant.entity.UserAddressEntity;
import com.weichuang.ecommerce.tenant.entity.UserAddressTemplate;
import com.weichuang.ecommerce.tenant.repository.IUserAddressDao;

/**
 * <p>ClassName: UserAddressDaoImpl.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户地址所需要的接口实现 </p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月6日 下午5:59:01</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
@ConfigurationProperties(prefix = "keyencode")
public class UserAddressDaoImpl implements IUserAddressDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    private String DESede;

    public String getDESede() {
        return DESede;
    }

    public void setDESede(String DESede) {
        this.DESede = DESede;
    }

    // 根据用户主键查询所有收货地址
    @Override
    public List<UserAddressTemplate> getUserAddressList(int userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("keyCode", this.getDESede());
        return sqlSessionTemplate.selectList(NameSpaceConstant.USER_ADDRESS + ".getUserAddressList", param);
    }

    // 根据主键查询详细信息
    @Override
    public UserAddressTemplate getUserAddressInfo(int id) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("keyCode", this.getDESede());
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER_ADDRESS + ".getUserAddressInfo", param);
    }

    // 增加收货地址
    @Override
    public int addUserAddress(UserAddressEntity entity) {
        entity.setKeyCode(this.getDESede());
        return sqlSessionTemplate.insert(NameSpaceConstant.USER_ADDRESS + ".addUserAddress", entity);
    }

    // 修改收货地址
    @Override
    public int updateUserAddress(UserAddressEntity entity) {
        entity.setKeyCode(this.getDESede());
        return sqlSessionTemplate.update(NameSpaceConstant.USER_ADDRESS + ".updateUserAddress", entity);
    }

    // 根据主键和用户删除收货地址
    @Override
    public int deleteUserAddress(int id, int userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("userId", userId);
        return sqlSessionTemplate.delete(NameSpaceConstant.USER_ADDRESS + ".deleteUserAddress", param);
    }

    // 根据主键和用户设置默认地址
    @Override
    public int defaultAddress(int id, int userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("id", id);
        param.put("userId", userId);
        return sqlSessionTemplate.update(NameSpaceConstant.USER_ADDRESS + ".defaultAddress", param);
    }
    //根据用户id获取用户默认收货地址
    public UserAddressTemplate getUserAddressInfoDefault(int userId){
        return sqlSessionTemplate.selectOne(NameSpaceConstant.USER_ADDRESS + ".getUserAddressDefault",userId);
    }
}
