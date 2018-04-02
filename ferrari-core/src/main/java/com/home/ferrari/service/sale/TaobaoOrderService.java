package com.home.ferrari.service.sale;


public interface TaobaoOrderService {

	public Integer createOrUpdateTaobaoOrder(String bizOrderId, String taobaoOrderStatus, Integer isTmall);

}
