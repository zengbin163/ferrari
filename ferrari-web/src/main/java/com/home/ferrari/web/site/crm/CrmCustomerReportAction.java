package com.home.ferrari.web.site.crm;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.crm.CrmCustomerReportService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/crmCustomerReport")
public class CrmCustomerReportAction extends BaseAction {

	private static final long serialVersionUID = -3052588713850217380L;
	
	@Autowired
	private CrmCustomerReportService crmCustomerReportService;
	
	/**
	 * 潜客管理-全部批次
	 * @return
	 */
	@RequestMapping("batchReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> batchReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer year = this.getIntParameter(request, "year", null);
		Integer month = this.getIntParameter(request, "month", null);
		Integer seed = this.getIntParameter(request, "seed", null);
		String userName = this.getFilteredParameter(request, "userName", 0, null);
		Integer uploadStatus = this.getIntParameter(request, "uploadStatus", null);
		String keyword = this.getFilteredParameter(request, "keyword", 0, null);
		return this.crmCustomerReportService.getCustomerBatchReport(pageOffset, pageSize, year, month, seed, userName, uploadStatus, keyword);
	}
	
	/**
	 * 潜客管理-数据分析 右侧统计报表
	 * @return
	 */
	@RequestMapping("rightStatsCrmCustomer")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> rightStatsCrmCustomer() {
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		return this.crmCustomerReportService.rightStatsCrmCustomer(isPerson);
	}

	/**
	 * 潜客管理-数据分析 按业务员数
	 * @return
	 */
	@RequestMapping("statsByBizAdmin")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> statsByBizAdmin() {
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		return this.crmCustomerReportService.statsByBizAdmin(isPerson);
	}
	
	/**
	 * 潜客管理-数据分析 按客户意向统计
	 * @return
	 */
	@RequestMapping("statsByPurpose")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> statsByPurpose() {
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		return this.crmCustomerReportService.statsByPurpose(isPerson);
	}
	
	/**
	 * 潜客管理-数据分析 按业务类型统计
	 * @return
	 */
	@RequestMapping("statsByBizType")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> statsByBizType() {
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		return this.crmCustomerReportService.statsByBizType(isPerson);
	}
	
	/**
	 * 潜客管理-业务员创建的客户统计
	 * @return
	 */
	@RequestMapping("statsByBizAdminCreate")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> statsByBizAdminCreate() {
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		return this.crmCustomerReportService.statsByBizAdminCreate(isPerson);
	}
}
