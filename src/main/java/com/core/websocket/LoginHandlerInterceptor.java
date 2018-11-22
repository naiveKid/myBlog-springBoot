package com.core.websocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

//握手类
public class LoginHandlerInterceptor extends HttpSessionHandshakeInterceptor {
	/**
	 * 客户端建立连接之前
	 * 
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		return super.beforeHandshake(request, response, wsHandler, attributes);//此处attributes循环添加了HttpSession中的值
	}
	/**
	 * 客户端建立连接之后
	 * 
	 */
	@Override
	public void afterHandshake(ServerHttpRequest request,
			ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception ex) {
		super.afterHandshake(request, response, wsHandler, ex);
	}
}
