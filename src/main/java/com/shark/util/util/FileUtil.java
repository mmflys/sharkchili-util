package com.shark.util.util;

import com.shark.util.Exception.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * File util
 *
 * @Author: SuLiang
 * @Date: 2018/9/6 0006
 */
public class FileUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * Read properties file
	 *
	 * @param fileName fine name
	 * @return A properties class of the file.
	 */
	public static Properties readProperties(String fileName) {
		return readProperties(fileName, FileUtil.class);
	}

	/**
	 * Read properties file according to file name and class.
	 *
	 * @param fileName file name
	 * @param c        search in relative path of a class
	 * @return A properties class of the file.
	 */
	public static Properties readProperties(String fileName, Class c) {
		Properties properties = new Properties();
		InputStream inputStream = c.getResourceAsStream(fileName);
		try {
			properties.load(inputStream);
		} catch (Exception e) {
			throw new FileException("Properties file is not found", e.getCause(), fileName);
		}
		return properties;
	}
}
