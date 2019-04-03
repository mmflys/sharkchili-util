package com.shark.util.classes.classloder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author: SuLiang
 * @Date: 2018/9/7 0007
 */
public abstract class AbstractSharkClassLoader extends ClassLoader{
	/**url string of parent the class*/
	String parentUrl;

	public AbstractSharkClassLoader(String parentUrl) {
		this.parentUrl = parentUrl;
	}

	AbstractSharkClassLoader() {
	}

	public Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getClassData(name);
		if (classData == null) {
			throw new ClassNotFoundException();
		} else {
			if (name.split("\\.").length<=1){
				return defineClass(null, classData, 0, classData.length);
			}else {
				try {
					return defineClass(name, classData, 0, classData.length);
				} catch (NoClassDefFoundError classFormatError) {
					return defineClass(null, classData, 0, classData.length);
				}
			}
		}
	}

	public Class<?> reDefineClass(String name, byte[] b, int off, int len){
		return super.defineClass(name, b, off, len);
	}

	public Class<?> findClass(byte[] bytes) throws ClassNotFoundException {
		if (bytes == null) {
			throw new ClassNotFoundException();
		} else {
			return defineClass(null, bytes, 0, bytes.length);
		}
	}

	public Class<?> findClass(URL url) throws ClassNotFoundException {
		byte[] classData = getClassData(url);
		if (classData == null) {
			throw new ClassNotFoundException();
		} else {
			return defineClass(null, classData, 0, classData.length);
		}
	}

	private byte[] getClassData(String className) {
		try {
			URL url= searchUrl(className);
			return ClassLoaderUtil.getByteDate(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private byte[] getClassData(URL url) {
		try {
			return ClassLoaderUtil.getByteDate(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * The url of class file
	 * @param className
	 * @return
	 */
	abstract URL searchUrl(String className) throws MalformedURLException;
}
