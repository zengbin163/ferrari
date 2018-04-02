package com.home.ferrari.service.account;

import java.util.Map;

public interface ShopAccountHeaderService {
	
	/**
	 * 查询门店核销账单列表
	 * @param shopId
	 * @param pageOffset
	 * @param pageSize
	 * @return
	 */
	public Map<String, Object> getShopAccountList(Integer shopId, Integer pageOffset, Integer pageSize);
	
}
