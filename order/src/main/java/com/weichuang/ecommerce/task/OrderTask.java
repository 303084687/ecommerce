package com.weichuang.ecommerce.task;

import com.weichuang.ecommerce.order.constants.OrderStatusEnum;
import com.weichuang.ecommerce.order.constants.OrderUtil;
import com.weichuang.ecommerce.order.entity.OrderEntity;
import com.weichuang.ecommerce.order.entity.OrderSentEntity;

import com.weichuang.ecommerce.order.responsitory.IOrderDao;
import com.weichuang.ecommerce.order.responsitory.IOrderSentDao;

import com.weichuang.ecommerce.task.constants.MessageConstant;
import com.weichuang.ecommerce.weixin.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * <p>ClassName: CouponRemindTimer.java</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description:优惠券过期提醒定时任务 </p>
 * <p>author：hanliucheng </p>
 * <p>2017年12月2日 下午1:38:53</p>
 */
@Component
public class OrderTask {

    private static final Logger logger = LoggerFactory.getLogger(OrderTask.class);

    @Autowired
    private IOrderDao orderDao;

    //订单发货详情接口
    @Autowired
    private IOrderSentDao orderSentDao;

    // 微信发送接口
    @Autowired
    private MessageService messageService;

    //订单自动收货 ： 2点 执行一次
    @Scheduled(cron="0 0 2 * * ?")
    public void executeOrderSendTask() {

        Thread current = Thread.currentThread();
        //  System.out.println("订单定时任务:"+current.getId());
        logger.info("OrderTask.executeOrderSendTask 订单自动收货定时任务:"+current.getId()+ ",name:"+current.getName());

        try {

            List<OrderSentEntity>  orderSentEntities =  orderSentDao.getOrderSentExpire(null);

            for (OrderSentEntity item : orderSentEntities) {

                orderSentDao.updateOrderSentStatusToReceivedByIdAndOrderNo(item.getId(),item.getOrderNo());
                OrderEntity orderEntity = orderDao.getOrderById(item.getId());

                // 查看订单需要发货的次数与实际的发货次数做对比，如果两个次数一至则表示订单交易完成，更新订单的状态为交易完成
                if (orderEntity.getSendTimes() == orderEntity.getSentTimes()) {
                    orderDao.updateOrderStatusByOrderNo(item.getOrderNo(), OrderStatusEnum.COMPLETED.getIndex());
                }
                logger.info("订单号:" + item.getOrderNo() + "已自动收货处理" );

            }


        } catch (Exception e) {
            logger.error("订单自动收货定时任务：错误");
        }


    }

    //订单自动关闭 ： 0-24点 每五分钟执行一次
    @Scheduled(cron="0 0/5 * * * ?")
    public void executeOrderExpireTask() {
        Thread current = Thread.currentThread();
        logger.info("OrderTask.executeOrderExpireTask 订单自动提醒/关闭定时任务:"+current.getId()+ ",name:"+current.getName());

        try {

            //过期3天自动关闭
            List<OrderEntity>  orderEntities =  orderDao.getOrderExpire(3);

            for (OrderEntity item : orderEntities) {

                orderDao.updateOrderStatusByOrderNo(item.getOrderNo(),OrderStatusEnum.CLOSED.getIndex());
                logger.info("订单号:" + item.getOrderNo() + "已自动关闭" );

            }
            //下单5分钟自动提醒
            List<OrderEntity>  orderEntities2 =  orderDao.getOrderRemind(5);
            Properties p = new Properties();
            for (OrderEntity item : orderEntities) {

                p.put("productName", item.getUserName());
                String str = OrderUtil.composeMessage(MessageConstant.PAY_REMIND,p);
                //微信提醒
                messageService.sendMessage(item.getUserName(), str);
                orderDao.updateOrderRemindByOrderNo(item.getOrderNo());
                logger.info("订单号:" + item.getOrderNo() + "已自动提醒" );
            }

        } catch (Exception e) {
            logger.error("订单自动提醒/关闭定时任务：错误");
        }

    }

    //订单提醒  - 每天早上9点执行一次
    @Scheduled(cron="0 0 9 * * ?")
    public void executeOrderRemindTask() {
        Thread current = Thread.currentThread();
        logger.info("OrderTask.executeOrderRemindTask 订单自动提醒定时任务:"+current.getId()+ ",name:"+current.getName());

        try {

            Properties p = new Properties();

            List<OrderEntity>  orderEntities =  orderDao.getOrderExpire(2);

            for (OrderEntity item : orderEntities) {

                p.put("productName", item.getUserName());
                String str = OrderUtil.composeMessage(MessageConstant.PAY_REMIND,p);

                //微信提醒
                 messageService.sendMessage(item.getUserName(), str);
                logger.info("订单号:" + item.getOrderNo() + "已自动提醒" );

            }


        } catch (Exception e) {
            logger.error("订单自动提醒定时任务：错误");
        }

    }

}

