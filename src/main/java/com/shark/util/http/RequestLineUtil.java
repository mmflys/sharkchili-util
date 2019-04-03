package com.shark.util.http;

import java.util.regex.Pattern;

/**
 * Request line util
 * @Author: SuLiang
 * @Date: 2018/9/16
 */
public class RequestLineUtil {
	public static final String METHOD_NAME = "method";
	public static final String METHOD_VALUE_GET = "GET";
	public static final String METHOD_VALUE_POST = "POST";

	public static final String PATH_TO_RESOURCE_NAME = "path-to-resource";

	public static final String VERSION_NUMBER_NAME = "version-number";

	/**
	 * Judge whether str is a http request line.
	 * eg: GET http://www.baidu.com/ HTTP/1.1
	 * @param str request string
	 * @return if str is a request line,return true,else false.
	 */
	public static boolean isRequestLine(String str) {
		String[] subStr = str.split(" ");
		for (String aSubStr : subStr) {
			if (!isMethod(aSubStr) && !isPathToResource(aSubStr) && !isVersionNumber(aSubStr)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Judge whether str is a request method(post,get)
	 * @param str request method
	 * @return if str is equal to "GET" or "POST",return true,else false.
	 */
	private static boolean isMethod(String str) {
		return str.equals(METHOD_VALUE_POST) || str.equals(METHOD_VALUE_GET);
	}

	/**
	 * Judge whether str is a url
	 * @param str url
	 * @return if str is a url,return true,else false
	 */
	private static boolean isPathToResource(String str) {
		String pattern = "[a-zA-z]+://[^\\s]*";
		return Pattern.matches(pattern, str);
	}

	/**
	 * Judge whether str is a version number
	 * @param str str
	 * @return if str is equal to HTTP_1_1 or HTTP_1_0,return true.
	 */
	private static boolean isVersionNumber(String str) {
		return str.equals("HTTP");
	}
}
