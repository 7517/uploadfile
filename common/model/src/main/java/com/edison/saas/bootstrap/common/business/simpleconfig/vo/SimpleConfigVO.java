package com.edison.saas.bootstrap.common.business.simpleconfig.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
* 通用配置的值对象
*/
@ApiModel(value = "展现通用配置的值对象")
public class SimpleConfigVO {

    @ApiModelProperty(value = "记录id")
    private Long id;

    /**配置类型*/
    @ApiModelProperty(value = "配置类型")
    private String configType;
    private SimpleConfigTypeVO configTypeVO;

    /**参数名称*/
    @ApiModelProperty(value = "参数名称")
    private String displayName;

    /**参数代码*/
    @ApiModelProperty(value = "参数代码")
    private String code;

    /**参数值*/
    @ApiModelProperty(value = "参数值")
    private String value;

    /**参数说明*/
    @ApiModelProperty(value = "参数说明")
    private String description;

    /**展现顺序*/
    @ApiModelProperty(value = "展现顺序")
    private Integer vIndex;


    public String getConfigType(){
        return configType;
    }
    public void setConfigType(String configType) {
        this.configType = configType;
    }
    public SimpleConfigTypeVO getConfigTypeVO(){
        return configTypeVO;
    }
    public void setConfigTypeVO(SimpleConfigTypeVO configTypeVO) {
        this.configTypeVO = configTypeVO;
    }

    public String getDisplayName(){
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode(){
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getValue(){
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVIndex(){
        return vIndex;
    }
    public void setVIndex(Integer vIndex) {
        this.vIndex = vIndex;
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