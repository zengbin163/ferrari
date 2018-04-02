package com.home.ferrari.service.sale;

import java.util.Map;

public interface SaleOrderTraceService {
	
	/**
	 * 创建订单跟踪记录
	 * @param bizOrderId
	 * @param taobaoOrderStatus
	 * @param shopOrderStatus
	 * @param shopId
	 * @param operatorType
	 * @param operatorId
	 * @param operatorNickName
	 * @param isShow
	 * @param traceAttr
	 */
	public void createSaleOrderTrace(String bizOrderId,
			String taobaoOrderStatus, Integer shopOrderStatus, Integer shopId,
			Integer operatorType, Integer operatorId, String operatorNickName,
			Integer isShow, String traceAttr);

	/**
	 * 查询订单执行轨迹
	 * @param bizOrderId
	 * @return
	 */
	public Map<String, Object> orderTrace(String bizOrderId);

}
