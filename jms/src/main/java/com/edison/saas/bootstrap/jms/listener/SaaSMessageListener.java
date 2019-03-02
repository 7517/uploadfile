package com.edison.saas.bootstrap.jms.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 监听相关事件
 */
@Slf4j
public class SaaSMessageListener {

    private static final String VirtualTopic = "VirtualTopic.";

    @Value("${spring.application.name}")
    private String clientId;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * destination的格式:Consumer.${spring.application.name}.VirtualTopic.speedcloud.project.
     * Consumer.${spring.application.name}.VirtualTopic 这段是固定的
     * speedcloud.project.create 这个可以替换成对应的事件队列的
     * @param json
     */
//    @JmsListener(destination="Consumer.${spring.application.name}.VirtualTopic.speedcloud.project.create", containerFactory = "queueListenerFactory")
//    public void listenProjectCreate(String json){
//        try {
//            ProjectVO projectVO = objectMapper.readValue(json, ProjectVO.class);
//
//            Product product = new Product();
//            product.setDescription(projectVO.getDescription());
//            product.setProductCode(StringUtils.trimToEmpty(projectVO.getCode()));
//            product.setProductName(projectVO.getName());
//
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//
//
//    }


}
