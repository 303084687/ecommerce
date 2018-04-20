package com.weichuang.ecommerce.withdraw.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.DateUtil;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.OrderDetailEntity;
import com.weichuang.ecommerce.order.responsitory.IOrderDetailDao;
import com.weichuang.ecommerce.withdraw.entity.SalesDailyIncomeDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesDailyIncomeSumEntity;
import com.weichuang.ecommerce.withdraw.entity.response.*;
import com.weichuang.ecommerce.withdraw.repository.ISalesIncomeDao;
import com.weichuang.ecommerce.withdraw.service.ISalesIncomeStatisticsService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>ClassName: SalesIncomeStatisticsServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:业务人员收入统计service</p>
 * <p>author jiangkesen</p>
 * <p>2017年11月23日 下午15:10:46</p>
 */
@Service
@SuppressWarnings("all")
public class SalesIncomeStatisticsServiceImpl implements ISalesIncomeStatisticsService {
    // 业务人员收入数据访问层
    @Autowired
    private ISalesIncomeDao salesIncomeDao;

    @Autowired
    private IOrderDetailDao orderDetailDao;


    /**
     * <p>Description: 根据业务人员的id查询某一时间段内每天的预计的可提成的汇总-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param startTime 开始时间 </p>
     * <p>param endTime 结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/23 </p>
     * <p>return SalesDailyIncomeSumListResponse</p>
     */
    @Override
    public SalesDailyIncomeSumListResponse getDailyIncomeSumBySalesId(int saleId, int pageNum, int pageSize) throws ServiceException, Exception {
        String startTime = null;
        String endTime = null;
        if (saleId <= 0) {
            throw new IllegalArgumentException("saleId is not exist");
        }
        if (StringUtils.isNotEmpty(startTime) && !DateUtil.validShortDate(startTime)) {
            throw new IllegalArgumentException("startTime format error");
        }
        if (StringUtils.isNotEmpty(endTime) && !DateUtil.validShortDate(endTime)) {
            throw new IllegalArgumentException("endTime format error");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            endTime = DateUtil.plusDays(endTime, 1);
        }
        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 5;
        }

        SalesDailyIncomeSumListResponse result = new SalesDailyIncomeSumListResponse();
        List<SalesDailyIncomeSumResponse> responseList = new ArrayList<SalesDailyIncomeSumResponse>();
        PageHelper.startPage(pageNum, pageSize);
        List<SalesDailyIncomeSumEntity> sumList = salesIncomeDao.getDailyIncomeSumBySalesId(saleId, startTime, endTime);

        for (SalesDailyIncomeSumEntity item : sumList) {
            SalesDailyIncomeSumResponse response = generatSalesDailyIncomeSumResponse(item);
            responseList.add(response);
        }
        PageInfo pageInfo = new PageInfo(responseList);
        result.setSalesDailyIncomeSumList(responseList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    private SalesDailyIncomeSumResponse generatSalesDailyIncomeSumResponse(SalesDailyIncomeSumEntity entity) {
        SalesDailyIncomeSumResponse result = new SalesDailyIncomeSumResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    /**
     * <p>Description: 根据业务人员的id查询某一时间段内每天的预计的可提成的详细信息-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param startTime 开始时间 </p>
     * <p>param endTime 结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/23 </p>
     * <p>return boolean</p>
     */
    @Override
    public SalesDailyIncomeDetailListResponse getDailyIncomeDetailBySalesId(int saleId) throws ServiceException, Exception {
        String startTime = DateUtil.toShortDateString(new Date());
        String endTime = startTime;
        if (saleId <= 0) {
            throw new IllegalArgumentException("saleId is not exist");
        }
        if (StringUtils.isNotEmpty(startTime) && !DateUtil.validShortDate(startTime)) {
            throw new IllegalArgumentException("startTime format error");
        }
        if (StringUtils.isNotEmpty(endTime) && !DateUtil.validShortDate(endTime)) {
            throw new IllegalArgumentException("endTime format error");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            endTime = DateUtil.plusDays(endTime, 1);
        }
        SalesDailyIncomeDetailListResponse result = new SalesDailyIncomeDetailListResponse();
        List<SalesDailyIncomeDetailResponse> responseList = new ArrayList<SalesDailyIncomeDetailResponse>();
        List<SalesDailyIncomeDetailEntity> sumList = salesIncomeDao.getDailyIncomeDetailBySalesId(saleId, startTime, endTime);
        for (SalesDailyIncomeDetailEntity item : sumList) {
            SalesDailyIncomeDetailResponse response = generatSalesDailyIncomeDetailResponse(item);
            // 查询订单商品信息
            List<OrderDetailEntity> orderDetailList = orderDetailDao.getOrderDetailsByOrderNo(item.getOrderNo());
            List<ShortProductResponse> productShortList = new ArrayList<ShortProductResponse>();
            for (OrderDetailEntity orderDetail : orderDetailList) {
                ShortProductResponse shortProduct = new ShortProductResponse();
                shortProduct.setProductId(orderDetail.getProductId());
                shortProduct.setProductName(orderDetail.getProductName());
                productShortList.add(shortProduct);
            }
            response.setShortProductList(productShortList);
            responseList.add(response);
        }
        result.setSalesDailyIncomeDetailList(responseList);
        return result;
    }

    private SalesDailyIncomeDetailResponse generatSalesDailyIncomeDetailResponse(SalesDailyIncomeDetailEntity entity) {
        SalesDailyIncomeDetailResponse result = new SalesDailyIncomeDetailResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }

    /**
     * <p>Description: 根据业务人员的id查询某一时间段内每天的预计的可提成的详细信息-</p>
     * <p>param orderNo 订单编号 </p>
     * <p>param startTime 开始时间 </p>
     * <p>param endTime 结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/11/23 </p>
     * <p>return boolean</p>
     */
    @Override
    public SalesDailyIncomeDetailListResponse getDailyIncomeDetailBySalesIdDate(int saleId, String oneDay) throws ServiceException, Exception {
        String startTime = oneDay;
        String endTime = oneDay;
        if (saleId <= 0) {
            throw new IllegalArgumentException("saleId is not exist");
        }
        if (StringUtils.isNotEmpty(startTime) && !DateUtil.validShortDate(startTime)) {
            throw new IllegalArgumentException("startTime format error");
        }
        if (StringUtils.isNotEmpty(endTime) && !DateUtil.validShortDate(endTime)) {
            throw new IllegalArgumentException("endTime format error");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            endTime = DateUtil.plusDays(endTime, 1);
        }
        SalesDailyIncomeDetailListResponse result = new SalesDailyIncomeDetailListResponse();
        List<SalesDailyIncomeDetailResponse> responseList = new ArrayList<SalesDailyIncomeDetailResponse>();
        List<SalesDailyIncomeDetailEntity> sumList = salesIncomeDao.getDailyIncomeDetailBySalesId(saleId, startTime, endTime);
        for (SalesDailyIncomeDetailEntity item : sumList) {
            SalesDailyIncomeDetailResponse response = generatSalesDailyIncomeDetailResponse(item);
            // 查询订单商品信息
            List<OrderDetailEntity> orderDetailList = orderDetailDao.getOrderDetailsByOrderNo(item.getOrderNo());
            List<ShortProductResponse> shortProductList = new ArrayList<ShortProductResponse>();
            for (OrderDetailEntity orderDetail : orderDetailList) {
                ShortProductResponse shortProduct = new ShortProductResponse();
                shortProduct.setProductId(orderDetail.getProductId());
                shortProduct.setProductName(orderDetail.getProductName());
                shortProductList.add(shortProduct);
            }
            response.setShortProductList(shortProductList);
            responseList.add(response);
        }
        result.setSalesDailyIncomeDetailList(responseList);
        return result;
    }

}
