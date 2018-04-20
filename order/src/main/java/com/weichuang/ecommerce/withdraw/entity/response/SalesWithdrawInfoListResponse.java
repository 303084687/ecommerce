package com.weichuang.ecommerce.withdraw.entity.response;

import java.util.List;

import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawInfoEntity;

/**
 * <p>ClassName: AgentIncomeListResponse 代理商预计收入列表返回对象</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户订单列表</p>
 * <p>author: jiangkesen</p>
 * <p>date: 2017/10/08 15:11 </p>
 */
public class SalesWithdrawInfoListResponse {

    

    // 提现列表
    private List<SalesWithdrawInfoEntity> list = null;

    public List<SalesWithdrawInfoEntity> getList() {
        return list;
    }

    public void setList(List<SalesWithdrawInfoEntity> list) {
        this.list = list;
    }

    
}
