package com.edison.saas.bootstrap.jms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.edison.saas.bootstrap.jms.message.TenantMessage;
import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.message.DomainEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.jms.Destination;

@Slf4j
@Component
public class SaaSMessageSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String VirtualTopic = "VirtualTopic.";



    @Async("asyncMessageSendExecutor")
    public void sendTopicAsync(DomainEvent domainEvent, Long tenantId, Object o){
        sendTopic(domainEvent, tenantId, o);
    }

    public void sendTopic(DomainEvent domainEvent, Long tenantId, Object o){

        if(tenantId == null){
            throw new BusinessException("SaaSMessageSender", "sendTopic", "NOT_TENANT_ID", "必须设置租户ID,OBJECT:"+o.toString());
        }

        TenantMessage<Object> message = new TenantMessage<>();
        message.setMessage(o);
        message.setEventType(domainEvent.getEventType());
        message.setTid(tenantId);

        sendTopic(domainEvent.getTopic(), message);
    }

    protected void sendTopic(String topic, TenantMessage message){

        Destination destination = new ActiveMQTopic(VirtualTopic + topic);
//        jmsTemplate.convertAndSend(destination, vo);
        try {
            jmsTemplate.convertAndSend(destination, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

    }


    protected void sendQueue(String topic, TenantMessage message){

        Destination destination = new ActiveMQQueue(topic);
        try {
            jmsTemplate.convertAndSend(destination, objectMapper.writeValueAsString(message));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
    }


}
