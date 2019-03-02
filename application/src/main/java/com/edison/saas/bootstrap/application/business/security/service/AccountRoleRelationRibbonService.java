package com.edison.saas.bootstrap.application.business.security.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.client.security.AccountRoleRelationClient;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountRoleRelationAddDto;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountRoleRelationCondition;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountRoleRelationEditDto;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountRoleRelationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("accountRoleRelationRibbonService")
public class AccountRoleRelationRibbonService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRoleRelationRibbonService.class);


	@Autowired
	private AccountRoleRelationClient accountRoleRelationClient;


	public AccountRoleRelationVO add(AccountRoleRelationAddDto addDto){
		RestResponse<AccountRoleRelationVO> result = accountRoleRelationClient.add(addDto);

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
		RestResponse<AccountRoleRelationVO> result = accountRoleRelationClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
	}
	public AccountRoleRelationVO merge(Long id, AccountRoleRelationEditDto editDto){
		RestResponse<AccountRoleRelationVO> result = accountRoleRelationClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public AccountRoleRelationVO find(Long id){
		RestResponse<AccountRoleRelationVO> result = accountRoleRelationClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<AccountRoleRelationVO> list(PageSearchRequest<AccountRoleRelationCondition> pageSearchRequest) {
		RestResponse<PageContent<AccountRoleRelationVO>> result = accountRoleRelationClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
