package com.edison.saas.bootstrap.application.business.application.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.client.application.AppClient;
import com.edison.saas.platform.services.platform.business.application.dto.AppAddDto;
import com.edison.saas.platform.services.platform.business.application.dto.AppCondition;
import com.edison.saas.platform.services.platform.business.application.dto.AppEditDto;
import com.edison.saas.platform.services.platform.business.application.vo.AppVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("appRibbonService")
public class AppRibbonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppRibbonService.class);


	@Autowired
	private AppClient appClient;


	public AppVO add(AppAddDto addDto){
		RestResponse<AppVO> result = appClient.add(addDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "APPLICATION", result.getCode()+"", result.getMessage());
		}
		return result.getData();
	
	}

	public void delete(Long id){
		if(null == id){
			LOGGER.warn("try delete T by empty id. Code need check");
			return ;
		}
		LOGGER.debug("delete t:{}", id);
		RestResponse<AppVO> result = appClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "APPLICATION", result.getCode()+"", result.getMessage());
		}
	}
	public AppVO merge(Long id, AppEditDto editDto){
		RestResponse<AppVO> result = appClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "APPLICATION", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public AppVO find(Long id){
		RestResponse<AppVO> result = appClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "APPLICATION", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public AppVO getByAppCode(Long appCode){
		RestResponse<AppVO> result = appClient.getByAppCode(appCode);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "APPLICATION", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<AppVO> list(PageSearchRequest<AppCondition> pageSearchRequest) {
		RestResponse<PageContent<AppVO>> result = appClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "APPLICATION", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
