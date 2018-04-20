package com.weichuang.ecommerce.withdraw.repository.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weichuang.ecommerce.withdraw.entity.AgentDailyIncomeDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.AgentIncomeEntity;

import com.weichuang.ecommerce.withdraw.entity.ParentAgentDailyIncomeEntity;
import com.weichuang.ecommerce.withdraw.entity.ParentAgentDailyIncomeSumEntity;
import com.weichuang.ecommerce.withdraw.entity.response.AgentDailyIncomeDetailListResponse;
import com.weichuang.ecommerce.withdraw.entity.response.ParentAgentDailyIncomeListResponse;
import com.weichuang.ecommerce.withdraw.entity.response.ParentAgentDailyIncomeSumListResponse;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.withdraw.constants.NameSpaceConstant;
import com.weichuang.ecommerce.withdraw.entity.response.BonusTemplate;
import com.weichuang.ecommerce.withdraw.repository.IAgentIncomeDao;

/**
 * <p>ClassName: SalesIncomeDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: 代理商预计收入接口类</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午4:22:32</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class AgentIncomeDaoImpl implements IAgentIncomeDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * <p>Description: 添加代理商预计收入记录</p>
     * <p>param entity 预计收入记录实体 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/10/08 15:00 </p>
     * <p>return </p>
     */
    @Override
    public int addAgentIncome(AgentIncomeEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.AGENT_INCOME + ".addAgentIncome", entity);
    }

    /**
     * <p>Description: 根据条件查询代理商预计收入列表（针对未提现的记录）</p>
     * <p>param agentId 代理商Id </p>
     * <p>param startTime 创建开始时间 </p>
     * <p>param endTime 创建结束时间 </p>
     * <p>author jiangkesen </p>
     * <p>date 2017/10/08 15:00 </p>
     * <p>return List<AgentIncomeEntity></p>
     */
    public List<AgentIncomeEntity> getAgentIncomeList(int agentId, Date startTime, Date endTime) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("agentId", agentId);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        List<AgentIncomeEntity> result = sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".getAgentIncomeList", map);
        return result;
    }

    /**
     * @param agentId    代理商id
     * @param
     * @return
     * @Title:selectBySaleId
     * @Description:根据代理商主键查询分红(一天7天以及可提现)
     */
    @Override
    public List<BonusTemplate> selectByAgentId(int agentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".selectByAgentId", params);
    }

    /**
     * @param agentId    代理商id
     * @param
     * @return
     * @Title:selectBySaleId
     * @Description:根据代理商主键查询分红(一天7天以及可提现)
     */
    @Override
    public List<BonusTemplate> selectSumListByAgentId(int agentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".selectSumListByAgentId", params);
    }

    /**
     * @param
     * @param systemTime
     * @return
     * @Title:updateCashStatus
     * @Description:根据代理商id和系统时间批量更新已提现状态
     */
    @Override
    public int updateCashStatus(int agentId, int status, String systemTime, String oldTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("status", status);
        params.put("systemTime", systemTime);
        params.put("oldTime", oldTime);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT_INCOME + ".updateCashStatus", params);
    }

    /**
     * @param
     * @param systemTime
     * @return
     * @Title:updateCashStatus
     * @Description:根据代理商id和系统时间批量更新已提现状态
     */
    @Override
    public int updateCashStatusSuccess(int agentId, int status, String systemTime, String oldTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("status", status);
        params.put("systemTime", systemTime);
        params.put("oldTime", oldTime);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT_INCOME + ".updateCashStatusSuccess", params);
    }

    /**
     * @param agentId
     * @param systemTime
     * @return
     * @Title:cashMoney
     * @Description:根据代理商id和系统时间查询可提现的金额,用于和前台传过来的金额进行二次比较
     */
    @Override
    public BonusTemplate cashMoney(int agentId, String systemTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("systemTime", systemTime);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".cashMoney", params);
    }

    /**
     * @param orderNo 订单编号
     * @return int
     * @Title:deleteSalesIncomeByOrderNo
     * @Description:订单退款后，逻辑删除代理商收入
     */
    @Override
    public int deleteAgentIncomeByOrderNo(String orderNo) {
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT_INCOME + ".deleteAgentIncomeByOrderNo", orderNo);
    }

    /**
     * 根据门店id获取此门店所有业务的提成总额
     * @param agentId
     * @return
     */
    @Override
    public BigDecimal selectSumByAgentId(int agentId){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".selectSumByAgentId", params);
    }

    /**
     *
     * @param agentId
     * @return
     */
    @Override
    public List<BonusTemplate> selectMsgByAgentId(int agentId){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".selectMsgByAgentId", params);
    }

    /**
     * 根据公司id查询提成信息（当天，可提现）
     * @param parentAgentId
     * @return
     */
    @Override
    public List<BonusTemplate> selectByParentAgentId(int parentAgentId){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".selectByParentAgentId", params);
    }


    /**
     * 根据公司id查询公司级别的提成总额
     * @param parentAgentId
     * @return
     */
    @Override
    public BigDecimal selectSumByParentAgentId(int parentAgentId){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".selectSumByParentAgentId", params);
    }


    /**
     * 根据公司id查询所属门店提成信息（当天，以及可提现）
     * @param parentAgentId
     * @return
     */
    @Override
    public List<BonusTemplate> selectAllByParentAgentId(int parentAgentId){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".selectAllByParentAgentId", params);
    }


    /**
     * 根据公司id获取所属店面提成总额
     * @param parentAgentId
     * @return
     */
    @Override
    public BigDecimal selectAllAgentSumByParentAgentId(int parentAgentId){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".selectAllAgentSumByByParentAgentId", params);
    }
    
   

	/**
	 * 查询所有公司当天的提成
	 * @param today
	 * @return
	 */
    @Override
	public BigDecimal queryAllParentAgentOneDay(String today){
    	 Map<String, Object> params = new HashMap<>();
         params.put("today", today);
         return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".queryAllParentAgentOneDay", params);
    }

	/**
	 * 查询所有门店当天的提成
	 * @param today
	 * @return
	 */
    @Override
	public BigDecimal queryAllAgentOneDay(String today){
    	 Map<String, Object> params = new HashMap<>();
         params.put("today", today);
         return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".queryAllAgentOneDay", params);
    }

	/**
	 * 查询所有公司的提成
	 * @return
	 */
    @Override
	public BigDecimal queryParentAgentTotal(){
    	 return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".queryParentAgentTotal");
    }
	
	/**
	 * 查询所有门店的提成
	 * @return
	 */

	@Override
	public BigDecimal queryAllAgentTotal(){
		return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_INCOME + ".queryAllAgentTotal");
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
    public List<ParentAgentDailyIncomeSumEntity> getParentAgentDailyIncomeSumByParentAgentId(int parentAgentId,String startTime,String endTime){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".getParentAgentDailyIncomeSumByParentAgentId", params);
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
    public List<ParentAgentDailyIncomeEntity> getParentAgentDailyIncomeListByParentAgentId(int parentAgentId,String startTime,String endTime){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".getParentAgentDailyIncomeListByParentAgentId", params);
    }


    /**
     * @param parentAgentId 代理/销售主键
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据公司的id查询某天的预计的可提成的详细信息（按门店显示）
     *//*
    public List<ParentAgentDailyIncomeEntity>  getParentAgentDailyIncomeListByParentAgentIdDate(int parentAgentId, String startTime,String endTime){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".getParentAgentDailyIncomeListByParentAgentIdDate", params);
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
    public List<AgentDailyIncomeDetailEntity> getAgentDailyIncomeDetailByAgentId(int agentId,String startTime,String endTime){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".getAgentDailyIncomeDetailByAgentId", params);
    }


    /**
     * @param agentId    代理/销售主键

     * @author liuzhanchao
     * @date 2017/12/29 17:57
     * @return Response<SalesDailyIncomeDetailListResponse>
     * @throws Exception
     * @Title:getDailyIncomeDetailBySalesId
     * @Description:根据门店的id查询某一天的预计的可提成的详细信息
     */
   /* public List<AgentDailyIncomeDetailEntity> getAgentDailyIncomeDetailByAgentIdDate(int agentId,String startTime,String endTime){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_INCOME + ".getAgentDailyIncomeDetailByAgentIdDate", params);
    }*/

	
}
