package com.home.ferrari.web.push;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

import com.home.ferrari.plugin.push.SystemWebSocketHandler;
import com.home.ferrari.plugin.push.WebSocketConstants;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/socket")
public class WebSocketPushAction extends BaseAction {

	private static final long serialVersionUID = 4170003762449105891L;

	    @Bean
	    public SystemWebSocketHandler systemWebSocketHandler() {
	        return new SystemWebSocketHandler();
	    }

	    @RequestMapping("/auditing")
	    @ResponseBody
	    public String auditing(HttpServletRequest request){
	    	String userName = this.getFilteredParameter(request, WebSocketConstants.SESSION_USERNAME, 0, null);
	    	String tutorials = this.getFilteredParameter(request, WebSocketConstants.SESSION_TUTORIALS, 0, null);
	        //无关代码都省略了
	        systemWebSocketHandler().sendMessageToUser(userName, new TextMessage(tutorials));
	        return null;
	    }
}
