package com.weichuang.ecommerce;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class C3p0DataSourceConfig {
	private static final Logger log = LoggerFactory.getLogger(C3p0DataSourceConfig.class);
	@Autowired
	private C3p0DataSourceProperties c3p0DataSourceProperties;
	private ComboPooledDataSource datasource = null;
	
	@Bean(name = "dataSource", destroyMethod = "close")
	public ComboPooledDataSource dataSource() throws Exception {
		log.info(ReflectionToStringBuilder.toString(c3p0DataSourceProperties));
		datasource = new ComboPooledDataSource();
		BeanUtils.copyProperties(datasource, c3p0DataSourceProperties);
		return datasource;
	}
	
	@PreDestroy
	public void close() {
		if (null != datasource) {
			datasource.close();
		}
	}
	
	//	@Bean(name="transactionManager")
	//	public PlatformTransactionManager transactionManager(EntityManagerFactory factory) {
	//        return new JpaTransactionManager(factory);
	//    }
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
	//	@Bean
	//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws Exception {
	//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	//        vendorAdapter.setDatabase(Database.HSQL);
	//        vendorAdapter.setGenerateDdl(true);
	//
	//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	//        factory.setJpaVendorAdapter(vendorAdapter);
	//        factory.setPackagesToScan(getClass().getPackage().getName());
	//        factory.setDataSource(primaryDataSource());
	//
	//        return factory;
	//    }
}
