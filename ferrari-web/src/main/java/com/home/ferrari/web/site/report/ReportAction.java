package com.home.ferrari.web.site.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.sale.SaleOrderReportService;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/report")
public class ReportAction extends BaseAction {

	private static final long serialVersionUID = -4814495951142070958L;
	@Autowired
	private SaleOrderReportService saleOrderReportService;

	@RequestMapping("provinceReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> provinceReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer percentFlag = this.getIntParameter(request, "percentFlag", null);
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		return this.saleOrderReportService.provinceReport(percentFlag, pageOffset, pageSize, orderCreatedBegin, orderCreatedEnd);
	}

	@RequestMapping("cityReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> cityReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer percentFlag = this.getIntParameter(request, "percentFlag", null);
		String province =this.getFilteredParameter(request, "province", 0, null);
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		return this.saleOrderReportService.cityReport(percentFlag, pageOffset, pageSize, province, orderCreatedBegin, orderCreatedEnd);
	}

	@RequestMapping("shopReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer percentFlag = this.getIntParameter(request, "percentFlag", null);
		String province =this.getFilteredParameter(request, "province", 0, null);
		String city =this.getFilteredParameter(request, "city", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		Integer orderBy = this.getIntParameter(request, "orderBy", null); //0:订单数     1:gmv
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		String shopName =this.getFilteredParameter(request, "shopName", 0, null);
		Integer shopType = this.getIntParameter(request, "shopType", null); //0:订单数     1:gmv
		Integer abcType = this.getIntParameter(request, "abcType", null); //0:订单数     1:gmv
		return this.saleOrderReportService.shopReport(percentFlag, pageOffset, pageSize, province, city, shopId, orderBy, orderCreatedBegin, orderCreatedEnd, shopName, shopType, abcType);
	}

	@RequestMapping("productReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> productReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer percentFlag = this.getIntParameter(request, "percentFlag", null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String taobaoSellerNick = WebUtil.decode(this.getFilteredParameter(request, "taobaoSellerNick", 0, null));
		String productName = WebUtil.decode(this.getFilteredParameter(request, "productName", 0, null));
		Integer orderBy = this.getIntParameter(request, "orderBy", null); //0:订单数     1:gmv
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		return this.saleOrderReportService.productReport(percentFlag, pageOffset, pageSize, shopId, productName, orderBy, orderCreatedBegin, orderCreatedEnd, taobaoSellerNick);
	}

	@RequestMapping("provinceRankReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> provinceRankReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String productName = WebUtil.decode(this.getFilteredParameter(request, "productName", 0, null));
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		return this.saleOrderReportService.provinceRankReport(pageOffset, pageSize, productName, orderCreatedBegin, orderCreatedEnd);
	}

	@RequestMapping("shopRankReport")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopRankReport() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String productName = WebUtil.decode(this.getFilteredParameter(request, "productName", 0, null));
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		return this.saleOrderReportService.shopRankReport(pageOffset, pageSize, productName, orderCreatedBegin, orderCreatedEnd);
	}

	@RequestMapping("shopOrderListDetail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> shopOrderListDetail() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		return this.saleOrderReportService.getSaleOrderListDetailByShopId(
				pageOffset, pageSize, shopId, orderCreatedBegin,
				orderCreatedEnd);
	}
}
