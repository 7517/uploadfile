package com.edison.saas.bootstrap.application.business.tenant.valid;


import com.edison.saas.platform.services.platform.business.tenant.dto.TenantAddDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class TenantValidator implements Validator {

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
	    if(obj instanceof TenantAddDto){
            this.validateAddDto((TenantAddDto)obj, errors);
        }
	}

	/**
     * 验证新增信息
     * @param tenant 租户
     * @param errors
     */
	public void validateAddDto(TenantAddDto tenant, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
		if(StringUtils.length(tenant.getTenantCode()) > 255){
			errors.rejectValue("tenantCode", null, "租户代号最长255个字符");
		}
		if(StringUtils.length(tenant.getName()) > 255){
			errors.rejectValue("name", null, "租户名称最长255个字符");
		}
		if(StringUtils.length(tenant.getCountry()) > 255){
			errors.rejectValue("country", null, "国家最长255个字符");
		}
		if(StringUtils.length(tenant.getProvince()) > 255){
			errors.rejectValue("province", null, "省份最长255个字符");
		}
		if(StringUtils.length(tenant.getCity()) > 255){
			errors.rejectValue("city", null, "市、县最长255个字符");
		}
		if(StringUtils.length(tenant.getAddress()) > 255){
			errors.rejectValue("address", null, "详细地址最长255个字符");
		}
		if(StringUtils.length(tenant.getFax()) > 255){
			errors.rejectValue("fax", null, "传真最长255个字符");
		}
		if(StringUtils.length(tenant.getTelephoneNo()) > 255){
			errors.rejectValue("telephoneNo", null, "联系电话最长255个字符");
		}
		if(StringUtils.length(tenant.getCrmCode()) > 255){
			errors.rejectValue("crmCode", null, "CRM系统代码最长255个字符");
		}
		if(StringUtils.length(tenant.getPrefixDomainName()) > 255){
			errors.rejectValue("prefixDomainName", null, "域名前缀最长255个字符");
		}
	}
}