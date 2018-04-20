package com.weichuang.ecommerce.pay.dao;

import com.weichuang.ecommerce.pay.entity.AgentEntity;

public interface IAgentDao {
    public AgentEntity selectBySalesId(int salesid);
}
