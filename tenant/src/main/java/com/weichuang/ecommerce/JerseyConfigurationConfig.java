//package com.zhidian.ecommerce;
//
//import org.glassfish.jersey.servlet.ServletContainer;
//import org.glassfish.jersey.servlet.ServletProperties;
//import org.springframework.boot.context.embedded.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;
//
//@Configuration
//@Order(1)
//public class JerseyConfigurationConfig {
//
//	@Bean
//    public ServletRegistrationBean jerseyServlet() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/api/*");
//        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyResourceConfig.class.getName());
//        registration.addInitParameter("jersey.config.server.wadl.disableWadl", "true");
//        return registration;
//    }
//
//}
