package com.edison.saas.bootstrap.application.business.authorize.service;

import com.edison.saas.common.framework.dto.CommonResponse;
import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.platform.services.application.business.security.dto.AppJumpRequest;
import com.edison.saas.platform.services.application.client.security.JumpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("jumpRibbonService")
@Slf4j
public class JumpRibbonService {

	@Autowired
	private JumpClient jumpClient;


	public CommonResponse token(Long accountId, String sourceAppCode, String targetAppCode){
		AppJumpRequest request = new AppJumpRequest();
		request.setAccountId(accountId);
		request.setSourceAppCode(sourceAppCode);
		request.setTargetAppCode(targetAppCode);

		RestResponse<CommonResponse> result = jumpClient.token(request);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "JUMP", result.getCode()+"", result.getMessage());
		}

		return result.getData();

	}



}
