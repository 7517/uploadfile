package com.edison.saas.bootstrap.application.startcheck;

import com.edison.saas.bootstrap.application.business.security.service.ResourceConstants;
import com.edison.saas.bootstrap.application.business.security.service.ResourceRibbonService;
import com.edison.saas.bootstrap.application.business.security.service.RoleResourceRelationRibbonService;
import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceAddDto;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceVO;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationAddDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 系统管理功能配置页面
 */
@Component
@Order(value=1)
public class ApplicationResourceConfigCheckService implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationResourceConfigCheckService.class);

	@Autowired
    private ResourceRibbonService resourceService;

	@Autowired
	private RoleResourceRelationRibbonService roleResourceRelationRibbonService;

	@Value("${application.code:-1}")
	private String appCode;

    @Override
    public void run(String... args) throws Exception {

	    ResourceAddDto resource998 = new ResourceAddDto();
		resource998.setAppCode(appCode);
		resource998.setCode(ResourceConstants.CODE_YYGL);
	    resource998.setParentCode(-1L);
	    resource998.setName("系统管理");
	    resource998.setType("module");
		resource998.setHidden(false);
	    resource998.setOrderIndex(998);
	    checkResource(resource998);

		ResourceAddDto resource998001 = new ResourceAddDto();

		resource998001.setAppCode(appCode);
		resource998001.setCode(ResourceConstants.CODE_YYGL_AQPZ);
	    resource998001.setParentCode(998L);
	    resource998001.setName("安全配置");
	    resource998001.setType("module");
		resource998001.setHidden(false);
	    resource998001.setOrderIndex(1);
	    checkResource(resource998001);

//	    Resource resource998001001 = new ResourceAddDto();
//	    resource998001001.setId(998001001L);
//	    resource998001001.setParentCode(998001L);
//	    resource998001001.setName("资源配置");
//	    resource998001001.setType("page");
//	    resource998001001.setUrl("AM.controller.application.security.ResourceController");
//	    resource998001001.setOrderIndex(1);
//	    checkResource(resource998001001);

		ResourceAddDto resource998001002 = new ResourceAddDto();

		resource998001002.setAppCode(appCode);
		resource998001002.setCode(ResourceConstants.CODE_YYGL_JSQL);
	    resource998001002.setParentCode(998001L);
	    resource998001002.setName("角色管理");
	    resource998001002.setType("function");
	    resource998001002.setUrl("AM.controller.application.security.RoleController");
		resource998001002.setHidden(false);
	    resource998001002.setOrderIndex(1);
	    checkResource(resource998001002);

		ResourceAddDto resource998001003 = new ResourceAddDto();

		resource998001003.setAppCode(appCode);
		resource998001003.setCode(ResourceConstants.CODE_YYGL_ZHGL);
	    resource998001003.setParentCode(998001L);
	    resource998001003.setName("账号管理");
	    resource998001003.setType("function");
	    resource998001003.setUrl("AM.controller.application.security.AccountController");
		resource998001003.setHidden(false);
	    resource998001003.setOrderIndex(1);
	    checkResource(resource998001003);

		ResourceAddDto resource998002 = new ResourceAddDto();

		resource998002.setAppCode(appCode);
		resource998002.setCode(ResourceConstants.CODE_CSPZ);
	    resource998002.setParentCode(998L);
	    resource998002.setName("参数配置");
	    resource998002.setType("module");
		resource998002.setHidden(false);
	    resource998002.setOrderIndex(1);
	    checkResource(resource998002);

		ResourceAddDto resource998002001 = new ResourceAddDto();

		resource998002001.setAppCode(appCode);
		resource998002001.setCode(ResourceConstants.CODE_CSPZ_YWCSPZ);
	    resource998002001.setParentCode(998002L);
	    resource998002001.setName("业务参数配置");
	    resource998002001.setType("function");
	    resource998002001.setUrl("AM.controller.common.SimpleConfigController");
		resource998002001.setHidden(false);
	    resource998002001.setOrderIndex(1);
	    checkResource(resource998002001);

		ResourceAddDto resource998002002 = new ResourceAddDto();

		resource998002002.setAppCode(appCode);
		resource998002002.setCode(ResourceConstants.CODE_CSPZ_YWCSLXPZ);
		resource998002002.setParentCode(998002L);
		resource998002002.setName("业务参数类型配置");
		resource998002002.setType("function");
		resource998002002.setUrl("AM.controller.common.SimpleConfigTypeController");
		resource998002002.setHidden(false);
		resource998002002.setOrderIndex(2);
		checkResource(resource998002002);

    }

    private void checkResource(ResourceAddDto resource){
	    LOGGER.info("[check resource]:{}", resource);
    	ResourceVO resource1 = resourceService.findByCodeAndAppCode(resource.getCode(), Long.parseLong(resource.getAppCode()));
    	if(resource1 == null){
    		ResourceVO vo = resourceService.add(resource);
			resource.setId(vo.getId());

			checkRelation(resource);
	    }

    }

	public void checkRelation(ResourceAddDto addDto){
		RoleResourceRelationAddDto relation = new RoleResourceRelationAddDto();
		relation.setRole(Long.parseLong(appCode));
		relation.setTid(1L);
		relation.setResource(addDto.getId());
		check(relation);

	}

	private void check(RoleResourceRelationAddDto resourceRelation){
		Integer count = roleResourceRelationRibbonService.countByRoleIdAndResourceId(resourceRelation.getRole(), resourceRelation.getResource());
		if(count == null || count <= 0){
			roleResourceRelationRibbonService.add(resourceRelation);
		}
	}

}