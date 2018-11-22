package com.core.websocket;

import com.base.util.WebSocketUtil;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

//处理类
public class LoginHandler extends TextWebSocketHandler {
	// 用于保存当前在线用户
	private static final Map<String, WebSocketSession> userMap;
	// 初始化map对象
	static {
		userMap = new HashMap<String, WebSocketSession>();
	}

	public LoginHandler() {
	}

	// 获取当前在线人员集合
	public static Map<String, WebSocketSession> getUserMap() {
		return userMap;
	}

	/**
	 * 连接成功之后，会触发页面上onopen方法
	 */
	@Override
    public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		// 当前连接的用户
		String userName = (String) session.getAttributes().get("userName");
		//之前已存在该用户且不为同一个WebSocketSession会话,则移除之前该用户
		if (userMap.containsKey(userName)&&!userMap.containsValue(session)) {
			WebSocketSession oldUser = userMap.get(userName);
			if (oldUser.isOpen()) {
				// 如果存在并且还是连接着的，则关闭连接，页面上收到弹出提示
				oldUser.close();
			}
		}
		// 添加当前登录用户
		userMap.put(userName, session);
		// 给所有在线人员广播当前在线人数消息
        Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("msgType", "updateCount");
		pMap.put("onlineCount", String.valueOf(userMap.size()));
		WebSocketUtil.sendMessageToAllUser(userMap, pMap);
	}

	/**
	 * 关闭连接之后触发页面的onclose方法
	 */
	@Override
    public void afterConnectionClosed(WebSocketSession session,
                                      CloseStatus closeStatus) throws Exception {
		// 当前连接的用户
		String userName = (String) session.getAttributes().get("userName");
		// 如果map中存在当前session用户，则移除
		if (userMap.containsValue(session)) {
			userMap.remove(userName);
		}
		// 给所有在线人员广播当前在线人数消息
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("msgType", "updateCount");
		pMap.put("onlineCount", String.valueOf(userMap.size()));
		WebSocketUtil.sendMessageToAllUser(userMap, pMap);
	}

	/**
	 * 当消息传送发生错误
	 */
	@Override
    public void handleTransportError(WebSocketSession session,
                                     Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		// 当前连接的用户
		String userName = (String) session.getAttributes().get("userName");
		if (userMap.containsValue(session)) {
			userMap.remove(userName);
		}

		// 给所有在线人员广播当前在线人数消息
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("msgType", "updateCount");
		pMap.put("onlineCount", String.valueOf(userMap.size()));
		WebSocketUtil.sendMessageToAllUser(userMap, pMap);
	}

    /**
     * 接受到客户端发送的文本消息的处理,并发送回去
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        //心跳重连检测
        if("heartBeat".equals(message.getPayload())){
            // 当前连接的用户
            String userName = (String) session.getAttributes().get("userName");
            if(userMap.containsKey(userName)){
                //构建消息
                Map<String,String> messageMap = new HashMap<String, String>();
                messageMap.put("msgType","heartBeat");
                messageMap.put("status","200");
                WebSocketUtil.sendMessageBySession(userMap.get(userName),messageMap);
            }
        }
    }

    /**
     * 接收到客户端传输的ping消息,发送回去pong消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session,message);
    }
}