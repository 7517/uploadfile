package com.edison.saas.bootstrap.common.business.simpleconfig.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
* 通用配置类型的值对象
*/
@ApiModel(value = "展现通用配置类型的值对象")
public class SimpleConfigTypeVO {

    @ApiModelProperty(value = "记录id")
    private Long id;

    /**类型名称*/
    @ApiModelProperty(value = "类型名称")
    private String typeName;

    /**类型代码*/
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


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}