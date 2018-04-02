package com.home.ferrari.plugin.push;

import java.io.IOException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler {

	private static final Logger logger;

	private static final ArrayList<WebSocketSession> users;

	static {
		users = new ArrayList<>();
		logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.warn("====================websocket connection success====================");
		users.add(session);
		String userName = (String) session.getAttributes().get(WebSocketConstants.WEBSOCKET_USERNAME);
		if (userName != null) {
			// 查询未读消息
			session.sendMessage(new TextMessage("websocket长连接建立成功"));
		}
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		// sendMessageToUsers();
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		if (session.isOpen()) {
			session.close();
		}
		logger.warn("====================websocket connection error====================");
		users.remove(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		logger.warn("====================websocket connection closed====================");
		users.remove(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 给所有在线用户发送消息
	 *
	 * @param message
	 */
	public void sendMessageToUsers(TextMessage message) {
		for (WebSocketSession user : users) {
			try {
				if (user.isOpen()) {
					user.sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 给某个用户发送消息
	 *
	 * @param userName
	 * @param message
	 */
	public void sendMessageToUser(String userName, TextMessage message) {
		for (WebSocketSession user : users) {
			if (user.getAttributes().get(WebSocketConstants.WEBSOCKET_USERNAME).equals(userName)) {
				try {
					if (user.isOpen()) {
						user.sendMessage(message);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
}
