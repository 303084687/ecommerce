package com.weichuang.ecommerce.withdraw.repository;

import java.math.BigDecimal;
import java.util.List;

import com.weichuang.commons.PageListResult;
import com.weichuang.ecommerce.withdraw.entity.request.WithdrawEntity;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawInfoEntity;

/**
 * <p>ClassName: ISalesWithdrawDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:业务人员提现 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午4:04:28</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface ISalesWithdrawDao {
    /**
     * @Title:addSaleCash  
     * @Description:增加一条待提现记录
     * @param entity
     * @return
     */
    public int addSaleCash(SalesWithdrawEntity entity);

    /**
     * @Title:updateSaleCash  
     * @Description:更新待提现为已提现并记录流水号
     * @param entity
     * @return
     */
    public int updateSaleCash(int id, String paymentSequence,String payTime);

    
    /**
     * @param endTime 
     * @param startTime 
     * @Title:queryWithdrawInfoList  
     * @Description:个人中心查询提现详情
     * @param saleId 代理/销售主键
     * @param type 1代理2销售
     * @return
     * @throws Exception
     */
    public List<SalesWithdrawInfoEntity> queryWithdrawInfoList(int salesId, String startTime, String endTime);

    /**
     * 后台查询列表
     * @param salesId
     * @return
     */

    public List<SalesWithdrawEntity> withdrawList(int salesId,int status,String keyWords);


    /**
     * 从income表和withdraw表获取提现详情
     * @param saleId
     * @param oldTime
     * @return
     */
	public List<SalesWithdrawDetailEntity> getSalesWithdrawDetailEntityListOfOriginal(
			int saleId, String oldTime);

	/**
     * @Title:addSalesWithdraw 
     * @Description:增加一条提现详细记录
     * @param entity
     * @return
     */
	public int addSalesWithdraw(SalesWithdrawDetailEntity entity);

    /**
     * 根据销售id获取已提现总额
     * @param saleId 销售id
     * @return
     */
	public BigDecimal getSalesTotalWithdrawDaoBySalesId(int saleId);
}
