package com.weichuang.ecommerce.barcode.repository;

import com.weichuang.ecommerce.barcode.entity.TenantCodeStoreEntity;
import com.weichuang.ecommerce.barcode.entity.UserCountEntity;
import com.weichuang.ecommerce.barcode.entity.UserRoleEntity;

import java.util.List;

public interface ITenantCodeStoreDao {
    public int addTenantCodeStore(TenantCodeStoreEntity entity);
    public TenantCodeStoreEntity selectByIdTypeStateOne(TenantCodeStoreEntity entity);
    public List<TenantCodeStoreEntity> selectByIdTypeStateList(TenantCodeStoreEntity entity);
    public void updateByPrimaryKeySelective(TenantCodeStoreEntity entity);
    public int selectUserCountByMobile(String mobile);
    public UserRoleEntity selectUserRoleDetailById(int id);
    public UserCountEntity selectInviteCountByUserId(int type,int userId);
}
