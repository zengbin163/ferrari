package com.home.ferrari.web.site.account;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.account.AccountHeaderService;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/account")
public class AccountAction extends BaseAction {

	private static final long serialVersionUID = -3519087089346932148L;
	@Autowired
	private AccountHeaderService accountHeaderService;

	@RequestMapping("accountHeaderList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> accountHeaderList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		return this.accountHeaderService.getAccountHeader(pageOffset, pageSize);
	}

	@RequestMapping("saveOrUpdateAccountInvoice")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> saveOrUpdateAccountInvoice() {
		String invoiceTitle = this.getFilteredParameter(request, "invoiceTitle", 0, null);
		String taxIdentify = this.getFilteredParameter(request, "taxIdentify", 0, null);
		String billingAddress = this.getFilteredParameter(request, "billingAddress", 0, null);
		String billingAccount = this.getFilteredParameter(request, "billingAccount", 0, null);
		String billingPhone = this.getFilteredParameter(request, "billingPhone", 0, null);
		String receiverAddress = this.getFilteredParameter(request, "receiverAddress", 0, null);
		String receiverPhone = this.getFilteredParameter(request, "receiverPhone", 0, null);
		String accountRemark = this.getFilteredParameter(request, "accountRemark", 0, null);
		return this.accountHeaderService.saveOrUpdateAccountInvoice(
				invoiceTitle, taxIdentify, billingAddress, billingAccount,
				billingPhone, receiverAddress, receiverPhone, accountRemark);
	}
	
	@RequestMapping("getInvoice")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getInvoice() {
		return this.accountHeaderService.getAccountInvoice();
	}
	
	@RequestMapping("getAccountHeaderDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getAccountHeaderDetail() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String finalNo = this.getFilteredParameter(request, "finalNo", 0, null);
		Integer accountStatus = this.getIntParameter(request, "accountStatus", null);
		return this.accountHeaderService.getAccountHeaderDetail(finalNo, accountStatus, pageOffset, pageSize);
	}
	
	@RequestMapping("getAccountShopDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getAccountShopDetail() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String finalNo = this.getFilteredParameter(request, "finalNo", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		return this.accountHeaderService.getAccountShopDetail(finalNo, shopId, pageOffset, pageSize);
	}

	@RequestMapping("updateAccountShop")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> updateAccountShop() {
		Integer id = this.getIntParameter(request, "id", null);
		Integer accountStatus = this.getIntParameter(request, "accountStatus", null);
		String accountFee = this.getFilteredParameter(request, "accountFee", 0, null);//财务实付金额
		BigDecimal accountFeeDecimal = null;
		if(!StringUtils.isEmpty(accountFee)) {
			accountFeeDecimal = new BigDecimal(accountFee);
		}
		String financeUrl = this.getFilteredParameter(request, "financeUrl", 0, null);//财务图片URL
		String logisticsCompany = this.getFilteredParameter(request, "logisticsCompany", 0, null);//物流公司
		String logisticsNo = this.getFilteredParameter(request, "logisticsNo", 0, null);//物流单号
		String logisticsRemark = this.getFilteredParameter(request, "logisticsRemark", 0, null);//物流备注
		return this.accountHeaderService.updateAccountShop(id, accountStatus,
				accountFeeDecimal, financeUrl, logisticsCompany, logisticsNo,
				logisticsRemark);
	}

	@RequestMapping("beginAccount")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> beginAccount() {
		String finalNo = this.getFilteredParameter(request, "finalNo", 0, null);//核销单号
		return this.accountHeaderService.beginAccount(finalNo);
	}
	
	@RequestMapping("getAccountUploadDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getAccountUploadDetail() {
		String finalNo = this.getFilteredParameter(request, "finalNo", 0, null);//核销单号
		return this.accountHeaderService.getAccountUploadDetail(finalNo);
	}
}
