package com.edison.saas.bootstrap.monitor.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 业务指标计数器
 * 固定最近10分钟的，每分钟一个
 */
@NoArgsConstructor
public class BusinessSeq {

    @JsonIgnore
    private int queueSize = 10;

    @Getter
    private Queue<BusinessIndicator> queue = new ConcurrentLinkedDeque<>();

    @JsonIgnore
    private Map<String, BusinessIndicator> map = new ConcurrentHashMap<>();


    public BusinessSeq(String code){
        this.code = code;
    }

    /**业务代码*/
    @Getter @Setter
    private String code;

    public void increment(){
        String time = currentTime();
        get(time).increment();
    }
    public void error(){
        String time = currentTime();
        get(time).error();
    }

    public void error(String message, StackTraceElement[] stackTraceElements){
        String time = currentTime();
        get(time).error(message, stackTraceElements);
    }





    /**
     * 得到当前所有的数据
     * @return
     */
    private Collection<BusinessIndicator> get(){
        return queue;
    }

    /**
     * 得到给定时间的业务计数器
      * @param time
     * @return
     */
    private BusinessIndicator get(String time){

        if(!map.containsKey(time)){
            synchronized (this){
                if(!map.containsKey(time)) {
                    addBusinessIndicator(time);
                }
            }
        }

        return map.get(time);

    }

    /**
     * 新增一个时间
     * @param time
     */
    private void addBusinessIndicator(String time){
        BusinessIndicator businessIndicator = new BusinessIndicator(time);
        map.put(time, businessIndicator);
        if(queue.size() >= queueSize){
            BusinessIndicator oldest = queue.remove();
            map.remove(oldest.getDateTime());
        }
        queue.add(businessIndicator);
    }

    private String currentTime(){
        return DateFormatUtils.format(new Date(), "yyyyMMddHHmm");
    }
}
