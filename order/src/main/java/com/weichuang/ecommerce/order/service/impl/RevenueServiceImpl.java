package com.weichuang.ecommerce.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.PageListResult;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.responsitory.IAdminOrderDao;
import com.weichuang.ecommerce.order.service.IRevenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("revenueService")
public class RevenueServiceImpl implements IRevenueService{

    @Autowired
    private IAdminOrderDao adminOrderDao;

    @Override
    public PageListResult<OrderEntity> orderList(String startTime, String endTime, int status, int pageNum, int pageSize) throws ServiceException {
        PageHelper.startPage(pageNum, pageSize);
        List<OrderEntity> list = adminOrderDao.orderList(startTime,endTime,status);
        PageInfo<OrderEntity> pageInfo = new PageInfo<OrderEntity>(list);
        return new PageListResult<OrderEntity>(list,pageInfo.getTotal(),pageInfo.getPages(),pageNum,pageSize);
    }

}
