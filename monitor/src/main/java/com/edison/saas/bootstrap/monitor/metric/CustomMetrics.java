package com.edison.saas.bootstrap.monitor.metric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomMetrics implements PublicMetrics {
    private ApplicationContext applicationContext;

    @Autowired
    public CustomMetrics(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Collection<Metric<?>> metrics() {
        List<Metric<?>> metrics = new ArrayList<Metric<?>>();
        metrics.add(new Metric<Long>("spring.startup-date", applicationContext.getStartupDate()));
        metrics.add(new Metric<Integer>("spring.bean.definitions", applicationContext.getBeanDefinitionCount()));
        metrics.add(new Metric<Integer>("spring.beans", applicationContext.getBeanNamesForType(Object.class).length));
        metrics.add(new Metric<Integer>("spring.controllers", applicationContext.getBeanNamesForAnnotation(Controller.class).length));
        return metrics;
    }
}
