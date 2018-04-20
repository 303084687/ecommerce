package com.weichuang.ecommerce.withdraw.service;

import com.weichuang.commons.PageListResult;
import com.weichuang.commons.ServiceException;

import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawEntity;
public interface IAdminWithdrawService {

public PageListResult<SalesWithdrawEntity> salesWithdrawList(int salesId, int status,String keyWords, int pageNum, int pageSize)throws ServiceException;


    public PageListResult<AgentWithdrawEntity> agentWithdrawList(int agentId, int status, String keyWords, int pageNum, int pageSize) throws ServiceException;


}
