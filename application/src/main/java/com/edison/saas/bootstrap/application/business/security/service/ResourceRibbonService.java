package com.edison.saas.bootstrap.application.business.security.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.client.security.ResourceClient;
import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceAddDto;
import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceCondition;
import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceEditDto;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("resourceRibbonService")
public class ResourceRibbonService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceRibbonService.class);


	@Autowired
	private ResourceClient resourceClient;


	public ResourceVO add(ResourceAddDto addDto){
		RestResponse<ResourceVO> result = resourceClient.add(addDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
		return result.getData();
	
	}

	public void delete(Long id){
		if(null == id){
			LOGGER.warn("try delete T by empty id. Code need check");
			return ;
		}
		LOGGER.debug("delete t:{}", id);
		RestResponse<ResourceVO> result = resourceClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
	}
	public ResourceVO merge(Long id, ResourceEditDto editDto){
		RestResponse<ResourceVO> result = resourceClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public ResourceVO find(Long id){
		RestResponse<ResourceVO> result = resourceClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public ResourceVO findByCodeAndAppCode(Long code, Long appCode){
		RestResponse<ResourceVO> result = resourceClient.findByCodeAndAppCode(code, appCode);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<ResourceVO> list(PageSearchRequest<ResourceCondition> pageSearchRequest) {
		RestResponse<PageContent<ResourceVO>> result = resourceClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
