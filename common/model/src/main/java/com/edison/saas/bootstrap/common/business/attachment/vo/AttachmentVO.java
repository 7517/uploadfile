package com.edison.saas.bootstrap.common.business.attachment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;


/**
* Attachment的值对象
*/
@ApiModel(value = "展现Attachment的值对象")
public class AttachmentVO {

    @ApiModelProperty(value = "记录id")
   private Long id;

    /**
    * 文件名
    * 原始文件名
    */
    @ApiModelProperty(value = "文件名")
    private String name;

    /**
    * 类型
    * 
    */
    @ApiModelProperty(value = "类型")
    private String type;

    /**
    * 是否有效
    * 
    */
    @ApiModelProperty(value = "是否有效")
    private Boolean disabled;

    /**
    * 存储服务器上文件名称
    * 
    */
    @ApiModelProperty(value = "存储服务器上文件名称")
    private String newFileName;

    /**
    * 文件类型
    * 
    */
    @ApiModelProperty(value = "文件类型")
    private String contentType;

    /**
    * 文件大小
    * 
    */
    @ApiModelProperty(value = "文件大小")
    private Long size;

    /**
    * 创建时间
    * 
    */
    @ApiModelProperty(value = "创建时间")
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