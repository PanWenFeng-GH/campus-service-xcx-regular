package com.boot.util.htttp;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class HttpInfo {
	private String charset = "UTF-8";
	private Map<String, String> cookies = new HashMap<>();
	private Map<String, String> headers = new HashMap<>();

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public Map<String, String> getCookies() {
		return cookies;
	}

	public void setCookies(Map<String, String> cookies) {
		this.cookies = cookies == null ? new HashMap<String, String>() : cookies;
	}

	public void setCookies(String cookie) {
		this.cookies = CookieUtil.parseCookieString(cookie);
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers == null ? new HashMap<String, String>() : headers;
	}

	public void streamHeaders(Stream stream) {
		streamMap(headers, stream);
	}

	public void streamCookies(Stream stream) {
		streamMap(cookies, stream);
	}

	public void streamMap(Map<String, String> map, Stream stream) {
		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> entry : entrySet) {
			stream.call(entry.getKey(), entry.getValue());
		}
	}

	public static interface Stream {
		void call(String key, String value);
	}
}