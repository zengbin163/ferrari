package com.home.ferrari.web.site.sale;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.sale.SaleOrderService;
import com.home.ferrari.service.sale.SaleOrderTraceService;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/order")
public class OrderAction extends BaseAction {

	private static final long serialVersionUID = 4453286597706852921L;
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private SaleOrderTraceService saleOrderTraceService;

	@RequestMapping("search")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> search() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer shopOrderStatus = this.getIntParameter(request, "shopOrderStatus", null);
		String buyerNick = this.getFilteredParameter(request, "buyerNick", 0, null);
		String taobaoSellerNick = WebUtil.decode(this.getFilteredParameter(request, "taobaoSellerNick", 0, null));
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String productName = WebUtil.decode(this.getFilteredParameter(request, "productName", 0, null));
		String shopName = WebUtil.decode(this.getFilteredParameter(request, "shopName", 0, null));
		String orderCreatedBegin =this.getFilteredParameter(request, "orderCreatedBegin", 0, null);
		String orderCreatedEnd =this.getFilteredParameter(request, "orderCreatedEnd", 0, null);
		String province =this.getFilteredParameter(request, "province", 0, null);
		String city =this.getFilteredParameter(request, "city", 0, null);
		return this.saleOrderService.searchOrder(pageOffset, pageSize, shopOrderStatus, shopId, buyerNick, bizOrderId, productName, shopName, orderCreatedBegin, orderCreatedEnd, taobaoSellerNick, province, city);
	}

	@RequestMapping("detail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> detail() {
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		return this.saleOrderService.orderDetail(bizOrderId);
	}

	@RequestMapping("distribute")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> distribute() {
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		return this.saleOrderService.distributeShop(bizOrderId, shopId);
	}
	
	@RequestMapping("accept")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> accept() {
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		return this.saleOrderService.acceptOrder(bizOrderId, getShopId());
	}
	
	@RequestMapping("finish")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> finish() {
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		return this.saleOrderService.finishOrder(bizOrderId, getShopId());
	}
	
	@RequestMapping("trace")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> trace() {
		String bizOrderId = this.getFilteredParameter(request, "bizOrderId", 0, null);
		return this.saleOrderTraceService.orderTrace(bizOrderId);
	}
}
