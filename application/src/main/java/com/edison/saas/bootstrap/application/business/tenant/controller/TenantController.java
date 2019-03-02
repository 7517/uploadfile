package com.edison.saas.bootstrap.application.business.tenant.controller;

import com.edison.saas.bootstrap.application.business.tenant.service.TenantRibbonService;
import com.edison.saas.bootstrap.application.business.tenant.valid.TenantValidator;
import com.edison.saas.common.framework.annotation.SaaSAnnotation;
import com.edison.saas.common.framework.spring.DateConverter;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.platform.business.tenant.dto.TenantAddDto;
import com.edison.saas.platform.services.platform.business.tenant.dto.TenantCondition;
import com.edison.saas.platform.services.platform.business.tenant.dto.TenantEditDto;
import com.edison.saas.platform.services.platform.business.tenant.vo.TenantVO;
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
 * 管理租户
 * @author icode
 */
@Api(description = "租户", tags = "Tenant")
@RestController
@RequestMapping(value = "/application/tenant/tenant")
public class TenantController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TenantController.class);
   
    @Autowired
	private TenantRibbonService tenantRibbonService;

	@Autowired
	private TenantValidator tenantValidator;


    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(tenantValidator);
		webDataBinder.registerCustomEditor(Date.class, new DateConverter());
	}

	/**
	 * 新增租户
	 * @param tenantAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增租户", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
  	@SaaSAnnotation()
	public TenantVO add(@RequestBody @Valid TenantAddDto tenantAddDto){
	
		return  tenantRibbonService.add(tenantAddDto);
	}

	/**
	 * 删除租户,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除租户", httpMethod = "DELETE")
	@DeleteMapping(path="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete tenant :{}", idArray);

		String[] ids = idArray.split(",");
      	for (String id : ids ){
			tenantRibbonService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新租户
	 * @param tenantEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产租户(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(path="/{id}")
	public TenantVO update(@RequestBody @Valid TenantEditDto tenantEditDto, @ApiParam(value = "要查询的租户id") @PathVariable Long id){

		TenantVO vo = tenantRibbonService.merge(id, tenantEditDto);

		return  vo;
	}

	/**
	 * 根据ID查询租户
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询租户", httpMethod = "GET")
	@GetMapping(path="/{id}")
	public TenantVO get(@ApiParam(value = "要查询的租户id") @PathVariable Long id) {

		TenantVO vo = tenantRibbonService.find(id);
		return vo;
	}

	/**
	 * 查询租户列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询租户列表", httpMethod = "POST")
	@PostMapping(path="/list")
  	@SaaSAnnotation(conditionClass = TenantCondition.class)
	public PageContent<TenantVO> list(@RequestBody @Valid PageSearchRequest<TenantCondition> pageSearchRequest){

		PageContent<TenantVO> pageContent = tenantRibbonService.list(pageSearchRequest);
		for(TenantVO vo : pageContent.getContent()){
			initViewProperty(vo);
		}

		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}


	private TenantVO initViewProperty( TenantVO vo){


	   
        return vo;

	}
}
