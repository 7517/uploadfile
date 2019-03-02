package com.edison.saas.bootstrap.common.business.attachment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@ApiModel(value = "查询通用配置使用的DTO")
@Getter
@Setter
public class SimpleConfigCondition{

    @ApiModelProperty(value = "配置类型")
    private String configType;
	@ApiModelProperty(value = "租户ID")
	private Long tid;
	@ApiModelProperty(value = "参数名称")
	private String displayName;
	@ApiModelProperty(value = "参数代码")
	private String code;


    public String getConfigType(){
        return configType;
    }
    public void setConfigType(String configType) {
        this.configType = configType;
    }


	public Long getTid(){
		return tid;
	}
	public void setTid(Long tid) {
		this.tid = tid;
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




	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
