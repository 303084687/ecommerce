package com.weichuang.ecommerce.barcode.entity.response;

import com.weichuang.ecommerce.barcode.entity.InviteListEntity;

import java.util.List;

/**
* <p>ClassName:InviteListResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/22 10:22</p>
**/
public class InviteListResponse {
    private List<InviteListEntity> list;
    private Integer total;//总记录数
    private Integer pages;//总页数

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public List<InviteListEntity> getList() {
        return list;
    }

    public void setList(List<InviteListEntity> list) {
        this.list = list;
    }
}
