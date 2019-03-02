package com.edison.saas.bootstrap.jms.message;

import lombok.Data;

@Data
public class TenantMessage<T> {

    /**租户的ID*/
    private Long tid;

    /**事件类型*/
    private String eventType;

    /**事件*/
    private T message;

}
