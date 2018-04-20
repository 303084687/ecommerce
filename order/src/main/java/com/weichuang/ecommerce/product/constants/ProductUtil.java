package com.weichuang.ecommerce.product.constants;

public class ProductUtil {
    // 商品code生成规则,按照商品分类生成,单品还是套餐
    public static String productCode(int type) {
        return "XYN" + type + codeNumber();
    }

    // 随机生成4位数字
    public static int codeNumber() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }

    // 生成微信支付订单号
    public static String weixinId() {
        return "XYNM" + (int) ((Math.random() * 9 + 1) * 100000);
    }
}
