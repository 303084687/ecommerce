package com.weichuang.ecommerce.withdraw.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.DateUtil;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.order.entity.OrderDetailEntity;
import com.weichuang.ecommerce.order.responsitory.IOrderDetailDao;
import com.weichuang.ecommerce.withdraw.entity.*;
import com.weichuang.ecommerce.withdraw.entity.response.*;

import com.weichuang.ecommerce.withdraw.repository.IAgentIncomeDao;
import com.weichuang.ecommerce.withdraw.repository.IAgentWithdrawDao;
import com.weichuang.ecommerce.withdraw.repository.ISalesIncomeDao;
import com.weichuang.ecommerce.withdraw.repository.ISalesWithdrawDao;
import com.weichuang.ecommerce.withdraw.service.IAgentIncomeService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>ClassName: AgentIncomeServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:代理预计收入service</p>
 * <p>author jiangkesen</p>
 * <p>2017年10月08日 下午15:43:46</p>
 */
@Service
@SuppressWarnings("all")
public class AgentIncomeServiceImpl implements IAgentIncomeService {
    // 代理预计收入数据访问层
    @Autowired
    private IAgentIncomeDao agentIncomeDao;

    @Autowired
    private IOrderDetailDao orderDetailDao;

    /**
     * <p>Description: 根据条件查询代理商预计收入列表</p>
     * <p>param agentId 代理商Id </p>
     * <p>param startTime 创建开始时间 </p>
     * <p>param endTime 创建结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/10/08 15:00 </p>
     * <p>return List<AgentIncomeEntity></p>
     */
    @Override
    public AgentIncomeListResponse getAgentIncomeList(int agentId, Date startTime, Date endTime, int pageNum, int pageSize) {
        if (agentId <= 0) {
            throw new IllegalArgumentException("agentId is illegal");
        }

        if (pageNum == 0) {
            pageNum = 1;
        }
        if (pageSize == 0) {
            pageSize = 5;
        }
        AgentIncomeListResponse result = new AgentIncomeListResponse();
        // 返回结果
        List<AgentIncomeResponse> agentIncomeList = new ArrayList<AgentIncomeResponse>();

        // 查询预计收入列表
        //执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<AgentIncomeEntity> agentIncomEntityList = agentIncomeDao.getAgentIncomeList(agentId, startTime, endTime);
        for (AgentIncomeEntity item : agentIncomEntityList) {
            //添加结果
            agentIncomeList.add(this.getAgentIncomeResponse(item));
        }
        //设置返回结果
        result.setOrderList(agentIncomeList);
        PageInfo pageInfo = new PageInfo(agentIncomEntityList);
        result.setPages(pageInfo.getPages());

        return result;
    }

    // 构造AgentIncomeResponse对象
    private AgentIncomeResponse getAgentIncomeResponse(AgentIncomeEntity entity) {
        AgentIncomeResponse result = new AgentIncomeResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }




    /**
     * @param parentAgentId 代理/销售主键
     * @return Response<SalesDailyIncomeSumListResponse>
     * @throws Exception
     * @Title:getDailyIncomeSumBySalesId
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Description:根据公司的id查询所有提成的汇总（按天显示）
     */
    @Override
    public ParentAgentDailyIncomeSumListResponse getParentAgentDailyIncomeSumByParentAgentId(int parentAgentId, int pageNum, int pageSize) throws ServiceException {
        String startTime = null;
        String endTime = null;
        if (parentAgentId <= 0) {
            throw new IllegalArgumentException("parentAgentId is not exist");
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

        ParentAgentDailyIncomeSumListResponse result = new ParentAgentDailyIncomeSumListResponse();
        //List<SalesDailyIncomeSumResponse> responseList = new ArrayList<SalesDailyIncomeSumResponse>();
        PageHelper.startPage(pageNum, pageSize);
        List<ParentAgentDailyIncomeSumEntity> sumList = agentIncomeDao.getParentAgentDailyIncomeSumByParentAgentId(parentAgentId, startTime, endTime);

        /*for (SalesDailyIncomeSumEntity item : sumList) {
            SalesDailyIncomeSumResponse response = generatSalesDailyIncomeSumResponse(item);
            responseList.add(response);
        }*/
        PageInfo pageInfo = new PageInfo(sumList);
        result.setParentAgentDailyIncomeSumlList(sumList);
        result.setPages(pageInfo.getPages());
        result.setTotal(pageInfo.getTotal());
        return result;
    }

   /* private SalesDailyIncomeSumResponse generatSalesDailyIncomeSumResponse(SalesDailyIncomeSumEntity entity) {
        SalesDailyIncomeSumResponse result = new SalesDailyIncomeSumResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }*/

    /**
     * @param agentId 代理/销售主键

     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据门店的id查询当天的预计的可提成的详细信息
     */
    @Override
    public AgentDailyIncomeDetailListResponse getAgentDailyIncomeDetailByAgentId(int agentId, int pageNum, int pageSize) throws ServiceException {
        String startTime = DateUtil.toShortDateString(new Date());
        String endTime = startTime;
        if (agentId <= 0) {
            throw new IllegalArgumentException("agentId is not exist");
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
        AgentDailyIncomeDetailListResponse result = new AgentDailyIncomeDetailListResponse();
        //List<SalesDailyIncomeDetailResponse> responseList = new ArrayList<SalesDailyIncomeDetailResponse>();
        PageHelper.startPage(pageNum, pageSize);
        List<AgentDailyIncomeDetailEntity> sumList = agentIncomeDao.getAgentDailyIncomeDetailByAgentId(agentId, startTime, endTime);
        for (AgentDailyIncomeDetailEntity item : sumList) {
            //SalesDailyIncomeDetailResponse response = generatSalesDailyIncomeDetailResponse(item);
            // 查询订单商品信息
            List<OrderDetailEntity> orderDetailList = orderDetailDao.getOrderDetailsByOrderNo(item.getOrderNo());
            List<ShortProductResponse> productShortList = new ArrayList<ShortProductResponse>();
            for (OrderDetailEntity orderDetail : orderDetailList) {
                ShortProductResponse shortProduct = new ShortProductResponse();
                shortProduct.setProductId(orderDetail.getProductId());
                shortProduct.setProductName(orderDetail.getProductName());
                productShortList.add(shortProduct);
            }
            item.setShortProductList(productShortList);

        }
        PageInfo pageInfo = new PageInfo(sumList);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        result.setAgentDailyIncomeDetailList(sumList);
        return result;
    }

   /* private SalesDailyIncomeDetailResponse generatSalesDailyIncomeDetailResponse(SalesDailyIncomeDetailEntity entity) {
        SalesDailyIncomeDetailResponse result = new SalesDailyIncomeDetailResponse();
        BeanUtils.copyProperties(entity, result);
        return result;
    }*/

    /**
     * @param agentId    代理/销售主键
     * @param oneDay 开始时间
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据门店的id查询某一天的预计的可提成的详细信息
     */
    @Override
    public AgentDailyIncomeDetailListResponse getAgentDailyIncomeDetailByAgentIdDate(int agentId,String oneDay,int pageNum, int pageSize) throws ServiceException
    {
        String startTime = oneDay;
        String endTime = oneDay;
        if (agentId <= 0) {
            throw new IllegalArgumentException("agentId is not exist");
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
        AgentDailyIncomeDetailListResponse result = new AgentDailyIncomeDetailListResponse();
        //List<SalesDailyIncomeDetailResponse> responseList = new ArrayList<SalesDailyIncomeDetailResponse>();
        PageHelper.startPage(pageNum, pageSize);
        List<AgentDailyIncomeDetailEntity> sumList = agentIncomeDao.getAgentDailyIncomeDetailByAgentId(agentId, startTime, endTime);
        for (AgentDailyIncomeDetailEntity item : sumList) {
            //SalesDailyIncomeDetailResponse response = generatSalesDailyIncomeDetailResponse(item);
            // 查询订单商品信息
            List<OrderDetailEntity> orderDetailList = orderDetailDao.getOrderDetailsByOrderNo(item.getOrderNo());
            List<ShortProductResponse> productShortList = new ArrayList<ShortProductResponse>();
            for (OrderDetailEntity orderDetail : orderDetailList) {
                ShortProductResponse shortProduct = new ShortProductResponse();
                shortProduct.setProductId(orderDetail.getProductId());
                shortProduct.setProductName(orderDetail.getProductName());
                productShortList.add(shortProduct);
            }
            item.setShortProductList(productShortList);

        }
        PageInfo pageInfo = new PageInfo(sumList);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        result.setAgentDailyIncomeDetailList(sumList);
        return result;
    }


    /**
     * @param parentAgentId 代理/销售主键
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Description:根据公司的id查询当天的预计的可提成的详细信息（按门店显示）
     */
    @Override
    public ParentAgentDailyIncomeListResponse getParentAgentDailyIncomeListByParentAgentId(int parentAgentId, int pageNum, int pageSize) throws ServiceException{
        String startTime = DateUtil.toShortDateString(new Date());
        String endTime = startTime;
        if (parentAgentId <= 0) {
            throw new IllegalArgumentException("parentAgentId is not exist");
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
        ParentAgentDailyIncomeListResponse result = new ParentAgentDailyIncomeListResponse();
        //List<SalesDailyIncomeDetailResponse> responseList = new ArrayList<SalesDailyIncomeDetailResponse>();
        PageHelper.startPage(pageNum, pageSize);
        List<ParentAgentDailyIncomeEntity> sumList = agentIncomeDao.getParentAgentDailyIncomeListByParentAgentId(parentAgentId, startTime, endTime);

        PageInfo pageInfo = new PageInfo(sumList);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        result.setParentAgentDailyIncomeList(sumList);
        return result;
    }
    /**
     * @param parentAgentId 代理/销售主键
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据公司的id查询某天的预计的可提成的详细信息（按门店显示）
     */
    @Override
    public ParentAgentDailyIncomeListResponse getParentAgentDailyIncomeListByParentAgentIdDate(int parentAgentId, String oneDay, int pageNum, int pageSize)throws ServiceException{
        String startTime = oneDay;
        String endTime = oneDay;
        if (parentAgentId <= 0) {
            throw new IllegalArgumentException("parentAgentId is not exist");
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
        ParentAgentDailyIncomeListResponse result = new ParentAgentDailyIncomeListResponse();
        //List<SalesDailyIncomeDetailResponse> responseList = new ArrayList<SalesDailyIncomeDetailResponse>();
        PageHelper.startPage(pageNum, pageSize);
        List<ParentAgentDailyIncomeEntity> sumList = agentIncomeDao.getParentAgentDailyIncomeListByParentAgentId(parentAgentId, startTime, endTime);

        PageInfo pageInfo = new PageInfo(sumList);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        result.setParentAgentDailyIncomeList(sumList);
        return result;
    }
}
