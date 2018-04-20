package com.weichuang.ecommerce.coupon.service.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.weichuang.commons.Response;
import com.weichuang.commons.Result;
import com.weichuang.commons.ServiceException;
import com.weichuang.ecommerce.coupon.entity.Coupon;
import com.weichuang.ecommerce.coupon.entity.CouponType;
import com.weichuang.ecommerce.coupon.entity.request.CouponAddRequest;
import com.weichuang.ecommerce.coupon.entity.response.CouponListEntity;
import com.weichuang.ecommerce.coupon.entity.response.CouponListResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponNumResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponResponse;
import com.weichuang.ecommerce.coupon.entity.response.CouponUsedEntity;
import com.weichuang.ecommerce.coupon.entity.response.CouponUsedListResponse;
import com.weichuang.ecommerce.coupon.feign.IUser;
import com.weichuang.ecommerce.coupon.feign.UserDetailEntity;
import com.weichuang.ecommerce.coupon.feign.UserDetailListResponse;
import com.weichuang.ecommerce.coupon.feign.UserDetailResponse;
import com.weichuang.ecommerce.coupon.responsitory.ICouponDao;
import com.weichuang.ecommerce.coupon.responsitory.ICouponTypeDao;
import com.weichuang.ecommerce.coupon.service.ICouponService;
import com.weichuang.ecommerce.coupon.thread.CouponThread;
import com.weichuang.ecommerce.coupon.thread.ThreadManager;
import com.weichuang.ecommerce.coupon.util.CouponHelper;
import com.weichuang.ecommerce.coupon.util.EncryptHelper;

/**
 * <p>ClassName: CouponServiceImpl.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券service实现 </p>
 * <p>author wanggongliang</p>
 * <p>2017年10月24日 下午3:41:45</p>
 */
@Service
@SuppressWarnings("all")
public class CouponServiceImpl implements ICouponService {
    // 注册优惠券服务接口
    @Autowired
    private ICouponDao couponDao;

    @Autowired
    private ICouponTypeDao couponTypeDao;

    @Autowired
    private IUser iUser;

    // 批量生成优惠券,使用场景 固定日期，实体优惠券(暂时未使用)
    @Override
    @Transactional
    public int addCoupon(CouponAddRequest request) throws ServiceException, Exception {
        // 判断生成的数量
        int number = request.getCreateNum() == 0 ? 1 : request.getCreateNum();
        // 查询优惠券类型信息
        CouponType couponType = couponTypeDao.getCouponTypeById(request.getTypeCode(), 0);
        // 循环生成数据
        List<Coupon> list = new ArrayList<Coupon>();
        for (int i = 0; i < number; i++) {
            Coupon coupon = new Coupon();
            // 根据优惠券类型信息填充数据
            coupon.setTypeCode(request.getTypeCode());
            // 优惠券号
            coupon.setCouponCode(CouponHelper.createUUID());
            // 密码
            String pass = java.net.URLEncoder.encode(CouponHelper.createUUID(), "utf-8");
            coupon.setCouponPass(EncryptHelper.toHexString(EncryptHelper.encryptCoupon(pass)));
            coupon.setStartTime(couponType.getStartTime());
            coupon.setExpireTime(couponType.getExpireTime());
            coupon.setHasSend(request.getHasSend());
            coupon.setCreator(request.getCreator());
            coupon.setRemark(request.getRemark());
            list.add(coupon);
        }
        return couponDao.addCoupon(list);
    }

    /**
     * @Title:bindCoupon  
     * @Description: b-c,c-c新人推荐赠送优惠券,规则B-C:C必须是新用户且只能领取一次,下单成功后B获取推荐奖
     * C1-C2规则:c2必须是新人,C2获取3张优惠券,c1获取2张优惠券,同理c2-c3:c3获取3张c2获取两张
     * @param openId 被推荐人的openId,需要绑定
     * @param referId 推荐人的主键
     * @param referOpenId 推荐人的openId
     * @param typeCode 发放的优惠券批次
     * @param exceptTypeCode 非公共批次
     * @param type 1C-C 2B-C
     * @return
     */
    @Override
    @Transactional
    public void bindCoupon(String openId, int referId, String referOpenId, String typeCode, String exceptTypeCode,
            int type) throws ServiceException, Exception {
        // 首先查询用户是否为新用户UserResponse user = iUser.getUserByOpenId(openId,
        // 1).getValue();
        int count = 0;
        // 是否有领取数量限制的
        String limitTypeCode = "";
        // 被推荐者数据集合
        List<Coupon> couponList = new ArrayList<Coupon>();
        // type为1时候为C-C
        if (type == 1) {
            // 因为c1-c2在满足条件的基础上C2领取3张c1获取1张,所以根据exceptTypeCode来判断不相同批次的那张,c1和c2获取的优惠券不一样
            if (StringUtils.isNotBlank(exceptTypeCode)) {
                typeCode += exceptTypeCode;
            }
            // 根据类型批次号查询相关信息
            List<CouponType> couponTypeList = couponTypeDao.getCouponTypeListByIds(typeCode);
            // 推荐人的优惠券集合
            List<Coupon> referCouponList = new ArrayList<Coupon>();
            if (couponTypeList.size() > 0) {
                for (int i = 0; i < couponTypeList.size(); i++) {
                    // 查询出有限定领取总数量的优惠券批次
                    if (couponTypeList.get(i).getReceiveCounts() > 0) {
                        limitTypeCode += couponTypeList.get(i).getTypeCode() + ",";
                    }
                    // 查询限制条件并绑定
                    if (!couponTypeList.get(i).getTypeCode().equals(exceptTypeCode)) {
                        CouponType receiveLimit = personCount(couponTypeList.get(i), 0, openId);
                        if (null != receiveLimit) {
                            Coupon coupon = addCoupon(couponTypeList.get(i), openId, null, referId);
                            couponList.add(coupon);
                        }
                    }
                }
                // 执行给被推荐者发送优惠券
                count = couponDao.bindCoupon(couponList);
                // 给推荐者发送优惠券
                for (int i = 0; i < couponTypeList.size(); i++) {
                    // 去掉不相同批次的优惠券
                    if (couponTypeList.get(i).getTypeCode().equals(exceptTypeCode)) {
                        Coupon coupon = addCoupon(couponTypeList.get(i), referOpenId, referId, referId);
                        referCouponList.add(coupon);
                    }
                }
                // 判断推荐不为空，才发放优惠券
                if (referCouponList.size() > 0) {
                    couponDao.bindCoupon(referCouponList);
                }
                if (count > 0) {
                    // 要是有限制领取总数量的批次执行更新剩余数量的方法
                    if (StringUtils.isNotBlank(limitTypeCode)) {
                        couponTypeDao.updateReceiveCount(limitTypeCode, 1);
                    }
                }
            }
        }
        // type为2是B-C场景
        if (type == 2) {
            // 根据类型批次号查询相关信息
            List<CouponType> couponTypeList = couponTypeDao.getCouponTypeListByIds(typeCode);
            if (couponTypeList.size() > 0) {
                for (int i = 0; i < couponTypeList.size(); i++) {
                    // 查询出有限定领取总数量的优惠券批次
                    if (couponTypeList.get(i).getReceiveCounts() > 0) {
                        limitTypeCode += couponTypeList.get(i).getTypeCode() + ",";
                    }
                    // 查询限制条件并绑定
                    CouponType receiveLimit = personCount(couponTypeList.get(i), 0, openId);
                    if (null != receiveLimit) {
                        Coupon coupon = addCoupon(couponTypeList.get(i), openId, null, referId);
                        couponList.add(coupon);
                    }
                }
                // 执行绑定的方法
                count = couponDao.bindCoupon(couponList);
                if (count > 0) {
                    // 要是有限制领取总数量的批次执行更新剩余数量的方法
                    if (StringUtils.isNotBlank(limitTypeCode)) {
                        couponTypeDao.updateReceiveCount(limitTypeCode, 1);
                    }
                }
            }
        }
    }

    /**
     * @Title:addCoupon  
     * @Description:增加通用方法
     * @param couponType
     * @param openId
     * @return
     * @throws Exception
     */
    public Coupon addCoupon(CouponType couponType, String openId, Integer userId, Integer saleId) throws Exception {
        Coupon coupon = new Coupon();
        // 根据优惠券类型信息填充数据
        coupon.setTypeCode(couponType.getTypeCode());
        // 优惠券号
        coupon.setCouponCode(CouponHelper.createUUID());
        // 密码
        String pass = java.net.URLEncoder.encode(CouponHelper.createUUID(), "utf-8");
        coupon.setCouponPass(EncryptHelper.toHexString(EncryptHelper.encryptCoupon(pass)));
        // 绑定用户openId
        coupon.setOpenId(openId);
        coupon.setUserId(userId);
        coupon.setSaleId(saleId);
        // 根据类型开始和结束时间来计算优惠券的有效期
        if (couponType.getTimeType() == 1) {// 固定时间
            coupon.setStartTime(couponType.getStartTime());
            coupon.setExpireTime(couponType.getExpireTime());
        } else {// 动态时间 开始时间为领券时间结束时间为领取后的指定天数
            coupon.setStartTime(new Date());// 开始时间为领券的时间
            // 结束时间为类型中的指定日期,从当天开始算起
            LocalDateTime endTime = LocalDateTime.now().plusDays(couponType.getDay() - 1);
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = endTime.atZone(zone).toInstant();
            coupon.setExpireTime(Date.from(instant));
        }
        coupon.setRemark(couponType.getDescription());// 优惠券描述
        return coupon;
    }

    /**
     * @Title: personCount 
     * @Description: 用于计算领取数量通用方法
     * @param @return
     * @param @throws ApiException    设定文件 
     * @return CouponReceiveLimit    返回类型 
     * @throws
      */
    public CouponType personCount(CouponType couponType, Integer userId, String openId) throws ServiceException {
        if (couponType.getReceiveCounts() == 0) {// 领取总数量无限制
            if (couponType.getPersonalCounts() == 0) {// 个人领取总量无限制
                return couponType;
                // 个人领取总量有限制
            } else {
                // 查询已经领取的优惠券
                int count = couponDao.getPersonCouponCount(couponType.getTypeCode(), userId, openId);
                if (count >= couponType.getPersonalCounts()) {
                    throw new ServiceException(Result.COUPON_EXCE_NUM.getCode(), Result.COUPON_EXCE_NUM.getMessage());
                } else {
                    return couponType;
                }
            }
            // 领取总数量有限制
        } else {
            // 先查询剩余数量
            if (couponType.getReceiveSurplus() <= 0) {
                throw new ServiceException(Result.COUPON_EXCE_TOTAL.getCode(), Result.COUPON_EXCE_TOTAL.getMessage());// 超过领取总量
            } else {
                if (couponType.getPersonalCounts() == 0) {// 个人领取总量无限制
                    return couponType;
                    // 个人领取总量有限制
                } else {
                    // 查询已经领取的优惠券
                    int count = couponDao.getPersonCouponCount(couponType.getTypeCode(), userId, openId);
                    if (count >= couponType.getPersonalCounts()) {
                        throw new ServiceException(Result.COUPON_EXCE_NUM.getCode(),
                                Result.COUPON_EXCE_NUM.getMessage());
                    } else {
                        return couponType;
                    }
                }
            }
        }
    }

    // 根据状态,是否使用,使用者,券号等查询优惠券列表
    @Override
    @Transactional(readOnly = true)
    public CouponListResponse getCouponList(String typeCode, String couponCode, String userName, int isUsed,
            int status, int pageNum, int pageSize) throws ServiceException, Exception {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        List<CouponListEntity> list = couponDao.getCouponList(typeCode, couponCode, userName, isUsed, status);
        // 解密密码
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCouponPass(
                    java.net.URLDecoder.decode(EncryptHelper.decrypt(list.get(i).getCouponPass()), "utf-8"));
        }
        // 封装返回数据
        CouponListResponse response = new CouponListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 返回的数据
        response.setPages(pageInfo.getPages());// 总的页数
        response.setTotal(pageInfo.getTotal());// 总的记录数
        return response;
    }

    // 根据券号查询优惠券详情
    @Override
    @Transactional(readOnly = true)
    public CouponResponse getCouponByCode(String couponCode) throws ServiceException, Exception {
        Coupon coupon = couponDao.getCouponByCode(couponCode);
        // 转换密码
        coupon.setCouponPass(java.net.URLDecoder.decode(EncryptHelper.decrypt(coupon.getCouponPass()), "utf-8"));
        CouponResponse response = new CouponResponse();
        response.setCoupon(coupon);
        return response;
    }

    // 根据用户主键和时间查询可用优惠券列表(删选条件：未过期 已到使用日期 状态有效的 未使用的)
    @Override
    @Transactional(readOnly = true)
    public CouponUsedListResponse couponNotUsedList(String openId, int pageNum, int pageSize) throws ServiceException,
            Exception {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        // 转换时间
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String systemTime = LocalDate.now().toString();
        // 查询数据
        List<CouponUsedEntity> list = couponDao.couponNotUsedList(openId, systemTime);
        // 解密密码
        for (int i = 0; i < list.size(); i++) {
            String pass = java.net.URLDecoder.decode(EncryptHelper.decrypt(list.get(i).getCouponPass().toString()),
                    "utf-8");
            list.get(i).setCouponPass(pass);
            // 如果有体验券需要查询出对应的销售信息
            if (list.get(i).getCategory() == 3 && list.get(i).getSaleId() > 0) {
                // 调取查询用户信息的接口
                Response<UserDetailResponse> detailResponse = iUser.getUserDetailByUserId(list.get(i).getSaleId());
                if (detailResponse.getValue() != null) {
                    // 补充用户信息
                    list.get(i).setAgentName(detailResponse.getValue().getAgentName());
                    list.get(i).setMobile(detailResponse.getValue().getMobile());
                    list.get(i).setRealName(detailResponse.getValue().getRealName());
                }
            }

        }
        // 封装返回数据
        CouponUsedListResponse response = new CouponUsedListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 集合
        response.setPages(pageInfo.getPages());// 总页数
        response.setTotal(pageInfo.getTotal());// 总条数
        return response;
    }

    // 根据用户主键和时间查询不可用优惠券列表总数量（删选条件：已过期 未到使用日期 状态无效的 已使用的）
    @Override
    @Transactional(readOnly = true)
    public CouponUsedListResponse couponUsedList(String openId, int pageNum, int pageSize) throws ServiceException,
            Exception {
        // 执行查询数据和分页处理
        PageHelper.startPage(pageNum, pageSize);
        // 转换时间
        String systemTime = LocalDate.now().toString();
        // 查询数据
        List<CouponUsedEntity> list = couponDao.couponUsedList(openId, systemTime);
        // 系统时间
        Long now = System.currentTimeMillis();
        // 解密密码
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setCouponPass(
                    java.net.URLDecoder.decode(EncryptHelper.decrypt(list.get(i).getCouponPass()), "utf-8"));
            // 用来区分过期类型
            if (list.get(i).getIsUsed() == 1) {
                // 判断时间
                if (list.get(i).getExpireTime().getTime() < now) {
                    // 已过期未过期的usedType为0
                    list.get(i).setUsedType(2);
                }
            } else {
                // 已经使用
                list.get(i).setUsedType(1);
            }
            // 如果有体验券需要查询出对应的销售信息
            if (list.get(i).getCategory() == 3 && list.get(i).getSaleId() > 0) {
                // 调取查询用户信息的接口
                Response<UserDetailResponse> detailResponse = iUser.getUserDetailByUserId(list.get(i).getSaleId());
                if (detailResponse.getValue() != null) {
                    // 补充用户信息
                    list.get(i).setAgentName(detailResponse.getValue().getAgentName());
                    list.get(i).setMobile(detailResponse.getValue().getMobile());
                    list.get(i).setRealName(detailResponse.getValue().getRealName());
                }
            }
        }
        // 封装返回数据
        CouponUsedListResponse response = new CouponUsedListResponse();
        PageInfo pageInfo = new PageInfo(list);
        response.setList(list);// 集合
        response.setPages(pageInfo.getPages());// 总页数
        response.setTotal(pageInfo.getTotal());// 总条数
        return response;
    }

    // 根据用户openId和时间查询优惠券可用和不可用的数量
    @Override
    @Transactional(readOnly = true)
    public CouponNumResponse couponNum(String openId) {
        String systemTime = LocalDate.now().toString();
        // 可用的数量
        int usableNum = couponDao.couponNotUsedCount(openId, systemTime);
        // 不可用的数量
        int unUsableNum = couponDao.couponUsedCount(openId, systemTime);
        CouponNumResponse response = new CouponNumResponse();
        response.setUsableNum(usableNum);
        response.setUnUsableNum(unUsableNum);
        return response;
    }

    // 根据券号将优惠券标为已经使用提交订单
    @Override
    @Transactional
    public int referCouponOrder(String userName, String orderId, String couponCode, int usedPlat)
            throws ServiceException {
        return couponDao.referCouponOrder(userName, orderId, couponCode, usedPlat);
    }

    // 根据券号将优惠券标为已经使用取消订单
    @Override
    @Transactional
    public int cancelCouponOrder(String couponCode) throws ServiceException {
        return couponDao.cancelCouponOrder(couponCode);
    }

    // 批量修改优惠券状态为禁止使用
    @Override
    @Transactional
    public int updateCouponStatus(String ids) throws ServiceException {
        return couponDao.updateCouponStatus(ids);
    }

    /**
     * 根据用户,未使用,未过期,渠道,最低订单金额,筛选符合订单的优惠券列表，用于订单页面的可使用列表
     * openId 用户openId,platForm限制使用平台,1商城2渠道,orderMoney最低订单金额,productIds 限制使用商品字符串
     */
    @Override
    @Transactional(readOnly = true)
    public CouponUsedListResponse choseCoupon(String openId, int platForm, BigDecimal orderMoney, String productIds) {
        // 首先先查询用户可用的优惠券
        String systemTime = LocalDate.now().toString();
        List<CouponUsedEntity> list = couponDao.choseCoupon(openId, platForm, orderMoney, systemTime);
        // 定义新的返回集合
        List<CouponUsedEntity> orderList = new ArrayList<CouponUsedEntity>();
        // 处理页面传递过来的商品字符串
        String strs[] = productIds.split(",");
        List<String> productList = Arrays.asList(strs);
        // 循环数据筛选符合订单的优惠券返回给前台使用,主要是筛选页面传递过来的商品是否符合限制使用条件
        for (CouponUsedEntity cEntity : list) {
            // 筛选有限制使用商品的
            if (cEntity.getProductLimit() == 2 && StringUtils.isNotBlank(cEntity.getProductId())) {
                String[] productId = cEntity.getProductId().split(",");
                List<String> pList = Arrays.asList(productId);
                Collections.addAll(pList);
                Collections.addAll(productList);
                // 判断传递过来的是否在限制条件内
                if (pList.containsAll(productList) == true) {
                    orderList.add(cEntity);
                }
            }
            // 无任何限制使用商品的
            if (cEntity.getProductLimit() == 1 && StringUtils.isBlank(cEntity.getProductId())) {
                orderList.add(cEntity);
            }
        }
        CouponUsedListResponse response = new CouponUsedListResponse();
        // 返回的集合
        response.setList(orderList);
        // 总的数量
        response.setTotal(Long.valueOf(orderList.size()));
        return response;
    }

    /**
     * 指定批量发送优惠券 type 1: 注册30天以内会员（含30天） 2：注册30天以上会员  3：所在公司的销售 4：所在公司的会员
     */
    @Override
    @Transactional
    public void batchAppointCoupon(String typeCode, int type, String creator, String companyIds)
            throws ServiceException, Exception {
        // 根据条件查询不同数据批量绑定优惠券,首先获取用户的集合
        List<UserDetailEntity> list = new ArrayList<UserDetailEntity>();

        UserDetailListResponse response = new UserDetailListResponse();
        if (type == 1 || type == 2) {
            response = iUser.getCounponUserList(type).getValue();
        } else {
            // 替换type 1员工 2会员
            if (type == 3) {
                type = 1;
            }
            if (type == 4) {
                type = 2;
            }
            response = iUser.getUserDetailByCompanyIds(type, companyIds).getValue();
        }
        // 需要绑定的优惠券用户集合
        List<Coupon> bindCouponList = new ArrayList<Coupon>();
        if (response != null && response.getList().size() > 0) {
            list = response.getList();
        }
        // 批量操作发送优惠券
        if (list.size() > 0) {
            // 获取批次信息
            List<CouponType> couponTypeList = couponTypeDao.getCouponTypeListByIds(typeCode);
            for (int k = 0; k < list.size(); k++) {
                for (int i = 0; i < couponTypeList.size(); i++) {
                    Coupon coupon = new Coupon();
                    // 根据优惠券类型信息填充数据
                    coupon.setTypeCode(couponTypeList.get(i).getTypeCode());
                    // 优惠券号
                    coupon.setCouponCode(CouponHelper.createUUID());
                    // 密码
                    String pass = java.net.URLEncoder.encode(CouponHelper.createUUID(), "utf-8");
                    coupon.setCouponPass(EncryptHelper.toHexString(EncryptHelper.encryptCoupon(pass)));
                    // 绑定用户openId
                    coupon.setOpenId(list.get(k).getOpenId());
                    coupon.setUserId(list.get(k).getId());
                    // 优惠券有效期
                    if (couponTypeList.get(i).getTimeType() == 1) {// 固定时间
                        coupon.setStartTime(couponTypeList.get(i).getStartTime());
                        coupon.setExpireTime(couponTypeList.get(i).getExpireTime());
                        // 动态时间 开始时间为领券时间结束时间为领取后的指定天数
                    } else {
                        // 开始时间为领券的时间
                        coupon.setStartTime(new Date());
                        // 结束时间为类型中的指定日期,从当天开始算起
                        LocalDateTime endTime = LocalDateTime.now().plusDays(couponTypeList.get(i).getDay() - 1);
                        ZoneId zone = ZoneId.systemDefault();
                        Instant instant = endTime.atZone(zone).toInstant();
                        coupon.setExpireTime(Date.from(instant));
                    }
                    // 优惠券描述
                    coupon.setRemark(couponTypeList.get(i).getDescription());
                    coupon.setCreator(creator);
                    bindCouponList.add(coupon);
                }
            }
            // 设定要启动的工作线程数为 5 个
            int threadCount = 5;
            // 使用线程操作
            List[] taskListPerThread = ThreadManager.distributeTasks(bindCouponList, threadCount);
            for (int i = 0; i < taskListPerThread.length; i++) {
                Thread workThread = new CouponThread(taskListPerThread[i], i, couponDao);
                workThread.start();
            }
        }
    }

    // 指定给某个用户发送优惠券
    @Override
    @Transactional
    public void singleBindCoupon(String typeCode, int userId, String openId, String creator) throws ServiceException,
            Exception {
        // 获取批次信息
        List<CouponType> couponTypeList = couponTypeDao.getCouponTypeListByIds(typeCode);
        // 需要绑定的优惠券用户集合
        List<Coupon> bindCouponList = new ArrayList<Coupon>();
        // 判断优惠券信息不为空的情况下
        if (couponTypeList.size() > 0) {
            for (int i = 0; i < couponTypeList.size(); i++) {
                Coupon coupon = new Coupon();
                // 根据优惠券类型信息填充数据
                coupon.setTypeCode(couponTypeList.get(i).getTypeCode());
                // 优惠券号
                coupon.setCouponCode(CouponHelper.createUUID());
                // 密码
                String pass = java.net.URLEncoder.encode(CouponHelper.createUUID(), "utf-8");
                coupon.setCouponPass(EncryptHelper.toHexString(EncryptHelper.encryptCoupon(pass)));
                // 绑定用户openId
                coupon.setOpenId(openId);
                coupon.setUserId(userId);
                // 优惠券有效期
                if (couponTypeList.get(i).getTimeType() == 1) {// 固定时间
                    coupon.setStartTime(couponTypeList.get(i).getStartTime());
                    coupon.setExpireTime(couponTypeList.get(i).getExpireTime());
                    // 动态时间 开始时间为领券时间结束时间为领取后的指定天数
                } else {
                    // 开始时间为领券的时间
                    coupon.setStartTime(new Date());
                    // 结束时间为类型中的指定日期,从当天开始算起
                    LocalDateTime endTime = LocalDateTime.now().plusDays(couponTypeList.get(i).getDay() - 1);
                    ZoneId zone = ZoneId.systemDefault();
                    Instant instant = endTime.atZone(zone).toInstant();
                    coupon.setExpireTime(Date.from(instant));
                }
                // 优惠券描述
                coupon.setRemark(couponTypeList.get(i).getDescription());
                coupon.setCreator(creator);
                bindCouponList.add(coupon);
            }
            // 执行绑定的方法
            couponDao.bindCoupon(bindCouponList);
        }
    }

    // 根据销售给特定的健身房老用户发放体验券
    @Override
    @Transactional
    public void oldUserCoupon(int userId, String userOpenId, int saleId, String typeCode, int companyId)
            throws ServiceException, Exception {
        // 是否有领取数量限制的
        String limitTypeCode = "";
        int count = 0;
        // 被推荐者数据集合
        List<Coupon> couponList = new ArrayList<Coupon>();
        // 首先查询公司是否有体验券
        List<CouponType> list = couponTypeDao.getLimitPlatId(companyId, 3);
        // 有体验券才会发放，否则直接返回
        if (list != null && list.size() > 0) {
            // 根据类型批次号查询相关信息
            List<CouponType> couponTypeList = couponTypeDao.getCouponTypeListByIds(typeCode);
            if (couponTypeList.size() > 0) {
                for (int i = 0; i < couponTypeList.size(); i++) {
                    // 查询出有限定领取总数量的优惠券批次
                    if (couponTypeList.get(i).getReceiveCounts() > 0) {
                        limitTypeCode += couponTypeList.get(i).getTypeCode() + ",";
                    }
                    // 查询限制条件并绑定
                    CouponType receiveLimit = personCount(couponTypeList.get(i), 0, userOpenId);
                    if (null != receiveLimit) {
                        Coupon coupon = addCoupon(couponTypeList.get(i), userOpenId, userId, saleId);
                        couponList.add(coupon);
                    }
                }
                // 执行给被推荐者发送优惠券
                count = couponDao.bindCoupon(couponList);
                if (count > 0) {
                    // 要是有限制领取总数量的批次执行更新剩余数量的方法
                    if (StringUtils.isNotBlank(limitTypeCode)) {
                        couponTypeDao.updateReceiveCount(limitTypeCode, 1);
                    }
                }
            }
        }
    }

    // 根据券号删除优惠券
    @Override
    @Transactional
    public int deleteCoupon(String couponCode) throws ServiceException {
        return couponDao.deleteCoupon(couponCode);
    }
}
