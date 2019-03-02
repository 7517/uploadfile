package com.edison.saas.bootstrap.application.business.security.controller;

import com.edison.saas.bootstrap.application.business.security.service.AccountRibbonService;
import com.edison.saas.bootstrap.application.business.security.valid.AccountValidator;
import com.edison.saas.common.framework.annotation.SaaSAnnotation;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountAddDto;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountCondition;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountEditDto;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * 管理账号
 * @author icode
 */
@Api(description = "账号", tags = "Account")
@RestController
@RequestMapping(path = "/application/security/account")
public class AccountController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
   
    @Autowired
	private AccountRibbonService accountRibbonService;

	@Autowired
	private AccountValidator accountValidator;


    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(accountValidator);
		webDataBinder.registerCustomEditor(Date.class, new DateConverter());
	}

	/**
	 * 新增账号
	 * @param accountAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增账号", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
  	@SaaSAnnotation()
	public AccountVO add(@RequestBody @Valid AccountAddDto accountAddDto){
	
		return  accountRibbonService.add(accountAddDto);
	}

	/**
	 * 删除账号,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除账号", httpMethod = "DELETE")
	@DeleteMapping(path="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete account :{}", idArray);

		String[] ids = idArray.split(",");
      	for (String id : ids ){
			accountRibbonService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新账号
	 * @param accountEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产账号(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(path="/{id}")
	public AccountVO update(@RequestBody @Valid AccountEditDto accountEditDto, @ApiParam(value = "要查询的账号id") @PathVariable Long id){

		AccountVO vo = accountRibbonService.merge(id, accountEditDto);

		return  vo;
	}

	/**
	 * 根据ID查询账号
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询账号", httpMethod = "GET")
	@GetMapping(path="/{id}")
	public AccountVO get(@ApiParam(value = "要查询的账号id") @PathVariable Long id) {

		AccountVO vo = accountRibbonService.find(id);
		return vo;
	}

	/**
	 * 查询账号列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询账号列表", httpMethod = "POST")
	@PostMapping(path="/list")
  	@SaaSAnnotation(conditionClass = AccountCondition.class)
	public PageContent<AccountVO> list(@RequestBody @Valid PageSearchRequest<AccountCondition> pageSearchRequest){

		PageContent<AccountVO> pageContent = accountRibbonService.list(pageSearchRequest);
		for(AccountVO vo : pageContent.getContent()){
			initViewProperty(vo);
		}

		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}


	private AccountVO initViewProperty( AccountVO vo){


	   
        return vo;

	}
}
