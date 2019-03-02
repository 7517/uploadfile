package com.edison.saas.bootstrap.common.business.attachment.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingcat on 2016/12/6.
 */
public class FileIputResponse {

	private List<InitialPreviewConfig> initialPreviewConfig = new ArrayList<>();

	private boolean success;
	public FileIputResponse(String caption, String width, String url, int key, String id){
		Extra extra = new Extra();
		extra.setId( id);
		InitialPreviewConfig config = new InitialPreviewConfig();
		config.setCaption(caption);
		config.setWidth(width);
		config.setUrl(url);
		config.setKey(key);
		config.setExtra(extra);
		initialPreviewConfig.add(config);
	}

	public static FileIputResponse success(String caption, String width, String url, int key, String id){
		FileIputResponse success = new FileIputResponse(caption, width, url, key, id);
		success.success = true;
		return success;
	}
	public static FileIputResponse failure(String caption, String width, String url, int key, String id){
		FileIputResponse failure = new FileIputResponse(caption, width, url, key, id);
		failure.success = false;
		return failure;
	}



	public List<InitialPreviewConfig> getInitialPreviewConfig() {
		return initialPreviewConfig;
	}

	public boolean isSuccess(){
		return success;
	}
}


class InitialPreviewConfig{
	private String caption;
	private String width;
	private String url;
	private int key;
	private Extra extra;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Extra getExtra() {
		return extra;
	}

	public void setExtra(Extra extra) {
		this.extra = extra;
	}
}

class Extra{
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}