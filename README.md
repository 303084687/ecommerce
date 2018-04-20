项目配置说明:
    1、项目结构为maven多模块结构,jar包版本统一在父项目里定义
    2、configserver【集中配置】,需要修改resource/env_dev/application.yml文件里search-locations配置为本地

项目所需组件
    操作系统:Linux 64Bit
    时间:ntp同步
    时区:东八区

    mysql
        版本:5.7.X
        字符集编码:UTF8
        默认排序规则:UTF8_GENERAL_CI
        时区:东八区
        
    rabbitmq 暂时没使用到
    
    redis 目前只有一个
        版本:3.X集群
        集群配置:3主3从

启动服务
    1、启动mysql
    2、启动rabbitmq
    3、启动redis cluster

swagger
http://本机ip地址:项目端口号

服务发现
http://localhost:8761

集中配置
    开发环境,执行的是target下面的,修改配置要修改target下面的才生效,或者把配置独立出去

    修改配置后推送
    curl -XPOST http://localhost:8888/bus/refresh
    
    查看配置
    http://localhost:8888/module1-dev.yml


admin-web
http://localhost:8000

RabbitMQ
http://localhost:15672
    test/test

登录redis
./redis-cli -c -h 192.168.0.108 -p 7000

zipkin
http://localhost:9411/

待解决问题
1、分布式事务追踪
2、记录请求参数、返回值 
3、日志收集、展示
4、接口参数验证
5、断路器
6、高可用,注册多个服务
7、公共日志接口
        logback写入mq
        接口日志
        业务日志
8、eureka身份验证
9、sql注入
10、定时任务
11、页面缓存
12、分库、字典表
13、网关验证传入参数为实体的签名验证

打war包部署到tomcat：
1、继承SpringBootServletInitializer类，参考com.springboot.ServletInitializer
2、pom.xml文件，去除嵌入式tomcat(从spring-boot-starter-web排除)，去除spring-boot-maven-plugin插件
3、jar更改为war

打包命令:
    mvn clean -Pdev package -pl commerce -am -Dmaven.test.skip=true
    -pl 构建指定的模块，模块间用逗号分割
    -am 同时构健模块的所依赖的模块

线上环境部署：
高可用服务发现和配置中心
1.Discovery:bootstrap.yml 修改如下，如果有多个服务发现，他们相互注册；
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
        defaultZone: http://ip-10-0-1-20.ap-northeast-1.compute.internal:8762/eureka/
2.其它服务
Configserver，增加8889端口
其他服务修改注册到多个服务发现：defaultZone: http://ip-10-0-1-20.ap-northeast-1.compute.internal:8761/eureka/,http://ip-10-0-2-20.ap-northeast-1.compute.internal:8762/eureka/
