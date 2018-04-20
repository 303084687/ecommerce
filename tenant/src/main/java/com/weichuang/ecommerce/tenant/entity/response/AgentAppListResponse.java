package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.AgentEntity;

import java.util.List;

/**
* <p>ClassName:AgentAppListResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/27 16:17</p>
**/
public class AgentAppListResponse {
    private int pages;
    private Long total;
    private List<AgentEntity> list;

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<AgentEntity> getList() {
        return list;
    }

    public void setList(List<AgentEntity> list) {
        this.list = list;
    }
}
