package com.home.ferrari.web.site.crm;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.service.sms.CrmSmsService;
import com.home.ferrari.vo.sms.SmsMeal;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/crmSms")
public class CrmSmsAction extends BaseAction {

	private static final long serialVersionUID = -2075416821003837742L;
	@Autowired
	private CrmSmsService crmSmsService;
	
	/**
	 * 刷新星奥总库存
	 * @return
	 */
	@RequestMapping("refreshSmsTotal")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> refreshSmsTotal() {
		return this.crmSmsService.refreshSmsTotal();
	}
	
	/**
	 * 查询星奥首页总库存量
	 * @return
	 */
	@RequestMapping("smsTotalReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> smsTotalReport() {
		Integer year = this.getIntParameter(request, "year", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getSmsTotalReport(year, pageOffset, pageSize);
	}
	
	/**
	 * 创建套餐
	 * @return
	 */
	@RequestMapping("saveSmsMeal")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> saveSmsMeal() {
		String mealName = this.getFilteredParameter(request, "mealName", 0, null);
		Long smsCount = this.getLongParameter(request, "smsCount", null);
		BigDecimal salePrice = this.getDecimalParameter(request, "salePrice", null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		SmsMeal smsMeal = new SmsMeal();
		smsMeal.setMealName(mealName);
		smsMeal.setSmsCount(smsCount);
		smsMeal.setSalePrice(salePrice);
		smsMeal.setRemark(remark);
		smsMeal.setUploadStatus(DefaultEnum.YES.getCode());
		return this.crmSmsService.saveSmsMeal(smsMeal);
	}

	/**
	 * 套餐详情
	 * @return
	 */
	@RequestMapping("detailSmsMeal")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> detailSmsMeal() {
		Integer id = this.getIntParameter(request, "id", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.detailSmsMeal(id, pageOffset, pageSize);
	}
	
	/**
	 * 编辑套餐
	 * @return
	 */
	@RequestMapping("editSmsMeal")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> editSmsMeal() {
		Integer id = this.getIntParameter(request, "id", null);
		String mealName = this.getFilteredParameter(request, "mealName", 0, null);
		Long smsCount = this.getLongParameter(request, "smsCount", null);
		BigDecimal salePrice = this.getDecimalParameter(request, "salePrice", null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		SmsMeal smsMeal = new SmsMeal();
		smsMeal.setId(id);
		smsMeal.setMealName(mealName);
		smsMeal.setSmsCount(smsCount);
		smsMeal.setSalePrice(salePrice);
		smsMeal.setRemark(remark);
		return this.crmSmsService.editSmsMeal(smsMeal);
	}

	/**
	 * 套餐上架或者下架
	 * @return
	 */
	@RequestMapping("activeOrFrozenSmsMeal")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> activeOrFrozenSmsMeal() {
		Integer id = this.getIntParameter(request, "id", null);
		Integer uploadStatus = this.getIntParameter(request, "uploadStatus", null);
		return this.crmSmsService.activeOrFrozenSmsMeal(id, uploadStatus);
	}

	/**
	 * 查询星奥短信总量详情
	 * @param year
	 * @param keyword
	 * @return
	 */
	@RequestMapping("getSmsTotalReportDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getSmsTotalReportDetail() {
		Integer year = this.getIntParameter(request, "year", null);
		String keyword = this.getFilteredParameter(request, "keyword", 0, null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getSmsTotalReportDetail(year, keyword, pageOffset, pageSize);
	}

	/**
	 * 门店短信发送详单
	 * @return
	 */
	@RequestMapping("getShopSmsSendDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopSmsSendDetail() {
		Integer adminId = this.getIntParameter(request, "adminId", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getShopSmsSendDetail(adminId, pageOffset, pageSize);
	}
	
	/**
	 * 根据发送者查询短信发送详单
	 * @return
	 */
	@RequestMapping("getShopSmsSendDetailBySenderId")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopSmsSendDetailBySenderId() {
		Integer senderId = this.getIntParameter(request, "senderId", null);
		Integer smsType = this.getIntParameter(request, "smsType", null); //短信发送类型 1crm系统管理员  2crm客户经理或者业务员
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getShopSmsSendDetailBySenderId(senderId, smsType, pageOffset, pageSize);
	}

	/**
	 * 某个分组发送短信详情
	 * @return
	 */
	@RequestMapping("getGroupSmsSendDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getGroupSmsSendDetail() {
		String groupCode = this.getFilteredParameter(request, "groupCode", 0, null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getGroupSmsSendDetail(groupCode, pageOffset, pageSize);
	}

	/**
	 * 查询星奥短信账单详情
	 * @param accountYear
	 * @param buyYear
	 * @param buyMonth
	 * @param keyword
	 * @return
	 */
	@RequestMapping("getSmsAccountReportDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getSmsAccountReportDetail() {
		Integer accountYear = this.getIntParameter(request, "accountYear", null);
		Integer buyYear = this.getIntParameter(request, "buyYear", null);
		Integer buyMonth = this.getIntParameter(request, "buyMonth", null);
		String crmShopName = this.getFilteredParameter(request, "crmShopName", 0, null);
		String mealName = this.getFilteredParameter(request, "mealName", 0, null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getSmsAccountReportDetail(accountYear, buyYear, buyMonth, crmShopName, mealName, pageOffset, pageSize);
	}
	
	/**
	 * 给订单开出发票
	 * @return
	 */
	@RequestMapping("openInvoice")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> openInvoice() {
		Integer id = this.getIntParameter(request, "id", null);
		return this.crmSmsService.openInvoice(id);
	}
	
	/**
	 * 门店经理，查询门店短信总库存量
	 * @return
	 */
	@RequestMapping("getShopSmsTotalReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopSmsTotalReport() {
		Integer adminId = this.getCrmAdminId();
		Integer year = this.getIntParameter(request, "year", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getShopSmsTotalReport(adminId, year, pageOffset, pageSize);
	}

	/**
	 * 门店经理，查询门店账单详情
	 * @return
	 */
	@RequestMapping("getShopSmsAccountDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getShopSmsAccountDetail() {
		Integer adminId = this.getCrmAdminId();
		Integer year = this.getIntParameter(request, "year", null);
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.crmSmsService.getShopSmsAccountDetail(adminId, year, pageOffset, pageSize);
	}
	

	/**
	 * 门店经理，下单
	 * @return
	 */
	@RequestMapping("createOrder")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> createOrder() {
		Integer adminId = this.getCrmAdminId();
		Integer mealId = this.getIntParameter(request, "mealId", null);
		Integer buyNum = this.getIntParameter(request, "buyNum", null);
		return this.crmSmsService.createOrder(mealId, adminId, buyNum, getIpAddr());
	}
}
