package com.edison.saas.bootstrap.common.business.simpleconfig.valid;

import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfigType;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigTypeAddDto;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigTypeEditDto;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class SimpleConfigTypeValidator implements Validator {

	/**
	 * 判断支持的JavaBean类型
	 * @param aClass
	 * @return
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		if(SimpleConfigTypeAddDto.class.equals(aClass))
			return true;
		if(SimpleConfigTypeEditDto.class.equals(aClass))
			return true;
		if(PageSearchRequest.class.equals(aClass))
			return true;
		return false;
	}

	/**
	 * 实现Validator中的validate接口
	 * @param obj
	 * @param errors
	 */
	@Override
	public void validate(Object obj, Errors errors) {
	    if(obj instanceof SimpleConfigTypeAddDto){
            this.validateSimpleConfigTypeAddDto((SimpleConfigTypeAddDto)obj, errors);
        }
	}

	/**
     * 实现Validator中的validate接口
     * @param simpleConfigType 通用配置类型
     * @param errors
     */
	public void validateSimpleConfigTypeAddDto(SimpleConfigTypeAddDto simpleConfigType, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
		if(StringUtils.length(simpleConfigType.getTypeName()) > 255){
			errors.rejectValue(SimpleConfigType.PROPERTY_TYPE_NAME,null,"类型名称最长255个字符");
		}
		if(StringUtils.length(simpleConfigType.getTypeCode()) > 255){
			errors.rejectValue(SimpleConfigType.PROPERTY_TYPE_CODE,null,"类型代码最长255个字符");
		}
	}
}