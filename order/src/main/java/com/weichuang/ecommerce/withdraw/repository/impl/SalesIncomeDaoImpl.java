package com.weichuang.ecommerce.withdraw.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.weichuang.ecommerce.withdraw.constants.NameSpaceConstant;
import com.weichuang.ecommerce.withdraw.entity.SalesDailyIncomeDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesDailyIncomeSumEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesIncomeEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;

import com.weichuang.ecommerce.withdraw.entity.response.BonusTemplate;
import com.weichuang.ecommerce.withdraw.repository.ISalesIncomeDao;

/**
 * <p>ClassName: SalesIncomeDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 业务人员预计收入接口类</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午4:22:32</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class SalesIncomeDaoImpl implements ISalesIncomeDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int addSalesIncome(SalesIncomeEntity salesIncomeEntity) {
        sqlSessionTemplate.insert(com.weichuang.ecommerce.order.constants.NameSpaceConstant.SALE_INCOME + ".addSalesIncome", salesIncomeEntity);
        return salesIncomeEntity.getId();
    }

    /**
     * @param saleId     销售人员id
     * @param systemTime 系统时间
     * @return
     * @Title:selectBySaleId
     * @Description:根据销售人员主键查询分红(一天7天以及可提现)
     */
    @Override
    public List<BonusTemplate> selectBySaleId(int saleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_INCOME + ".selectBySaleId", params);
    }
    
    /**
     * @param saleId     销售人员id
     * @param systemTime 系统时间
     * @return
     * @Title:selectBySaleId
     * @Description:根据销售人员主键查询分红(一天7天以及可提现)
     */
    @Override
    public List<BonusTemplate> selectSumBySaleId(int saleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_INCOME + ".selectSumBySaleId", params);
    }

    /**
     * @param saleId
     * @param systemTime
     * @return
     * @Title:updateCashStatus
     * @Description:根据销售人员id和系统时间批量更新已提现状态
     */
    @Override
    public int updateCashStatus(int saleId, int status, String systemTime, String oldTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        params.put("status", status);
        params.put("systemTime", systemTime);
        params.put("oldTime", oldTime);
        return sqlSessionTemplate.update(NameSpaceConstant.SALES_INCOME + ".updateCashStatus", params);
    }
    
    
    
    /**
     * @param saleId
     * @param systemTime
     * @return
     * @Title:updateCashStatus
     * @Description:根据销售人员id和系统时间批量更新已提现状态
     */
    @Override
    public int updateCashStatusSuccess(int saleId, int status, String systemTime, String oldTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        params.put("status", status);
        params.put("systemTime", systemTime);
        params.put("oldTime", oldTime);
        return sqlSessionTemplate.update(NameSpaceConstant.SALES_INCOME + ".updateCashStatusSuccess", params);
    }

    /**
     * @param saleId
     * @param systemTime
     * @return
     * @Title:cashMoney
     * @Description:根据销售人员id和系统时间查询可提现的金额,用于和前台传过来的金额进行二次比较
     */
    @Override
    public BonusTemplate cashMoney(int saleId, String systemTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        params.put("systemTime", systemTime);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.SALES_INCOME + ".cashMoney", params);
    }

    /**
     * @param saleId
     * @param startTime
     * @param endTime
     * @return
     * @Title:getDailyIncomeSumBySalesId
     * @Description:根据业务人员的id查询某一时间段内每天的预计的可提成的汇总
     */
    @Override
    public List<SalesDailyIncomeSumEntity> getDailyIncomeSumBySalesId(int saleId, String startTime, String endTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_INCOME + ".getDailyIncomeSumBySalesId", params);
    }

    /**
     * @param saleId
     * @param startTime
     * @param endTime
     * @return
     * @Title:SalesDailyIncomeDetailEntity
     * @Description:根据业务人员的id查询某一时间段内每天的预计的可提成的详细信息
     */
    @Override
    public List<SalesDailyIncomeDetailEntity> getDailyIncomeDetailBySalesId(int saleId, String startTime, String endTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("saleId", saleId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.SALES_INCOME + ".getDailyIncomeDetailBySalesId", params);
    }

    /**
     * @param orderNo 订单编号
     * @return int
     * @Title:deleteSalesIncomeByOrderNo
     * @Description:订单退款后，逻辑删除业务人员收入
     */
    @Override
    public int deleteSalesIncomeByOrderNo(String orderNo) {
        return sqlSessionTemplate.update(NameSpaceConstant.SALES_INCOME + ".deleteSalesIncomeByOrderNo", orderNo);
    }
}
