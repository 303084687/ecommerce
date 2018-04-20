package com.weichuang.ecommerce.order.constants;

import com.weichuang.commons.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by jiangks on 2017-11-14.
 */
public class OrderUtil {

//    public static void main(String[] args) throws Exception {
////        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
////        //时间转为字符串
////        LocalDateTime date =LocalDateTime.now();
////        String str = date.format(f);  // 2014-11-07 14:10:36
////        //字符串转为时间
////        date = LocalDateTime.parse(str,f);
//
//        int month = 0;
//        boolean r = true;
//
//        month = month | (r ? 1 :0);
//        System.out.println(month);
//
//        //System.out.println(isInCurrentMonth(new Date()));
//    }

    // 判断日期是否在本月内
    public static synchronized boolean isInCurrentMonth(Date date) {
        boolean result = false;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        Date firstDate = DateUtil.toShortDate(c.getTime());
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));//本月最后一天
        Date lastDay = c.getTime();
        String lastDayString = DateUtil.plusDays(lastDay, 1);
        lastDay = DateUtil.toShortDate(lastDayString);

        result = date.compareTo(firstDate) >= 0 && date.compareTo(lastDay) == -1;

        return result;
    }

    // 每个月的发货时间
    public static synchronized int getSendTime(int sendTime) {
        int result = 0;
        if (sendTime <= 0) {
            // 每个月的最后一天发货
            result = 31;
        } else {
            result = sendTime;
        }
        return result;
    }

    public static synchronized int getSendMonth(int sendTime) {
        Calendar c = Calendar.getInstance();
        int currentDay = c.get(Calendar.DAY_OF_MONTH); // 当前的日期
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        int lastDay = c.get(Calendar.DAY_OF_MONTH); //本月的最后一天

        int result = 0;
        // 如果为-1为每个月的最后一天发货，再看是本月发货还是下个月发货
        if (sendTime <= 0) {
            // 如果当前日期在与本月的最后一天相等，则在下个月发货，否则当天发货
            if (currentDay == lastDay) {
                result = 1;
            } else {
                result = 0;
            }
            return result;
        }

        // 选择的发货是期是当月的最后一天，则下个月发货开始发货（发货时间为每月的最后一天）
        if (sendTime == lastDay) {
            result = 1;
            return result;
        }
        // 发货时间在当前日期之前，下个月发货；发货时间在当前日期之后则本月发货
        if (sendTime <= currentDay) {
            result = 1;
            return result;
        } else {
            result = 0;
            return result;
        }
    }

    // 计划每个月的发货日期
    // sendTime每个月需要发货的日期，times需要发货的次数（第几次发货），如每月第31号发货，第2次发货
    public static synchronized Date getSendDate(int sendTime, int times) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, times);
        c.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        int day = c.get(Calendar.DAY_OF_MONTH);
        int needSendTime = 0;
        if (day < sendTime) {
            needSendTime = day;
        } else {
            needSendTime = sendTime;
        }
        c.set(Calendar.DAY_OF_MONTH, needSendTime);
        Date result = format.parse(format.format(c.getTime()));

        return result;
    }

    // 模板替换
    public static String composeMessage(String template,Properties data) throws Exception{
        Iterator it =data.entrySet().iterator();
        while(it.hasNext()){
            Object o=it.next();
            template=template.replaceFirst("\\$\\{"+o.toString().split("=")[0]+"}", o.toString().split("=")[1]);
        }

        return template;
    }

}
