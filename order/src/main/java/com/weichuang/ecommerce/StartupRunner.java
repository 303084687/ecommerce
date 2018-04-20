package com.weichuang.ecommerce;

import javax.sql.DataSource;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by zhangjiming on 16/9/1.
 */
@Component
public class StartupRunner implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);

    @Autowired
    private C3p0DataSourceProperties c3p0DataSourceProperties;

    @Autowired
    private DataSource dataSource;

    // @Autowired
    // JedisCluster jedisCluster;

    // @Autowired
    // RedisTemplate redisTemplate;

    @Autowired
    private RedisHelper redisHelper;

    @Override
    public void run(String... strings) throws Exception {
        log.info("CommandLineRunner.........");
        log.info("c3p0DataSourceProperties: " + ReflectionToStringBuilder.toString(c3p0DataSourceProperties));
        log.info("Datasource: " + ReflectionToStringBuilder.toString(dataSource));
        redisHelper.set("a1", "测试redis");
        log.info("jedisCluster: " + redisHelper.get("a1"));
        // redisTemplate.opsForValue().set("", "");
        // redisTemplate.opsForValue().set("ff", "abcd");
        // log.info("RedisTemplate: "+redisTemplate);
        // log.info("RedisTemplate.opsForValue(): "+redisTemplate.opsForValue());
        // RedisClusterConnection connection =
        // connectionFactory.getClusterConnection();
        // connection.set("foo", "foo".getBytes());
        // connection.set("bar", "bar".getBytes());
        log.info("CommandLineRunner End");
    }
}
