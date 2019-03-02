package com.edison.saas.bootstrap.common.business.simpleconfig.controller;

import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfig;
import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfigType;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigAddDto;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigCondition;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigEditDto;
import com.edison.saas.bootstrap.common.business.simpleconfig.service.SimpleConfigService;
import com.edison.saas.bootstrap.common.business.simpleconfig.service.SimpleConfigTypeService;
import com.edison.saas.bootstrap.common.business.simpleconfig.valid.SimpleConfigValidator;
import com.edison.saas.bootstrap.common.business.simpleconfig.vo.SimpleConfigTypeVO;
import com.edison.saas.bootstrap.common.business.simpleconfig.vo.SimpleConfigVO;
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
 * 管理通用配置
 * @author icode
 */
@Api(description = "通用配置", tags = "SimpleConfig")
@RestController
@RequestMapping(value = "/common/simpleConfig")
public class SimpleConfigController {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConfigController.class);


	@Autowired
	private SimpleConfigService simpleConfigService;

	@Autowired
	private SimpleConfigTypeService simpleConfigTypeService;

	@Autowired
	private SimpleConfigValidator simpleConfigValidator;

	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(simpleConfigValidator);
	}

	/**
	 * 新增通用配置
	 * @param simpleConfigAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增通用配置", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
	public SimpleConfigVO add(@RequestBody @Valid SimpleConfigAddDto simpleConfigAddDto){
		SimpleConfig simpleConfig = new SimpleConfig();
		BeanUtils.copyProperties(simpleConfigAddDto, simpleConfig);

		simpleConfigService.add(simpleConfig);

		return  initViewProperty(simpleConfig);
	}

	/**
	 * 删除通用配置,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除通用配置", httpMethod = "DELETE")
	@DeleteMapping(value="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete simpleConfig :{}", idArray);

		String[] ids = idArray.split(",");
		for (String id : ids ){
			simpleConfigService.delete(Long.parseLong(id));
		}

	}

	/**
	 * 更新通用配置
	 * @param simpleConfigEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产通用配置(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(value="/{id}")
	public SimpleConfigVO update(@RequestBody @Valid SimpleConfigEditDto simpleConfigEditDto, @PathVariable Long id){
		SimpleConfig simpleConfig = new SimpleConfig();
		BeanUtils.copyProperties(simpleConfigEditDto, simpleConfig);
		simpleConfig.setId(id);
		simpleConfigService.merge(simpleConfig);

		SimpleConfigVO vo = initViewProperty(simpleConfig);
		return  vo;
	}

	/**
	 * 根据ID查询通用配置
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询通用配置", httpMethod = "GET")
	@GetMapping(value="/{id}")
	public SimpleConfigVO get(@PathVariable Long id) {

		SimpleConfig simpleConfig = simpleConfigService.find(id);

		SimpleConfigVO vo = initViewProperty(simpleConfig);
		return vo;
	}

	/**
	 * 查询通用配置列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询通用配置列表", httpMethod = "POST")
	@PostMapping("/list")
	public PageContent<SimpleConfigVO> list(@RequestBody PageSearchRequest<SimpleConfigCondition> pageSearchRequest){

		PageRequest pageRequest = PageRequestConvert.convert(pageSearchRequest);

		Page<SimpleConfig> page = simpleConfigService.find(pageSearchRequest.getSearchCondition(), pageRequest);

		List<SimpleConfigVO> voList = new ArrayList<>();
		for(SimpleConfig simpleConfig : page.getContent()){
			voList.add(initViewProperty(simpleConfig));
		}

		PageContent<SimpleConfigVO> pageContent = new PageContent<>(voList, page.getTotalElements());
		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}

	private SimpleConfigVO initViewProperty(SimpleConfig simpleConfig){
	    SimpleConfigVO vo = new SimpleConfigVO();

        BeanUtils.copyProperties(simpleConfig, vo);

	    //初始化其他对象
	    initConfigTypePropertyGroup(vo, simpleConfig);
        return vo;
	}


	private void initConfigTypePropertyGroup(SimpleConfigVO simpleConfigVO, SimpleConfig simpleConfig){
	
		SimpleConfigType configType = simpleConfigTypeService.findByCode(simpleConfig.getConfigType());
		if(configType == null){
			return;
		}
		SimpleConfigTypeVO configTypeVO = new SimpleConfigTypeVO();
		BeanUtils.copyProperties(configType, configTypeVO);

		simpleConfigVO.setConfigTypeVO(configTypeVO);

	}


}
