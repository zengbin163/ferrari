package com.home.ferrari.service.sale.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.dao.sale.SaleOrderTraceDao;
import com.home.ferrari.service.sale.SaleOrderTraceService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.sale.SaleOrderTrace;

@Service
public class SaleOrderTraceServiceImpl implements SaleOrderTraceService {

	@Autowired
	private SaleOrderTraceDao saleOrderTraceDao;

	@Override
	@Async
	public void createSaleOrderTrace(String bizOrderId,
			String taobaoOrderStatus, Integer shopOrderStatus, Integer shopId,
			Integer operatorType, Integer operatorId, String operatorNickName,
			Integer isShow, String traceAttr) {
		SaleOrderTrace trace = new SaleOrderTrace(bizOrderId, taobaoOrderStatus, shopOrderStatus, shopId, operatorType, operatorId, operatorNickName, isShow, traceAttr);
		this.saleOrderTraceDao.insertSaleOrderTrace(trace);
	}

	public Map<String, Object> orderTrace(String bizOrderId) {
		Assert.notNull(bizOrderId, "淘宝订单id不能为空");
		List<SaleOrderTrace> traceList = this.saleOrderTraceDao.getSaleOrderTraceListByBizOrderId(bizOrderId);
		if(CollectionUtils.isEmpty(traceList)) {
			return ResultUtil.successMap(traceList);
		}
		for(SaleOrderTrace trace : traceList) {
			String attrs = null;
			switch (trace.getShopOrderStatus()) {
				case 100:
					attrs = "订单" + trace.getBizOrderId() + "待分配";
					break;
				case 101:
					attrs = "订单" + trace.getBizOrderId() + "已分配门店，待门店接单";
					break;
				case 102:
					attrs = "订单" + trace.getBizOrderId() + "已被门店接单，待门店服务";
					break;
				case 200:
					attrs = "订单" + trace.getBizOrderId() + "已被门店服务完成";
					break;
				default:
					break;
			}
			trace.setTraceAttr(attrs);
		}
		return ResultUtil.successMap(traceList);
	}
}
