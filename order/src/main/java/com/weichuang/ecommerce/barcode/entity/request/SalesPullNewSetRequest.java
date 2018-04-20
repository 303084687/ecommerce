package com.weichuang.ecommerce.barcode.entity.request;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import java.awt.*;
import java.util.Date;

/**
 * <p>ClassName:SalesPullNewSetRequest</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:红包参数设置类</p>
 * <p>author:liuzhanchao</p>
 * <p>2017/12/26 21:51</p>
 **/

public class SalesPullNewSetRequest {


    private int id;
    private int aid;
    //人数
    private int pullNewNum;

    // 收益
    private double pullNewIncome;

    private int operatorId;

    private int state;


    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPullNewNum() {
        return pullNewNum;
    }

    public void setPullNewNum(int pullNewNum) {
        this.pullNewNum = pullNewNum;
    }

    public double getPullNewIncome() {
        return pullNewIncome;
    }

    public void setPullNewIncome(double pullNewIncome) {
        this.pullNewIncome = pullNewIncome;
    }


}
