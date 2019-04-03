package com.shark.util.Exception;

/**
 * File com.shark.job.exception.
 * @Author: SuLiang
 * @Date: 2018/10/31 0031
 */
public class FileException extends UtilException{
	public FileException() {
		super();
	}

	public FileException(String message, Object... args) {
		super(message, args);
	}

	public FileException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public FileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
		super(message, cause, enableSuppression, writableStackTrace, args);
	}
}
