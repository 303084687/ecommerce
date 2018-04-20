package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.AgentCouponEntity;

import java.util.List;

/**
* <p>ClassName:AgentCouponEntityResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/23 14:20</p>
**/
public class AgentCouponEntityResponse {
    List<AgentCouponEntity> list;

    public List<AgentCouponEntity> getList() {
        return list;
    }

    public void setList(List<AgentCouponEntity> list) {
        this.list = list;
    }
}
