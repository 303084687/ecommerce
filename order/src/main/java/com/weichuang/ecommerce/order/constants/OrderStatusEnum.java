package com.weichuang.ecommerce.order.constants;

/**
 * <p>ClassName: OrderStatusEnum.java</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description:订单状态杖举 </p>
 * <p>author: jiangkesen</p>
 * <p>2017年9月26日 上午11:27:17</p>
 */
public enum OrderStatusEnum {

    UNPAYMENT("待付款", 1),  SHIPPING ("配送中", 2),  COMPLETED("已完成", 3), CLOSED("已关闭", 4), REFUNDING("退款中", 5);

    // 成员变量
    private String name;
    private int index;

    // 构造方法
    private OrderStatusEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    // 根据杖值获取相应的名称
    public static String getName(int index) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // 根据杖值获取相应的杖举值
    public static OrderStatusEnum getOrderStatus(int index) {
        for (OrderStatusEnum c : OrderStatusEnum.values()) {
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
