package com.home.ferrari.service.sale;

import java.util.Map;

public interface SaleOrderReportService {

	public Map<String, Object> provinceReport(Integer percentFlag,
			Integer pageOffset, Integer pageSize, String orderCreatedBegin,
			String orderCreatedEnd);

	public Map<String, Object> cityReport(Integer percentFlag,
			Integer pageOffset, Integer pageSize, String province,
			String orderCreatedBegin, String orderCreatedEnd);

	public Map<String, Object> shopReport(Integer percentFlag,
			Integer pageOffset, Integer pageSize, String province, String city,
			Integer shopId, Integer orderBy, String orderCreatedBegin,
			String orderCreatedEnd, String shopName, Integer shopType,
			Integer abcType);

	public Map<String, Object> productReport(Integer percentFlag,
			Integer pageOffset, Integer pageSize, Integer shopId,
			String productName, Integer orderBy, String orderCreatedBegin,
			String orderCreatedEnd,String taobaoSellerNick);

	public Map<String, Object> provinceRankReport(Integer pageOffset,
			Integer pageSize, String productName, String orderCreatedBegin,
			String orderCreatedEnd);

	public Map<String, Object> shopRankReport(Integer pageOffset,
			Integer pageSize, String productName, String orderCreatedBegin,
			String orderCreatedEnd);

	public Map<String, Object> getSaleOrderListDetailByShopId(
			Integer pageOffset, Integer pageSize, Integer shopId,
			String orderCreatedBegin, String orderCreatedEnd);

}
