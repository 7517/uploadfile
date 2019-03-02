package com.edison.saas.bootstrap.monitor.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor

public class BusinessError{

    public BusinessError(String message, StackTraceElement[] stackTraceElements){
        this.message = message;
        this.stackTraceElements = stackTraceElements;
    }
    private String code;
    private String dateTime;
    private String message;
    private StackTraceElement[] stackTraceElements;
}