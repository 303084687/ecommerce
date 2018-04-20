package com.weichuang.ecommerce.withdraw.repository.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.withdraw.constants.NameSpaceConstant;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawInfoEntity;
import com.weichuang.ecommerce.withdraw.repository.ISalesWithdrawDao;

/**
 * <p>ClassName: SalesWithdrawDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: :业务人员提现接口</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午4:11:49</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class SalesWithdrawDaoImpl implements ISalesWithdrawDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Title:addSaleCash  
     * @Description:增加一条待提现记录
     * @param entity
     * @return
     */
    @Override
    public int addSaleCash(SalesWithdrawEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.SALES_WITHDRAW + ".addSaleCash", entity);
    }

    /**
     * @Title:updateSaleCash  
     * @Description:更新待提现为已提现并记录流水号
     * @param entity
     * @return
     */
    @Override
    public int updateSaleCash(int id, String paymentSequence,String payTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("paymentSequence", paymentSequence);
        params.put("payTime",payTime);
        return sqlSessionTemplate.update(NameSpaceConstant.SALES_WITHDRAW + ".updateSaleCash", params);
    }
    
    /**
     * @Title:queryWithdrawInfoList  
     * @Description:个人中心查询提现详情
     * @return
     * @throws Exception
     */
    @Override
    public List<SalesWithdrawInfoEntity> queryWithdrawInfoList(int salesId,String startTime,String endTime){
        Map<String, Object> params = new HashMap<>();
        params.put("salesId", salesId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_WITHDRAW + ".queryWithdrawInfoList", params);
    }

    @Override

    public List<SalesWithdrawEntity> withdrawList(int salesId,int status ,String keyWords) {
        Map<String, Object> params = new HashMap<>();
        params.put("salesId", salesId);
        params.put("status", status);
        params.put("keyWords", keyWords);

        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_WITHDRAW + ".queryWithdrawList", params);
    }

    /**
     * @Title:getSalesWithdrawDetailEntityListOfOriginal  
     * @Description:获取原始提现详情
     * @param saleId 代理/销售主键
     * @param type 1代理2销售
     * @return
     * @throws Exception
     */
	@Override
	public List<SalesWithdrawDetailEntity> getSalesWithdrawDetailEntityListOfOriginal(
			int saleId, String systemTime) {
		Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        params.put("systemTime", systemTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_WITHDRAW_DETAIL + ".getSalesWithdrawDetailEntityListOfOriginal", params);
	}
	
	/**
     * @Title:addSalesWithdraw 
     * @Description:增加一条提现详细记录
     * @param entity
     * @return
     */
    @Override
    public int addSalesWithdraw(SalesWithdrawDetailEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.SALES_WITHDRAW_DETAIL + ".addSalesWithdraw", entity);
    }

    /**
     * 根据销售id获取已提现总额
     * @param saleId 销售id
     * @return
     */
    @Override
    public BigDecimal getSalesTotalWithdrawDaoBySalesId(int saleId){
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_WITHDRAW + ".getSalesTotalWithdrawDaoBySalesId", params);
    }
}
