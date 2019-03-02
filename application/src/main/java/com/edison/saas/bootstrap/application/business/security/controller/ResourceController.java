package com.edison.saas.bootstrap.application.business.security.controller;

import com.edison.saas.bootstrap.application.business.security.service.ResourceRibbonService;
import com.edison.saas.bootstrap.application.business.security.valid.ResourceValidator;
import com.edison.saas.common.framework.annotation.SaaSAnnotation;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceAddDto;
import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceCondition;
import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceEditDto;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceVO;
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
 * 管理资源
 * @author icode
 */
@Api(description = "资源", tags = "Resource")
@RestController
@RequestMapping(value = "/application/security/resource")
public class ResourceController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);
   
    @Autowired
	private ResourceRibbonService resourceRibbonService;

	@Autowired
	private ResourceValidator resourceValidator;


    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(resourceValidator);
		webDataBinder.registerCustomEditor(Date.class, new DateConverter());
	}

	/**
	 * 新增资源
	 * @param resourceAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增资源", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
  	@SaaSAnnotation()
	public ResourceVO add(@RequestBody @Valid ResourceAddDto resourceAddDto){
	
		return  resourceRibbonService.add(resourceAddDto);
	}

	/**
	 * 删除资源,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除资源", httpMethod = "DELETE")
	@DeleteMapping(path="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete resource :{}", idArray);

		String[] ids = idArray.split(",");
      	for (String id : ids ){
			resourceRibbonService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新资源
	 * @param resourceEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产资源(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(path="/{id}")
	public ResourceVO update(@RequestBody @Valid ResourceEditDto resourceEditDto, @ApiParam(value = "要查询的资源id") @PathVariable Long id){

		ResourceVO vo = resourceRibbonService.merge(id, resourceEditDto);

		return  vo;
	}

	/**
	 * 根据ID查询资源
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询资源", httpMethod = "GET")
	@GetMapping(path="/{id}")
	public ResourceVO get(@ApiParam(value = "要查询的资源id") @PathVariable Long id) {

		ResourceVO vo = resourceRibbonService.find(id);
		return vo;
	}

	/**
	 * 查询资源列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询资源列表", httpMethod = "POST")
	@PostMapping(path="/list")
  	@SaaSAnnotation(conditionClass = ResourceCondition.class)
	public PageContent<ResourceVO> list(@RequestBody @Valid PageSearchRequest<ResourceCondition> pageSearchRequest){

		PageContent<ResourceVO> pageContent = resourceRibbonService.list(pageSearchRequest);
		for(ResourceVO vo : pageContent.getContent()){
			initViewProperty(vo);
		}

		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}



	private ResourceVO initViewProperty( ResourceVO vo){


	   
        return vo;

	}
}
