//package com.zhidian.ecommerce.rabbitmq.resource;
//
//import com.zhidian.ecommerce.rabbitmq.service.IRabbitMQService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.config.ConfigurableBeanFactory;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//@Path("/rabbitmq")
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@Component
//public class RabbitMQDemoResource {
//    private static final Logger log = LoggerFactory.getLogger(RabbitMQDemoResource.class);
//
//    @Autowired
//    @Qualifier("rabbitMQService")
//    private IRabbitMQService rabbitMQService;
//
//    @Path("/hello")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String hello() {
//        rabbitMQService.convertAndSend();
//
//        return "ok";
//    }
//
//}
