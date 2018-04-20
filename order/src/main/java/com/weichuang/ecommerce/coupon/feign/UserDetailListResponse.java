package com.weichuang.ecommerce.coupon.feign;

import java.util.List;

/**
 *<p>Description:</p>
 *<p>param null:</p>
 *<p>author:zhanghongsheng</p>
 *<p>date:2017/11/29 13:35</p>
 *<p>return:</p>
 *<p>throws: </p>
 */
public class UserDetailListResponse {

    private List<UserDetailEntity> list;

    public List<UserDetailEntity> getList() {
        return list;
    }

    public void setList(List<UserDetailEntity> list) {
        this.list = list;
    }

}
