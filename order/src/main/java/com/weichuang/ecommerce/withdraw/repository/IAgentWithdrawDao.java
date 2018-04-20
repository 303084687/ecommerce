package com.weichuang.ecommerce.withdraw.repository;

import com.weichuang.ecommerce.withdraw.constants.NameSpaceConstant;
import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawInfoEntity;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawDetailEntity;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.stereotype.Component;

import com.weichuang.ecommerce.MyBatisConfig;
import com.weichuang.ecommerce.withdraw.entity.AgentWithdrawEntity;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>ClassName: ISalesWithdrawDao.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:代理商提现表 </p>
 * <p>author wanggongliang</p>
 * <p>2017年9月22日 下午4:04:28</p>
 */
@Component
@AutoConfigureAfter(MyBatisConfig.class)
public interface IAgentWithdrawDao {
    /**
     * @Title:addSaleCash  
     * @Description:增加一条待提现记录
     * @param entity
     * @return
     */
    public int addSaleCash(AgentWithdrawEntity entity);

    /**
     * @Title:updateSaleCash  
     * @Description:更新待提现为已提现并记录流水号

     * @return
     */
    public int updateSaleCash(int id, String paymentSequence,String payTime);

    /**
     * 根据门店id获取门店所有员工已提现总额
     * @param agentId
     * @return
     */
    public BigDecimal getAgentTotalWithdrawDaoByAgentId(int agentId);

    /**
     * 根据公司id查询公司已提现总额
     * @param parentAgentId
     * @return
     */
    public BigDecimal getAgentTotalWithdrawDaoByParentAgentId(int parentAgentId);


    /**
     * 根据公司id获取公司所属门店已提现的总额
     * @param parentAgentId
     * @return
     */
    public BigDecimal getAllAgentTotalWithdrawDaoByParentAgentId(int parentAgentId);

    /**
     * @Title:queryWithdrawInfoList
     * @Description:个人中心查询提现详情

     * @return
     * @throws Exception
     */

    public List<AgentWithdrawInfoEntity> queryAgentWithdrawInfoList(int agentId, String startTime, String endTime);

    /**
     * 获取原始代理商提现数据
     * @param agentId
     * @param oldTime
     * @return
     */
    public List<AgentWithdrawDetailEntity> getAgentWithdrawDetailEntityListOfOriginal(int agentId, String oldTime);

    /**
     * @Title:addSalesWithdraw
     * @Description:增加一条提现详细记录
     * @param entity
     * @return
     */

    public int addAgentDetailWithdraw(AgentWithdrawDetailEntity entity) ;

    /**
     * 获取代理商提现列表
     * @param agentId
     * @param status
     * @param keyWords
     * @return
     */

    public List<AgentWithdrawEntity> withdrawList(int agentId,int status,String keyWords);


}
