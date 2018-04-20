package com.weichuang.ecommerce.withdraw.entity.response;

import java.util.List;

import com.weichuang.ecommerce.withdraw.entity.IncomeSetEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawInfoEntity;

/**
 * <p>ClassName: AgentIncomeListResponse 代理商预计收入列表返回对象</p>
 * <p>Company: 伟业创投(北京)科技有限公司   http://www.weichuangwuxian.cn</p>
 * <p>Description: 用户订单列表</p>
 * <p>author: liuzhanchao</p>
 * <p>date: 2017/12/26 15:11 </p>
 */
public class IncomeSetListResponse {

    

    // 提现列表
    private List<IncomeSetEntity> list = null;

    public List<IncomeSetEntity> getList() {
        return list;
    }

    public void setList(List<IncomeSetEntity> list) {
        this.list = list;
    }

    
}
