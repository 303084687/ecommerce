//package com.zhidian.ecommerce.rabbitmq.service.impl;
//
//import com.zhidian.ecommerce.RabbitMQConfig;
//import com.zhidian.ecommerce.RabbitMQHelper;
//import com.zhidian.ecommerce.rabbitmq.service.IRabbitMQService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.ExchangeTypes;
//import org.springframework.amqp.rabbit.annotation.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service("rabbitMQService")
//public class RabbitMQServiceImpl implements IRabbitMQService {
//    private static final Logger log = LoggerFactory.getLogger(RabbitMQServiceImpl.class);
//
//    @Autowired
//    private RabbitMQHelper rabbitMQHelper;
//
//    //======================发送消息，无返回值 begin=====================================================
//    @Override
//    public void convertAndSend() {
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("key", "发送Map类型消息-无返回值");
//        rabbitMQHelper.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, map);
//    }
//
//    //监听消息方法，可重载，按参数类型匹配
////    @RabbitHandler
////    private void receiver(AMQP.Channel channel, Message msg) {
////        MessageProperties messageProperties = msg.getMessageProperties();
////        log.info("接收消息："+msg);
////        log.info(msg.toString());
////        log.info(ReflectionToStringBuilder.toString(messageProperties));
////    }
//
//    //监听消息方法，可重载，按参数类型匹配
//    @RabbitListener(bindings=@QueueBinding(
//            value=@Queue(value=RabbitMQConfig.QUEUE1, durable="true"),
//            exchange=@Exchange(value=RabbitMQConfig.EXCHANGE, type= ExchangeTypes.DIRECT),
//            key= RabbitMQConfig.ROUTINGKEY1))
//    @RabbitHandler
//    private void receiver(Map<String, String> message) {
//        log.info("接收Map类型消息："+message);
//        log.info(message.get("key"));
//    }
//    //======================发送消息，无返回值 end=====================================================
//
//
//
//
//    //======================发送消息，有返回值RPC begin=====================================================
//    @Override
//    public Object convertSendAndReceive() {
//        Object obj = null;
//        String message = "发送String类型消息-RPC模式";
//        obj = rabbitMQHelper.convertSendAndReceive(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY2, message);
//        return obj;
//    }
//
//    //监听消息，可重载，按参数类型匹配
//    @RabbitListener(bindings=@QueueBinding(
//            value=@Queue(value=RabbitMQConfig.QUEUE2, durable="true"),
//            exchange=@Exchange(value=RabbitMQConfig.EXCHANGE, type= ExchangeTypes.DIRECT),
//            key= RabbitMQConfig.ROUTINGKEY2))
//    @RabbitHandler
//    public Object receiver(String message) throws InterruptedException {
//        log.info("接收String类型消息："+message);
//        Thread.sleep(1000*5);
//        return "RPC模式返回："+message;
//    }
//    //======================发送消息，有返回值RPC end=====================================================
//
//}
