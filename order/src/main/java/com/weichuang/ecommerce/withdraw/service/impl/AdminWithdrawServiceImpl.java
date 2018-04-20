package com.weichuang.ecommerce.withdraw.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.PageListResult;
import com.weichuang.commons.ServiceException;

import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawEntity;
import com.weichuang.ecommerce.withdraw.repository.IAgentWithdrawDao;

import com.weichuang.ecommerce.withdraw.repository.ISalesWithdrawDao;
import com.weichuang.ecommerce.withdraw.service.IAdminWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@SuppressWarnings("all")
public class AdminWithdrawServiceImpl implements IAdminWithdrawService{

    @Autowired
    private ISalesWithdrawDao salesWithdrawDao;


    @Autowired
    private IAgentWithdrawDao agentWithdrawDao;

    @Override
    @Transactional
    public PageListResult<SalesWithdrawEntity> salesWithdrawList(int salesId, int status,String keyWords, int pageNum, int pageSize) throws ServiceException {
        PageHelper.startPage(pageNum, pageSize);
        List<SalesWithdrawEntity> list = salesWithdrawDao.withdrawList(salesId,status,keyWords);

        PageInfo<SalesWithdrawEntity> pageInfo = new PageInfo<SalesWithdrawEntity>(list);
        return new PageListResult<SalesWithdrawEntity>(list,pageInfo.getTotal(),pageInfo.getPages(),pageNum,pageSize);

    }


    @Override
    @Transactional
    public PageListResult<AgentWithdrawEntity> agentWithdrawList(int agentId, int status,String keyWords, int pageNum, int pageSize) throws ServiceException {
        PageHelper.startPage(pageNum, pageSize);
        List<AgentWithdrawEntity> list = agentWithdrawDao.withdrawList(agentId,status,keyWords);
        PageInfo<AgentWithdrawEntity> pageInfo = new PageInfo<AgentWithdrawEntity>(list);
        return new PageListResult<AgentWithdrawEntity>(list,pageInfo.getTotal(),pageInfo.getPages(),pageNum,pageSize);

    }

}
