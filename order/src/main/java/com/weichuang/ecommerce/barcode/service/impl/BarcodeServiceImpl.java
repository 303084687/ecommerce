package com.weichuang.ecommerce.barcode.service.impl;

import com.alibaba.fastjson.JSON;
import com.weichuang.commons.*;
import com.weichuang.ecommerce.BarcodeUploadConfig;
import com.weichuang.ecommerce.RedisHelper;
import com.weichuang.ecommerce.barcode.entity.*;
import com.weichuang.ecommerce.barcode.entity.request.CodeAndTextRequest;
import com.weichuang.ecommerce.barcode.entity.request.TenantCodeStoreRequest;
import com.weichuang.ecommerce.barcode.entity.response.SalesPullNewcResponse;
import com.weichuang.ecommerce.barcode.entity.response.TenantCodeStoreResponse;
import com.weichuang.ecommerce.barcode.entity.response.UserCountResponse;
import com.weichuang.ecommerce.barcode.feign.AgentEntity;
import com.weichuang.ecommerce.barcode.feign.AgentResponse;
import com.weichuang.ecommerce.barcode.feign.IAgent;
import com.weichuang.ecommerce.barcode.repository.*;
import com.weichuang.ecommerce.barcode.service.BarcodeService;
import com.weichuang.ecommerce.barcode.util.BarCodeTools;
import com.weichuang.ecommerce.barcode.util.ImageTools;
import com.weichuang.ecommerce.coupon.entity.response.CompanyNumResponse;
import com.weichuang.ecommerce.coupon.feign.IUser;
import com.weichuang.ecommerce.coupon.feign.UserDetailResponse;
import com.weichuang.ecommerce.coupon.service.IReferRecommeService;
import com.weichuang.ecommerce.pay.resource.PayResources;
import com.weichuang.ecommerce.weixinPay.WeiXinProperties;
import com.weichuang.ecommerce.weixinPay.hb.ReadPackUtils;
import com.weichuang.ecommerce.weixinPay.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.List;

/**
 * <p>ClassName:BarcodeServiceImpl</p>
 * <p>Company:伟业创投（北京）科技有限公司http://www.weichuangwuxian.com</p>
 * <p>Description:二维码和文字合成</p>
 * <p>author:zhanghongsheng</p>
 * <p>2017/10/11 17:55</p>
 **/
@Service
public class BarcodeServiceImpl implements BarcodeService {

    private static final Logger log = LoggerFactory.getLogger(PayResources.class);
    @Value("${webchat.h5NetworkUrl}")
    private String h5NetworkUrl;
    @Autowired
    private ITenantCodeStoreDao tenantStoreDao;
    @Autowired
    private RedisHelper helper;
    @Autowired
    private RedisHelper redisHelper;
    @Autowired
    private ISalesPullNewcDao pullNewcDao;
    @Autowired
    private ISalesRedEnvelopActiviDao redEnvelopActiviDao;
    @Autowired
    private ISalesRedEnvelopeReceDao salesRedEnvelopeReceDao;
    @Autowired
    private WeiXinProperties weiXinProperties;
    @Autowired
    private ISalesPullNewSetDao salesPullNewSetDao;
    @Autowired
    private IUser userService;
    @Autowired
    private BarcodeUploadConfig barcodeUploadConfig;
    @Autowired
    private IAgent agentFeign;
    // 优惠券service
    @Autowired
    private IReferRecommeService recommeService;
    @Transactional(rollbackFor = ServiceException.class)
    /**
     * req:实体参数
     * hasimg:true:有 false：无（二维码）
     */
    public TenantCodeStoreResponse addBarCode(TenantCodeStoreRequest req,boolean hasimg) throws ServiceException {
        log.info("addBarCode req="+ JSON.toJSONString(req)+",hasimg="+hasimg);
        Response<TenantCodeStoreResponse> responseEntity=null;
        TenantCodeStoreEntity entity=new TenantCodeStoreEntity();
        entity.setCodeType(req.getCodeType());
        entity.setUserAgentId(req.getUserAgentId());
        entity.setAddressUrl(req.getAddressUrl());
        if(StringUtils.isNotEmpty(req.getOtherData()))
        {
            entity.setOtherData(req.getOtherData());
        }
        entity.setState(1);
        String uuid= UUID.randomUUID().toString();
        entity.setCodeKey(uuid);
        entity.setActionType(req.getActionType());
        ByteArrayOutputStream bo=new ByteArrayOutputStream();
        try{
            if(req.getCodeType()!=null &&req.getCodeType()==1 ||req.getCodeType()==2){//只有健身房和门店才对nx进行判断
                log.info("addBarCode codeType="+req.getCodeType()+",userAgentId="+(req.getUserAgentId()==null?0:req.getUserAgentId()));
                TenantCodeStoreEntity paramEntity=new TenantCodeStoreEntity();
                paramEntity.setUserAgentId(req.getUserAgentId());
                paramEntity.setState(1);
                paramEntity.setCodeType(req.getCodeType());
                //查询数据库是否存在对应记录
                TenantCodeStoreEntity entity1=tenantStoreDao.selectByIdTypeStateOne(paramEntity);
                    if(req.getNx()==1){
                        log.info("addBarCode nx=1");
                        //能查到，返回旧的
                        if(entity1!=null &&(entity1.getBid()!=null &&entity1.getBid()>0))
                        {
                            TenantCodeStoreResponse res=new TenantCodeStoreResponse();
                            res.setEntity(entity1);
                            return res;
                        }
                    }
                    else if(req.getNx()==2){
                        log.info("addBarCode nx=2");
                        //数据库存在记录，就把老数据状态更新为无效
                        if(entity1!=null &&entity1.getBid()!=null &&(entity1.getCodeType()==1 ||entity1.getCodeType()==2))
                        {
                            TenantCodeStoreEntity paramEntity1=new TenantCodeStoreEntity();
                            paramEntity1.setBid(entity1.getBid());
                            paramEntity1.setState(0);
                            tenantStoreDao.updateByPrimaryKeySelective(paramEntity1);
                            //清除redis中信息
                            redisHelper.del(entity1.getCodeKey());
                        }
                    }
            }
            String reqUrl=req.getStoreData();
            String endWith="action=scand&codekey="+uuid;
            if(reqUrl.indexOf("?")>0) {
                req.setStoreData(req.getStoreData()+"&"+endWith);
            }
            else{
                req.setStoreData(req.getStoreData()+"?"+endWith);
            }
            entity.setStoreData(req.getStoreData());
            //保存并返回新记录
            if(hasimg){
                int codeType=req.getCodeType();
                int userAgentId=req.getUserAgentId();
                BufferedImage bufImg= BarCodeTools.createImage(req.getStoreData(),430,430,codeType);
                BufferedImage bgImg=null;
                if(codeType==1){
                    String companyName="";
                    int companyId=0;
                    String companyRedisKey="addBarCode:company:"+userAgentId;
                    if(redisHelper.exists(companyRedisKey)){
                        String jsonstr=redisHelper.get(companyRedisKey).toString();
                        Map jsonMap=JSON.parseObject(jsonstr);
                        companyName=jsonMap.get("agentName")==null?"":jsonMap.get("agentName").toString();
                        companyId=jsonMap.get("id")==null?0:Integer.parseInt(jsonMap.get("id").toString());
                    }
                    else{
                        Response<AgentResponse> response=agentFeign.getAgentInfoByAgentId(userAgentId);
                        int code=response.getCode();
                        if(code!=0){
                            throw new ServiceException(50001,"barcodeService agentFeign.getAgentInfoByAgentId error");
                        }
                        AgentEntity agentEntity=response.getValue().getEntity();
                        if(agentEntity==null){
                            throw new ServiceException(50002,"barcodeService agentFeign.getAgentInfoByAgentId agentEntity is null");
                        }
                         companyName=agentEntity.getAgentName();

                    }
                    bgImg=ImageTools.createImage(companyName+"("+companyId+")",null,430);
                    ImageTools.compositeImage(bufImg,bgImg,bo);
                }
                else if(codeType==2){
                    int shopId=0;
                    String shopName="";
                    String companyName="";
                    int companyId=0;
                    String shopRedisKey="addBarCode:shop:"+userAgentId;
                    if(redisHelper.exists(shopRedisKey) ){
                        String jsonstr=redisHelper.get(shopRedisKey).toString();
                        Map jsonMap=JSON.parseObject(jsonstr);
                         companyName=jsonMap.get("companyName")==null?"":jsonMap.get("companyName").toString();
                         companyId=jsonMap.get("companyId")==null?0:Integer.parseInt(jsonMap.get("companyId").toString());
                         shopName=jsonMap.get("shopName")==null?"":jsonMap.get("shopName").toString();
                        shopId=jsonMap.get("shopId")==null?0:Integer.parseInt(jsonMap.get("shopId").toString());
                    }
                    else{
                        Response<AgentResponse> response=agentFeign.getAgentInfoByAgentId(userAgentId);
                        int code=response.getCode();
                        if(code!=0){
                            throw new ServiceException(50003,"barcodeService agentFeign.getAgentInfoByAgentId error");
                        }
                        AgentEntity agentEntity=response.getValue().getEntity();
                        if(agentEntity==null){
                            throw new ServiceException(50004,"barcodeService agentFeign.getAgentInfoByAgentId agentEntity is null");
                        }
                        shopName=agentEntity.getAgentName();
                        Integer parentId=agentEntity.getParentId();
                        Response<AgentResponse> response1=agentFeign.getCompanyInfoByAgentId(parentId);
                        int code1=response1.getCode();
                        if(code1!=0){
                            throw new ServiceException(50005,"barcodeService agentFeign.getAgentInfoByAgentId error");
                        }
                        AgentEntity agentEntity1=response1.getValue().getEntity();
                        if(agentEntity1==null){
                            throw new ServiceException(50006,"barcodeService agentFeign.getAgentInfoByAgentId agentEntity is null");
                        }
                        companyName=agentEntity1.getAgentName();

                    }
                    bgImg=ImageTools.createImage(companyName+"("+companyId+")",shopName+"("+shopId+")",430);
                    ImageTools.compositeImage(bufImg,bgImg,bo);
                }
                else{
                    ImageIO.write(bufImg,"png",bo);
                }
                InputStream in = new ByteArrayInputStream( bo.toByteArray());
                String imageName=barcodeUploadConfig.getBarcodePath()+uuid+".png";
                String uploadPath=AliyunOSS.uploadQrFile(imageName,in);
                log.info("userAgentId="+req.getUserAgentId()+",uploadPath="+uploadPath);
                /*
                BASE64Encoder encoder=new BASE64Encoder();
                byte[] bytes = bo.toByteArray();
                String imgbase64= encoder.encodeBuffer(bytes).trim();
                redisHelper.set(uuid,imgbase64);
                int actType=req.getActionType();
                if(actType==3){
                    helper.expire(uuid,24 * 60 * 60*7);//优惠券7天过期
                }
                else if(actType==2){
                    helper.expire(uuid,24 * 60 * 60*7);//分享7天过期
                }
                else if(actType==4){
                    helper.expire(uuid,24 * 60 * 60*1);//邀新1天过期
                }*/
            }
            entity.setCtime(new Date());
            if(StringUtils.isNotEmpty(req.getOtherData())){
                entity.setOtherData(req.getOtherData());
            }
            tenantStoreDao.addTenantCodeStore(entity);
            TenantCodeStoreResponse res=new TenantCodeStoreResponse();
            res.setEntity(entity);
            return res;
        }catch(Exception ex)
        {
            log.error("addBarCode生成二维码错误",ex);
            throw  new ServiceException(500,"生成二维码错误！");
        }
    }
    /**
     *<p>Description:分享商品二维码生成</p>
     *<p>param userAgentId:销售id</p>
     *<p>param productId:商品id</p>
     *<p>param productCode:商品编码</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/23 10:12</p>
     *<p>return:</p>
     */
    @Transactional(rollbackFor = Exception.class)
    public TenantCodeStoreResponse addShareBarCode( int userAgentId, int productId,String productCode) throws ServiceException,Exception{
        if (userAgentId<=0) {
            throw new ServiceException(2001,"userAgentId参数错误");
        }
        if (productId<=0) {
            throw new ServiceException(2002,"productId参数错误");
        }
        if(org.apache.commons.lang.StringUtils.isEmpty(productCode)){
            throw new ServiceException(2003,"productCode参数错误");
        }
        String addressUrl="/product/info?productId="+productId+"&productCode="+productCode;
        String storeData=h5NetworkUrl+"/redirect1";
        TenantCodeStoreRequest req=new TenantCodeStoreRequest();
        req.setAddressUrl(addressUrl);
        req.setUserAgentId(userAgentId);
        req.setHasImg(true);
        req.setNx(2);
        req.setActionType(2);
        req.setCodeType(3);
        req.setStoreData(storeData);
        TenantCodeStoreResponse response=addBarCode(req,true);
        return response;
    }
    /**
     *<p>Description:生成修改收款人二维码</p>
     *<p>param userId:当前登录人id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/28 11:20</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public TenantCodeStoreResponse addReceiverBarCode( int userId) throws ServiceException,Exception{
        if (userId<=0) {
            throw new ServiceException(2001,"userId参数错误");
        }
        String addressUrl=h5NetworkUrl+"/index";
        String storeData=h5NetworkUrl+"/redirect1";
        TenantCodeStoreRequest req=new TenantCodeStoreRequest();
        req.setAddressUrl(addressUrl);
        req.setUserAgentId(userId);
        req.setHasImg(true);
        req.setNx(2);
        req.setActionType(5);
        req.setCodeType(5);
        req.setStoreData(storeData);
        TenantCodeStoreResponse response=addBarCode(req,true);
        return response;
    }
    public TenantCodeStoreResponse addInviteSaleBarCode( int userId) throws ServiceException,Exception{
        if (userId<=0) {
            throw new ServiceException(2001,"userId参数错误");
        }
        String addressUrl=h5NetworkUrl+"/index";
        String storeData=h5NetworkUrl+"/redirect1";
        TenantCodeStoreRequest req=new TenantCodeStoreRequest();
        req.setAddressUrl(addressUrl);
        req.setUserAgentId(userId);
        req.setHasImg(true);
        req.setNx(2);
        req.setActionType(6);
        req.setCodeType(6);
        req.setStoreData(storeData);
        TenantCodeStoreResponse response=addBarCode(req,true);
        return response;
    }
    /**
     *<p>Description:二维码生成</p>
     *<p>param userAgentId:</p>
     *<p>param mobile:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 10:12</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @Transactional(rollbackFor = Exception.class)
    public TenantCodeStoreResponse addInviteBarCode(TenantCodeStoreRequest req, String mobile) throws ServiceException,Exception {
        TenantCodeStoreResponse res=null;
        req.setStoreData(h5NetworkUrl+"/redirect1");
        int usercount=tenantStoreDao.selectUserCountByMobile(mobile);
        if (!PatternHelper.isMobile(mobile)) {
            throw new ServiceException(5001,"mobile format error");
        }
        UserRoleEntity userRoleEntity=tenantStoreDao.selectUserRoleDetailById(req.getUserAgentId());
        if(userRoleEntity==null) {
            throw new ServiceException(5002,"Sale's news is null!");
        }
        if(userRoleEntity.getRoleId()!= UserRoleCommonEnum.EMPLOYEE.getIndex()){
            throw new ServiceException(5003,"Sale's role is wrong!");
        }
        if(usercount>0) {
            throw new ServiceException(5004,"user is older,can not create invite new barcode!");
        }

        res=this.addBarCode(req,req.isHasImg());
        if(res.getEntity()==null || org.apache.commons.lang.StringUtils.isEmpty(res.getEntity().getCodeKey())){
            throw new ServiceException(5005,"server is error,can not create barcode!");
        }
        String codekey=res.getEntity().getCodeKey();
        redisHelper.expire(codekey,3600*60*24);
        SalesPullNewcEntity pullNewcEntity=new SalesPullNewcEntity();
        pullNewcEntity.setCodeKey(res.getEntity().getCodeKey());
        pullNewcEntity.setSaleId(req.getUserAgentId());
        pullNewcEntity.setInvitetype(3);
        pullNewcEntity.setMobile(mobile);
        pullNewcEntity.setState(0);
        pullNewcDao.insertNew(pullNewcEntity);
        return res;
    }

    /**
     *<p>Description:通过codekey查找邀新信息</p>
     *<p>param codekey:</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 14:16</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    public SalesPullNewcResponse getInviteNewBycode(String codekey)throws ServiceException,Exception {

        SalesPullNewcResponse response=null;
        try{
            if(StringUtils.isEmpty(codekey))
            {
                throw new ServiceException(5003,"codekey can not null!");
            }
            SalesPullNewcEntity entity=pullNewcDao.selectByCodekey(codekey);
            response=new SalesPullNewcResponse();
            response.setEntity(entity);
        }catch(Exception ex)
        {
            log.error(ex.getMessage());
            throw ex;
        }
        return response;
    }


    @Override
    public TenantCodeStoreResponse getByCodeKey(String codeKey) throws ServiceException {
        TenantCodeStoreEntity paramEntity=new TenantCodeStoreEntity();
        TenantCodeStoreResponse res=new TenantCodeStoreResponse();
        paramEntity.setCodeKey(codeKey);
        try{
            TenantCodeStoreEntity entity=tenantStoreDao.selectByIdTypeStateOne(paramEntity);
            res.setEntity(entity);
        }catch(Exception ex)
        {
            log.error("getByCodeKey获取信息错误",ex);
            throw  new ServiceException(500,"获取信息异常！");
        }
        return res;
    }

    /**
     * 二维码和文字合成
     * @param request 配置参数
     * @param response 输出流
     * @throws Exception
     */
    public void mergeImage(CodeAndTextRequest request, HttpServletResponse response) throws Exception{
        BufferedImage bufImg= BarCodeTools.createImage(request.getCodeUrl(),request.getCodeImageSize(),request.getCodeImageSize(),1);
        Font font=new Font(request.getTextFontName(), request.isTextIsBold()?1:0, request.getTextFontSize());
        String color=request.getTextColor();
        Class<?>  clazz=Class.forName("java.awt.Color");
        Field f=clazz.getDeclaredField(color);
        f.setAccessible(true);
        Color c=(Color)f.get(clazz.getClass());
        BufferedImage bgImg= ImageTools.createImage(request.getText(),font, c,request.getCodeImageSize(),request.getCodeImageSize()+request.getBgImageMarginTop()+request.getTextFontSize());
        OutputStream os= response.getOutputStream();
        ImageTools.compositeImage(bufImg,bgImg,os);
        os.flush();
    }
    /**
     *<p>Description:修改状态</p>
     *<p>param codekey:标识主键</p>
     *<p>param state:状态</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/20 15:12</p>
     *<p>return:</p>
     *<p>throws: </p>
     */
    @Transactional(rollbackFor= {Exception.class,ServiceException.class})
    public int updateInviteNew(String codekey,Integer state,Integer userId) throws ServiceException,Exception{
        SalesPullNewcEntity entity=pullNewcDao.selectByCodekey(codekey);
        if(entity==null ||entity.getId()==null){
            throw new ServiceException(5004,"codekey无效,没有对应信息");
        }
        if(state==1){
            this.arrivedRedEnvelop(entity.getSaleId());//更新达到的红包等级
        }
       int  flag= pullNewcDao.updateInviteNew(codekey,state,userId);
        return flag;
    }
    /**
     *<p>Description:查询邀新红包图</p>
     *<p>param userId:店员id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/27 17:40</p>
     */
    public InviteNewEnvelopResponse getByUserId(Integer userId) throws ServiceException, Exception {
        if(userId==null ||userId<=0){
            throw new ServiceException(6001,"userId参数不能为空!");
        }
        SalesRedEnvelopActiviti activiti=redEnvelopActiviDao.selectByState(1);
        if(activiti==null || activiti.getId()==null ||activiti.getId()<=0){
            throw new ServiceException(6002,"查询不到有效的红包活动!");
        }
        int aid=activiti.getId();
        int pullNewCount=pullNewcDao.selectInviteNewCount(aid,userId);//查询邀新个数
        SalesPullNewSetEntity arrivedSet=salesPullNewSetDao.findGtPullNewNum(aid,pullNewCount);//查询达到的设置
        InviteNewEnvelopResponse res=new InviteNewEnvelopResponse();
        if(arrivedSet!=null &&arrivedSet.getId()>0){
            int arriveNum=arrivedSet.getPullNewNum();
            res.setSalesPullNewNum(pullNewCount);
            int minusCount=arriveNum-pullNewCount;
            res.setSalesDifferenceNum(minusCount);//差多少人
            res.setSalesGetRedEnvelop(arrivedSet.getPullNewIncome());//差得到的红包
        }
        List<SalesPullNewSetEntity> setList=salesPullNewSetDao.findListByAid(aid,userId);
        if(setList!=null &&!setList.isEmpty()){
            res.setSetList(setList);
        }
        return res;
    }
    @Transactional(rollbackFor ={ Exception.class,ServiceException.class})
    public int insertNew(SalesPullNewcEntity entity)throws ServiceException,Exception{
        if(entity.getState()==1){
           this.arrivedRedEnvelop(entity.getSaleId());
        }
        return pullNewcDao.insertNew(entity);
    }
    /**
     *<p>Description:更新达到红包等级</p>
     *<p>param salesId:销售id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/28 16:10</p>
     */
    private void  arrivedRedEnvelop(int salesId) throws ServiceException,Exception{
        SalesRedEnvelopActiviti activiti=redEnvelopActiviDao.selectByState(1);
        if(activiti==null){
            throw new ServiceException(4001,"查询不到红包活动信息");
        }
        int pullNewCount=pullNewcDao.selectInviteNewCount(activiti.getId(),salesId);//查询邀新个数
        SalesPullNewSetEntity arrivedSet=salesPullNewSetDao.findEqPullNewNum(activiti.getId(),pullNewCount+1);//加1达到的红包
        //查询到达到的邀新红包
        if(arrivedSet!=null &&arrivedSet.getId()>0){
            int rid=arrivedSet.getId();
            int count=salesRedEnvelopeReceDao.selectCountBySalesAndSet(salesId,rid);
            if(count==0){//没有记录才保存
                SalesRedEnvelopeRece envelopeRece=new SalesRedEnvelopeRece();
                envelopeRece.setSalesId(salesId);
                envelopeRece.setState(0);
                envelopeRece.setRid(arrivedSet.getId());
                salesRedEnvelopeReceDao.insertSelective(envelopeRece);
            }
        }
    }
    /**
     *<p>Description:红包领取</p>
     *<p>param salesId:销售id</p>
     *<p>param rid:设置id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/11/28 16:53</p>
     */
    @Transactional(rollbackFor = {Exception.class,ServiceException.class})
    public int RedEnvelopRece(HttpServletRequest req, int salesId, int rid){
        SalesPullNewSetEntity entity=salesPullNewSetDao.selectById(rid);
        if(entity==null){
            throw new ServiceException(8001,"不能获取邀新红包设置信息！");
        }
        SalesRedEnvelopeRece rece=salesRedEnvelopeReceDao.selectBySalesAndSet(salesId,rid);
        if(rece==null){
            throw new ServiceException(8002,"不能获取待领取的红包设置信息！");
        }
        if(rece.getState()==0){
            Response<UserDetailResponse> userRes=userService.getUserDetailByUserId(salesId);
            if(userRes.getCode()!=0){
                throw new ServiceException(8003,"不能获取当前销售详细信息");
            }
            UserDetailResponse userDetail=userRes.getValue();
            if(userDetail==null){
                throw new ServiceException(8003,"不能获取当前销售详细信息");
            }
            String openId=userDetail.getOpenId();
            if(StringUtils.isEmpty(openId)){
                throw new ServiceException(8004,"不能获取当前销售的微信openId");
            }
            log.info("销售id="+salesId+"开始领取红包,红包设置id="+rid);
            SalesPullNewSetEntity entity1= salesPullNewSetDao.selectById(rid);
            int pull_num=entity1.getPullNewNum();
            BigDecimal pull_money=new BigDecimal(entity1.getPullNewNum());
            BigDecimal total_amount=new BigDecimal(entity1.getPullNewIncome()).setScale(2,BigDecimal.ROUND_DOWN);
            String wishing="感谢参加邀新红包活动！恭喜你获得了邀请"+pull_num+"人得"+String.valueOf(total_amount)+"元红包";
            try{
                boolean result = ReadPackUtils.sendredpack(req, String.valueOf(total_amount), "1", wishing, "邀新得红包活动",
                        "邀请新人越多，红包越多，加油！", openId, weiXinProperties.getMchId(),
                        weiXinProperties.getAppId(), weiXinProperties.getSendName(), weiXinProperties.getPaternerKey(),
                        weiXinProperties.getCertPath());
                if(result){
                    int effectNum= salesRedEnvelopeReceDao.updateReceState(salesId,rid,1);
                    log.info("销售id="+salesId+",红包设置id="+rid+",红包下发成功！修改状态="+effectNum);
                    if(effectNum==0){
                        log.error("销售id="+salesId+",红包设置id="+rid+",红包下发成功！修改状态失败，state="+effectNum);
                    }
                    return 1;
                }
                else{
                    log.error("销售id="+salesId+",红包设置id="+rid+",红包下发失败！微信返回错误！");
                }
            }catch(Exception ex) {
                ex.printStackTrace();
                log.error("销售id="+salesId+",红包设置id="+rid+",红包下发失败！",ex);
            }
        }
        return 0;
    }
    /**
     *<p>Description:通过id查询发送邀请数量</p>
     *<p>param type:1 店员  2 门店 3 公司</p>
     *<p>param userId:店员或门店id</p>
     *<p>author:zhanghongsheng</p>
     *<p>date:2017/12/22 15:13</p>
     *<p>return:</p>
     */
    public UserCountResponse getInviteCount(int type, int userId){
        UserCountEntity entity=tenantStoreDao.selectInviteCountByUserId(type,userId);
        UserCountResponse response=new UserCountResponse();
        response.setEntity(entity);
        return response;
    }
}
