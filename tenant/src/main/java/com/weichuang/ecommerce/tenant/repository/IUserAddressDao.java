package com.weichuang.ecommerce.tenant.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.tenant.entity.UserAddressEntity;
import com.weichuang.ecommerce.tenant.entity.UserAddressTemplate;

/**
 * <p>ClassName: IUserAddressDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:收货地址 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月21日 下午5:59:41</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IUserAddressDao {
    // 根据用户主键查询所有收货地址
    public List<UserAddressTemplate> getUserAddressList(int userId);

    // 根据主键查询详细信息
    public UserAddressTemplate getUserAddressInfo(int id);

    // 增加收货地址
    public int addUserAddress(UserAddressEntity entity);

    // 修改收货地址
    public int updateUserAddress(UserAddressEntity entity);

    // 根据主键删除收货地址
    public int deleteUserAddress(int id, int userId);

    // 根据主键和用户设置默认地址
    public int defaultAddress(int id, int userId);

    //根据用户id获取用户默认收货地址
    public UserAddressTemplate getUserAddressInfoDefault(int userId);
}
