package com.weichuang.ecommerce.coupon.entity.response;

import java.util.List;

import com.weichuang.ecommerce.coupon.entity.CouponType;

/**
 * <p>ClassName: CouponTypeListResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券类型返回 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午3:38:13</p>
 */
public class CouponTypeListResponse {
    private List<CouponType> list;// 返回的集合

    private int pages;// 总页数

    private Long total;// 总条数

    public List<CouponType> getList() {
        return list;
    }

    public void setList(List<CouponType> list) {
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
