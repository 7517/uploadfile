package com.edison.saas.bootstrap.jms.config;

import com.edison.saas.bootstrap.jms.sender.SaaSMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.jms.ConnectionFactory;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableJms
@Slf4j
public class JmsConfig {

    @Value("${spring.application.name}")
    private String clientId;


    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
        factory.setSubscriptionDurable(true); //设置消息持久化
        factory.setConnectionFactory(connectionFactory);
        factory.setClientId(clientId);
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
        factory.setConnectionFactory(connectionFactory);
        return factory;
    }

    @Bean
    public SaaSMessageSender saaSMessageSender(){
        return new SaaSMessageSender();
    }

    @Bean(name = "asyncMessageSendExecutor")
    public Executor asyncMessageSendExecutor() {

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