package com.edison.saas.bootstrap.common.business.simpleconfig.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Size;


/**
 * 通用配置类型
 * @author icode
 */
@ApiModel(value = "新增通用配置类型使用的DTO")
public class SimpleConfigTypeAddDto {

    /**
	 * 类型名称
	 * 
     */
	@ApiModelProperty(value = "类型名称", required = false)
	@Size(max = 255, message = "类型名称超长，最多255个字符")
	private String typeName;

    /**
	 * 类型代码
	 * 
     */
	@ApiModelProperty(value = "类型代码", required = false)
	@Size(max = 255, message = "类型代码超长，最多255个字符")
	private String typeCode;


	public String getTypeName(){
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode(){
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
