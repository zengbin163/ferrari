/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月26日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;
import org.springframework.web.socket.TextMessage;

import com.home.ferrari.enums.PlatformEnum;
import com.home.ferrari.plugin.push.SystemWebSocketHandler;

/** 
* @ClassName: WebUtil 
* @Description: web层的工具类
* @author zengbin
* @date 2015年10月26日 下午5:21:00 
*/
public class WebUtil {
    
    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);
    
    @Bean
    public static SystemWebSocketHandler systemWebSocketHandler() {
        return new SystemWebSocketHandler();
    }
    
    public static void returnJSON(HttpServletResponse response, String jsonData, String dataType) {
        if ("text".equals(dataType)) {
            response.setContentType("text/html;charset=UTF-8");
        } else {
            response.setContentType("application/json;charset=UTF-8");
        }
        response.setHeader("Charset", "UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            LoggerUtil.ERROR(logger, String.format(e.getMessage()));
        }
        out.write(jsonData);
        out.flush();
    }
    
    public static String escapeString(String value) {
        if (value == null) {
            return "";
        }
        value = StringUtils.replace(value, "|", "");
        value = StringUtils.replace(value, "&amp;", "");
        value = StringUtils.replace(value, "$", "");
        value = StringUtils.replace(value, "'", "");
        value = StringUtils.replace(value, "\"", "");
        value = StringUtils.replace(value, "\\'", "");
        value = StringUtils.replace(value, "&lt;", "");
        value = StringUtils.replace(value, "&gt;", "");
        value = StringUtils.replace(value, "<", "");
        value = StringUtils.replace(value, ">", "");
        value = StringUtils.replace(value, "\n", "");
        value = StringUtils.replace(value, "\r", "");
        value = StringUtils.replace(value, "\\", "");
        
        return value;
    }
    
    public final static String encode(String value){
		if (!org.springframework.util.StringUtils.hasText(value)) {
			return value;
		}
        try {
            return URLEncoder.encode(value,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public final static String decode(String value){
		if (!org.springframework.util.StringUtils.hasText(value)) {
			return value;
		}
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public final static String getSessionId(String mobile, PlatformEnum platform, String ipAddress, String desKey) {
    	Assert.notNull(mobile, "登陆账号不能为空");
    	Assert.notNull(platform, "platform不能为空");
    	StringBuffer sb = new StringBuffer();
    	sb.append("mobile=").append(DesUtil.encrypt(mobile, desKey)).append("|");
    	sb.append("platform=").append(platform.toString()).append("|");
    	sb.append("ipAddress=").append(ipAddress);
    	String base64Mobile = Base64Util.encode(sb.toString());
    	return base64Mobile;
    }
    
    public static void sendSocketMsg(String userName, String message) {
        systemWebSocketHandler().sendMessageToUser(userName, new TextMessage(message));
    }
    
    /**
     * 返回crm登录账号
     * @param crmUserId
     * @return
     */
    public final static String getCrmLoginNo(Integer crmUserId) {
    	String loginNo = "crm";
    	Integer length = crmUserId.toString().length();
		if (1 == length) {
			for (int i = 0; i < 5; i++) {
				int random = (int) (Math.random() * 10);
				loginNo = loginNo + random;
			}
		} else if (2 == length) {
			for (int i = 0; i < 4; i++) {
				int random = (int) (Math.random() * 10);
				loginNo = loginNo + random;
			}
		} else if (3 == length) {
			for (int i = 0; i < 3; i++) {
				int random = (int) (Math.random() * 10);
				loginNo = loginNo + random;
			}
		} else if (4 == length) {
			for (int i = 0; i < 2; i++) {
				int random = (int) (Math.random() * 10);
				loginNo = loginNo + random;
			}
		} else if (5 == length) {
			for (int i = 0; i < 1; i++) {
				int random = (int) (Math.random() * 10);
				loginNo = loginNo + random;
			}
		}else{
			;
		}
		return loginNo + crmUserId;
    }
    
    public static final void main(String []args) {
    	String sessionId = getSessionId("18867102687", PlatformEnum.FERRARI, "127.0.0.1", "asdfQ#!@#!");
    	System.out.println(sessionId);
    	System.out.println(getSessionId("18867102687", PlatformEnum.TESLA, "127.0.0.1", "asdfQ#!@#!"));
    	System.out.println(Base64Util.decode("bW9iaWxlPWw1dllhc2h3bU0xNi9RdXFBRUlWOVE9PXxwbGF0Zm9ybT1URVNMQXxpcEFkZHJlc3M9MTI3LjAuMC4x"));
    	System.out.println(DesUtil.decrypt("l5vYashwmM16/QuqAEIV9Q==", "ferrar!@#$%tesla"));
    }
}
