package com.weichuang.ecommerce;

import javax.servlet.DispatcherType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.weichuang.commons.ApiOriginFilter;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 伟创科技(北京)科技有限公司</p>
 * <p>Description: api网关 </p>
 * <p>Author:jmzhang/王共亮, 16/08/25</p>
 */
@SpringCloudApplication
@EnableZuulProxy
@EnableEurekaClient
// @EnableSidecar
public class GatewayApplication {
    private static final Logger log = LoggerFactory.getLogger(GatewayApplication.class);

    @Bean
    // 调用顺序
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    public FilterRegistrationBean simpleCORSFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ApiOriginFilter());
        registration.setEnabled(true);
        registration.addUrlPatterns("/*");
        return registration;
    }

    /**
     * <p>Description: 启动入口 </p>
     *
     * @param args <p>Author:jmzhang/王共亮, 16/08/26</p>
     */
    public static void main(String[] args) {
        Object[] obj = { GatewayApplication.class };
        SpringApplication app = new SpringApplication(obj);
        ApplicationContext context = app.run(args);
        String[] activeProfiles = context.getEnvironment().getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            log.info("当前环境: " + activeProfile);
        }
    }
}
