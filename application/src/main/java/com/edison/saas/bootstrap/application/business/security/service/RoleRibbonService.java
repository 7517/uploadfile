package com.edison.saas.bootstrap.application.business.security.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.client.security.RoleClient;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleAddDto;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleCondition;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleEditDto;
import com.edison.saas.platform.services.platform.business.security.role.vo.RoleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("roleRibbonService")
public class RoleRibbonService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleRibbonService.class);


	@Autowired
	private RoleClient roleClient;


	public RoleVO add(RoleAddDto addDto){
		RestResponse<RoleVO> result = roleClient.add(addDto);

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
		RestResponse<RoleVO> result = roleClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
	}
	public RoleVO merge(Long id, RoleEditDto editDto){
		RestResponse<RoleVO> result = roleClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public RoleVO find(Long id){
		RestResponse<RoleVO> result = roleClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<RoleVO> list(PageSearchRequest<RoleCondition> pageSearchRequest) {
		RestResponse<PageContent<RoleVO>> result = roleClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
