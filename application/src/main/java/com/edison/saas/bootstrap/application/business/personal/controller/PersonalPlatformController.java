package com.edison.saas.bootstrap.application.business.personal.controller;


import com.edison.saas.bootstrap.application.business.personal.service.PersonalPlatformConfigRibbonService;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.tenant.SaaSUtil;
import com.edison.saas.platform.services.application.business.personal.dto.ChangeDefaultAppRequest;
import com.edison.saas.platform.services.application.business.personal.vo.PersonalPlatformConfigVO;
import com.edison.saas.platform.services.application.business.personal.vo.UsableAppVO;
import com.edison.saas.platform.services.platform.business.personal.vo.DefaultAppVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理平台个人配置
 * @author icode
 */
@Api(description = "个性化配置", tags = "App")
@RestController("applicationPersonalController")
@RequestMapping(value = "/application/personal/platform")
@Slf4j
public class PersonalPlatformController {

    @Autowired
    private PersonalPlatformConfigRibbonService personalPlatformConfigRibbonService;


    @Autowired
    private SaaSUtil saaSUtil;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){

        webDataBinder.registerCustomEditor(Date.class, new DateConverter());
    }

    /**
     * 设置个人默认应用
     * @param request
     * @param userId
     * @return
     */
    @ApiOperation(value = "修改", notes = "修改个人默认应用(修改全部字段,未传入置空)", httpMethod = "PUT")
    @PostMapping(path="/{userId}")
    public DefaultAppVO changeDefaultApp(@RequestBody @Valid ChangeDefaultAppRequest request, @PathVariable Long userId){

        Long accountId = saaSUtil.getAccountId();
        if(accountId == null){
            return null;
        }
        DefaultAppVO vo = personalPlatformConfigRibbonService.changeDefaultApp(accountId, request);

        return  vo;

    }

    /**
     * 根据用户ID查询个人平台级配置
     * @return
     */
    @ApiOperation(value = "根据ID查询", notes = "根据ID查询应用", httpMethod = "GET")
    @GetMapping(path="/config")
    public PersonalPlatformConfigVO get() {

        Long accountId = saaSUtil.getAccountId();

        if(accountId == null){
            return null;
        }

        PersonalPlatformConfigVO vo = personalPlatformConfigRibbonService.getPlatformConfig(accountId);

        //过滤掉当前应用
        List<UsableAppVO> usableAppList = vo.getUsableAppList();
        List<UsableAppVO> result = new ArrayList<>();

        String appCode = saaSUtil.getAppCode();
        for(UsableAppVO usableAppVO : usableAppList){
            if(!StringUtils.equals(appCode, usableAppVO.getAppVO().getCode())){
                result.add(usableAppVO);
            }
        }


        vo.setUsableAppList(result);
        return vo;

    }



}

