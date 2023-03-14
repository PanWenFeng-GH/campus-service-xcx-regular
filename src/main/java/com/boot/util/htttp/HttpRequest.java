package com.boot.util.htttp;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author xiechanglei  
 *
 */
/**
 * @author Administrator
 *
 */
public class HttpRequest extends HttpInfo {
	public static final int DEFAULT_TIMEOUT = 20 * 1000;
	private Integer connectTimeOut = DEFAULT_TIMEOUT;
	private Integer readTimeOut = DEFAULT_TIMEOUT;
	private Map<String, String> params = new HashMap<>();
	private Method method = Method.GET;
	private String writeBody;
	private boolean jsonRequest = false;

	public Method getMethod() {
		return method;
	}

	public HttpRequest setMethod(Method method) {
		this.method = method;
		return this;
	}

	public HttpRequest m(Method method) {
		return this.setMethod(method);
	}

	public Integer getConnectTimeOut() {
		return connectTimeOut;
	}

	public HttpRequest setConnectTimeOut(Integer timeOut) {
		this.connectTimeOut = timeOut == null ? DEFAULT_TIMEOUT : timeOut;
		return this;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public Integer getReadTimeOut() {
		return readTimeOut;
	}

	public HttpRequest setReadTimeOut(Integer readTimeOut) {
		this.readTimeOut = readTimeOut == null ? DEFAULT_TIMEOUT : readTimeOut;
		return this;
	}

	public static int getDefaultTimeout() {
		return DEFAULT_TIMEOUT;
	}

	public HttpRequest setParams(Map<String, String> params) {
		this.params = params == null ? new HashMap<String, String>() : params;
		return this;
	}

	/**
	 * easy api
	 * 
	 * @return
	 */
	public static HttpRequest createDefaultRequestInfo() {
		HttpRequest requestInfo = new HttpRequest();
		requestInfo.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		return requestInfo;
	}

	public static HttpRequest create() {
		return createDefaultRequestInfo();
	}

	public HttpRequest addParam(String key, Object value) {
		this.params.put(key, value.toString());
		return this;
	}

	public HttpRequest p(String key, Object value) {
		return this.addParam(key, value);
	}

	public HttpRequest removeParam(String key) {
		this.params.remove(key);
		return this;
	}

	public void streamParams(Stream stream) {
		streamMap(params, stream);
	}

	public HttpRequest write(String body) {
		this.writeBody = body;
		return this;
	}

	/**
	 * 写入json,请求头中自动加入对应的contentType
	 * 
	 * @return
	 */
	public HttpRequest writeJsonStr(String body) {
		this.writeBody = body;
		this.jsonRequest = true;
		return this;
	}

	public HttpRequest writeJson(Object jsonObject) throws Exception {
		//this.writeBody = JsonUtil.toJson(jsonObject);
		this.writeBody = JSON.toJSONString(jsonObject);
		this.jsonRequest = true;
		return this;
	}

	public String getWriteBody() {
		return this.writeBody;
	}

	// ================== for method chain
	public HttpRequest c(String key, Object value) {
		return this.addCookie(key, value);
	}

	public HttpRequest addCookie(String key, Object value) {
		super.getCookies().put(key, value.toString());
		return this;
	}

	public HttpRequest removeCookie(String key) {
		super.getCookies().remove(key);
		return this;
	}

	public HttpRequest h(String key, Object value) {
		return this.addHeader(key, value);
	}

	public HttpRequest addHeader(String key, Object value) {
		super.getHeaders().put(key, value.toString());
		return this;
	}

	public HttpRequest removeHeader(String key) {
		super.getHeaders().remove(key);
		return this;
	}

	public boolean isJsonRequest() {
		return jsonRequest;
	}
}