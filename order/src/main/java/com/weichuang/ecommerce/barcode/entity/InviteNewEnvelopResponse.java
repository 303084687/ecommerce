package com.weichuang.ecommerce.barcode.entity;

import java.util.List;

public class InviteNewEnvelopResponse {
    private int salesPullNewNum;//已邀新个数
    private int salesDifferenceNum;//差多少人得红包
    private double salesGetRedEnvelop;//差得到的红包
    private List<SalesPullNewSetEntity> setList;//红包设置集合

    public int getSalesPullNewNum() {
        return salesPullNewNum;
    }

    public void setSalesPullNewNum(int salesPullNewNum) {
        this.salesPullNewNum = salesPullNewNum;
    }

    public int getSalesDifferenceNum() {
        return salesDifferenceNum;
    }

    public void setSalesDifferenceNum(int salesDifferenceNum) {
        this.salesDifferenceNum = salesDifferenceNum;
    }

    public double getSalesGetRedEnvelop() {
        return salesGetRedEnvelop;
    }

    public void setSalesGetRedEnvelop(double salesGetRedEnvelop) {
        this.salesGetRedEnvelop = salesGetRedEnvelop;
    }

    public List<SalesPullNewSetEntity> getSetList() {
        return setList;
    }

    public void setSetList(List<SalesPullNewSetEntity> setList) {
        this.setList = setList;
    }
}
