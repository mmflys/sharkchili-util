package com.shark.util.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Log util
 * @Author: SuLiang
 * @Date: 2018/9/27 0027
 */
public class LogUtil {

	/**
	 * Get a logger instance with sl4j
	 * @param c class
	 * @return a logger
	 */
	public static Logger LOGGER(Class c){
		return LoggerFactory.getLogger(c);
	}

}
