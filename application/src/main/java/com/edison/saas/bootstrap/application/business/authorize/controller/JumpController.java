package com.edison.saas.bootstrap.application.business.authorize.controller;


import com.edison.saas.bootstrap.application.business.authorize.service.JumpRibbonService;
import com.edison.saas.common.framework.app.ApplicationProperties;
import com.edison.saas.common.framework.dto.CommonResponse;
import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.tenant.SaaSUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 跳转
 */
@RestController
@RequestMapping(value = "/security/jump")
@Slf4j
@Api(description = "申请跳转", tags = "Jump")
public class JumpController {

    @Autowired
    private SaaSUtil saaSUtil;

    @Autowired
    private JumpRibbonService jumpRibbonService;

    @Autowired
    private ApplicationProperties applicationProperties;

    /**
     * 获取跳转令牌
     * @return
     */
    @PostMapping("/{appCode}")
    @ApiOperation(value = "申请跳转令牌", notes = "申请跳转令牌", httpMethod = "POST")
    public @ResponseBody String token(@ApiParam("要跳转到的系统代码") @PathVariable String appCode){

        Long accountId = saaSUtil.getAccountId();

        CommonResponse response = jumpRibbonService.token(accountId, applicationProperties.getCode()+"", appCode);

        if(!response.getSuccess()){
            throw new BusinessException("PLATFORM", "JUMP", "REQUEST_TOKEN_ERROR", response.getMessage());
        }

        return response.getMessage();
    }


}
