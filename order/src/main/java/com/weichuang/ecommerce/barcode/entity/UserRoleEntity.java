package com.weichuang.ecommerce.barcode.entity;
/**
* <p>ClassName:UserRoleEntity</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:用户角色id信息</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/20 11:43</p>
**/
public class UserRoleEntity {
    //用户id
    private int id;
    //角色id
    private int roleId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
