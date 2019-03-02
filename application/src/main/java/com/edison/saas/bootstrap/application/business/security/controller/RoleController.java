package com.edison.saas.bootstrap.application.business.security.controller;

import com.edison.saas.bootstrap.application.business.security.service.RoleRibbonService;
import com.edison.saas.bootstrap.application.business.security.valid.RoleValidator;
import com.edison.saas.common.framework.annotation.AppAnnotation;
import com.edison.saas.common.framework.annotation.SaaSAnnotation;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleAddDto;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleCondition;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleEditDto;
import com.edison.saas.platform.services.platform.business.security.role.vo.RoleVO;
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
 * 管理角色
 * @author icode
 */
@Api(description = "角色", tags = "Role")
@RestController
@RequestMapping(path = "/application/security/role")
public class RoleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
   
    @Autowired
	private RoleRibbonService roleRibbonService;

	@Autowired
	private RoleValidator roleValidator;


    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(roleValidator);
		webDataBinder.registerCustomEditor(Date.class, new DateConverter());
	}

	/**
	 * 新增角色
	 * @param roleAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增角色", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
  	@SaaSAnnotation()
	public RoleVO add(@RequestBody @Valid RoleAddDto roleAddDto){
	
		return  roleRibbonService.add(roleAddDto);
	}

	/**
	 * 删除角色,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除角色", httpMethod = "DELETE")
	@DeleteMapping(path="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete role :{}", idArray);

		String[] ids = idArray.split(",");
      	for (String id : ids ){
			roleRibbonService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新角色
	 * @param roleEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产角色(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(path="/{id}")
	public RoleVO update(@RequestBody @Valid RoleEditDto roleEditDto, @ApiParam(value = "要查询的角色id") @PathVariable Long id){

		RoleVO vo = roleRibbonService.merge(id, roleEditDto);

		return  vo;
	}

	/**
	 * 根据ID查询角色
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询角色", httpMethod = "GET")
	@GetMapping(path="/{id}")
	public RoleVO get(@ApiParam(value = "要查询的角色id") @PathVariable Long id) {

		RoleVO vo = roleRibbonService.find(id);
		return vo;
	}

	/**
	 * 查询角色列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询角色列表", httpMethod = "POST")
	@PostMapping(path="/list")
  	@SaaSAnnotation(conditionClass = RoleCondition.class)
	@AppAnnotation
	public PageContent<RoleVO> list(@RequestBody @Valid PageSearchRequest<RoleCondition> pageSearchRequest){

		PageContent<RoleVO> pageContent = roleRibbonService.list(pageSearchRequest);
		for(RoleVO vo : pageContent.getContent()){
			initViewProperty(vo);
		}

		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}



	private RoleVO initViewProperty( RoleVO vo){


	   
        return vo;

	}
}
