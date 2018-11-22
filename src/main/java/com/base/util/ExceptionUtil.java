package com.base.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @Description: 常工具类
 *
 */
public class ExceptionUtil {

	/**
	 * 将CheckedException转换为UncheckedException.
	 * 
	 * @param e
	 * @return RuntimeException
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 * 
	 * @param e
	 * @return String
	 */
	public static String getStackTraceAsString(Throwable e) {
		if (e == null) {
			return "";
		}
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 * 
	 * @param ex
	 * @param causeExceptionClasses
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}

}
