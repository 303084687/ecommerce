package com.weichuang.ecommerce.withdraw.entity.response;

import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawInfoEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawInfoEntity;

import java.util.List;

/**
 * <p>ClassName: AgentIncomeListResponse 代理商预计收入列表返回对象</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户订单列表</p>
 * <p>author: liuzhanchao</p>
 * <p>date: 2017/12/28 14:11 </p>
 */
public class AgentWithdrawInfoListResponse {

    

    // 提现列表
    private List<AgentWithdrawInfoEntity> list = null;

    public List<AgentWithdrawInfoEntity> getList() {
        return list;
    }

    public void setList(List<AgentWithdrawInfoEntity> list) {
        this.list = list;
    }

    
}
