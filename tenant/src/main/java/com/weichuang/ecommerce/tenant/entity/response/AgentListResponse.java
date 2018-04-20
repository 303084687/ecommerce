package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.AgentEntity;

import java.util.List;
/**
* <p>ClassName:AgentListResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:后台返回列表</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/2 11:12</p>
**/
public class AgentListResponse {
    private List<AgentEntity> list;

    public List<AgentEntity> getList() {
        return list;
    }

    public void setList(List<AgentEntity> list) {
        this.list = list;
    }
}
