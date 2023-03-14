package com.boot.util.htttp;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;

public class HttpResponse extends HttpInfo {
	private Integer code;
	private String content;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public <T> T jsonToObject(Class<T> t) throws Exception {
		return JSON.parseObject(this.content,t);
	}

	public String readStringFromJson(String expression) throws Exception {
		return (String) JsonPath.read(this.content, expression);
	}

	public Integer readIntFromJson(String expression) throws Exception {
		return (Integer) JsonPath.read(this.content, expression);
	}

	public Long readLongFromJson(String expression) throws Exception {
		return (Long) JsonPath.read(this.content, expression);
	}

	public Double readDoubleFromJson(String expression) throws Exception {
		return (Double) JsonPath.read(this.content, expression);
	}

	public Boolean readBoolFromJson(String expression) throws Exception {
		return (Boolean) JsonPath.read(this.content, expression);
	}

	public void addHeader(String key, Object value) {
		super.getHeaders().put(key, value.toString());
	}

}