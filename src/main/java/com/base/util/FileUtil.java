package com.base.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.List;

/**
 * 文件操作工具类
 */
public class FileUtil {

	private static final Logger logger = Logger.getRootLogger();

	/* 图片 */
	private static String[] pic = { "gif", "jpg", "jpeg", "png", "bmp" };
	/* 文档 */
	private static String[] doc = { "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt", "pdf" };
	/* 音频 */
	private static String[] mp3 = { "mp3" };
	/* 视频 */
	private static String[] avi = { "avi", "navi", "wma", "mpeg", "mpg", "mov", "wmv", "rmvb", "rm", "flash", "flv",
			"mp4", "mid", "3gp", "asf" };
	/* 其他 */
	private static String[] other = { "rar", "zip" };

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExtend(String fileName) {
		String extend = "";
		if (!StringUtils.isEmpty(fileName)) {
			int index = fileName.lastIndexOf('.');
			if (index > 0 && index < fileName.length() - 1) {
				extend = fileName.substring(index + 1).toLowerCase();
			}
		}
		return extend;
	}

	/**
	 * 定义允许上传的附件类别
	 * 
	 * @return
	 */
	public static List<Object> getAllowFileTypes() {
		List<Object> list = null;
		Object[] s = (Object[]) CommonUtil.addArray(pic, doc, mp3, avi, other);
		list = Arrays.asList(s);
		return list;
	}

	/**
	 * 该后缀是否为图片
	 * 
	 * @param fileExt
	 * @return
	 */
	public static boolean ifPic(String fileExt) {
		List<String> list = Arrays.asList(pic);
		return list.contains(fileExt);
	}

	/**
	 * 获取文件名称（不含后缀名）
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFilePrefix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(0, splitIndex);
	}

	/**
	 * 文件复制
	 * 
	 * @param inputFile
	 * @param outputFile
	 */
	public static void copyFile(String inputFile, String outputFile) {
		File sFile = new File(inputFile);
		File tFile = new File(outputFile);
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			if (!tFile.getParentFile().exists()) {
				tFile.getParentFile().mkdirs();
			}
			fi = new FileInputStream(sFile);
			fo = new FileOutputStream(tFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (Exception ex) {
			logger.error("文件复制失败。", ex);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (fi != null) {
					fi.close();
				}
				if (fo != null) {
					fo.close();
				}
			} catch (IOException ex) {
				logger.error("关闭文件资源失败。", ex);
			}
		}

	}

	/**
	 * 把文件流生成到磁盘中
	 * 
	 * @param fi
	 * @param outputFile
	 */
	public static void copyFile(FileInputStream fi, String outputFile) {
		File tFile = new File(outputFile);
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			if (!tFile.getParentFile().exists()) {
				tFile.getParentFile().mkdirs();
			}
			fo = new FileOutputStream(tFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (Exception ex) {
			logger.error("文件复制失败。", ex);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (fi != null) {
					fi.close();
				}
				if (fo != null) {
					fo.close();
				}
			} catch (IOException ex) {
				logger.error("关闭文件资源失败。", ex);
			}
		}

	}

	/**
	 * 创建多级目录
	 * 
	 * @param path
	 * @return
	 */
	public static boolean creatDirs(String path) {
		if (path.contains(".")) {
			path = new File(path).getParentFile().getPath();
		}
		File aFile = new File(path);
		if (!aFile.exists()) {
			return aFile.mkdirs();
		} else {
			return true;
		}
	}
}
