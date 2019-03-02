package com.edison.saas.bootstrap.common.business.simpleconfig.controller;

import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfigType;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigTypeAddDto;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigTypeCondition;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigTypeEditDto;
import com.edison.saas.bootstrap.common.business.simpleconfig.service.SimpleConfigTypeService;
import com.edison.saas.bootstrap.common.business.simpleconfig.valid.SimpleConfigTypeValidator;
import com.edison.saas.bootstrap.common.business.simpleconfig.vo.SimpleConfigTypeVO;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageRequest;
import com.edison.saas.common.framework.web.data.PageRequestConvert;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理通用配置类型
 * @author icode
 */
@Api(description = "通用配置类型", tags = "SimpleConfigType")
@RestController
@RequestMapping(value = "/common/simpleConfigType")
public class SimpleConfigTypeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConfigTypeController.class);


	@Autowired
	private SimpleConfigTypeService simpleConfigTypeService;


	@Autowired
	private SimpleConfigTypeValidator simpleConfigTypeValidator;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(simpleConfigTypeValidator);
	}

	/**
	 * 新增通用配置类型
	 * @param simpleConfigTypeAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增通用配置类型", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
	public SimpleConfigTypeVO add(@RequestBody @Valid SimpleConfigTypeAddDto simpleConfigTypeAddDto){
		SimpleConfigType simpleConfigType = new SimpleConfigType();
		BeanUtils.copyProperties(simpleConfigTypeAddDto, simpleConfigType);

		simpleConfigTypeService.add(simpleConfigType);

		return  initViewProperty(simpleConfigType);
	}

	/**
	 * 删除通用配置类型,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除通用配置类型", httpMethod = "DELETE")
	@DeleteMapping(value="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete simpleConfigType :{}", idArray);

		String[] ids = idArray.split(",");
		for (String id : ids ){
			simpleConfigTypeService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新通用配置类型
	 * @param simpleConfigTypeEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产通用配置类型(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(value="/{id}")
	public SimpleConfigTypeVO update(@RequestBody @Valid SimpleConfigTypeEditDto simpleConfigTypeEditDto, @PathVariable Long id){
		SimpleConfigType simpleConfigType = new SimpleConfigType();
		BeanUtils.copyProperties(simpleConfigTypeEditDto, simpleConfigType);
		simpleConfigType.setId(id);
		simpleConfigTypeService.merge(simpleConfigType);

		SimpleConfigTypeVO vo = initViewProperty(simpleConfigType);
		return  vo;
	}

	/**
	 * 根据ID查询通用配置类型
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询通用配置类型", httpMethod = "GET")
	@GetMapping(value="/{id}")
	public SimpleConfigTypeVO get(@PathVariable Long id) {

		SimpleConfigType simpleConfigType = simpleConfigTypeService.find(id);

		SimpleConfigTypeVO vo = initViewProperty(simpleConfigType);
		return vo;
	}

	/**
	 * 查询通用配置类型列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询通用配置类型列表", httpMethod = "POST")
	@PostMapping("/list")
	public PageContent<SimpleConfigTypeVO> list(@RequestBody PageSearchRequest<SimpleConfigTypeCondition> pageSearchRequest){

		PageRequest pageRequest = PageRequestConvert.convert(pageSearchRequest);

		Page<SimpleConfigType> page = simpleConfigTypeService.find(pageSearchRequest.getSearchCondition(), pageRequest);

		List<SimpleConfigTypeVO> voList = new ArrayList<>();
		for(SimpleConfigType simpleConfigType : page.getContent()){
			voList.add(initViewProperty(simpleConfigType));
		}

		PageContent<SimpleConfigTypeVO> pageContent = new PageContent<>(voList, page.getTotalElements());
		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}

	private SimpleConfigTypeVO initViewProperty(SimpleConfigType simpleConfigType){
	    SimpleConfigTypeVO vo = new SimpleConfigTypeVO();

        BeanUtils.copyProperties(simpleConfigType, vo);

	    //初始化其他对象
        return vo;
	}


}
