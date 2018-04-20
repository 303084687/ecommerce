package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.AgentEntity;
import com.weichuang.ecommerce.tenant.entity.SalesPullNewSetEntity;

import java.util.List;
/**
* <p>ClassName:AgentListResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:后台返回列表</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/2 11:12</p>
**/
public class SalesPullNewSetListResponse {
    private List<SalesPullNewSetEntity> list;

    public List<SalesPullNewSetEntity> getList() {
        return list;
    }

    public void setList(List<SalesPullNewSetEntity> list) {
        this.list = list;
    }
}
