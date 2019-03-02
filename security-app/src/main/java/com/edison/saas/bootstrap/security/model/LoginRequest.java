package com.edison.saas.bootstrap.security.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created by gonghongrui on 2017/1/12.
 */
@ApiModel(value = "登录请求")
public class LoginRequest {

	@ApiModelProperty(value = "登录用户名", required = false)
	private String username;

	@ApiModelProperty(value = "登录密码", required = false)
	private String password;

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
