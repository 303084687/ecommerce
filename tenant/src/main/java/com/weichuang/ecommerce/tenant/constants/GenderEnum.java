package com.weichuang.ecommerce.tenant.constants;

/**
 * <p>ClassName: GenderEnum.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户性别</p>
 * <p>author: jiangkesen</p>
 * <p>2017年10月11日 14:59:37</p>
 */
public enum GenderEnum {
    MALE(1, "男"), FEMALE(2, "女"), UNKNOW(0, "未知");
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private GenderEnum(int index, String name) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (GenderEnum c : GenderEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static GenderEnum getGenderType(int index) {
        for (GenderEnum c : GenderEnum.values()) {
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
