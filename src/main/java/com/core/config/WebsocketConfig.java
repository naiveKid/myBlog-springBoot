package com.core.config;

import com.core.websocket.LoginHandler;
import com.core.websocket.LoginHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
// 声明支持websocket
// 注册类(依赖握手类)
public class WebsocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// 注册websocket实现类，指定参数访问地址;allowed-origins="*" 允许跨域
		registry.addHandler(myLoginHandler(), "/websocket")
				.addInterceptors(myLoginHandshake()).setAllowedOrigins("*");
		// 允许客户端使用SockJS
		registry.addHandler(myLoginHandler(), "/websocket/sockjs")
				.addInterceptors(myLoginHandshake()).withSockJS();
	}

	@Bean
	public LoginHandler myLoginHandler() {
		return new LoginHandler();
	}

	@Bean
	public LoginHandlerInterceptor myLoginHandshake() {
		return new LoginHandlerInterceptor();
	}
}
