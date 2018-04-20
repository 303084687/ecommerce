package com.weichuang.ecommerce.tenant.feign;

/**
 * Created by DELL on 2017/1/10.
 */
public enum MailTemplateType {
	ACTIVATE(1, "templateActivateMail.vm"), //激活
	REGISTRATIOIN(2, "templateRegistrationSuccess.vm"), //注册
	RESET(3, "templatePasswordReset.vm"), //忘记和修改密码
	RECOVER(4, "templateRecoverPassword.vm");//意见反馈
	private final int value;
	private final String text;
	
	MailTemplateType(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public int getValue() {
		return value;
	}
	
	public String getText() {
		return text;
	}
	
	public static MailTemplateType valueOf(int value) {
		for (MailTemplateType s : values()) {
			if (s.value == value) {
				return s;
			}
		}
		throw new RuntimeException("Undefined MailTemplateType " + value);
	}
}
