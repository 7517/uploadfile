package com.edison.saas.bootstrap.application.business.security.valid;



import com.edison.saas.platform.services.platform.business.security.account.dto.AccountPasswordAddDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AccountPasswordValidator implements Validator {

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
	    if(obj instanceof AccountPasswordAddDto){
            this.validateAddDto((AccountPasswordAddDto)obj, errors);
        }
	}

	/**
     * 验证新增信息
     * @param accountPassword 账号密码
     * @param errors
     */
	public void validateAddDto(AccountPasswordAddDto accountPassword, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
		if(StringUtils.length(accountPassword.getAccountName()) > 255){
			errors.rejectValue("accountName", null, "姓名最长255个字符");
		}
		if(StringUtils.length(accountPassword.getPassword()) > 255){
			errors.rejectValue("password", null, "密码最长255个字符");
		}
	}
}