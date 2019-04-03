package com.shark.util.classes.classloder;


import com.shark.util.util.StringUtil;

import java.net.MalformedURLException;
import java.net.URL;

public class ClassLoaderFactory {

	public static ClassLoader getClassLoader() {
		return system();
	}

	private static ClassLoader system() {
		return ClassLoader.getSystemClassLoader();
	}

	/**
	 * Get a class loader according to the url str
	 * @param urlStr url,if url is null,set url root path of project
	 * @return a class loader
	 */
	public static ClassLoader classLoader(String urlStr) {
		try {
			URL url = new URL(urlStr);
			if (url.getProtocol().equals("http") || url.getProtocol().equals("https")) {
				return new NetworkClassLoader(urlStr);
			} else {
				return new FileSystemClassLoader(urlStr);
			}
		} catch (MalformedURLException e) {
			String projectRootDir= StringUtil.getUserDir();
			return new FileSystemClassLoader(projectRootDir);
		}
	}

}
