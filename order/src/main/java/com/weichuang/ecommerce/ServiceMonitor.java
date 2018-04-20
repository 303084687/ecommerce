package com.weichuang.ecommerce;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class ServiceMonitor {
    private static final Logger log = LoggerFactory.getLogger(ServiceMonitor.class);

    @Autowired
    private CounterService counterService;

    @Autowired
    private GaugeService gaugeService;

    @Before("execution(* com.weichuang.ecommerce.*.controller.*.*(..))")
    public void countServiceInvoke(JoinPoint joinPoint) {
        log.info("统计请求数");
        counterService.increment(joinPoint.getSignature() + "");
    }

    @Around("execution(* com.weichuang.ecommerce.*.controller.*.*(..))")
    public Object latencyService(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        Object obj = pjp.proceed();
        long end = System.currentTimeMillis();
        double d = end - start;
        gaugeService.submit(pjp.getSignature().toString(), d);
        log.info("统计用时:" + d);
        return obj;
    }

}
