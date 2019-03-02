package com.edison.saas.bootstrap.application.business.security.valid;



import com.edison.saas.platform.services.platform.business.security.account.dto.AccountAddDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AccountValidator implements Validator {

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
	    if(obj instanceof AccountAddDto){
            this.validateAddDto((AccountAddDto)obj, errors);
        }
	}

	/**
     * 验证新增信息
     * @param account 账号
     * @param errors
     */
	public void validateAddDto(AccountAddDto account, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
		if(StringUtils.length(account.getNickName()) > 255){
			errors.rejectValue("nickName", null, "昵称最长255个字符");
		}
		if(StringUtils.length(account.getName()) > 255){
			errors.rejectValue("name", null, "姓名最长255个字符");
		}
		if(StringUtils.length(account.getAccountName()) > 255){
			errors.rejectValue("accountName", null, "账号最长255个字符");
		}
		if(StringUtils.length(account.getMobile()) > 255){
			errors.rejectValue("mobile", null, "手机号最长255个字符");
		}
		if(StringUtils.length(account.getEmail()) > 255){
			errors.rejectValue("email", null, "邮箱最长255个字符");
		}
	}
}