//package com.zhidian.ecommerce;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//
////@EnableRabbit
//@Configuration
//@SuppressWarnings("SpringJavaAutowiringInspection")
//public class RabbitMQConfig {
//    private static final Logger log = LoggerFactory.getLogger(RabbitMQConfig.class);
//
//	public static final String EXCHANGE   = "spring-boot-exchange";
//    public static final String ROUTINGKEY1 = "spring-boot-routingKey1";
//    public static final String QUEUE1 = "spring-boot-queue1";
//    public static final String ROUTINGKEY2 = "spring-boot-routingKey2";
//    public static final String QUEUE2 = "spring-boot-queue2";
//
//    @Autowired
//    private RabbitProperties rabbitProperties;
//
////    @Autowired
////    private ConnectionFactory connectionFactory;
//
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setAddresses(rabbitProperties.getAddresses());
//        connectionFactory.setUsername(rabbitProperties.getUsername());
//        connectionFactory.setPassword(rabbitProperties.getPassword());
//        connectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
//        connectionFactory.setRequestedHeartBeat(rabbitProperties.getRequestedHeartbeat());
////        connectionFactory.setChannelCacheSize(5);
////        connectionFactory.setPublisherConfirms(true);
////        connectionFactory.setPublisherReturns(true);
//        connectionFactory.setPublisherConfirms(true); //必须要设置
//
//        return connectionFactory;
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//    //必须是prototype类型，否则无法使用回调函数
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
////        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) ->{
////    		log.info("回调id:" + correlationData + ", ack:" + ack + ", cause:" + cause);
////            if (ack) {
////            	log.info("消息投递成功");
////            } else {
////            	log.error("消息投递失败:" + cause);
////            }
////        });
//
//        return rabbitTemplate;
//    }
//
////    @Bean
////    public DirectExchange defaultExchange() {
////        return new DirectExchange(EXCHANGE);
////    }
////
////    @Bean
////    public Queue queue() {
////        return new Queue(QUEUE, true); //队列持久
////    }
////
////    @Bean
////    public Binding binding() {
////        return BindingBuilder.bind(queue()).to(defaultExchange()).with(ROUTINGKEY);
////    }
//
//}
