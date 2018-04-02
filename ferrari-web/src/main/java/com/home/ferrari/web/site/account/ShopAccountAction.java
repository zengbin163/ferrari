package com.home.ferrari.web.site.account;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.account.ShopAccountHeaderService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/shopAccount")
public class ShopAccountAction extends BaseAction {

	private static final long serialVersionUID = 6951287193018992135L;
	@Autowired
	private ShopAccountHeaderService shopAccountHeaderService;

	@RequestMapping("getShopAccount")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopAccount() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.shopAccountHeaderService.getShopAccountList(this.getTeslaUser().getShopId(), pageOffset, pageSize);
	}

	
}
