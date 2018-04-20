package com.weichuang.ecommerce.withdraw.service;

import javax.servlet.http.HttpServletRequest;

import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.withdraw.entity.SalesWithdrawDetailEntity;
import com.weichuang.ecommerce.withdraw.entity.request.WithdrawEntity;
import com.weichuang.ecommerce.withdraw.entity.response.*;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>ClassName: IWithdrawService.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:销售和代理分红service</p>
 * <p>author wanggongliang</p>
 * <p>2017年9月14日 下午4:28:46</p>
 */
public interface IWithdrawService {
    /**
     * @Title:extractMoney  
     * @Description:个人中心首页返回的分红实体数字
     * @return
     * @throws ServiceException
     */
    public WithdrawResponse queryWithdraw(int saleId, int type) throws ServiceException;

    /**
     * @Title:addWaitExtract  
     * @Description:增加一条待返现记录返回实体
     * @param saleId 代理/销售主键
     * @param type 1代理商 2销售人员
     * @param money 返现金额
     * @param saleName 代理/销售姓名
     * @param openId 微信openId
     * @return
     * @throws ServiceException
     */
    public WithdrawEntity addWaitExtract(int saleId, int type, String money, String saleName, String openId,
            String oldTime) throws ServiceException;

    /**
     * @Title:extractMoney  
     * @Description:提现流程 1微信企业转账 2更新状态
     * @param type 1代理商 2销售人员
     * @return
     * @throws ServiceException
     */
    public void extractMoney(HttpServletRequest request, WithdrawEntity entity)
            throws ServiceException;
    
    
    /**
     * @Title:queryWithdrawInfoList  
     * @Description:个人中心查询提现详情
     * @param salesId 代理/销售主键

     * @return
     * @throws Exception
     */

    public SalesWithdrawInfoListResponse queryWithdrawInfoList(int salesId) throws ServiceException;

    
    /**
     * 增加提现详情
     * @Title:addWithdrawDetail
     * @param saleId
     * @param type

     * @return
     * @throws ServiceException
     */
	public void addWithdrawDetail(int saleId, int type, 
			 String oldTime) throws ServiceException;

    /**
     * @return
     * @throws ServiceException
     * @Title:extractMoney
     * @Description:后台显示所有公司的提成总额和拉新数字
     */
    public AllFinanceMsgResponse queryAllFinanceMsg() throws ServiceException;


   

    /**
     * @param parentAgentId 公司主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByParentAgentId
     * @Description:后台显示公司提成信息
     */

    public IncomeMsgResponse queryIncomeMsgByParentAgentId(int parentAgentId) throws ServiceException;

    /**
     * @param agentId 门店主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByAgentId
     * @Description:后台显示门店提成信息
     */

    public IncomeMsgResponse queryIncomeMsgByAgentId(int agentId) throws ServiceException;

    /**
     * @param saleId 销售主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgBySaleId
     * @Description:后台显示员工提成信息
     */

    public IncomeMsgResponse queryIncomeMsgBySaleId(int saleId) throws ServiceException;


    /**
     * @param parentAgentId 公司主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByParentAgentId
     * @Description:后台显示公司所属门店提成信息
     */
    public IncomeMsgResponse queryAllAgentIncomeMsgByParentAgentId(int parentAgentId) throws ServiceException;


    /**
     * @param agentId 代理/销售主键

     * @return
     * @throws Exception
     * @Title:queryAgentWithdrawInfoList
     * @Description:根据公司id查询提现详情
     */

    public AgentWithdrawInfoListResponse queryAgentWithdrawInfoList(int agentId)
            throws ServiceException;
}
