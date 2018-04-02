package com.home.ferrari.dao.sale;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.sale.SaleOrder;
import com.home.ferrari.vo.sale.SearchSaleOrder;

public interface SaleOrderDao {

	public Integer insertSaleOrder(SaleOrder saleOrder);

	public Integer updateSaleOrder(SaleOrder saleOrder);

	public SaleOrder getSaleOrderByBizOrderId(
			@Param(value = "bizOrderId") String bizOrderId);

	public List<SearchSaleOrder> getSaleOrderList(
			@Param(value = "page") Page<?> page,
			@Param(value = "shopOrderStatus") Integer shopOrderStatus,
			@Param(value = "shopId") Integer shopId,
			@Param(value = "buyerNick") String buyerNick,
			@Param(value = "bizOrderId") String bizOrderId,
			@Param(value = "productName") String productName,
			@Param(value = "shopName") String shopName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd,
			@Param(value = "taobaoSellerNick") String taobaoSellerNick,
			@Param(value = "province") String province,
			@Param(value = "city") String city
			);

	public Integer countSaleOrderList(
			@Param(value = "shopOrderStatus") Integer shopOrderStatus,
			@Param(value = "shopId") Integer shopId,
			@Param(value = "buyerNick") String buyerNick,
			@Param(value = "bizOrderId") String bizOrderId,
			@Param(value = "productName") String productName,
			@Param(value = "shopName") String shopName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd,
			@Param(value = "taobaoSellerNick") String taobaoSellerNick,
			@Param(value = "province") String province,
			@Param(value = "city") String city
			);
	
	public List<SearchSaleOrder> getExportSaleOrderList(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "productName") String productName,
			@Param(value = "province") String province,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	public List<SaleOrder> getUnAcceptSaleOrderList(
			@Param(value = "acceptTimeout") Long acceptTimeout);
}
