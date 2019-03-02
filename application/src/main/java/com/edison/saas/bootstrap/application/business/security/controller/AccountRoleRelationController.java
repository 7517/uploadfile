package com.edison.saas.bootstrap.application.business.security.controller;

import com.edison.saas.bootstrap.application.business.security.service.AccountRoleRelationRibbonService;
import com.edison.saas.bootstrap.application.business.security.valid.AccountRoleRelationValidator;
import com.edison.saas.common.framework.annotation.SaaSAnnotation;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountRoleRelationAddDto;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountRoleRelationCondition;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountRoleRelationEditDto;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountRoleRelationVO;
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
 * 管理账号角色关联
 * @author icode
 */
@Api(description = "账号角色关联", tags = "AccountRoleRelation")
@RestController
@RequestMapping(path = "/application/security/accountRoleRelation")
public class AccountRoleRelationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccountRoleRelationController.class);
   
    @Autowired
	private AccountRoleRelationRibbonService accountRoleRelationRibbonService;

	@Autowired
	private AccountRoleRelationValidator accountRoleRelationValidator;


    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(accountRoleRelationValidator);
		webDataBinder.registerCustomEditor(Date.class, new DateConverter());
	}

	/**
	 * 新增账号角色关联
	 * @param accountRoleRelationAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增账号角色关联", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
  	@SaaSAnnotation()
	public AccountRoleRelationVO add(@RequestBody @Valid AccountRoleRelationAddDto accountRoleRelationAddDto){
	
		return  accountRoleRelationRibbonService.add(accountRoleRelationAddDto);
	}

	/**
	 * 删除账号角色关联,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除账号角色关联", httpMethod = "DELETE")
	@DeleteMapping(path="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete accountRoleRelation :{}", idArray);

		String[] ids = idArray.split(",");
      	for (String id : ids ){
			accountRoleRelationRibbonService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新账号角色关联
	 * @param accountRoleRelationEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产账号角色关联(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(path="/{id}")
	public AccountRoleRelationVO update(@RequestBody @Valid AccountRoleRelationEditDto accountRoleRelationEditDto, @ApiParam(value = "要查询的账号角色关联id") @PathVariable Long id){

		AccountRoleRelationVO vo = accountRoleRelationRibbonService.merge(id, accountRoleRelationEditDto);

		return  vo;
	}

	/**
	 * 根据ID查询账号角色关联
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询账号角色关联", httpMethod = "GET")
	@GetMapping(path="/{id}")
	public AccountRoleRelationVO get(@ApiParam(value = "要查询的账号角色关联id") @PathVariable Long id) {

		AccountRoleRelationVO vo = accountRoleRelationRibbonService.find(id);
		return vo;
	}

	/**
	 * 查询账号角色关联列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询账号角色关联列表", httpMethod = "POST")
	@PostMapping(path="/list")
  	@SaaSAnnotation(conditionClass = AccountRoleRelationCondition.class)
	public PageContent<AccountRoleRelationVO> list(@RequestBody @Valid PageSearchRequest<AccountRoleRelationCondition> pageSearchRequest){

		PageContent<AccountRoleRelationVO> pageContent = accountRoleRelationRibbonService.list(pageSearchRequest);
		for(AccountRoleRelationVO vo : pageContent.getContent()){
			initViewProperty(vo);
		}

		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}


	private AccountRoleRelationVO initViewProperty( AccountRoleRelationVO vo){


	   
        return vo;

	}
}
