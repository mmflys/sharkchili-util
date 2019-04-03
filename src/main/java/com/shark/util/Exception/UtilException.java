package com.shark.util.Exception;

import com.shark.util.util.StringUtil;

/**
 * Abstract util com.shark.job.exception.
 * @Author: SuLiang
 * @Date: 2018/10/31 0031
 */
public abstract class UtilException extends RuntimeException{
	UtilException() {
		super();
	}

	UtilException(String message, Object... args) {
		super(StringUtil.format(message,args));
	}

	UtilException(String message, Throwable cause, Object... args) {
		super(StringUtil.format(message,args), cause);
	}

	UtilException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object... args) {
		super(StringUtil.format(message,args), cause, enableSuppression, writableStackTrace);
	}
}