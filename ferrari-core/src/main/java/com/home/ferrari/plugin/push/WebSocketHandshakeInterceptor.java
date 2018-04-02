package com.home.ferrari.plugin.push;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.CachePrefixEnum;
import com.home.ferrari.plugin.cache.RedisService;
import com.home.ferrari.plugin.exception.TeslaBizException;
import com.home.ferrari.plugin.session.Session;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.util.SpringContextUtil;
import com.home.ferrari.vo.tesla.user.TeslaUser;

public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {
	

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
			Session session = SessionContainer.getSession();
			TeslaUser teslaUser = null;
			if(null == session) {
				RedisService redisService = SpringContextUtil.getBean("redisService");
				String sessionId = servletRequest.getServletRequest().getParameter("sessionId");
				Assert.notNull(sessionId, "sessionId不能为空");
	            Object obj = redisService.getObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
				if (null == obj) {
					throw new TeslaBizException(ResultCode.NO_LOGIN, sessionId, String.format("用户未登录，sessionId=", sessionId));
				}
				session = (Session) obj;
				teslaUser = session.getTeslaUser();
				SessionContainer.setSession(session);
			}
			if(null==teslaUser) {
				throw new TeslaBizException(ResultCode.SHOP_CAN_LISTEN, session.getMobile(), String.format("只有门店系统才能开启语音提示功能，mobile=", session.getMobile()));
			}
			attributes.put(WebSocketConstants.WEBSOCKET_USERNAME, WebSocketConstants.SESSION_SHOP_PREFIX + teslaUser.getShopId());
		}
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

	}
}
