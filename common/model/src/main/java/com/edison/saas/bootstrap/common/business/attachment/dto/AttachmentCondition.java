package com.edison.saas.bootstrap.common.business.attachment.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;


@ApiModel(value = "查询Attachment使用的DTO")
@Getter
@Setter
public class AttachmentCondition implements Serializable{

	@ApiModelProperty(value = "文件名")
	private String name;
	@ApiModelProperty(value = "类型")
	private String type;
	@ApiModelProperty(value = "是否有效")
	private Boolean disabled;
	@ApiModelProperty(value = "存储服务器上文件名称")
	private String newFileName;
	@ApiModelProperty(value = "文件类型")
	private String contentType;
	@ApiModelProperty(value = "文件大小")
	private Long size;
	@ApiModelProperty(value = "文件大小最大值")
	private Long sizeMax;
	@ApiModelProperty(value = "文件大小最小值")
	private Long sizeMin;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "起始创建时间")
	private Date createTimeStart;
	@ApiModelProperty(value = "结束创建时间")
	private Date createTimeEnd;


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

	public Long getSizeMin(){
		return sizeMin;
	}
	public void setSizeMin(Long sizeMin) {
		this.sizeMin = sizeMin;
	}

	public Long getSizeMax(){
		return sizeMax;
	}
	public void setSizeMax(Long sizeMax) {
		this.sizeMax = sizeMax;
	}


	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getCreateTimeStart(){
		return createTimeStart;
	}
	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public Date getCreateTimeEnd(){
		return createTimeEnd;
	}
	public void setCreateTimeEnd(Date createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}




	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
