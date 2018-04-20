package com.weichuang.ecommerce.coupon.timer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.weichuang.ecommerce.coupon.entity.Coupon;
import com.weichuang.ecommerce.coupon.entity.CouponType;
import com.weichuang.ecommerce.coupon.feign.IUser;
import com.weichuang.ecommerce.coupon.feign.UserDetailEntity;
import com.weichuang.ecommerce.coupon.feign.UserDetailListResponse;
import com.weichuang.ecommerce.coupon.responsitory.ICouponDao;
import com.weichuang.ecommerce.coupon.responsitory.ICouponTypeDao;
import com.weichuang.ecommerce.coupon.util.CouponHelper;
import com.weichuang.ecommerce.coupon.util.EncryptHelper;
import com.weichuang.ecommerce.weixin.service.MessageService;

/**
 * <p>ClassName: CouponTimer.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:每月初送B端用户优惠券 </p>
 * <p>author wanggongliang</p>
 * <p>2017年11月28日 下午5:27:57</p>
 */
@Configuration
@EnableScheduling
// 启用定时任务
public class CouponTimer {
    // 优惠券
    @Autowired
    private ICouponDao couponDao;

    // 优惠券类型
    @Autowired
    private ICouponTypeDao couponTypeDao;

    @Autowired
    private IUser iUser;

    // 微信推送接口
    @Autowired
    private MessageService messageService;

    @Scheduled(cron = "0 0 8 1 * ?")
    // 每月1号凌晨1点执行一次：0 0 1 1 * ?
    public void scheduler() throws Exception {
        // 1：首先查询出所有B端用户的主键和openId
        List<UserDetailEntity> list = new ArrayList<UserDetailEntity>();
        UserDetailListResponse response = iUser.getSalesAll().getValue();
        if (response.getList().size() > 0) {
            list = response.getList();
        }
        // 2：需要送出的优惠券批次C56F0156(100元),C56F0157(400元)
        // 根据类型批次号查询相关信息
        List<CouponType> couponTypeList = couponTypeDao.getCouponTypeListByIds("C56F0156,C56F0157");
        // 计算出有没有限制领取次数
        String limitTypeCode = "";
        if (couponTypeList.size() > 0) {
            for (int a = 0; a < couponTypeList.size(); a++) {
                if (couponTypeList.get(a).getReceiveCounts() > 0) {
                    limitTypeCode += couponTypeList.get(a).getTypeCode() + ",";
                }
            }
        }
        // 需要绑定的优惠券集合
        List<Coupon> bindCouponList = new ArrayList<Coupon>();
        // 需要绑定的openId集合
        List<String> openIdList = new ArrayList<String>();

        if (list.size() > 0) {
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
                    // 优惠券的有效期为当月,因为每个月的天数不一样,所以不使用优惠券类型的时间
                    LocalDate startTime = LocalDate.now();
                    coupon.setStartTime(localDateToDate(startTime.with(TemporalAdjusters.firstDayOfMonth())));
                    // 结束时间为最后一天
                    LocalDate endTime = startTime.with(TemporalAdjusters.lastDayOfMonth());
                    coupon.setExpireTime(localDateToDate(endTime));
                    coupon.setRemark(couponTypeList.get(i).getDescription());// 优惠券描述
                    coupon.setCreator("系统定时任务");
                    bindCouponList.add(coupon);
                }
                openIdList.add(list.get(k).getOpenId());
            }
            // 执行绑定的方法
            int count = couponDao.bindCoupon(bindCouponList);
            // 减去对应的批次总数量
            if (count > 0) {
                // 要是有限制领取总数量的批次执行更新剩余数量的方法
                if (StringUtils.isNotBlank(limitTypeCode)) {
                    couponTypeDao.updateReceiveCount(limitTypeCode, 1);
                }
            }
            // 绑定成功之后推送消息
            int typeSize = couponTypeList.size();
            if (openIdList.size() > 0) {
                for (int j = 0; j < openIdList.size(); j++) {
                    // 查询出需要已送的openId
                    String content = "每月福利到啦！送你" + typeSize + "张专享优惠券，您是我最关心的人，一般人我可不送哦~";
                    messageService.sendMessage(openIdList.get(j), content);
                }
            }
        }
    }

    /**
     * localDate转Date
     */
    public static Date localDateToDate(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        Instant instant1 = zonedDateTime.toInstant();
        Date from = Date.from(instant1);
        return from;
    }
}
