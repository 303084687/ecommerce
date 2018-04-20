package com.weichuang.ecommerce;

import java.lang.reflect.Method;

import javax.servlet.DispatcherType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.weichuang.commons.ApiOriginFilter;

/**
 * Created by wanggongliang on 16/8/8.
 */
@SpringBootApplication
@SpringCloudApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableCaching
@EnableConfigurationProperties
// @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(proxyTargetClass = true)
// @EnableSwagger2
@EnableCircuitBreaker
public class TenantApplication {
    private static final Logger log = LoggerFactory.getLogger(TenantApplication.class);

    @Bean
    public FilterRegistrationBean simpleCORSFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ApiOriginFilter());
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Bean
    public ServletListenerRegistrationBean<IntrospectorCleanupListener> introspectorCleanupListener() {
        ServletListenerRegistrationBean<IntrospectorCleanupListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<IntrospectorCleanupListener>(
                new IntrospectorCleanupListener());
        servletListenerRegistrationBean.setOrder(0);
        return servletListenerRegistrationBean;
    }

    // 使用@Cacheable缓存注解的自定义key生成方法,为了防止key重复
    @Bean
    public KeyGenerator customKeyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getClass().getName());
                sb.append(method.getName());
                for (Object obj : objects) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("ValidationMessages");
        // http://blog.csdn.net/a491057947/article/details/46724707
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setProviderClass(org.hibernate.validator.HibernateValidator.class);
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    public static void main(String[] args) {
        Object[] obj = { TenantApplication.class };
        SpringApplication app = new SpringApplication(obj);
        // app.setAddCommandLineProperties(false); //屏蔽命令行参数
        app.setWebEnvironment(true);
        ApplicationContext context = app.run(args);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            log.info("spring boot使用环境为: " + activeProfile);
        }
    }
}
