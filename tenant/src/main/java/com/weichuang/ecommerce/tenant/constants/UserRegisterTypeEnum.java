package com.weichuang.ecommerce.tenant.constants;

/**
 * <p>ClassName: UserRegisterTypeEnum.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户注册类型杖举值 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月21日 下午14:12:17</p>
 */
public enum UserRegisterTypeEnum {
    WEIXING("微信授权", 1), QQ("QQ授权登陆", 2), ALIPAY("支付宝授", 3);
    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private UserRegisterTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (UserRegisterTypeEnum c : UserRegisterTypeEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static UserRegisterTypeEnum getUserRegisterType(int index) {
        for (UserRegisterTypeEnum c : UserRegisterTypeEnum.values()) {
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
