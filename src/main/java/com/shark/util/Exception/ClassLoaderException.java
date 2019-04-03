package com.shark.util.Exception;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/3 0003
 */
public class ClassLoaderException extends UtilException{
	public ClassLoaderException() {
		super();
	}

	public ClassLoaderException(String message, Object... args) {
		super(message, args);
	}

	public ClassLoaderException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ClassLoaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
		super(message, cause, enableSuppression, writableStackTrace, args);
	}
}
