package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.MyCustomerEntity;

/**
* <p>ClassName:MyCustomerResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/22 16:02</p>
**/
public class MyCustomerResponse {
    private MyCustomerEntity entity;

    public MyCustomerEntity getEntity() {
        return entity;
    }

    public void setEntity(MyCustomerEntity entity) {
        this.entity = entity;
    }
}
