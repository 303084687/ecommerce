package com.weichuang.ecommerce.tenant.constants;

public class RedisStatus {
	public static final String TYPE_REGISTER = "register"; // 注册
	public static final String TYPE_BIND = "bind"; //绑定账号
	public static final String TYPE_LOGIN = "login"; // 登录
	public static final String TYPE_IMMEREX_TOKEN = "immerexToken"; // 设备登录token
	public static final Long USER_LOGIN_TIME = 15 * 24 * 60 * 60L; // 15天后超时用户登录时间
	public static final Long USER_REGISTER_TIME = 60 * 60L; // 1小时后超时用户注册
	public static final Long USER_IMMEREX_TIME = 24 * 60 * 60L;//1天后超时 设备登录
}
