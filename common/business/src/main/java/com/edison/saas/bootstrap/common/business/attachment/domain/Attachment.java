package com.edison.saas.bootstrap.common.business.attachment.domain;

import com.edison.saas.common.framework.eo.GenericBaseEntity;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * Attachment
 * @author icode
 */
@Entity
@Table(name = "common_attachment")
//@DynamicUpdate
//@DynamicInsert
public class Attachment extends GenericBaseEntity<Long>{

	public static final String PROPERTY_NAME = "name";
	public static final String PROPERTY_TYPE = "type";
	public static final String PROPERTY_DISABLED = "disabled";
	public static final String PROPERTY_NEW_FILE_NAME = "newFileName";
	public static final String PROPERTY_CONTENT_TYPE = "contentType";
	public static final String PROPERTY_SIZE = "size";
	public static final String PROPERTY_CREATE_TIME = "createTime";


    @Id
    @Column(name = "id")
    private Long id;


    /**
    * 文件名
    * 原始文件名
    */
    @Column(name = "name")
	@Size(max = 255, message = "文件名超长，最多255个字符")
	private String name;

    /**
    * 类型
    * 
    */
    @Column(name = "type")
	@Size(max = 255, message = "类型超长，最多255个字符")
	private String type;

    /**
    * 是否有效
    * 
    */
    @Column(name = "disabled")
	private Boolean disabled;

    /**
    * 存储服务器上文件名称
    * 
    */
    @Column(name = "new_file_name")
	@Size(max = 255, message = "存储服务器上文件名称超长，最多255个字符")
	private String newFileName;

    /**
    * 文件类型
    * 
    */
    @Column(name = "content_type")
	@Size(max = 255, message = "文件类型超长，最多255个字符")
	private String contentType;

    /**
    * 文件大小
    * 
    */
    @Column(name = "size")
	private Long size;

    /**
    * 创建时间
    * 
    */
    @Column(name = "create_time")
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

