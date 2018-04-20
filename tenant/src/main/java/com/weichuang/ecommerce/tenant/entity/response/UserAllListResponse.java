package com.weichuang.ecommerce.tenant.entity.response;

import com.weichuang.ecommerce.tenant.entity.UserAllEntity;

import java.util.List;
/**
* <p>ClassName:UserAllListResponse</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:全部用户查询</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/2 14:53</p>
**/
public class UserAllListResponse {
    private List<UserAllEntity> list;
    private Long total;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    private int pages;





    public List<UserAllEntity> getList() {
        return list;
    }

    public void setList(List<UserAllEntity> list) {
        this.list = list;
    }
}
