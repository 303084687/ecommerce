package com.weichuang.ecommerce.tenant.constants;

/**
 * Created by licheng on 2016/10/11.
 */
/**
 * @Title:
 * @Package: com.weichuang.ecommerce.tenant.constants
 * @Description:
 * @author: licheng
 * @date: 2016/10/11 13:41
 */
public class NameSpaceConstant {
	public static final String COUNTRY = "sqlmap.tenant.orgi.CountryMapper";
	//用户-角色-菜单开始
	public static final String ADMIN = "sqlmap.tenant.orgi.AdminMapper";
	public static final String ROLE = "sqlmap.tenant.orgi.RoleMapper";
	public static final String MENU = "sqlmap.tenant.orgi.MenuMapper";
	//用户-角色-菜单结束
	public static final String USER_ADDRESS = "sqlmap.tenant.orgi.UserAddressMapper";
	//用户
	public static final String USER = "sqlmap.tenant.orgi.UserMapper";
	//第三方用户登陆表
	public static final String USER_THIRD = "sqlmap.tenant.orgi.UserThirdMapper";
	//用户角色表
	public static final String USER_AGENT_ROLE = "sqlmap.tenant.orgi.UserAgentRoleMapper";
	//代理商
	public static final String AGENT = "sqlmap.tenant.orgi.AgentMapper";
	// 用户角色
	public static final String USER_ROLE = "sqlmap.tenant.orgi.UserRoleMapper";
	// 拉新参数设置表
    public static final String PULL_NEW = "sqlmap.tenant.orgi.PullNewMapper";
    //邀请店员
    public static final String INVITE_SALE_JOIN="com.weichuang.ecommerce.tenant.InviteSaleJoinEntityMapper";

}
