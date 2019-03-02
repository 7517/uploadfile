package com.edison.saas.bootstrap.application.business.tenant.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.client.tenant.TenantClient;
import com.edison.saas.platform.services.platform.business.tenant.dto.TenantAddDto;
import com.edison.saas.platform.services.platform.business.tenant.dto.TenantCondition;
import com.edison.saas.platform.services.platform.business.tenant.dto.TenantEditDto;
import com.edison.saas.platform.services.platform.business.tenant.vo.TenantVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("tenantRibbonService")
public class TenantRibbonService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(TenantRibbonService.class);


	@Autowired
	private TenantClient tenantClient;


	public TenantVO add(TenantAddDto addDto){
		RestResponse<TenantVO> result = tenantClient.add(addDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "TENANT", result.getCode()+"", result.getMessage());
		}
		return result.getData();
	
	}

	public void delete(Long id){
		if(null == id){
			LOGGER.warn("try delete T by empty id. Code need check");
			return ;
		}
		LOGGER.debug("delete t:{}", id);
		RestResponse<TenantVO> result = tenantClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "TENANT", result.getCode()+"", result.getMessage());
		}
	}
	public TenantVO merge(Long id, TenantEditDto editDto){
		RestResponse<TenantVO> result = tenantClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "TENANT", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public TenantVO find(Long id){
		RestResponse<TenantVO> result = tenantClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "TENANT", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<TenantVO> list(PageSearchRequest<TenantCondition> pageSearchRequest) {
		RestResponse<PageContent<TenantVO>> result = tenantClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "TENANT", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
