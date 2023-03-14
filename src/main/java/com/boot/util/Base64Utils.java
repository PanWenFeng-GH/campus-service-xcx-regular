package com.boot.util;

import java.io.UnsupportedEncodingException;

import org.apache.tomcat.util.codec.binary.Base64;

public class Base64Utils {

	/**
	 * 使用进行编码
	 * @param base64Str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getBase64Str(String str) throws UnsupportedEncodingException {
		return Base64.encodeBase64String(str.getBytes("utf-8"));
	}

	/**
	 * 使用Base64 解码
	 * @param base64Str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getStr(String base64Str) {
		if (checkBase64(base64Str)) {
			try {
				return new String(Base64.decodeBase64(base64Str), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return base64Str;
	}

	/**
	* 判断是否进行base64加密
	* @param str
	* @return
	*/
	public static boolean checkBase64(String str) {
		if (str.length() % 4 != 0) {
			return false;
		}
		char[] charArray = str.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] >= 'A' && charArray[i] <= 'Z') {
				continue;
			}
			if (charArray[i] >= 'a' && charArray[i] <= 'z') {
				continue;
			}
			if (charArray[i] >= '0' && charArray[i] <= '9') {
				continue;
			}
			if (charArray[i] == '+' || charArray[i] == '\\' || charArray[i] == '=') {
				continue;
			}
			return false;
		}
		return true;
	}

}
