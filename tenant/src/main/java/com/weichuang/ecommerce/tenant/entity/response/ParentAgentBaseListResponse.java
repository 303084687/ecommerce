package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.ParentAgentBaseEntity;

import java.util.List;
/**
* <p>ClassName:ParentAgentBaseListResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:后台返回列表</p>
* <p>author:liuzhanchao</p>
* <p>2017/11/2 11:12</p>
**/
public class ParentAgentBaseListResponse {
    private List<ParentAgentBaseEntity> list;

    public List<ParentAgentBaseEntity> getList() {
        return list;
    }

    public void setList(List<ParentAgentBaseEntity> list) {
        this.list = list;
    }
}
