package com.shark.util.util;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/10 0010
 */
public class ThreadUtil {

	/**
	 * Get class that located now
	 * @return class
	 */
	public static Class getLocationClass(){
		int length=Thread.currentThread() .getStackTrace().length;
		String clazz = Thread.currentThread() .getStackTrace()[length-1].getClassName();
		try {
			System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
			return Thread.currentThread().getContextClassLoader().loadClass(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Get method that located now
	 * @return method
	 */
	public static Method getLocationMethod(){
		Class c=getLocationClass();
		int length=Thread.currentThread() .getStackTrace().length;
		String methodName=Thread.currentThread() .getStackTrace()[length-1].getMethodName();
		try {
			return c.getDeclaredMethod(methodName);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

}
