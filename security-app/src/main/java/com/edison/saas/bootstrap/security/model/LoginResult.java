package com.edison.saas.bootstrap.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 登陆结果
 * Created by gonghongrui on 2017/1/5.
 */
@ApiModel
public class LoginResult {

	@ApiModelProperty(value = "是否成功")
	private boolean success;

	@ApiModelProperty(value = "获取到的sessionId")
	private String sessionId;

	@ApiModelProperty(value = "失败后的提示消息")
	private String message;

	public LoginResult(Boolean success){
		this.success = success;
	}
	public LoginResult(Boolean success, String message){
		this.success = success;
		this.message = message;
	}

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
