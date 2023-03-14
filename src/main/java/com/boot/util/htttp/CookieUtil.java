package com.boot.util.htttp;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CookieUtil {
	public static final String COOKIE_SPLT = "; ";
	public static final String COOKIE_EQ = "=";

	/**
	 * parse the cookie string into a map
	 */
	public static Map<String, String> parseCookieString(String cookie) {
		Map<String, String> cookies = new HashMap<>();
		if (cookie != null) {
			String[] split = cookie.split(COOKIE_SPLT);
			for (String s : split) {
				int firstEqual = s.indexOf(COOKIE_EQ);
				if (firstEqual != -1) {
					cookies.put(s.substring(0, firstEqual), s.substring(firstEqual + 1, s.length()));
				}
			}
		}
		return cookies;
	}

	/**
	 * generate a cookie string with a map
	 */
	public static String toCookieString(Map<String, String> cookies) {
		String result = "";
		Set<Entry<String, String>> entrySet = cookies.entrySet();
		for (Entry<String, String> entry : entrySet) {
			result += entry.getKey() + COOKIE_EQ + entry.getValue() + COOKIE_SPLT;
		}
		return result;
	}
}
