package com.weichuang.ecommerce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;

/**
 * <p>Copyright: All Rights Reserved</p>
 * <p>Company: 伟创科技(北京)科技有限公司 </p>
 * <p>Description: 服务发现 </p>
 * <p>Author:jmzhang/王共亮, 16/08/25</p>
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class DiscoveryApplication {
	private static final Logger log = LoggerFactory.getLogger(DiscoveryApplication.class);
	
	/**
	 * <p>Description: 启动入口 </p>
	 * @param args
	 * <p>Author:jmzhang/王共亮, 16/08/26</p>
	 */
	public static void main(String[] args) {
		//https://github.com/spring-cloud/spring-cloud-netflix/blob/master/spring-cloud-netflix-eureka-client/src/main/java/org/springframework/cloud/netflix/eureka/EurekaClientConfigBean.java
		//https://github.com/spring-cloud/spring-cloud-netflix/blob/master/spring-cloud-netflix-eureka-client/src/main/java/org/springframework/cloud/netflix/eureka/EurekaInstanceConfigBean.java
		Object[] obj = { DiscoveryApplication.class };
		SpringApplication app = new SpringApplication(obj);
		ApplicationContext context = app.run(args);
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		for (String activeProfile : activeProfiles) {
			log.info("当前环境: " + activeProfile);
		}
	}
}
