package com.edison.saas.bootstrap.monitor.endpoint;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "endpoints.business")
public class ExceptionEndpoint extends AbstractEndpoint<Map<String, Object>> {

  public ExceptionEndpoint() {
    super("business", false);
  }


  @Override
  public Map<String, Object> invoke() {
    Map<String, Object> result = new HashMap<String, Object>();
    Date dateTime = new Date();
    result.put("当前时间", dateTime.toString());
    result.put("当前时间戳", dateTime.getTime());
    return result;
  }
}
