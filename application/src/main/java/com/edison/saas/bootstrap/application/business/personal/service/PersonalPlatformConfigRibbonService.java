package com.edison.saas.bootstrap.application.business.personal.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.platform.services.application.business.personal.dto.ChangeDefaultAppRequest;
import com.edison.saas.platform.services.application.business.personal.vo.PersonalPlatformConfigVO;
import com.edison.saas.platform.services.application.client.personal.PersonalPlatformClient;
import com.edison.saas.platform.services.platform.business.personal.vo.DefaultAppVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("personalPlatformRibbonService")
@Slf4j
public class PersonalPlatformConfigRibbonService {

	@Autowired
	private PersonalPlatformClient personalPlatformClient;


	public DefaultAppVO changeDefaultApp(Long accountId, ChangeDefaultAppRequest request){

		RestResponse<DefaultAppVO> result = personalPlatformClient.changeDefaultApp(accountId, request);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "PERSONAL", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PersonalPlatformConfigVO getPlatformConfig(Long accountId){
		RestResponse<PersonalPlatformConfigVO> result = personalPlatformClient.getPersonalPlatformConfig(accountId);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "PERSONAL", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}


}
