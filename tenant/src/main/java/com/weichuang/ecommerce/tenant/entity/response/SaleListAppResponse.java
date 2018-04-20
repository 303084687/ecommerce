package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.SalesAppEntity;
import com.weichuang.ecommerce.tenant.entity.SalesEntity;

import java.util.List;

/**
* <p>ClassName:SaleListAppResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/27 15:33</p>
**/
public class SaleListAppResponse {
    private List<SalesAppEntity> list;

    private int pages;

    private Long total;// 返回的总条数

    public List<SalesAppEntity> getList() {
        return list;
    }

    public void setList(List<SalesAppEntity> list) {
        this.list = list;
    }

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
}
