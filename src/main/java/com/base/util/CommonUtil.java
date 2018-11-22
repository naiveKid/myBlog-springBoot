package com.base.util;

import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 */
public class CommonUtil {
	/**
	 * 数组合并 多个数组合并成一个
	 * 
	 * @param strs
	 * @return
	 */
	public static Object addArray(Object[]... strs) {
		Object[] rs = (Object[]) new Object[0];
		for (Object[] str : strs) {
			rs = (Object[]) ArrayUtils.addAll(rs, str);
		}
		return rs;
	}

	/**
	 * 数组合并 多个数组合并成一个
	 * 
	 * @param strs
	 * @return
	 */
	public static String[] addArray(String[]... strs) {
		String[] rs = (String[]) new String[0];
		for (String[] str : strs) {
			rs = (String[]) ArrayUtils.addAll(rs, str);
		}
		return rs;
	}

	/**
	 * 去掉空格 回车 换行
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 替换字符串中的单引号和双引号
	 * @param str
	 * @return
	 */
	public static String replaceDSYin(String str) {
		str = str.replaceAll("\"", "\\\\\"");
		str = str.replaceAll("\'", "\\\\\'");
		return str;
	}

	public static int getDyReadonly(String str) {
		int i = 0;
		try {
			i = str.split("LODOP.ADD_PRINT_TEXT").length - 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 判断对象是否为空，如果对象为ArrayList，且不为空，则还要判断是否有长度
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(Object obj) {
		List list = null;
		String str = null;
		boolean flag = false;
		String[] strs = null;
		Long l;
		if (obj == null) {
			flag = true;
		} else {
			if (obj.getClass().getSimpleName().equals("ArrayList")) {
				list = (ArrayList) obj;
				if (list.size() == 0) {
					flag = true;
				}
			}
			if (obj.getClass().getSimpleName().equals("String[]")) {
				strs = (String[]) obj;
				if (strs.length == 1) {
					if (CommonUtil.isNull(strs[0])) {
						flag = true;
					}
				}
			}
			if (obj.getClass().getSimpleName().equals("String")) {
				str = (String) obj;
				if (str.trim().equals("") || str.trim().equals("null") || str.trim().equals("NULL")) {
					flag = true;
				}
			}
			if (obj.getClass().getSimpleName().equals("Long")) {
				l = (Long) obj;
				if (l == 0) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * String参数转Integer对象
	 * @param str
	 * @return
	 */
	public static Integer stringToInteger(String str) {
		Integer obj = null;
		if (CommonUtil.isNull(str)) {
			obj = null;
		} else {
			obj = new Integer(str);
		}
		return obj;
	}

	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getClintIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (!CommonUtil.isNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (!CommonUtil.isNull(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
}
