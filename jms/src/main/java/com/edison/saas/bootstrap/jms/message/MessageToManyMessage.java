package com.edison.saas.bootstrap.jms.message;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class MessageToManyMessage {

    /**时间*/
    public Date current;

    /**队列中消息数量*/
    public int messageInQueue;


}
