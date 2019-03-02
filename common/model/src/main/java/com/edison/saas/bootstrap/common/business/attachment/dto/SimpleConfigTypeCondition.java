package com.edison.saas.bootstrap.common.business.attachment.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@ApiModel(value = "查询通用配置类型使用的DTO")
@Getter
@Setter
public class SimpleConfigTypeCondition{

	@ApiModelProperty(value = "租户ID")
	private Long tid;

	@ApiModelProperty(value = "类型名称")
	private String typeName;
	@ApiModelProperty(value = "类型代码")
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
