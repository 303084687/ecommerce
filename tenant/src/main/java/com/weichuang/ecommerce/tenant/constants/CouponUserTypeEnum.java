package com.weichuang.ecommerce.tenant.constants;
/**
* <p>ClassName:CouponUserTypeEnum</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2017/12/13 15:12</p>
**/
public enum CouponUserTypeEnum {
    USER_THIRTYDAY_IN("注册30天内会员", 1), USER_THIRTYDAY_GT("注册大于30天的会员", 2), SALES_ALL("所有销售", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private CouponUserTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (CouponUserTypeEnum c : CouponUserTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static CouponUserTypeEnum getBarCodeType(int index) {
        for (CouponUserTypeEnum c : CouponUserTypeEnum.values()) {
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
