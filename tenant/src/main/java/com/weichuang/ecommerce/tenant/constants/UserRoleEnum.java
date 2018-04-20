package com.weichuang.ecommerce.tenant.constants;

/**
 * <p>ClassName: UserRoleEnum.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:用户角色杖举值 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月21日 下午14:12:17</p>
 */
public enum UserRoleEnum {

    USER("普通用户", 1),
    RECEPTIONIST("前台", 2),
    MEMBERSHIP("会藉", 3),
    PERSONAL_TRAINER("私人教练", 4),
    EMPLOYEE("健身房员工", 5),
    MANAGER("健身房管理员", 6);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private UserRoleEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (UserRoleEnum c : UserRoleEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static UserRoleEnum getUserRole(int index) {
        for (UserRoleEnum c : UserRoleEnum.values()) {
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
