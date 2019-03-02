package com.edison.saas.bootstrap.monitor.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(EnableBusinessMonitorImportSelector.class)
public @interface EnableBusinessMonitor {
}
