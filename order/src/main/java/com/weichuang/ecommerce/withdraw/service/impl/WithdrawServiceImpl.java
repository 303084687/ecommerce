package com.weichuang.ecommerce.withdraw.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.weichuang.ecommerce.withdraw.entity.*;
import com.weichuang.ecommerce.withdraw.entity.response.*;
import net.sf.json.JSONObject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.DateUtil;
import com.weichuang.commons.OrderNumberUtil;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.Transfers.TransfersUtils;
import com.weichuang.ecommerce.withdraw.entity.request.WithdrawEntity;
import com.weichuang.ecommerce.withdraw.repository.IAgentIncomeDao;
import com.weichuang.ecommerce.withdraw.repository.IAgentWithdrawDao;
import com.weichuang.ecommerce.withdraw.repository.ISalesIncomeDao;
import com.weichuang.ecommerce.withdraw.repository.ISalesWithdrawDao;
import com.weichuang.ecommerce.withdraw.service.IWithdrawService;

/**
 * <p>
 * ClassName: IWithdrawService.java
 * </p>
 * <p>
 * Company: 伟创科技(北京)科技有限公司
 * </p>
 * <p>
 * Description:销售和代理分红service
 * </p>
 * <p>
 * author wanggongliang
 * </p>
 * <p>
 * 2017年9月14日 下午4:28:46
 * </p>
 */
@Service
@SuppressWarnings("all")
public class WithdrawServiceImpl implements IWithdrawService {
    // 销售人员
    @Autowired
    private ISalesIncomeDao incomeDao;

    @Autowired
    private ISalesWithdrawDao salesWithdrawDao;

    // 代理商
    @Autowired
    private IAgentIncomeDao agentIncomeDao;

    @Autowired
    private IAgentWithdrawDao agentWithdrawDao;

    // 微信
    @Autowired
    private WeiXinProperties weiXinProperties;

    /**
     * @throws ServiceException
     * @Title:extractMoney
     * @Description:个人中心首页返回的分红实体数字
     * @return从sql查询出的数据是按照天进行分组的
     */
    @Override
    @Transactional(readOnly = true)
    public WithdrawResponse queryWithdraw(int saleId, int type)
            throws ServiceException {
        // 获取系统当天时间
        LocalDate today = LocalDate.now();
        // 计算当天到7天
        LocalDate sevenDay = today.plusDays(-6);
        // 计算15天前
        LocalDate fifteDay = today.plusDays(-15);
        BigDecimal oneDayMoney = new BigDecimal("0.00");// 一天的预计收入
        BigDecimal totalMoney = new BigDecimal("0.00");// 总收入包括提现和未提现的
        BigDecimal cashMoney = new BigDecimal("0.00");// 可以提现的金额
        // 查询结果集循环比较数据,根据类型不同执行不同查询
        List<BonusTemplate> list = new ArrayList<BonusTemplate>();
        List<BonusTemplate> listSum = new ArrayList<BonusTemplate>();
        if (type == 1) {// 查询代理商
            list = agentIncomeDao.selectByAgentId(saleId);
            listSum=agentIncomeDao.selectSumListByAgentId(saleId);
        }
        if (type == 2) {// 查询销售人员
            list = incomeDao.selectBySaleId(saleId);
            listSum = incomeDao.selectSumBySaleId(saleId);
        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // 查询出来的时间进行转换
                LocalDate date = LocalDate.parse(list.get(i).getDay());
                LocalDate time = LocalDate.of(date.getYear(), date.getMonth(),
                        date.getDayOfMonth());
                // 当天的结果集合
                if (time.equals(today)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    oneDayMoney = oneDayMoney.add(money);
                }
                // 15天前的结果集
                if (time.isBefore(fifteDay)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    cashMoney = cashMoney.add(money);
                }
                // 总的结果集包含提现和未提现的
                //totalMoney = totalMoney.add(list.get(i).getEveryMoney());
            }


        }
        if (listSum.size() > 0) {
            for (int i = 0; i < listSum.size(); i++) {
                totalMoney = totalMoney.add(listSum.get(i).getEveryMoney());
            }
        }
        BonusTemplate template = new BonusTemplate();
        template.setSaleId(saleId);
        template.setOneDayMoney(oneDayMoney.setScale(2, 1));// 一天
        template.setSevenDayMoney(totalMoney.setScale(2, 1));// 七天
        template.setCashMoney(cashMoney.setScale(2, 1));// 15天前
        WithdrawResponse response = new WithdrawResponse();
        response.setTemplate(template);
        return response;
    }

    /**
     * @param saleId   代理/销售主键
     * @param type     1代理商 2销售人员
     * @param money    返现金额
     * @param saleName 代理/销售姓名
     * @param openId   微信openId
     * @return
     * @throws ServiceException
     * @Title:addWaitExtract
     * @Description:增加一条待返现记录
     */
    @Override
    @Transactional
    public WithdrawEntity addWaitExtract(int saleId, int type, String money,
                                         String saleName, String openId, String oldTime)
            throws ServiceException {
        // 1首先根据传递过来的金额和数据库查询的金额做比较，二次确认提现金额是否正确
        BonusTemplate template = null;
        WithdrawEntity withdrawEntity = new WithdrawEntity();
        if (type == 1) {// 查询代理商
            template = agentIncomeDao.cashMoney(saleId, oldTime);
        }
        if (type == 2) {// 查询销售人员
            template = incomeDao.cashMoney(saleId, oldTime);
        }
        // 金额比较 数据库查询的金额大于等于提现金额
        if (template != null) {
            if (template.getEveryMoney().compareTo(new BigDecimal(money)) == 0
                    || template.getEveryMoney()
                    .compareTo(new BigDecimal(money)) == 1) {
                if (type == 1) {// 代理商
                    // 1插入明细表
                    AgentWithdrawEntity entity = new AgentWithdrawEntity();
                    entity.setAgentId(saleId);
                    entity.setPaymentNo(OrderNumberUtil.getWeiXinNo());// 第三方订单号,用于失败查询使用和其他
                    entity.setAgentName(saleName);
                    entity.setOpenId(openId);// 微信账号openId
                    entity.setCreateTime(new Date());// 创建时间
                    entity.setPaymentType(2);// 微信支付
                    entity.setWithdraw(new BigDecimal(money).setScale(2, 1));// 提现的总金额

                    agentWithdrawDao.addSaleCash(entity);
                    entity.setId(entity.getId());// 把返回的主键返回给实体
                    System.out.println(entity.getAgentId());

                    // 批量更新体现表为待提现
                    agentIncomeDao.updateCashStatus(saleId, 1, DateUtil.toLongDateTimeString(entity.getCreateTime()), oldTime);
                    BeanUtils.copyProperties(entity, withdrawEntity);
                    withdrawEntity.setType(type);
                }
                if (type == 2) {// 销售人员
                    // 插入明细表
                    SalesWithdrawEntity entity = new SalesWithdrawEntity();
                    entity.setSalesId(saleId);
                    entity.setSalesName(saleName);
                    entity.setOpenId(openId);// 微信账号openId
                    entity.setPaymentNo(OrderNumberUtil.getWeiXinNo());// 第三方订单号,用于失败查询使用和其他
                    entity.setCreateTime(new Date());// 创建时间
                    entity.setPaymentType(2);// 微信支付
                    entity.setWithdraw(new BigDecimal(money).setScale(2, 1));// 提现的总金额
                    salesWithdrawDao.addSaleCash(entity);
                    entity.setId(entity.getId());// 把返回的主键返回给实体
                    // 批量更新为待提现
                    incomeDao.updateCashStatus(saleId, 1, DateUtil.toLongDateTimeString(entity.getCreateTime()), oldTime);
                    BeanUtils.copyProperties(entity, withdrawEntity);
                    withdrawEntity.setAgentId(saleId);
                    withdrawEntity.setType(type);
                }
            } else {
                throw new ServiceException(Result.WITHDRAW_MONEY.getCode(),
                        Result.WITHDRAW_MONEY.getMessage());
            }
        } else {
            throw new ServiceException(Result.WITHDRAW_MONEY_NOTNULL.getCode(),
                    Result.WITHDRAW_MONEY_NOTNULL.getMessage());
        }
        return withdrawEntity;
    }

    /**
     * 增加提现详情
     *
     * @param saleId
     * @param type
     * @param money
     * @param saleName
     * @param string
     * @return
     * @throws ServiceException
     * @Title:addWithdrawDetail
     */

    @Override
    @Transactional
    public void addWithdrawDetail(int saleId, int type,
                                  String oldTime) throws ServiceException {
        // 1首先根据传递过来的金额和数据库查询的金额做比较，二次确认提现金额是否正确

        List<SalesWithdrawDetailEntity> salesWithdrawDetailEntityList = new ArrayList<SalesWithdrawDetailEntity>();
        /*if (type == 1) {// 查询代理商
            template = agentIncomeDao.cashMoney(saleId, oldTime);
		}*/
        if (type == 2) {// 查询销售人员
            salesWithdrawDetailEntityList = salesWithdrawDao.getSalesWithdrawDetailEntityListOfOriginal(saleId, oldTime);

        }
        // 添加到提现详情
        if (salesWithdrawDetailEntityList != null) {
            for (SalesWithdrawDetailEntity salesWithdrawDetailEntity : salesWithdrawDetailEntityList) {
                salesWithdrawDao.addSalesWithdraw(salesWithdrawDetailEntity);

            }
        }

    }


    /**
     * 增加代理提现详情
     *
     * @param saleId
     * @param type
     * @param money
     * @param saleName
     * @param string
     * @return
     * @throws ServiceException
     * @Title:addWithdrawDetail
     */


    public void addAgentWithdrawDetail(int agentId,
                                  String oldTime) throws ServiceException {
        // 1首先根据传递过来的金额和数据库查询的金额做比较，二次确认提现金额是否正确

        List<AgentWithdrawDetailEntity> agentWithdrawDetailEntityList = new ArrayList<AgentWithdrawDetailEntity>();

        agentWithdrawDetailEntityList = agentWithdrawDao.getAgentWithdrawDetailEntityListOfOriginal(agentId, oldTime);


        // 添加到提现详情
        if (agentWithdrawDetailEntityList != null) {
            for (AgentWithdrawDetailEntity agentWithdrawDetailEntity : agentWithdrawDetailEntityList) {
                agentWithdrawDao.addAgentDetailWithdraw(agentWithdrawDetailEntity);

            }
        }

    }

    /**
     * @param type 1代理商 2销售人员
     * @return
     * @throws ServiceException
     * @Title:extractMoney
     * @Description:提现流程
     */
    @Override
    @Transactional
    public void extractMoney(HttpServletRequest request, WithdrawEntity entity) throws ServiceException {
        // 调取微信企业付款的接口
        String result = TransfersUtils.sendTransfers(request, entity
                .getWithdraw().toString(), "销售分红" + entity.getOpenId(), entity
                .getOpenId(), entity.getPaymentNo(), weiXinProperties
                .getMchId(), weiXinProperties.getAppId(), weiXinProperties
                .getPaternerKey(), weiXinProperties.getCertPath());
        // 转换结果集
        JSONObject jsonObject = JSONObject.fromObject(result);
        int type=entity.getType();
        if (null != jsonObject) {
            // 根据返回的结果进行更新数据库记录
            if (type == 1) {// 代理商
                // 更新分红详情表信息
                agentWithdrawDao.updateSaleCash(entity.getId(),
                        jsonObject.getString("paymentNo"),jsonObject.getString("paymentTime"));
                // 批量更新分红表
                agentIncomeDao.updateCashStatusSuccess(entity.getAgentId(), 2,
                        jsonObject.getString("paymentTime"),DateUtil.toLongDateTimeString(entity.getCreateTime()) );
                addAgentWithdrawDetail(entity.getAgentId(),jsonObject.getString("paymentTime"));
            }
            if (type == 2) {// 销售人员
                // 更新分红详情表信息
                salesWithdrawDao.updateSaleCash(entity.getId(),
                        jsonObject.getString("paymentNo"),jsonObject.getString("paymentTime"));
                // 批量更新分红表
                incomeDao.updateCashStatusSuccess(entity.getAgentId(), 2,
                        jsonObject.getString("paymentTime"), DateUtil.toLongDateTimeString(entity.getCreateTime()));
                addWithdrawDetail(entity.getAgentId(), 2, jsonObject.getString("paymentTime"));
            }
        }
    }


    /**
     * @param saleId 代理/销售主键
     * @param type   1代理2销售
     * @return
     * @throws Exception
     * @Title:queryWithdrawInfoList
     * @Description:个人中心查询提现详情
     */
    @Override
    @Transactional
    public SalesWithdrawInfoListResponse queryWithdrawInfoList(int salesId)
            throws ServiceException {

        String startTime = DateUtil.beforeDays(-6);
        String endTime = DateUtil.beforeDays(1);
        // 查询销售人员
        List<SalesWithdrawInfoEntity> list = salesWithdrawDao
                .queryWithdrawInfoList(salesId, startTime, endTime);

        SalesWithdrawInfoListResponse response = new SalesWithdrawInfoListResponse();

        response.setList(list);// 返回的数据集合

        return response;
    }

    /**
     * @param agentId 代理/销售主键
     * @param type   1代理2销售
     * @return
     * @throws Exception
     * @Title:queryAgentWithdrawInfoList
     * @Description:个人中心查询提现详情
     */
    @Override
    @Transactional
    public AgentWithdrawInfoListResponse queryAgentWithdrawInfoList(int agentId)
            throws ServiceException {

        String startTime = DateUtil.beforeDays(-6);
        String endTime = DateUtil.beforeDays(1);
        // 查询销售人员
        List<AgentWithdrawInfoEntity> list = agentWithdrawDao
                .queryAgentWithdrawInfoList(agentId, startTime, endTime);

        AgentWithdrawInfoListResponse response = new AgentWithdrawInfoListResponse();

        response.setList(list);// 返回的数据集合

        return response;
    }

    /**
     * @param saleId 销售主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgBySaleId
     * @Description:后台显示员工提成信息
     */
    @Override
    @Transactional
    public IncomeMsgResponse queryIncomeMsgBySaleId(int saleId) throws ServiceException {
        IncomeMsgResponse response = new IncomeMsgResponse();
        WithdrawResponse withdrawResponse = queryWithdraw(saleId, 2);
        //当天
        response.setOneDayMoney(withdrawResponse.getTemplate().getOneDayMoney());
        //合计
        response.setTotalMoney(withdrawResponse.getTemplate().getSevenDayMoney());
        //可提现
        response.setCashMoney(withdrawResponse.getTemplate().getCashMoney());
        //已提现
        BigDecimal alreadyMoney = salesWithdrawDao.getSalesTotalWithdrawDaoBySalesId(saleId);
        if(alreadyMoney==null){
            alreadyMoney=new BigDecimal("0.00");
        }
        response.setAlreadyMoney(alreadyMoney);                                  

        return response;
    }

    /**
     * @param agentId 门店主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByAgentId
     * @Description:后台显示门店提成信息
     */
    @Override
    @Transactional
    public IncomeMsgResponse queryIncomeMsgByAgentId(int agentId) throws ServiceException {
        IncomeMsgResponse response = new IncomeMsgResponse();
        // 获取系统当天时间
        LocalDate today = LocalDate.now();
        // 计算当天到7天
        LocalDate sevenDay = today.plusDays(-6);
        // 计算15天前
        LocalDate fifteDay = today.plusDays(-15);
        BigDecimal oneDayMoney = new BigDecimal("0.00");// 一天的预计收入
        BigDecimal totalMoney = new BigDecimal("0.00");// 总收入包括提现和未提现的
        BigDecimal cashMoney = new BigDecimal("0.00");// 可以提现的金额
        BigDecimal alreadyMoney=new BigDecimal("0.00");//已提现总额
        // 查询结果集循环比较数据,根据类型不同执行不同查询
        List<BonusTemplate> list = new ArrayList<BonusTemplate>();
        

      
        // 查询门店
        list = agentIncomeDao.selectMsgByAgentId(agentId);
       

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // 查询出来的时间进行转换
                LocalDate date = LocalDate.parse(list.get(i).getDay());
                LocalDate time = LocalDate.of(date.getYear(), date.getMonth(),
                        date.getDayOfMonth());
                // 当天的结果集合
                if (time.equals(today)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    oneDayMoney = oneDayMoney.add(money);
                }
                // 15天前的结果集
                if (time.isBefore(fifteDay)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    cashMoney = cashMoney.add(money);
                }
                // 总的结果集包含提现和未提现的
                //totalMoney = totalMoney.add(list.get(i).getEveryMoney());
            }


        }
        BigDecimal alreadyMoneyTemp = agentWithdrawDao.getAgentTotalWithdrawDaoByAgentId(agentId);
        if(alreadyMoneyTemp!=null){
        	alreadyMoney=alreadyMoneyTemp;
        }
        BigDecimal sum = agentIncomeDao.selectSumByAgentId(agentId);
        if(sum!=null){
        	totalMoney=sum;
        }
      
        response.setOneDayMoney(oneDayMoney.setScale(2, 1));
       
        response.setTotalMoney(totalMoney.setScale(2, 1));
        response.setCashMoney(cashMoney.setScale(2, 1));

        response.setAlreadyMoney(alreadyMoney);
        return response;

    }

    /**
     * @param parentAgentId 公司主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByParentAgentId
     * @Description:后台显示公司提成信息
     */
    @Override
    @Transactional
    public IncomeMsgResponse queryIncomeMsgByParentAgentId(int parentAgentId) throws ServiceException {
        IncomeMsgResponse response = new IncomeMsgResponse();
        // 获取系统当天时间
        LocalDate today = LocalDate.now();
        // 计算当天到7天
        LocalDate sevenDay = today.plusDays(-6);
        // 计算15天前
        LocalDate fifteDay = today.plusDays(-15);
        BigDecimal oneDayMoney = new BigDecimal("0.00");// 一天的预计收入
        BigDecimal totalMoney = new BigDecimal("0.00");// 总收入包括提现和未提现的
        BigDecimal cashMoney = new BigDecimal("0.00");// 可以提现的金额
        BigDecimal alreadyMoney= new BigDecimal("0.00");//已提现总额
        // 查询结果集循环比较数据,根据类型不同执行不同查询
        List<BonusTemplate> list = new ArrayList<BonusTemplate>();
     // 查询门店
        list = agentIncomeDao.selectByParentAgentId(parentAgentId);
       
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // 查询出来的时间进行转换
                LocalDate date = LocalDate.parse(list.get(i).getDay());
                LocalDate time = LocalDate.of(date.getYear(), date.getMonth(),
                        date.getDayOfMonth());
                // 当天的结果集合
                if (time.equals(today)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    oneDayMoney = oneDayMoney.add(money);
                }
                // 15天前的结果集
                if (time.isBefore(fifteDay)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    cashMoney = cashMoney.add(money);
                }
               
            }


        }
        BigDecimal alreadyMoneyTemp = agentWithdrawDao.getAgentTotalWithdrawDaoByParentAgentId(parentAgentId);
        if(alreadyMoneyTemp!=null){
        	alreadyMoney=alreadyMoneyTemp;
        }
        
        BigDecimal sum = agentIncomeDao.selectSumByParentAgentId(parentAgentId);
        if(sum!=null){
        	totalMoney=sum;
        }
       
        response.setOneDayMoney(oneDayMoney.setScale(2, 1));
        response.setTotalMoney(totalMoney.setScale(2, 1));
        response.setCashMoney(cashMoney.setScale(2, 1));

        response.setAlreadyMoney(alreadyMoney);
        return response;
    }

    /**
     * @param parentAgentId 公司主键
     * @return
     * @throws Exception
     * @Title:queryIncomeMsgByParentAgentId
     * @Description:后台显示公司所属门店提成信息
     */
    @Override
    @Transactional
    public IncomeMsgResponse queryAllAgentIncomeMsgByParentAgentId(int parentAgentId) throws ServiceException {
        IncomeMsgResponse response = new IncomeMsgResponse();
        // 获取系统当天时间
        LocalDate today = LocalDate.now();
        System.out.println(today);
        // 计算当天到7天
        LocalDate sevenDay = today.plusDays(-6);
        // 计算15天前
        LocalDate fifteDay = today.plusDays(-15);
        BigDecimal oneDayMoney = new BigDecimal("0.00");// 一天的预计收入
        BigDecimal totalMoney = new BigDecimal("0.00");// 总收入包括提现和未提现的
        BigDecimal cashMoney = new BigDecimal("0.00");// 可以提现的金额
        BigDecimal alreadyMoney= new BigDecimal("0.00");//已提现总额
        // 查询结果集循环比较数据,根据类型不同执行不同查询
        List<BonusTemplate> list = new ArrayList<BonusTemplate>();
        
        // 查询门店
        list = agentIncomeDao.selectAllByParentAgentId(parentAgentId);
        

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                // 查询出来的时间进行转换
                LocalDate date = LocalDate.parse(list.get(i).getDay());
                LocalDate time = LocalDate.of(date.getYear(), date.getMonth(),
                        date.getDayOfMonth());
                // 当天的结果集合
                if (time.equals(today)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    oneDayMoney = oneDayMoney.add(money);
                }
                // 15天前的结果集
                if (time.isBefore(fifteDay)) {
                    BigDecimal money = list.get(i).getEveryMoney();
                    cashMoney = cashMoney.add(money);
                }
                // 总的结果集包含提现和未提现的
                //totalMoney = totalMoney.add(list.get(i).getEveryMoney());
            }


        }
        
        BigDecimal alreadyMoneyTemp = agentWithdrawDao.getAllAgentTotalWithdrawDaoByParentAgentId(parentAgentId);
        if(alreadyMoneyTemp!=null){
        	alreadyMoney=alreadyMoneyTemp;
        }
        
        
        BigDecimal sum = agentIncomeDao.selectAllAgentSumByParentAgentId(parentAgentId);
        if(sum!=null){
        	totalMoney=sum;
        }
       
        response.setOneDayMoney(oneDayMoney.setScale(2, 1));
        response.setTotalMoney(totalMoney.setScale(2, 1));
        response.setCashMoney(cashMoney.setScale(2, 1));

        response.setAlreadyMoney(alreadyMoney);
        return response;
    }

    /**
     * @return
     * @throws ServiceException
     * @Title:extractMoney
     * @Description:后台显示所有公司的提成信息(当天的和所有的)
     */
    public AllFinanceMsgResponse queryAllFinanceMsg() throws ServiceException {
        AllFinanceMsgResponse response = new AllFinanceMsgResponse();

        // 获取系统当天时间
        String today = LocalDate.now().toString();
        //当天的所有公司包含门店的提成
        BigDecimal oneDayMoney = new BigDecimal("0.00");// 一天的预计收入
        //所有公司包含门店的总的提成
        BigDecimal totalMoney = new BigDecimal("0.00");// 总收入包括提现和未提现的
        
        
        //当天所有公司的提成
        BigDecimal oneDayParentAgent=agentIncomeDao.queryAllParentAgentOneDay(today);
        //当天所有门店的提成总额（所有员工）
        BigDecimal oneDayAllAgent=agentIncomeDao.queryAllAgentOneDay(today);
        //所有公司的总的提成
        BigDecimal totalParentAgent=agentIncomeDao.queryParentAgentTotal();
        //所有门店的总的提成（所有员工）
        BigDecimal totalAllAgent=agentIncomeDao.queryAllAgentTotal();
        if(oneDayParentAgent!=null){
        	oneDayMoney=oneDayMoney.add(oneDayParentAgent);
        }
        if(oneDayAllAgent!=null){
        	oneDayMoney=oneDayMoney.add(oneDayAllAgent);
        }
        if(totalParentAgent!=null){
        	totalMoney=totalMoney.add(totalParentAgent);
        }
        if(totalAllAgent!=null){
        	totalMoney=totalMoney.add(totalAllAgent);
        }
        
        response.setOneDayMoney(oneDayMoney);
        response.setTotalMoney(totalMoney);
        
        return response;
    }


   

   

    
}
