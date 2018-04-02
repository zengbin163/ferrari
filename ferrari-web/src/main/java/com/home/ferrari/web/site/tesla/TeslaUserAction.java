package com.home.ferrari.web.site.tesla;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.tesla.TeslaUserService;
import com.home.ferrari.util.MD5Util;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/tesla")
public class TeslaUserAction extends BaseAction {

	private static final long serialVersionUID = 7135848433352574917L;
	
	@Autowired
	private TeslaUserService teslaUserService;
	
	@RequestMapping("login")
	@ResponseBody
	@LoginRequired(needLogin=false)
	public Map<String, Object> login() {
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		String password = MD5Util.md5(this.getFilteredParameter(request, "password", 0, null));
		return this.teslaUserService.login(mobile, password, getIpAddr(), response);
	}
	
	@RequestMapping("password")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> password() {
		String mobile = this.getTeslaUser().getMobile();
		String oldPass = MD5Util.md5(this.getFilteredParameter(request, "oldPass", 0, null));
		String newPass = MD5Util.md5(this.getFilteredParameter(request, "newPass", 0, null));
		return this.teslaUserService.password(mobile, oldPass, newPass);
	}

	@RequestMapping("resetPass")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> resetPass() {
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		return this.teslaUserService.resetPass(shopName);
	}
	
	@RequestMapping("logout")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> logout() {
		String sessionId = this.getFilteredParameter(request, "sessionId", 0, null);
		return this.teslaUserService.logout(sessionId);
	}
}
