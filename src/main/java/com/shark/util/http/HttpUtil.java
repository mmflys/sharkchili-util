package com.shark.util.http;

import com.github.kevinsawicki.http.HttpRequest;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Http util
 * @Author: SuLiang
 * @Date: 2018/9/16
 */
public class HttpUtil {
	private static final Logger LOGGER=LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * List of request header keys
	 */
	private static List<String> requestHeaderKey;

	static {
		requestHeaderKey = Lists.newArrayList();
		requestHeaderKey.add(HttpRequest.HEADER_ACCEPT);
		requestHeaderKey.add(HttpRequest.HEADER_ACCEPT_CHARSET);
		requestHeaderKey.add(HttpRequest.HEADER_ACCEPT_ENCODING);
		requestHeaderKey.add(HttpRequest.HEADER_AUTHORIZATION);
		requestHeaderKey.add(HttpRequest.HEADER_CONTENT_TYPE);
		requestHeaderKey.add(HttpRequest.HEADER_CONTENT_LENGTH);
		requestHeaderKey.add(HttpRequest.HEADER_CONTENT_ENCODING);
		requestHeaderKey.add(HttpRequest.HEADER_CACHE_CONTROL);
		requestHeaderKey.add(HttpRequest.HEADER_DATE);
		requestHeaderKey.add(HttpRequest.HEADER_ETAG);
		requestHeaderKey.add(HttpRequest.HEADER_EXPIRES);
		requestHeaderKey.add(HttpRequest.HEADER_IF_NONE_MATCH);
		requestHeaderKey.add(HttpRequest.HEADER_LAST_MODIFIED);
		requestHeaderKey.add(HttpRequest.HEADER_LOCATION);
		requestHeaderKey.add(HttpRequest.HEADER_PROXY_AUTHORIZATION);
		requestHeaderKey.add(HttpRequest.HEADER_REFERER);
		requestHeaderKey.add(HttpRequest.HEADER_SERVER);
		requestHeaderKey.add(HttpRequest.HEADER_USER_AGENT);
		requestHeaderKey.add("Host");
		requestHeaderKey.add("Connection");
		requestHeaderKey.add("Upgrade-Insecure-Requests");
		requestHeaderKey.add("Accept-Language");
		requestHeaderKey.add("Cookie");
		requestHeaderKey.add("Purpose");
	}

	/**
	 * Judge request whether is a http request or not
	 *
	 * @param requestHead request head contain key and value
	 * @return a map of request header
	 */
	public static Map<String,String> isHttpRequestHeader(String requestHead) {
		requestHead=requestHead.trim();
		Map<String,String> header=Maps.newHashMap();
		String[] subHeader=requestHead.split("\r\n");
		for (String sh : subHeader) {
			String[] subStr1 = sh.split(":");
			String[] subStr2 = sh.split(": ");
			String[] subStr=subStr2.length==2?subStr2:subStr1;
			if (subStr.length == 2) {
				String key = subStr[0];
				if (requestHeaderKey.contains(key)) {
					String value = subStr[1];
					header.put(key,value);
				}else {
					LOGGER.error("Request header Key not contain key ["+key+"]");
				}
			}
			// judge whether requestHead is a request line or not
			if (subStr.length==1){
				if (RequestLineUtil.isRequestLine(requestHead)){
					header.put("requestLine",requestHead);
				}
			}
		}
		return header;
	}
}
