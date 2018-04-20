package com.weichuang.ecommerce.barcode.service.impl;

import com.netflix.discovery.converters.Auto;
import com.weichuang.commons.*;
import com.weichuang.ecommerce.barcode.entity.*;
import com.weichuang.ecommerce.barcode.entity.response.BalanceWithdrawResponse;
import com.weichuang.ecommerce.barcode.entity.response.SalesIncomeDetailResponse;
import com.weichuang.ecommerce.barcode.entity.response.TenantCodeStoreResponse;
import com.weichuang.ecommerce.barcode.repository.ISalesShareEnvelopDao;
import com.weichuang.ecommerce.barcode.repository.ISalesShareEnvelopSetDao;
import com.weichuang.ecommerce.barcode.resource.SalesShareEnvelopResource;
import com.weichuang.ecommerce.barcode.service.BarcodeService;
import com.weichuang.ecommerce.barcode.service.ISalesShareEnvelopService;
import com.weichuang.ecommerce.coupon.feign.IUser;
import com.weichuang.ecommerce.coupon.feign.UserDetailResponse;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.hb.ReadPackUtils;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
* <p>ClassName:SalesShareEnvelopServiceImpl</p>
* <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
* <p>Description:</p>
* <p>author:zhanghongsheng</p>
* <p>2018/1/2 10:33</p>
**/
@Service
public class SalesShareEnvelopServiceImpl implements ISalesShareEnvelopService {
    @Autowired
    private ISalesShareEnvelopSetDao envelopSetDao;
    @Autowired
    private ISalesShareEnvelopDao envelopDao;

    @Autowired
    private WeiXinProperties weiXinProperties;
    @Autowired
    private IUser iUserFeign;
    @Autowired
    private BarcodeService barcodeService;
    private static final Logger log = LoggerFactory
            .getLogger(SalesShareEnvelopResource.class);
    /**
     *<p>Description:查询销售可用的分享的红包次数</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:33</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public Response<Integer> getShareEnvelopCount(int saleId) throws ServiceException,Exception{

        Response<Integer> res=null;
        SalesShareEnvelopeSet envelopeSet =envelopSetDao.selectUserSetByState(1);
        if(envelopeSet==null){
            throw new ServiceException(80009,"SalesShareEnvelopeSet is null ");
        }
        int setCount=envelopeSet.getShareCount();
        if(setCount<=0){
            throw new ServiceException(80010,"SalesShareEnvelopeSet's sharecount is zero ");
        }
        int nowCount=envelopDao.selectCountByUserId(saleId);
        int canUseCount=(setCount-nowCount)<=0?0:(setCount-nowCount);
        res=new Response<>(Result.SUCCESS,canUseCount);
        return res;
    }
    /**
     *<p>Description:分享领红包</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 10:33</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @Transactional(rollbackFor = {ServiceException.class,Exception.class})
    public Response<String> getShareEnvelop(HttpServletRequest request, int saleId,String codekey) throws ServiceException,Exception{
        if(saleId<=0){
            throw new ServiceException(80001,"getShareEnvelop saleId is error");
        }
        Response<UserDetailResponse> userResponse= iUserFeign.getUserDetailByUserId(saleId);
        int userResCode=userResponse.getCode();
        if(userResCode!=0){
            throw new ServiceException(80002,"getShareEnvelop getUserDetailByUserId error:code="+userResCode+",msg="+userResponse.getMessage());
        }
        UserDetailResponse userDetail=userResponse.getValue();
        if(userDetail==null){
            throw new ServiceException(80003,"getShareEnvelop getUserDetailByUserId userDetail is null");
        }
        Response<Integer> res=getShareEnvelopCount(saleId);
        int canUseCount=res.getValue();
        int flag=0;
        if(canUseCount<=0){
            throw new ServiceException(80004,"getShareEnvelop canUseCount=0 ");
        }
        if(StringUtils.isEmpty(codekey)){
            throw new ServiceException(80005,"getShareEnvelop codekey is error");
        }
        TenantCodeStoreResponse tenantStore=barcodeService.getByCodeKey(codekey);
        if(tenantStore==null){
            throw new ServiceException(80006,"getShareEnvelop codekey="+codekey+" can not find TenantCodeStoreResponse");
        }
        int useCodekeyCount=envelopDao.selectCountByUserIdCodekey(saleId,codekey);
        if(useCodekeyCount>0){
            throw new ServiceException(80007,"getShareEnvelop codekey can't be used ");
        }
        SalesShareEnvelopeSet envelopeSet=envelopSetDao.selectUserSetByState(1);
        if(envelopeSet==null){
            throw new ServiceException(80008,"getShareEnvelop SalesShareEnvelopeSet is null");
        }
        String openId=userDetail.getOpenId();
        BigDecimal maxV=envelopeSet.getShareMaxIncome();
        BigDecimal minV=envelopeSet.getShareMinIncome();
        BigDecimal total_amount= RandomUtil.nextBigDecimal(minV,maxV);
        SalesShareEnvelope shareEnvelope=new SalesShareEnvelope();
        shareEnvelope.setCodekey(codekey);
        shareEnvelope.setOpenid(openId);
        shareEnvelope.setUserid(saleId);
        shareEnvelope.setSetid(envelopeSet.getId());
        shareEnvelope.setShareIncome(total_amount);
        shareEnvelope.setState(0);//未提现
        envelopDao.saveShareEnvelop(shareEnvelope);
        Response<String> resultRes=new Response<>(Result.SUCCESS,String.valueOf(total_amount));
        return resultRes;
    }
    /**
     *<p>Description:分享红包提现</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 11:41</p>
     *<p>return:</p>
     */
    @Transactional(rollbackFor = {Exception.class,ServiceException.class})
    public Response<Integer> getShareEnvelopWithdraw(HttpServletRequest request,int saleId) throws ServiceException,Exception{

        if(saleId<=0){
            throw new ServiceException(80011,"getShareEnvelopWithdraw saleId is error");
        }
        Response<UserDetailResponse> userResponse= iUserFeign.getUserDetailByUserId(saleId);
        int userResCode=userResponse.getCode();
        if(userResCode!=0){
            throw new ServiceException(80012,"getShareEnvelopWithdraw getUserDetailByUserId error:code="+userResCode+",msg="+userResponse.getMessage());
        }
        UserDetailResponse userDetail=userResponse.getValue();
        if(userDetail==null){
            throw new ServiceException(80013,"getShareEnvelopWithdraw getUserDetailByUserId userDetail is null");
        }
        List<SalesShareEnvelope> balance_list=envelopDao.selectAllIncomeList(saleId);
        if(balance_list==null||balance_list.isEmpty()){
            throw new ServiceException(80014,"getShareEnvelopWithdraw balance_list  is null");
        }
        BigDecimal balanceSum=envelopDao.selectAllIncome(saleId);//总金额
        BigDecimal limit_get=new BigDecimal(1);
        if(balanceSum.compareTo(limit_get)==-1){
            throw new ServiceException(80015,"getShareEnvelopWithdraw balanceSum  must >1");
        }
        SalesShareEnvelopeWithdraw withdraw=new SalesShareEnvelopeWithdraw();
        withdraw.setIncome(balanceSum);
        withdraw.setUserid(saleId);
        withdraw.setOpenid(userDetail.getOpenId());
        //保存提现表
        envelopDao.saveSalesEnvelopWithdraw(withdraw);

        List<Integer> ids=new ArrayList<Integer>();
        List<SalesShareEnvelopeWithdrawDetail> withdrawDetailList=new ArrayList<SalesShareEnvelopeWithdrawDetail>();
        for(SalesShareEnvelope envelope:balance_list){
            ids.add(envelope.getId());
            SalesShareEnvelopeWithdrawDetail detail=new SalesShareEnvelopeWithdrawDetail();
            detail.setWithdrawId(withdraw.getId());
            detail.setGetEnvelopId(envelope.getId());
            withdrawDetailList.add(detail);
        }
        //更新所有提现得状态
        envelopDao.updateAllIncomeState(ids);
        //保存提现详细
        envelopDao.insertDetailList(withdrawDetailList);
        String wishing="感谢参加分享得红包活动！";
        //下发红包
        boolean result = ReadPackUtils.sendredpack(request, String.valueOf(balanceSum), "1", wishing, "分享得红包活动",
                "每天分享得红包", userDetail.getOpenId(), weiXinProperties.getMchId(),
                weiXinProperties.getAppId(), weiXinProperties.getSendName(), weiXinProperties.getPaternerKey(),
                weiXinProperties.getCertPath());
        if(!result){
            throw new ServiceException(80016,"getShareEnvelopWithdraw redEnvelop post fail result=false" );
        }
        Response<Integer> resultRes=new Response<>(Result.SUCCESS,1);
        return resultRes;
    }
    /**
     *<p>Description:分享红包提现列表</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 17:28</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public Response<SalesIncomeDetailResponse> selectIncomeDetailList(int saleId) throws ServiceException,Exception{
        if(saleId<=0){
            throw new ServiceException(80017,"selectIncomeDetailList saleId is error");
        }
        Response<UserDetailResponse> userResponse= iUserFeign.getUserDetailByUserId(saleId);
        int userResCode=userResponse.getCode();
        if(userResCode!=0){
            throw new ServiceException(80018,"selectIncomeDetailList getUserDetailByUserId error:code="+userResCode+",msg="+userResponse.getMessage());
        }
        UserDetailResponse userDetail=userResponse.getValue();
        if(userDetail==null){
            throw new ServiceException(80019,"selectIncomeDetailList getUserDetailByUserId userDetail is null");
        }
        String nickName=userDetail.getNickName()==null?"":userDetail.getNickName().toString();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(nickName)){
            nickName= EncryptUtil.getFromBase64(nickName);
        }
        LinkedHashMap<String,List<SalesIncomeDetailEntity>> linkedList=null;
        List<SalesIncomeDetailEntity> incomeList=envelopDao.selectIncomeDetail(saleId);
        if(incomeList!=null &&!incomeList.isEmpty()){
            linkedList=new LinkedHashMap<String,List<SalesIncomeDetailEntity>>();
            for(SalesIncomeDetailEntity entity:incomeList){
                String come_date=entity.getComDate();
                if(linkedList.containsKey(come_date)){
                    List<SalesIncomeDetailEntity> comelist=linkedList.get(come_date);
                    comelist.add(entity);
                }
                else{
                    List<SalesIncomeDetailEntity> comelist=new ArrayList<SalesIncomeDetailEntity>();
                    entity.setNickName(nickName);
                    comelist.add(entity);
                    linkedList.put(come_date,comelist);
                }
            }
        }
        SalesIncomeDetailResponse response=new SalesIncomeDetailResponse();
        response.setLinkedMap(linkedList);
        Response<SalesIncomeDetailResponse> res=new Response<>(Result.SUCCESS,response);
        return res;
    }
    /**
     *<p>Description:可提现余额</p>
     *<p>param saleId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2018/1/4 17:57</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public Response<BalanceWithdrawResponse> selectBalanceWithdraw(int saleId) throws ServiceException,Exception{
        if(saleId<=0){
            throw new ServiceException(80020,"selectIncomeDetailList saleId is error");
        }
        Response<UserDetailResponse> userResponse= iUserFeign.getUserDetailByUserId(saleId);
        int userResCode=userResponse.getCode();
        if(userResCode!=0){
            throw new ServiceException(80021,"selectIncomeDetailList getUserDetailByUserId error:code="+userResCode+",msg="+userResponse.getMessage());
        }
        UserDetailResponse userDetail=userResponse.getValue();
        if(userDetail==null){
            throw new ServiceException(80022,"selectIncomeDetailList getUserDetailByUserId userDetail is null");
        }
        BigDecimal allIncome=envelopDao.selectAllIncome(saleId);
        BigDecimal limit=new BigDecimal(1);
        BalanceWithdrawResponse res=new BalanceWithdrawResponse();
        res.setBalance(allIncome);
        if(allIncome.compareTo(limit)==-1){
            res.setCanUse(false);
        }
        else{
            res.setCanUse(true);
        }
        Response<BalanceWithdrawResponse> resp=new Response<>(Result.SUCCESS,res);
        return resp;
    }
}
