package com.edison.saas.bootstrap.application.business.security.controller;


import com.edison.saas.bootstrap.application.business.security.service.RoleResourceRelationRibbonService;
import com.edison.saas.platform.services.application.business.security.dto.ParentResourceDto;
import com.edison.saas.platform.services.platform.business.security.role.vo.RoleResourceCheckTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理角色资源关系
 * @author icode
 */
@RestController("applicationRoleResourceRelationManageController")
@RequestMapping(value = "/application/security/roleResourceRelation")
public class RoleResourceRelationManageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleResourceRelationManageController.class);

	@Autowired
	private RoleResourceRelationRibbonService roleResourceRelationService;


	@Value("${application.code:-999}")
	private String appCode;

	/**
	 * Method getChildNodes.
	 * @param id int
	 * @param id int
	 * @return ResponseEntity<String>
	 */
//	@RequestMapping(params = "method=getChildNodes", headers="Accept=application/xml, application/json")
	@PostMapping(path = "/childNodes")
	public @ResponseBody
//	List<RoleResourceCheckTreeNode> getChildNodes(
//			@RequestParam(required=false,value="id",defaultValue = "-1") Long id,
//			@RequestParam(required=false,value="roleId",defaultValue = "-1") Long roleId
//	){
	List<RoleResourceCheckTreeNode> getChildNodes(
			@RequestBody ParentResourceDto parentResourceDto
	){

		parentResourceDto.setAppCode(appCode);
		return roleResourceRelationService.getChildNodes(parentResourceDto.getId(), parentResourceDto.getRoleId(), appCode);


	}






}
