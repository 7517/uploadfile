package com.edison.saas.bootstrap.application.business.security.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.client.security.AccountClient;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountAddDto;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountCondition;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountEditDto;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("accountRibbonService")
public class AccountRibbonService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRibbonService.class);


	@Autowired
	private AccountClient accountClient;


	public AccountVO add(AccountAddDto addDto){
		RestResponse<AccountVO> result = accountClient.add(addDto);

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
		RestResponse<AccountVO> result = accountClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
	}
	public AccountVO merge(Long id, AccountEditDto editDto){
		RestResponse<AccountVO> result = accountClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public AccountVO find(Long id){
		RestResponse<AccountVO> result = accountClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<AccountVO> list(PageSearchRequest<AccountCondition> pageSearchRequest) {
		RestResponse<PageContent<AccountVO>> result = accountClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
