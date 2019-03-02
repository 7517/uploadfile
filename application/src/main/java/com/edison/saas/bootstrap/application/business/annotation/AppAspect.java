package com.edison.saas.bootstrap.application.business.annotation;

import com.edison.saas.common.framework.annotation.AppAnnotation;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * 处理SaaS参数的注入问题
 */
@Aspect //AOP 切面
@Order(2)
@Component
public class AppAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppAspect.class);

    @Value("${application.code}")
    private String appCode;

    @Pointcut("@annotation(com.edison.saas.common.framework.annotation.AppAnnotation)")
    public  void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint){

        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
        Method method = sign.getMethod();
        AppAnnotation annotation = method.getAnnotation(AppAnnotation.class);


        Object[] args = joinPoint.getArgs();
        for(Object arg : args){
            LOGGER.info(arg.getClass().getName());
            if(arg instanceof PageSearchRequest) {
                fillPageSearchRequest((PageSearchRequest) arg, annotation.autoCondition(), annotation.conditionClass());
            }
            else{
                fillProperty(arg);
            }
        }

//        MethodSignature sign =  (MethodSignature)joinPoint.getSignature();
//        Method method = sign.getMethod();
//        OperateLog annotation = method.getAnnotation(OperateLog.class);
//        System.out.print("打印："+annotation.value()+" 前置日志");
    }


    private void fillProperty(Object object){

        Method method = ReflectionUtils.findMethod(object.getClass(), "setAppCode", String.class);

        if(method == null){
            LOGGER.info("-------------NO setAppCode(String) method");
            return;
        }

        LOGGER.info("-------------HAVE setAppCode(String) method");

        ReflectionUtils.invokeMethod(method, object, appCode);

    }


    private void fillPageSearchRequest(PageSearchRequest pageSearchRequest, boolean autoCondition, Class clazz){

        LOGGER.info(pageSearchRequest.getClass().getName());

        if(autoCondition) {
            checkCondition(pageSearchRequest, clazz);
        }

        Object searchCondition = pageSearchRequest.getSearchCondition();

        if(searchCondition != null){
            fillProperty(searchCondition);
        }

    }

    /**
     * 判断是否需要初始化一个查询条件对象
     * @param pageSearchRequest
     * @param clazz
     */
    private void checkCondition(PageSearchRequest pageSearchRequest, Class clazz){
        if(clazz.equals(Object.class)){
            return;
        }
        if(pageSearchRequest.getSearchCondition() != null){
            return;
        }
        try {
            Object o = clazz.newInstance();
            pageSearchRequest.setSearchCondition(o);
        } catch (InstantiationException e) {
            LOGGER.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
