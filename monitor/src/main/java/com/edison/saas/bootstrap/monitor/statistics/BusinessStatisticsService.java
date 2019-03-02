package com.edison.saas.bootstrap.monitor.statistics;

import org.springframework.scheduling.annotation.Async;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务统计服务
 */

public class BusinessStatisticsService {

    private Map<String, BusinessSeq> map = new HashMap<>();
    @Async("asyncMonitorExecutor")
    public void increment(String code){
        get(code).increment();
    }

    @Async("asyncMonitorExecutor")
    public void error(String code, String message, StackTraceElement[] stackTraceElementList){
        get(code).error(message, stackTraceElementList);
    }

    /**
     * 得到给定业务的统计序列
     * @param code
     * @return
     */
    private BusinessSeq get(String code){

        if(!map.containsKey(code)){
            synchronized (this){
                if(!map.containsKey(code)) {
                    BusinessSeq seq = new BusinessSeq(code);
                    map.put(code, seq);
                }
            }
        }

        return map.get(code);

    }

    /**
     * 得到当前所有的业务队列数据
     * @return
     */
    public Collection<BusinessSeq> get(){

        return map.values();

    }

}
