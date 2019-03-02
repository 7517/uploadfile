package com.edison.saas.bootstrap.common.business.simpleconfig.domain;

import com.edison.saas.common.framework.eo.GenericBaseEntity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 通用配置
 * @author icode
 */
@Entity
@Table(name = "common_simple_config")
//@DynamicUpdate
//@DynamicInsert
public class SimpleConfig extends GenericBaseEntity<Long>{

	public static final String PROPERTY_CONFIG_TYPE = "configType";
	public static final String PROPERTY_TID = "tid";
	public static final String PROPERTY_DISPLAY_NAME = "displayName";
	public static final String PROPERTY_CODE = "code";
	public static final String PROPERTY_VALUE = "value";
	public static final String PROPERTY_DESCRIPTION = "description";
	public static final String PROPERTY_V_INDEX = "vIndex";


    @Id
    @Column(name = "rid")
    private Long id;


    /**
    * 配置类型
    * 
    */
    @Column(name = "config_type")
	@NotNull(message = "配置类型不能为空")
	private String configType;

    /**
    * 租户ID
    *
    */
    @Column(name = "tid", updatable = false)
	private Long tid;

    /**
    * 参数名称
    * 
    */
    @Column(name = "display_name")
	@NotNull(message = "参数名称不能为空")
	@Size(max = 255, message = "参数名称超长，最多255个字符")
	private String displayName;

    /**
    * 参数代码
    * 
    */
    @Column(name = "code")
	@NotNull(message = "参数代码不能为空")
	@Size(max = 255, message = "参数代码超长，最多255个字符")
	private String code;

    /**
    * 参数值
    * 
    */
    @Column(name = "value")
	@NotNull(message = "参数值不能为空")
	@Size(max = 255, message = "参数值超长，最多255个字符")
	private String value;

    /**
    * 参数说明
    * 
    */
    @Column(name = "description", columnDefinition = "TEXT")
	private String description;

    /**
    * 展现顺序
    * 
    */
    @Column(name = "v_index")
	private Integer vIndex;

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

