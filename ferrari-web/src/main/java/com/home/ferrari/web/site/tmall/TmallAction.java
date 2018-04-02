package com.home.ferrari.web.site.tmall;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.util.access.AccessToken;
import com.home.ferrari.util.access.AccessTokenUtil;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/tmall")
public class TmallAction extends BaseAction {

	private static final long serialVersionUID = 4306842018709865027L;
	
	@RequestMapping("auth")
	@ResponseBody
	@LoginRequired(needLogin=false)
	public AccessToken auth() {
		String code = this.getFilteredParameter(request, "code", 0, null);
		System.out.println("code=" + code);
		AccessToken token = AccessTokenUtil.getAuthorizeAccessToken(code);
		return token;
	}
	
}
