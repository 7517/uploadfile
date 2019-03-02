package com.edison.saas.bootstrap.application.business.authorize.controller;


import com.edison.saas.bootstrap.application.business.authorize.service.CurrentApplicationService;
import com.edison.saas.common.framework.app.ApplicationProperties;
import com.edison.saas.platform.services.platform.business.application.vo.AppVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 得到当前应用
 */
@RestController
@RequestMapping(value = "/current")
@Slf4j
public class CurrentApplicationController {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Value("${management.security.enabled:true}")
    private boolean notInTest = false;

    @Autowired
    private CurrentApplicationService currentApplicationService;


    /**
     * 获取当前应用
     * @return
     */
    @PostMapping("/app")
    public AppVO getApp(){
        Long appCode = applicationProperties.getCode();
        return currentApplicationService.getApp(appCode);
    }



}
