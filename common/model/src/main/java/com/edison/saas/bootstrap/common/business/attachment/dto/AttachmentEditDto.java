package com.edison.saas.bootstrap.common.business.attachment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Size;
import java.util.Date;


/**
 * Attachment
 * @author icode
 */
@ApiModel(value = "修改Attachment使用的DTO")
public class AttachmentEditDto {


    /**
	 * 文件名
	 * 原始文件名
     */
	@ApiModelProperty(value = "文件名", required = false)
	@Size(max = 255, message = "文件名超长，最多255个字符")
	private String name;


    /**
	 * 类型
	 * 
     */
	@ApiModelProperty(value = "类型", required = false)
	@Size(max = 255, message = "类型超长，最多255个字符")
	private String type;


    /**
	 * 是否有效
	 * 
     */
	@ApiModelProperty(value = "是否有效", required = false)
	private Boolean disabled;


    /**
	 * 存储服务器上文件名称
	 * 
     */
	@ApiModelProperty(value = "存储服务器上文件名称", required = false)
	@Size(max = 255, message = "存储服务器上文件名称超长，最多255个字符")
	private String newFileName;


    /**
	 * 文件类型
	 * 
     */
	@ApiModelProperty(value = "文件类型", required = false)
	@Size(max = 255, message = "文件类型超长，最多255个字符")
	private String contentType;


    /**
	 * 文件大小
	 * 
     */
	@ApiModelProperty(value = "文件大小", required = false)
	private Long size;


    /**
	 * 创建时间
	 * 
     */
	@ApiModelProperty(value = "创建时间", required = false)
	private Date createTime;


	public String getName(){
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getType(){
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Boolean getDisabled(){
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public String getNewFileName(){
		return newFileName;
	}
	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	public String getContentType(){
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getSize(){
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}

	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
