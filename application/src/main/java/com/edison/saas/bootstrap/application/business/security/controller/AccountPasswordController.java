package com.edison.saas.bootstrap.application.business.security.controller;

import com.edison.saas.bootstrap.application.business.security.service.AccountPasswordRibbonService;
import com.edison.saas.bootstrap.application.business.security.valid.AccountPasswordValidator;
import com.edison.saas.common.framework.annotation.SaaSAnnotation;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountPasswordAddDto;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountPasswordCondition;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountPasswordEditDto;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountPasswordVO;
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
 * 管理账号密码
 * @author icode
 */
@Api(description = "账号密码", tags = "AccountPassword")
@RestController
@RequestMapping(path = "/application/security/accountpassword")
public class AccountPasswordController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountPasswordController.class);
   
    @Autowired
	private AccountPasswordRibbonService accountPasswordRibbonService;

	@Autowired
	private AccountPasswordValidator accountPasswordValidator;


    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(accountPasswordValidator);
		webDataBinder.registerCustomEditor(Date.class, new DateConverter());
	}

	/**
	 * 新增账号密码
	 * @param accountPasswordAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增账号密码", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
  	@SaaSAnnotation()
	public AccountPasswordVO add(@RequestBody @Valid AccountPasswordAddDto accountPasswordAddDto){
	
		return  accountPasswordRibbonService.add(accountPasswordAddDto);
	}

	/**
	 * 删除账号密码,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除账号密码", httpMethod = "DELETE")
	@DeleteMapping(path="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete accountPassword :{}", idArray);

		String[] ids = idArray.split(",");
      	for (String id : ids ){
			accountPasswordRibbonService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新账号密码
	 * @param accountPasswordEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产账号密码(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(path="/{id}")
	public AccountPasswordVO update(@RequestBody @Valid AccountPasswordEditDto accountPasswordEditDto, @ApiParam(value = "要查询的账号密码id") @PathVariable Long id){

		AccountPasswordVO vo = accountPasswordRibbonService.merge(id, accountPasswordEditDto);

		return  vo;
	}

	/**
	 * 根据ID查询账号密码
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询账号密码", httpMethod = "GET")
	@GetMapping(path="/{id}")
	public AccountPasswordVO get(@ApiParam(value = "要查询的账号密码id") @PathVariable Long id) {

		AccountPasswordVO vo = accountPasswordRibbonService.find(id);
		return vo;
	}

	/**
	 * 查询账号密码列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询账号密码列表", httpMethod = "POST")
	@PostMapping(path="/list")
  	@SaaSAnnotation(conditionClass = AccountPasswordCondition.class)
	public PageContent<AccountPasswordVO> list(@RequestBody @Valid PageSearchRequest<AccountPasswordCondition> pageSearchRequest){

		PageContent<AccountPasswordVO> pageContent = accountPasswordRibbonService.list(pageSearchRequest);
		for(AccountPasswordVO vo : pageContent.getContent()){
			initViewProperty(vo);
		}

		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}



	private AccountPasswordVO initViewProperty( AccountPasswordVO vo){


	   
        return vo;

	}
}
