package com.edison.saas.bootstrap.application.business.security.valid;



import com.edison.saas.platform.services.platform.business.security.role.dto.RoleResourceRelationAddDto;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class RoleResourceRelationValidator implements Validator {

	/**
	 * 判断支持的JavaBean类型
	 * @param aClass
	 * @return
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}

	/**
	 * 实现Validator中的validate接口
	 * @param obj
	 * @param errors
	 */
	@Override
	public void validate(Object obj, Errors errors) {
	    if(obj instanceof RoleResourceRelationAddDto){
            this.validateAddDto((RoleResourceRelationAddDto)obj, errors);
        }
	}

	/**
     * 验证新增信息
     * @param roleResourceRelation 角色资源关系
     * @param errors
     */
	public void validateAddDto(RoleResourceRelationAddDto roleResourceRelation, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
	}
}