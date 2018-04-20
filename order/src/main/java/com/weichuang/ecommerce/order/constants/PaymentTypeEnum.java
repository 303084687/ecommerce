package com.weichuang.ecommerce.order.constants;

/**
 * <p>ClassName: OrderSourceEnum.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:支付方式杖举 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年10月14日 上午</p>
 */
public enum PaymentTypeEnum {
    ALIPAY("支付宝", 1), WEIXIN("微信支付", 2), CREDITCARD("信用卡", 3);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private PaymentTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (PaymentTypeEnum c : PaymentTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static PaymentTypeEnum getOrderSource(int index) {
        for (PaymentTypeEnum c : PaymentTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }
}
