package com.edison.saas.bootstrap.application.business.security.service;


import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.client.security.AccountPasswordClient;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountPasswordAddDto;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountPasswordCondition;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountPasswordEditDto;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountPasswordVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("accountPasswordRibbonService")
public class AccountPasswordRibbonService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountPasswordRibbonService.class);


	@Autowired
	private AccountPasswordClient accountPasswordClient;


	public AccountPasswordVO add(AccountPasswordAddDto addDto){
		RestResponse<AccountPasswordVO> result = accountPasswordClient.add(addDto);

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
		RestResponse<AccountPasswordVO> result = accountPasswordClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
	}
	public AccountPasswordVO merge(Long id, AccountPasswordEditDto editDto){
		RestResponse<AccountPasswordVO> result = accountPasswordClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public AccountPasswordVO find(Long id){
		RestResponse<AccountPasswordVO> result = accountPasswordClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<AccountPasswordVO> list(PageSearchRequest<AccountPasswordCondition> pageSearchRequest) {
		RestResponse<PageContent<AccountPasswordVO>> result = accountPasswordClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
