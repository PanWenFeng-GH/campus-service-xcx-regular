package com.boot.util.htttp;
public enum Method {
	POST("POST"), GET("GET"), PUT("PUT");
	private Method(String str) {
		this.str = str;
	}

	private String str;

	public String toString() {
		return str;
	}
}