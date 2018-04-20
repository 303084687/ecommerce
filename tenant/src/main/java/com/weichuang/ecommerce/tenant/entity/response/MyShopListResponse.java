package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.MyShopEntity;

import java.util.List;

/**
* <p>ClassName:MyShopListResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/27 14:06</p>
**/
public class MyShopListResponse {
    private List<MyShopEntity> list;

    public List<MyShopEntity> getList() {
        return list;
    }

    public void setList(List<MyShopEntity> list) {
        this.list = list;
    }
}
