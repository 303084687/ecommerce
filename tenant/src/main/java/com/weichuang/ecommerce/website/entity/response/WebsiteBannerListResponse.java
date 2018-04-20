package com.weichuang.ecommerce.website.entity.response;

import java.util.List;

import com.weichuang.ecommerce.website.entity.WebsiteBannerEntity;

/**
 * <p>ClassName: WebsiteBannerListResponse.java</p>
 * <p>Company: 指点无限(北京)科技有限公司 </p>
 * <p>Description:列表输出 </p>
 * <p>author: wanggongliang</p>
 * <p>2017年4月7日 下午4:20:20</p>
 */
public class WebsiteBannerListResponse {
    private List<WebsiteBannerEntity> list;

    private int pages;// 总页数

    public List<WebsiteBannerEntity> getList() {
        return list;
    }

    public void setList(List<WebsiteBannerEntity> list) {
        this.list = list;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
