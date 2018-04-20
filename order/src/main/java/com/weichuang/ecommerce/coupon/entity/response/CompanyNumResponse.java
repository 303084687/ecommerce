package com.weichuang.ecommerce.coupon.entity.response;

import java.util.List;

import com.weichuang.ecommerce.coupon.entity.ReferCompany;

/**
 * <p>ClassName: CompanyNumResponse.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:公司统计包含分享优惠券和领取优惠券 </p>
 * <p>author wanggongliang</p>
 * <p>2017年12月22日 上午11:45:05</p>
 */
public class CompanyNumResponse {
    // 今日分享优惠券次数
    private int shareCouponNum;

    // 今日领取优惠券
    private int receiveCouponNum;

    // 公司总计领取次数
    private int companyReceiveTotal;

    // 公司总计分享次数
    private int conpanyShareTotal;

    // 门店和个人的集合
    private List<ReferCompany> list;

    public List<ReferCompany> getList() {
        return list;
    }

    public void setList(List<ReferCompany> list) {
        this.list = list;
    }

    public int getShareCouponNum() {
        return shareCouponNum;
    }

    public void setShareCouponNum(int shareCouponNum) {
        this.shareCouponNum = shareCouponNum;
    }

    public int getReceiveCouponNum() {
        return receiveCouponNum;
    }

    public void setReceiveCouponNum(int receiveCouponNum) {
        this.receiveCouponNum = receiveCouponNum;
    }

    public int getCompanyReceiveTotal() {
        return companyReceiveTotal;
    }

    public void setCompanyReceiveTotal(int companyReceiveTotal) {
        this.companyReceiveTotal = companyReceiveTotal;
    }

    public int getConpanyShareTotal() {
        return conpanyShareTotal;
    }

    public void setConpanyShareTotal(int conpanyShareTotal) {
        this.conpanyShareTotal = conpanyShareTotal;
    }

}
