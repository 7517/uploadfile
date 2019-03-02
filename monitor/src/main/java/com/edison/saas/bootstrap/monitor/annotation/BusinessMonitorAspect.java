package com.edison.saas.bootstrap.monitor.annotation;

import com.edison.saas.bootstrap.monitor.statistics.BusinessStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 处理业务的监控问题
 */
@Aspect //AOP 切面
@Component
@Slf4j
@Order()
public class BusinessMonitorAspect {

    @Autowired(required = false)
    private BusinessStatisticsService businessStatisticsService;

    @Pointcut("@annotation(com.edison.saas.bootstrap.monitor.annotation.BusinessFuncMonitor)")
    public void annotationPointCut() {
    }

    @AfterThrowing(value = "annotationPointCut()", throwing = "throwable")
    public void exceptionPointCut(JoinPoint joinPoint, Throwable throwable){

        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
        Method method = sign.getMethod();
        BusinessFuncMonitor annotation = method.getAnnotation(BusinessFuncMonitor.class);

        if(!annotation.exception()){
            return;
        }

        String businessCode = annotation.value();

        //业务异常+1
        businessStatisticsService.error(businessCode, throwable.getMessage(), throwable.getStackTrace());
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint){

        //没有统计服务的话, 则啥也不干
        if(businessStatisticsService == null){
            return;
        }

        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
        Method method = sign.getMethod();
        BusinessFuncMonitor annotation = method.getAnnotation(BusinessFuncMonitor.class);

        if(!annotation.count()){
            return;
        }

        String businessCode = annotation.value();

        //业务统计+1
        businessStatisticsService.increment(businessCode);

    }


}
