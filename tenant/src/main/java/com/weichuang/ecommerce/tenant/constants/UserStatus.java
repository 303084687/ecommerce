package com.weichuang.ecommerce.tenant.constants;

/**
 * <p>ClassName: UserStatus.java</p>
 * <p>Company: 指点无限(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户状态</p>
 * <p>author: wanggongliang</p>
 * <p>2016年12月2日 上午11:43:37</p>
 */
public enum UserStatus {
	NOT_ACTIVE(1, "未激活"), ENABLE(2, "启用"), DISABLE(3, "禁用");
	private final int value;
	private final String text;
	
	UserStatus(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
	
	public static UserStatus valueOf(int value) {
		for (UserStatus s : values()) {
			if (s.value == value) {
				return s;
			}
		}
		throw new RuntimeException("Undefined OrderType " + value);
	}
}
