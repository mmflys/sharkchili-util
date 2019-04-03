package com.shark.util.Exception;

/**
 * Class com.shark.job.exception.
 * @Author: SuLiang
 * @Date: 2018/10/31 0031
 */
public class ClassException extends UtilException{
	public ClassException() {
		super();
	}

	public ClassException(String message, Object... args) {
		super(message, args);
	}

	public ClassException(String message, Throwable cause, Object... args) {
		super(message, cause, args);
	}

	public ClassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
		super(message, cause, enableSuppression, writableStackTrace, args);
	}
}
