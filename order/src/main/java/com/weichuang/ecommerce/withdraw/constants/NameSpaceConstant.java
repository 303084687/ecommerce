package com.weichuang.ecommerce.withdraw.constants;

/**
 * @Title:
 * @Package: com.weichuang.ecommerce.withdraw.constants
 * @Description:
 * @author: jiangkesen
 * @date: 2017/09/14 13:41
 */
public class NameSpaceConstant {
    // 业务人员预计收入记录
    public static final String SALES_INCOME = "sqlmap.withdraw.orgi.SalesIncomeMapper";

    // 业务人员提现记录
    public static final String SALES_WITHDRAW = "sqlmap.withdraw.orgi.SalesWithdrawMapper";

    // 代理预计收入记录
    public static final String AGENT_INCOME = "sqlmap.withdraw.orgi.AgentIncomeMapper";

    // 代理人员提现记录
    public static final String AGENT_WITHDRAW = "sqlmap.withdraw.orgi.AgentWithdrawMapper";
    
 // 业务人员提现详情
    public static final String SALES_WITHDRAW_DETAIL = "sqlmap.order.orgi.SalesWithdrawDetailMapper";
    //代理提现详情

    public static final String AGENT_WITHDRAW_DETAIL = "sqlmap.order.orgi.AgentWithdrawDetailMapper";
    // 提成参数设置
    public static final String INCOME_SET = "sqlmap.withdraw.orgi.IncomeSetMapper";

    

}
