package com.boot.util.htttp;
/**
 * 
 * @author xiechanglei
 *
 */
public class HttpUtil {

	// ================================get==================================
	public static HttpResponse get(String url) throws Exception {
		return HttpUtilReciver.requestForm(url, HttpRequest.createDefaultRequestInfo());
	}

	public static HttpResponse get(String url, HttpRequest request) throws Exception {
		request.setMethod(Method.GET);
		return HttpUtilReciver.requestForm(url, request);
	}

	// ================================post==================================
	public static HttpResponse post(String url) throws Exception {
		return HttpUtilReciver.requestForm(url, HttpRequest.createDefaultRequestInfo().setMethod(Method.POST));
	}

	public static HttpResponse post(String url, HttpRequest request) throws Exception {
		request.setMethod(Method.POST);
		return HttpUtilReciver.requestForm(url, request);
	}

	// ================================put==================================
	public static HttpResponse put(String url) throws Exception {
		return HttpUtilReciver.requestForm(url, HttpRequest.createDefaultRequestInfo().setMethod(Method.PUT));
	}

	public static HttpResponse put(String url, HttpRequest request) throws Exception {
		request.setMethod(Method.PUT);
		return HttpUtilReciver.requestForm(url, request);
	}

	public static HttpResponse servive(String url) throws Exception {
		return HttpUtilReciver.requestForm(url, HttpRequest.createDefaultRequestInfo());
	}

	public static HttpResponse servive(String url, HttpRequest request) throws Exception {
		return HttpUtilReciver.requestForm(url, request);
	}

}