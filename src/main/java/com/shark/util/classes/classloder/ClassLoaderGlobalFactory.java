package com.shark.util.classes.classloder;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/14 0014
 */
public class ClassLoaderGlobalFactory {

	private static final Map<String, ClassLoader> CLASS_LOADERS = Maps.newHashMap();

	public  static ClassLoader classLoader(String urlStr) {
		ClassLoader classLoader = findClassLoader(urlStr);
		if (classLoader == null) {
			classLoader = ClassLoaderFactory.classLoader(urlStr);
		}
		return classLoader;
	}

	private static ClassLoader findClassLoader(String urlStr) {
		for (String key : CLASS_LOADERS.keySet()) {
			if (key.equals(urlStr)) {
				return CLASS_LOADERS.get(key);
			}
		}
		return null;
	}
}
