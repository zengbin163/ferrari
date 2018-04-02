package com.home.ferrari.web.site.crm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/passport")
public class PassportAction extends BaseAction {

	private static final long serialVersionUID = 2477151474792449583L;
	@Autowired
	private CrmUserService crmUserService;
	
	/**
	 * 平台和门店跳转crm
	 * @param sessionId 平台 门店 CRM的sessionId
	 * @param platform 
	 * 			0 平台跳CRM
	 * 		    1 门店跳CRM
	 * 		    2 CRM跳平台
	 * 		    3 CRM跳门店
	 * @return
	 */
	@RequestMapping("verify")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> verify() {
		String sessionId = this.getFilteredParameter(request, "sessionId", 0, null);
		Integer platform = this.getIntParameter(request, "platform", null);
		return this.crmUserService.passport(sessionId, platform, getIpAddr(), response);
	}
}
