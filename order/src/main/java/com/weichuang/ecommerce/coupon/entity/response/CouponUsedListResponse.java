package com.weichuang.ecommerce.coupon.entity.response;

import java.util.List;

/**
 * <p>ClassName: CouponListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券可用和不可用列表返回集合 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午3:32:53</p>
 */
public class CouponUsedListResponse {
    private List<CouponUsedEntity> list;// 返回的集合

    private int pages;// 总页数

    private Long total;// 总的条数

    public List<CouponUsedEntity> getList() {
        return list;
    }

    public void setList(List<CouponUsedEntity> list) {
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
