package com.weichuang.ecommerce.appVersion.entity.response;

import java.util.List;

import com.weichuang.ecommerce.appVersion.entity.AppVersionEntity;

/**
 * <p>ClassName: AppVersionListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:app版本后台列表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月22日 下午1:56:42</p>
 */
public class AppVersionListResponse {
    private List<AppVersionEntity> list;

    private int pages;// 总页数

    private Long total;// 返回的总条数

    public List<AppVersionEntity> getList() {
        return list;
    }

    public void setList(List<AppVersionEntity> list) {
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
