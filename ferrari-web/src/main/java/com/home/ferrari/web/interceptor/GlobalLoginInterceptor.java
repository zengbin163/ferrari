package com.home.ferrari.web.interceptor;

import java.io.Serializable;
import java.lang.reflect.Method;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.CachePrefixEnum;
import com.home.ferrari.global.GlobalConstants;
import com.home.ferrari.plugin.cache.RedisService;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.session.Session;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.ferrari.FerrariUserService;

public class GlobalLoginInterceptor implements HandlerInterceptor, Serializable {

	private static final long serialVersionUID = -7315374246047220621L;

	@Autowired
	private RedisService redisService;
	
	@Autowired
	private FerrariUserService ferrariUserService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler instanceof HandlerMethod){
	        HandlerMethod handlerMethod = (HandlerMethod) handler;
	        Method method = handlerMethod.getMethod();
	        LoginRequired annotation = method.getAnnotation(LoginRequired.class);
	        if (annotation != null) {
	            boolean needLogin = annotation.needLogin();
	            if (!needLogin) {
	            	return true;
	            }
				Session session = SessionContainer.getSession();
				if(null == session) {
		            String sessionId = request.getParameter("sessionId");
		            if(StringUtils.isEmpty(sessionId)) {
					 	Cookie []cookies = request.getCookies();
					 	if(null == cookies) {
					 		throw new FerrariBizException(ResultCode.NO_LOGIN_NO_COOKIE, "Cookie不存在，请用户登录");
					 	}
						for (int i = 0; i < cookies.length; i++) {
							if(GlobalConstants.SESSION_ID.equals(cookies[i].getName())) {
								sessionId = cookies[i].getValue();
							}
						}
		            }
					Assert.notNull(sessionId, "sessionId不能为空");
		            Object obj = this.redisService.getObj(CachePrefixEnum.PREFIX_SESSION_ + sessionId);
					if (null == obj) {
						throw new FerrariBizException(ResultCode.NO_LOGIN, sessionId, String.format("用户未登录，sessionId=", sessionId));
					}
					SessionContainer.setSession((Session) obj);
				}
				this.auth(request, SessionContainer.getSession(), annotation);
	        }
		}
		return true;
	}
	
	/**
	 * 权限认证
	 * @param request
	 * @param session
	 */
	private void auth(HttpServletRequest request, Session session, LoginRequired annotation) {
        if (!annotation.needAuth()) {
        	return;
        }
		String requestUrl = request.getContextPath() + request.getServletPath();
		this.ferrariUserService.hasAuthority(requestUrl, session);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception {
	
	}
}
