package com.edison.saas.bootstrap.application.business.security.valid;



import com.edison.saas.platform.services.platform.business.security.resource.dto.ResourceAddDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class ResourceValidator implements Validator {

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
	    if(obj instanceof ResourceAddDto){
            this.validateAddDto((ResourceAddDto)obj, errors);
        }
	}

	/**
     * 验证新增信息
     * @param resource 资源
     * @param errors
     */
	public void validateAddDto(ResourceAddDto resource, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
		if(StringUtils.length(resource.getAppCode()) > 255){
			errors.rejectValue("appCode", null, "所属应用最长255个字符");
		}
		if(StringUtils.length(resource.getName()) > 255){
			errors.rejectValue("name", null, "名称最长255个字符");
		}
		if(StringUtils.length(resource.getUrl()) > 255){
			errors.rejectValue("url", null, "资源链接最长255个字符");
		}
		if(StringUtils.length(resource.getType()) > 255){
			errors.rejectValue("type", null, "资源类型最长255个字符");
		}
		if(StringUtils.length(resource.getIconClass()) > 255){
			errors.rejectValue("iconClass", null, "资源图标最长255个字符");
		}
	}
}