package com.home.ferrari.web.site.complain;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.complain.ComplainReportService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/complainReport")
public class ComplainReportAction extends BaseAction {

	private static final long serialVersionUID = 1199208125839639785L;
	
	@Autowired
	private ComplainReportService complainReportService;
	
	@RequestMapping("reportStatistics")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> reportStatistics() {
		Integer shopId = this.getIntParameter(request, "shopId", null);
		return this.complainReportService.reportStatistics(shopId);
	}
	
	@RequestMapping("getShopReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		return this.complainReportService.getShopReport(pageOffset, pageSize,
				province, city, bizOrderId);
	}
	
}
