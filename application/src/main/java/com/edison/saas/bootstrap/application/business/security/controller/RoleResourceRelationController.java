package com.edison.saas.bootstrap.application.business.security.controller;

import com.edison.saas.bootstrap.application.business.security.service.RoleResourceRelationRibbonService;
import com.edison.saas.bootstrap.application.business.security.valid.RoleResourceRelationValidator;
import com.edison.saas.common.framework.annotation.SaaSAnnotation;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationAddDto;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationCondition;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationEditDto;
import com.edison.saas.platform.services.platform.business.security.role.vo.RoleResourceRelationVO;
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
 * 管理角色资源关系
 * @author icode
 */
@Api(description = "角色资源关系", tags = "RoleResourceRelation")
@RestController
@RequestMapping(path = "/application/security/roleResourceRelation")
public class RoleResourceRelationController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleResourceRelationController.class);
   
    @Autowired
	private RoleResourceRelationRibbonService roleResourceRelationRibbonService;

	@Autowired
	private RoleResourceRelationValidator roleResourceRelationValidator;


    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(roleResourceRelationValidator);
		webDataBinder.registerCustomEditor(Date.class, new DateConverter());
	}

	/**
	 * 新增角色资源关系
	 * @param roleResourceRelationAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增角色资源关系", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
  	@SaaSAnnotation()
	public RoleResourceRelationVO add(@RequestBody @Valid RoleResourceRelationAddDto roleResourceRelationAddDto){
	
		return  roleResourceRelationRibbonService.add(roleResourceRelationAddDto);
	}

	/**
	 * 删除角色资源关系,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除角色资源关系", httpMethod = "DELETE")
	@DeleteMapping(path="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete roleResourceRelation :{}", idArray);

		String[] ids = idArray.split(",");
      	for (String id : ids ){
			roleResourceRelationRibbonService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新角色资源关系
	 * @param roleResourceRelationEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产角色资源关系(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(path="/{id}")
	public RoleResourceRelationVO update(@RequestBody @Valid RoleResourceRelationEditDto roleResourceRelationEditDto, @ApiParam(value = "要查询的角色资源关系id") @PathVariable Long id){

		RoleResourceRelationVO vo = roleResourceRelationRibbonService.merge(id, roleResourceRelationEditDto);

		return  vo;
	}

	/**
	 * 根据ID查询角色资源关系
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询角色资源关系", httpMethod = "GET")
	@GetMapping(path="/{id}")
	public RoleResourceRelationVO get(@ApiParam(value = "要查询的角色资源关系id") @PathVariable Long id) {

		RoleResourceRelationVO vo = roleResourceRelationRibbonService.find(id);
		return vo;
	}

	/**
	 * 查询角色资源关系列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询角色资源关系列表", httpMethod = "POST")
	@PostMapping(path="/list")
  	@SaaSAnnotation(conditionClass = RoleResourceRelationCondition.class)
	public PageContent<RoleResourceRelationVO> list(@RequestBody @Valid PageSearchRequest<RoleResourceRelationCondition> pageSearchRequest){

		PageContent<RoleResourceRelationVO> pageContent = roleResourceRelationRibbonService.list(pageSearchRequest);
		for(RoleResourceRelationVO vo : pageContent.getContent()){
			initViewProperty(vo);
		}

		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}


	private RoleResourceRelationVO initViewProperty( RoleResourceRelationVO vo){


	   
        return vo;

	}
}
