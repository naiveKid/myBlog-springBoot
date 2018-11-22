package com.base.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 发送http请求
 *
 */
public class HttpClientUtil {

	/**
	 * Get请求
	 * 
	 * @param u 请求地址
	 * @param charset 字符编码
	 * @return
	 */
	public static String dojsonGet(String u, String charset) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(u);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("contentType", charset);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
			String lines;
			while ((lines = reader.readLine()) != null) {
				sb.append(lines);
			}
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
