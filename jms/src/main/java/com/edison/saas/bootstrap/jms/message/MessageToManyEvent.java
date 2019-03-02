package com.edison.saas.bootstrap.jms.message;

import com.edison.saas.common.framework.message.DomainEvent;

/**
 * 系统的消息太多事件
 */
public class MessageToManyEvent implements DomainEvent {


    @Override
    public String getTopic() {
        return "bootstrap.jms.event.queue.full";
    }

    @Override
    public String getEventType() {
        return "full";
    }
}
