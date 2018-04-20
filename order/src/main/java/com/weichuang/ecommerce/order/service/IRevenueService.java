package com.weichuang.ecommerce.order.service;

import com.weichuang.commons.PageListResult;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.OrderEntity;

public interface IRevenueService {

    /**
     * 列表
     * @param startTime
     * @param endTime
     * @param status
     * @param pageNum
     * @param pageSize
     * @return
     * @throws ServiceException
     */
    public PageListResult<OrderEntity> orderList(String startTime,String endTime, int status, int pageNum, int pageSize) throws ServiceException;


}
