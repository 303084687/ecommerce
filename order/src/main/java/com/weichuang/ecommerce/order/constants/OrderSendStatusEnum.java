package com.weichuang.ecommerce.order.constants;

/**
 * <p>ClassName: OrderSendStatusEnum.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单状态杖举 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年11月7日</p>
 */
public enum OrderSendStatusEnum {

    UNSHIPPING("待发货", 1),  SHIPPING ("待收货", 2), RECEIVE("已收货", 3);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private OrderSendStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (OrderSendStatusEnum c : OrderSendStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static OrderSendStatusEnum getOrderStatus(int index) {
        for (OrderSendStatusEnum c : OrderSendStatusEnum.values()) {
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
