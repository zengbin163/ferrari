package com.home.ferrari.web.site.sale;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.dao.sale.SaleOrderDao;
import com.home.ferrari.plugin.third.TmallSoldgetApi;
import com.home.ferrari.service.sale.TaobaoOrderService;
import com.home.ferrari.status.TaobaoOrderStatus;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.sale.SaleOrder;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/orderoperate")
public class OrderOperateAction extends BaseAction {

	private static final long serialVersionUID = 7926224217550014663L;
	@Autowired
	private TaobaoOrderService taobaoOrderService;
	@Autowired
	private SaleOrderDao saleOrderDao;

	@RequestMapping("sync")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public String syncTaobaoOrder () {
		String startCreated = WebUtil.decode(this.getFilteredParameter(request, "startCreated", 0, null));
		String endCreated = WebUtil.decode(this.getFilteredParameter(request, "endCreated", 0, null));
		Long pageNo = this.getLongParameter(request, "pageNo", null);
		Long pageSize = this.getLongParameter(request, "pageSize", null);
		Integer isTmall = this.getIntParameter(request, "isTmall", 1);
		TmallSoldgetApi api = new TmallSoldgetApi();
		List<String> ids = api.getSoldTmallOrderList(startCreated, endCreated, pageNo, pageSize, isTmall);
		if(CollectionUtils.isEmpty(ids)) {
			return "订单不存在";
		}
		JSONObject json = new JSONObject();
		for(String id : ids) {
			SaleOrder saleOrder = this.saleOrderDao.getSaleOrderByBizOrderId(id);
			if(null != saleOrder) {
				System.out.println("订单" + id + "已存在");
				continue;
			}
			Integer flag = taobaoOrderService.createOrUpdateTaobaoOrder(id, TaobaoOrderStatus.WAIT_SELLER_SEND_GOODS, isTmall);
			if (flag <= 0) {
				json.put(id, "订单操作失败");
			} else {
				json.put(id, "订单操作成功");
			}
		}
		return json.toJSONString();
	}
}
