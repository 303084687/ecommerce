//package com.zhidian.ecommerce;
//
//import com.alibaba.fastjson.JSON;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.AmqpException;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessagePostProcessor;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.rabbit.support.CorrelationData;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@AutoConfigureAfter(RabbitMQConfig.class)
//@Component("rabbitMQHelper")
//public class RabbitMQHelper {
//	private static final Logger log = LoggerFactory.getLogger(RabbitMQHelper.class);
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	public void convertAndSend(String exchange, String routingKey, Object message) {
//		log.info(JSON.toJSONString(message));
////		CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
////		log.debug("exchange:"+exchange+", routingKey:"+routingKey+", message:"+message+", CorrelationData"+correlationId);
//		MessagePostProcessor messagePostProcessor = new MessagePostProcessor(){
//			@Override
//			public Message postProcessMessage(Message message) throws AmqpException {
////				message.getMessageProperties().setExpiration(timeOut+"");
//				return message;
//			}
//		};
//
//		rabbitTemplate.convertAndSend(exchange, routingKey, message, messagePostProcessor);
//	}
//
//	public Object convertSendAndReceive(String exchange, String routingKey, Object message) {
//		return convertSendAndReceive(exchange, routingKey, message, 5);
//	}
//
//	public Object convertSendAndReceive(String exchange, String routingKey, Object message, int replyTimeout) {
//		log.info(JSON.toJSONString(message));
//		MessagePostProcessor messagePostProcessor = new MessagePostProcessor(){
//			@Override
//			public Message postProcessMessage(Message message) throws AmqpException {
////				message.getMessageProperties().setExpiration(timeOut+"");
//				return message;
//			}
//		};
//
//		CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
//		log.debug("exchange:"+exchange+", routingKey:"+routingKey+", message:"+message+", CorrelationData"+correlationId);
//		rabbitTemplate.setReplyTimeout(1000*replyTimeout);
//		Object obj = rabbitTemplate.convertSendAndReceive(exchange, routingKey, message, messagePostProcessor, correlationId);
//
//		return obj;
//	}
//}
