package com.edison.saas.bootstrap.application.business.security.valid;



import com.edison.saas.platform.services.platform.business.security.role.dto.RoleAddDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class RoleValidator implements Validator {

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
	    if(obj instanceof RoleAddDto){
            this.validateAddDto((RoleAddDto)obj, errors);
        }
	}

	/**
     * 验证新增信息
     * @param role 角色
     * @param errors
     */
	public void validateAddDto(RoleAddDto role, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
		if(StringUtils.length(role.getAppCode()) > 255){
			errors.rejectValue("appCode", null, "应用代码最长255个字符");
		}
		if(StringUtils.length(role.getCode()) > 255){
			errors.rejectValue("code", null, "代码最长255个字符");
		}
		if(StringUtils.length(role.getName()) > 255){
			errors.rejectValue("name", null, "名称最长255个字符");
		}
		if(StringUtils.length(role.getDescription()) > 255){
			errors.rejectValue("description", null, "描述最长255个字符");
		}
	}
}