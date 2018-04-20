package com.weichuang.ecommerce.withdraw.repository.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawInfoEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawInfoEntity;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.withdraw.constants.NameSpaceConstant;
import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawEntity;
import com.weichuang.ecommerce.withdraw.repository.IAgentWithdrawDao;

/**
 * <p>ClassName: SalesWithdrawDaoImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: :代理商提现接口</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午4:11:49</p>
 */

@Component
@AutoConfigureAfter(MyBatisConfig.class)
public class AgentWithdrawDaoImpl implements IAgentWithdrawDao {
    @Autowired
    public SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Title:addSaleCash  
     * @Description:增加一条待提现记录
     * @param entity
     * @return
     */
    @Override
    public int addSaleCash(AgentWithdrawEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.AGENT_WITHDRAW + ".addSaleCash", entity);
    }

    /**
     * @Title:updateSaleCash  
     * @Description:更新待提现为已提现并记录流水号
     * @param
     * @return
     */
    @Override
    public int updateSaleCash(int id, String paymentSequence,String payTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("paymentSequence", paymentSequence);
        params.put("payTime",payTime);
        return sqlSessionTemplate.update(NameSpaceConstant.AGENT_WITHDRAW + ".updateSaleCash", params);
    }

    /**
     * 根据门店id获取门店所有员工已提现总额
     * @param agentId
     * @return
     */
    public BigDecimal getAgentTotalWithdrawDaoByAgentId(int agentId){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);

        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_WITHDRAW + ".getAgentTotalWithdrawDaoByAgentId", params);
    }

    /**
     * 根据公司id查询公司已提现总额
     * @param parentAgentId
     * @return
     */
    public BigDecimal getAgentTotalWithdrawDaoByParentAgentId(int parentAgentId){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);

        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_WITHDRAW + ".getAgentTotalWithdrawDaoByParentAgentId", params);
    }


    /**
     * 根据公司id获取公司所属门店已提现的总额
     * @param parentAgentId
     * @return
     */
    public BigDecimal getAllAgentTotalWithdrawDaoByParentAgentId(int parentAgentId){
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);

        return sqlSessionTemplate.selectOne(NameSpaceConstant.AGENT_WITHDRAW + ".getAllAgentTotalWithdrawDaoByParentAgentId", params);
    }

    /**
     * @Title:queryWithdrawInfoList
     * @Description:个人中心查询提现详情

     * @return
     * @throws Exception
     */
    @Override
    public List<AgentWithdrawInfoEntity> queryAgentWithdrawInfoList(int agentId, String startTime, String endTime){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_WITHDRAW + ".queryAgentWithdrawInfoList", params);
    }

    /**
     * 获取原始代理商提现数据
     * @param agentId
     * @param systemTime
     * @return
     */
    @Override
    public List<AgentWithdrawDetailEntity> getAgentWithdrawDetailEntityListOfOriginal(int agentId, String systemTime){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("systemTime", systemTime);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_WITHDRAW_DETAIL + ".getAgentWithdrawDetailEntityListOfOriginal", params);
    }

    /**
     * @Title:addSalesWithdraw
     * @Description:增加一条提现详细记录
     * @param entity
     * @return
     */
    @Override
    public int addAgentDetailWithdraw(AgentWithdrawDetailEntity entity) {
        return sqlSessionTemplate.insert(NameSpaceConstant.AGENT_WITHDRAW_DETAIL + ".addAgentDetailWithdraw", entity);
    }

    /**
     * 获取代理商提现记录
     * @param agentId
     * @param status
     * @param keyWords
     * @return
     */
    public List<AgentWithdrawEntity> withdrawList(int agentId,int status,String keyWords){
        Map<String, Object> params = new HashMap<>();
        params.put("agentId", agentId);
        params.put("status", status);
        params.put("keyWords", keyWords);
        return sqlSessionTemplate.selectList(NameSpaceConstant.AGENT_WITHDRAW + ".withdrawList", params);
    }
}
