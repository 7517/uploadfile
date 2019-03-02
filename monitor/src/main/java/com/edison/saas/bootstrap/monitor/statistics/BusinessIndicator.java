package com.edison.saas.bootstrap.monitor.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 业务指标
 */
@Getter @Setter @NoArgsConstructor
public class BusinessIndicator {

    public BusinessIndicator(String dateTime){
        this.dateTime = dateTime;
    }
    public BusinessIndicator(String code, String dateTime){
        this.code = code;
        this.dateTime = dateTime;
    }


    /**业务代码*/
    private String code;
    /**时间*/
    private String dateTime;
    /**发生次数*/
    private int count = 0;
    /**异常次数*/
    private int errorCount = 0;

    List<BusinessError> businessErrorList = new ArrayList<>();

    public void increment(){
        ++count;
    }
    public void error(){
        ++errorCount;
    }

    public void error(String message, StackTraceElement[] stackTraceElements){
        BusinessError businessError = new BusinessError(message, stackTraceElements);
        businessErrorList.add(businessError);
        ++errorCount;
        if(errorCount > 10){
            businessErrorList.remove(0);
        }
    }
}


