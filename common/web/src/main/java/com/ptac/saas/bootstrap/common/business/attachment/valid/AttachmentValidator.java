package com.edison.saas.bootstrap.common.business.attachment.valid;

import com.edison.saas.bootstrap.common.business.attachment.domain.Attachment;
import com.edison.saas.bootstrap.common.business.attachment.dto.AttachmentAddDto;
import com.edison.saas.bootstrap.common.business.attachment.dto.AttachmentEditDto;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Service
public class AttachmentValidator implements Validator {

	/**
	 * 判断支持的JavaBean类型
	 * @param aClass
	 * @return
	 */
	@Override
	public boolean supports(Class<?> aClass) {
		if(AttachmentAddDto.class.equals(aClass))
			return true;
		if(AttachmentEditDto.class.equals(aClass))
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
	    if(obj instanceof AttachmentAddDto){
            this.validateAttachmentAddDto((AttachmentAddDto)obj, errors);
        }
	}

	/**
     * 实现Validator中的validate接口
     * @param attachment Attachment
     * @param errors
     */
	public void validateAttachmentAddDto(AttachmentAddDto attachment, Errors errors) {


		//把校验信息注册到Error的实现类里
		//验证必填

		//验证长度
		if(StringUtils.length(attachment.getName()) > 255){
			errors.rejectValue(Attachment.PROPERTY_NAME,null,"文件名最长255个字符");
		}
		if(StringUtils.length(attachment.getType()) > 255){
			errors.rejectValue(Attachment.PROPERTY_TYPE,null,"类型最长255个字符");
		}
		if(StringUtils.length(attachment.getNewFileName()) > 255){
			errors.rejectValue(Attachment.PROPERTY_NEW_FILE_NAME,null,"存储服务器上文件名称最长255个字符");
		}
		if(StringUtils.length(attachment.getContentType()) > 255){
			errors.rejectValue(Attachment.PROPERTY_CONTENT_TYPE,null,"文件类型最长255个字符");
		}
	}
}