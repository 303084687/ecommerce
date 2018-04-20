package com.weichuang.ecommerce.barcode.entity.request;

import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;

/**
 * <p>ClassName:CodeAndTextRequest</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:二维码及文本请求类</p>
 * <p>author:zhanghongsheng</p>
 * <p>2017/10/11 13:51</p>
 **/

public class CodeAndTextRequest {


    private String codeUrl; //二维码存储的url信息


    private int codeImageSize;//二维码大小


    private int bgImageMarginTop = 0;//文字和二维码间距,默认0


    private String text;//文本文字


    private String textFontName = "宋体";//文本的字体，默认宋体


    private int textFontSize = 12;//文本文字大小，默认12


    private boolean textIsBold = false;//文本字体是否加粗，默认不加粗


    private String textColor = "red"; //文本颜色,取java.awt.Color的静态属性名，默认红色

    public int getCodeImageSize() {
        return codeImageSize;
    }

    public void setCodeImageSize(int codeImageSize) {
        this.codeImageSize = codeImageSize;
    }

    public int getBgImageMarginTop() {
        return bgImageMarginTop;
    }

    public void setBgImageMarginTop(int bgImageMarginTop) {
        this.bgImageMarginTop = bgImageMarginTop;
    }

    public boolean isTextIsBold() {
        return textIsBold;
    }

    public void setTextIsBold(boolean textIsBold) {
        this.textIsBold = textIsBold;
    }


    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextFontName() {
        return textFontName;
    }

    public void setTextFontName(String textFontName) {
        this.textFontName = textFontName;
    }

    public int getTextFontSize() {
        return textFontSize;
    }

    public void setTextFontSize(int textFontSize) {
        this.textFontSize = textFontSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }


}
