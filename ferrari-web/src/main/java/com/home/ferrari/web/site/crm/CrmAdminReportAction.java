package com.home.ferrari.web.site.crm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.crm.CrmAdminReportService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/crmAdminReport")
public class CrmAdminReportAction extends BaseAction {

	private static final long serialVersionUID = -305279116229858735L;
	
	@Autowired
	private CrmAdminReportService crmAdminReportService;
	
	/**
	 * 星奥-CRM账号管理-数据统计 省份一部分
	 * @return
	 */
	@RequestMapping("crmProvinceReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> crmProvinceReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmAdminReportService.crmProvinceReport(pageOffset, pageSize);
	}

	/**
	 * 星奥-CRM账号管理-数据统计 门店公司一部分
	 * @return
	 */
	@RequestMapping("crmShopReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> crmShopReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String crmProvince = this.getFilteredParameter(request, "crmProvince", 0, null);
		String crmCity = this.getFilteredParameter(request, "crmCity", 0, null);
		String crmShopName = this.getFilteredParameter(request, "crmShopName", 0, null);
		return this.crmAdminReportService.crmShopReport(pageOffset, pageSize, crmProvince, crmCity, crmShopName);
	}
	
	
}
