package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.MyShopEntity;

/**
* <p>ClassName:MyShopInfoResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/27 13:51</p>
**/
public class MyShopInfoResponse {
    private MyShopEntity entity;

    public MyShopEntity getEntity() {
        return entity;
    }

    public void setEntity(MyShopEntity entity) {
        this.entity = entity;
    }
}
