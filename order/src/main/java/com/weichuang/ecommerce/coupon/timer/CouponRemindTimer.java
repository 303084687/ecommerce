package com.weichuang.ecommerce.coupon.timer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.weichuang.ecommerce.coupon.entity.CouponExpire;
import com.weichuang.ecommerce.coupon.responsitory.ICouponDao;
import com.weichuang.ecommerce.weixin.service.MessageService;

/**
 * <p>ClassName: CouponRemindTimer.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券过期提醒定时任务 </p>
 * <p>author wanggongliang</p>
 * <p>2017年12月20日 下午1:38:53</p>
 */
@Configuration
@EnableScheduling
// 启用定时任务
public class CouponRemindTimer {
    // 优惠券
    @Autowired
    private ICouponDao couponDao;

    // 微信发送接口
    @Autowired
    private MessageService messageService;

    @Scheduled(cron = "0 0 8 * * ?")
    // 每天早晨8点执行一次：0 0 8 * * ?，每隔5秒执行一次*/5 * * * * ?
    public void scheduler() throws Exception {
        // 首先查询出即将过期的优惠券，取出对应的openid,用于微信发送消息
        LocalDate date = LocalDate.now();
        String nowTime = date.toString();
        String compareTime = date.plusDays(3).toString();
        List<CouponExpire> getExpireCouponList = couponDao.getExpireCouponList(nowTime, compareTime);
        // 当查询出来的集合不为空时调用微信发送消息接口
        if (getExpireCouponList != null && getExpireCouponList.size() > 0) {
            for (int i = 0; i < getExpireCouponList.size(); i++) {
                // 调取微信发送消息接口
                String content = "您的账户中有优惠券即将到期了，赶快使用吧！";
                messageService.sendMessage(getExpireCouponList.get(i).getOpenId(), content);
            }
        }
    }
}
