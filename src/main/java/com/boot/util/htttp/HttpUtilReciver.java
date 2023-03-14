package com.boot.util.htttp;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.boot.util.StringUtil;


public class HttpUtilReciver {

	// ==================================the impl==============================
	protected static HttpResponse requestForm(String url, HttpRequest request) throws Exception {
		request = request == null ? HttpRequest.createDefaultRequestInfo() : request;
		HttpResponse response = new HttpResponse();
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			url = rebuildUrl(url, request);
			final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();// open connection
			connection.setRequestMethod(request.getMethod().toString());
			if (url.indexOf("https") == 0) {
				SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
				sslcontext.init(null, new TrustManager[] { myX509TrustManager }, new java.security.SecureRandom());
				((HttpsURLConnection) connection).setSSLSocketFactory(sslcontext.getSocketFactory());
			}
			connection.setConnectTimeout(request.getConnectTimeOut());
			connection.setReadTimeout(request.getReadTimeOut());
			connection.setRequestProperty("Cookie", CookieUtil.toCookieString(request.getCookies())); // set cookie
			if (request.isJsonRequest()) {
				connection.setRequestProperty("Content-Type", "application/json; charset=" + request.getCharset());
			} else {
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=" + request.getCharset());
			}
			request.streamHeaders(new HttpInfo.Stream() {// set the header
				@Override
				public void call(String key, String value) {
					connection.setRequestProperty(key, value);
				}
			});
			if (request.getMethod() == Method.POST || request.getMethod() == Method.PUT) {// set the params
				connection.setDoOutput(true);
				outputStream = connection.getOutputStream();
				PrintWriter pw = new PrintWriter(outputStream);
				pw.print(buildParam(request));
				pw.flush();
			}
			inputStream = connection.getInputStream();
			response.setCode(connection.getResponseCode());
			loadHeaders(response, connection);
			String content = "";
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, request.getCharset()));
			while (true) {
				String readLine = reader.readLine();
				if (readLine != null) {
					content += readLine + "\n";
				} else {
					break;
				}
			}
			response.setContent(content);
			return response;
		} catch (Exception e) {
			throw e;
		} finally {
			StreamUtil.closeStream(inputStream, outputStream);
		}
	}

	private static TrustManager myX509TrustManager = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	};

	/**
	 * load the headers as a map from the connection
	 */
	private static void loadHeaders(HttpResponse response, final HttpURLConnection connection) {
		Map<String, List<String>> headerFields = connection.getHeaderFields();
		Set<Entry<String, List<String>>> entrySet = headerFields.entrySet();
		for (Entry<String, List<String>> entry : entrySet) {
			response.addHeader(entry.getKey(), connection.getHeaderField(entry.getKey()));
		}
	}

	/**
	 * build the request param as a String from a map
	 */
	private static String buildParam(final HttpRequest request) {

		String writeBody = request.getWriteBody();
		if (StringUtil.isNotBlank(writeBody)) {
			return writeBody;
		} else {
			final StringBuilder builder = new StringBuilder("");
			request.streamParams(new HttpInfo.Stream() {
				@Override
				public void call(String key, String value) {
					try {
						builder.append("&" + URLEncoder.encode(key, request.getCharset()) + "=" + URLEncoder.encode(value, request.getCharset()));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			});
			return builder.toString();
		}

	}

	/**
	 * when the request is set a get method, the url will be rebuild
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private static String rebuildUrl(String url, HttpRequest request) throws UnsupportedEncodingException {
		if (request.getMethod() == Method.GET) {
			int index = url.indexOf("?");
			if (index == -1) {
				url += "?";
			}
			url += buildParam(request);
		}
		return url;
	}
}