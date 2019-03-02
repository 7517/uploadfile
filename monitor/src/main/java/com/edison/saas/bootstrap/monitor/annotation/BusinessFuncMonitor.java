package com.edison.saas.bootstrap.monitor.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BusinessFuncMonitor {

    /**
     * 必填, 业务代码
     */
    @AliasFor("code")
    String value() ;

    @AliasFor
    String code() default "";

    boolean exception() default true;

    boolean count() default false;


}
