package com.shark.util.Exception;

/**
 * @Author: Shark Chili
 * @Email: sharkchili.su@gmail.com
 * @Date: 2018/12/1 0001
 */
public class ProperitesException extends UtilException{
	public ProperitesException() {
		super();
	}

	public ProperitesException(String message, Object... args) {
		super(message, args);
	}

	public ProperitesException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ProperitesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
		super(message, cause, enableSuppression, writableStackTrace, args);
	}
}
