package com.edison.saas.bootstrap.application.business.security.service;

import com.edison.saas.common.framework.exception.BusinessException;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.controller.RestResponse;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import com.edison.saas.platform.services.application.business.security.dto.ParentResourceDto;
import com.edison.saas.platform.services.application.client.security.RoleResourceRelationClient;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationAddDto;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationCondition;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationEditDto;
import com.edison.saas.platform.services.platform.business.security.role.vo.RoleResourceCheckTreeNode;
import com.edison.saas.platform.services.platform.business.security.role.vo.RoleResourceRelationVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("roleResourceRelationRibbonService")
public class RoleResourceRelationRibbonService  {

	private static final Logger LOGGER = LoggerFactory.getLogger(RoleResourceRelationRibbonService.class);


	@Autowired
	private RoleResourceRelationClient roleResourceRelationClient;

	public List<RoleResourceCheckTreeNode> getChildNodes(Long id, Long roldId, String appCode){

		ParentResourceDto dto = new ParentResourceDto();
		dto.setId(id);
		dto.setAppCode(appCode);
		dto.setRoleId(roldId);
		RestResponse<List<RoleResourceCheckTreeNode>> result = roleResourceRelationClient.getChildNodes(dto);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
		return result.getData();
	}

	public RoleResourceRelationVO add(RoleResourceRelationAddDto addDto){
		RestResponse<RoleResourceRelationVO> result = roleResourceRelationClient.add(addDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
		return result.getData();
	
	}

	public void delete(Long id){
		if(null == id){
			LOGGER.warn("try delete T by empty id. Code need check");
			return ;
		}
		LOGGER.debug("delete t:{}", id);
		RestResponse<RoleResourceRelationVO> result = roleResourceRelationClient.delete(id);
		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}
	}
	public RoleResourceRelationVO merge(Long id, RoleResourceRelationEditDto editDto){
		RestResponse<RoleResourceRelationVO> result = roleResourceRelationClient.update(id, editDto);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
	public RoleResourceRelationVO find(Long id){
		RestResponse<RoleResourceRelationVO> result = roleResourceRelationClient.get(id);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public Integer countByRoleIdAndResourceId(Long roleId, Long resourceId){
		RestResponse<Integer> result = roleResourceRelationClient.countByRoleIdAndResourceId(roleId, resourceId);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}

	public PageContent<RoleResourceRelationVO> list(PageSearchRequest<RoleResourceRelationCondition> pageSearchRequest) {
		RestResponse<PageContent<RoleResourceRelationVO>> result = roleResourceRelationClient.list(pageSearchRequest);

		if(!result.isSuccess()){
			throw new BusinessException("PLATFORM", "SECURITY", result.getCode()+"", result.getMessage());
		}

		return result.getData();
	}
}
