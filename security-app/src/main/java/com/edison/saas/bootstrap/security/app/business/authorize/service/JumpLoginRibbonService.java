package com.edison.saas.bootstrap.security.app.business.authorize.service;

import com.edison.saas.bootstrap.security.model.LoginResult;
import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.platform.services.application.business.security.dto.AppJumpRequest;
import com.edison.saas.platform.services.application.client.security.JumpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("jumpLoginRibbonService")
@Slf4j
public class JumpLoginRibbonService {

	@Autowired
	private JumpClient jumpClient;

	public LoginResult authenticate(String appCode, String token){

		RestResponse<AppJumpRequest> result = jumpClient.authenticate(appCode, token);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "JUMP", result.getCode()+"", result.getMessage());
		}

		LoginResult loginResult = new LoginResult(false, "未进行认证操作");
		loginResult.setSuccess(true);
		return loginResult;

	}



}
