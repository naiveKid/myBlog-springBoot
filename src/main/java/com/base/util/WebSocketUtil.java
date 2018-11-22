package com.base.util;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;

/**
 * 关于websocket的工具类
 * 
 */
public class WebSocketUtil {

	/**
	 * 给指定的session用户发送消息
	 * 
	 * @param session
	 *            发送对象
	 * @param map
	 *            要发送的消息
	 * @throws Exception
	 */
	public static void sendMessageBySession(WebSocketSession session, Map<String, String> map) throws Exception {
		if (session.isOpen()) {
			try {
				session.sendMessage(new TextMessage(JsonUtil.toJson(map)));
			} catch (Exception e) {
				session.close();
			}
		}
	}

	/**
	 * 给所有在线用户发送消息
	 * 
	 * @param userMap
	 *            在线用户
	 * @param map
	 *            要发送的消息
	 */
	public static void sendMessageToAllUser(Map<String, WebSocketSession> userMap, Map<String, String> map) throws Exception {
		if (!CommonUtil.isNull(userMap) && userMap.size() > 0) {
			for (WebSocketSession wss : userMap.values()) {
				WebSocketUtil.sendMessageBySession(wss, map);
			}
		}
	}
}
