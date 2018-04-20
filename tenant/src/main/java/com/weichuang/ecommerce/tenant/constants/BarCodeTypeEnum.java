package com.weichuang.ecommerce.tenant.constants;
/**
* <p>ClassName:BarCodeTypeEnum</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:二维码类型</p>
* <p>author:zhanghongsheng</p>
* <p>2017/11/21 16:13</p>
**/
public enum BarCodeTypeEnum {
    shop("门店", 1), company("公司", 2);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private BarCodeTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (BarCodeTypeEnum c : BarCodeTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static BarCodeTypeEnum getBarCodeType(int index) {
        for (BarCodeTypeEnum c : BarCodeTypeEnum.values()) {
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
