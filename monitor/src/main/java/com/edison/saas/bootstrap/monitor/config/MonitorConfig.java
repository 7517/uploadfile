package com.edison.saas.bootstrap.monitor.config;

import com.edison.saas.bootstrap.monitor.endpoint.BusinessEndpoint;
import com.edison.saas.bootstrap.monitor.statistics.BusinessStatisticsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@ComponentScan("com.edison.saas.bootstrap.monitor")
@EnableAsync
public class MonitorConfig {

    @Bean
    @ConditionalOnMissingBean(BusinessEndpoint.class)
    BusinessEndpoint businessEndpoint(){
        return new BusinessEndpoint();
    }

    @Bean
    @ConditionalOnMissingBean(BusinessStatisticsService.class)
    BusinessStatisticsService businessStatisticsService(){
        return new BusinessStatisticsService();
    }

    @Bean(name = "asyncMonitorExecutor")
    public Executor asyncMonitorExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(1);
        //配置最大线程数
        executor.setMaxPoolSize(1);
        //配置队列大小
        executor.setQueueCapacity(100);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-event-executor");

        // setRejectedExecutionHandler：Queue满了以后，咋办
        // CallerRunsPolicy：不在新线程中执行任务，而是在调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
