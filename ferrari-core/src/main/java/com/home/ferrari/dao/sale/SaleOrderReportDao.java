package com.home.ferrari.dao.sale;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;

public interface SaleOrderReportDao {
	/**
	 * 总数
	 * 
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public Map<String, Object> getFerrariTotalReport(
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd,
			@Param(value = "taobaoSellerNick") String taobaoSellerNick,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "shopType") Integer shopType,
			@Param(value = "abcType") Integer abcType);

	/**
	 * 省份
	 * 
	 * @param page
	 * @param province
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<Map<String, Object>> getFerrariProvinceReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "province") String province,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	public Integer countFerrariProvinceReport(
			@Param(value = "province") String province,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	/**
	 * 城市
	 * 
	 * @param page
	 * @param province
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<Map<String, Object>> getFerrariCityReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "province") String province,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	public Integer countFerrariCityReport(
			@Param(value = "province") String province,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	/**
	 * 门店
	 * 
	 * @param page
	 * @param shopId
	 * @param city
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<Map<String, Object>> getFerrariShopReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "shopId") Integer shopId,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "orderBy") Integer orderBy,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd,
			@Param(value = "taobaoSellerNick") String taobaoSellerNick,
			@Param(value = "shopName") String shopName,
			@Param(value = "shopType") Integer shopType,
			@Param(value = "abcType") Integer abcType);

	public Integer countFerrariShopReport(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd,
			@Param(value = "taobaoSellerNick") String taobaoSellerNick,
			@Param(value = "shopName") String shopName,
			@Param(value = "shopType") Integer shopType,
			@Param(value = "abcType") Integer abcType);

	/**
	 * 商品
	 * 
	 * @param page
	 * @param shopId
	 * @param productName
	 * @param orderBy
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<Map<String, Object>> getFerrariProductReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "shopId") Integer shopId,
			@Param(value = "productName") String productName,
			@Param(value = "orderBy") Integer orderBy,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd,
			@Param(value = "taobaoSellerNick") String taobaoSellerNick);

	public Integer countFerrariProductReport(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "productName") String productName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd,
			@Param(value = "taobaoSellerNick") String taobaoSellerNick);

	/**
	 * 商品在省排名
	 * 
	 * @param page
	 * @param shopId
	 * @param productName
	 * @param orderBy
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<Map<String, Object>> getFerrariProvinceProductReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "productName") String productName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	public Integer countFerrariProvinceProductReport(
			@Param(value = "productName") String productName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	/**
	 * 商品在门店排名
	 * 
	 * @param page
	 * @param shopId
	 * @param productName
	 * @param orderBy
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<Map<String, Object>> getFerrariShopProductReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "productName") String productName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	public Integer countFerrariShopProductReport(
			@Param(value = "productName") String productName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);
	
	/**
	 * 查询商品总销量
	 * 
	 * @param productName
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public Map<String, Object> getFerrariProductTotalReport(
			@Param(value = "productName") String productName,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);

	/**
	 * 查询门店在某个时间段范围的订单销售情况
	 * @param shopId
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<Map<String, Object>> getSaleOrderListDetailByShopId(
			@Param(value = "page") Page<?> page,
			@Param(value = "shopId") Integer shopId,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);
	public Integer countSaleOrderListDetailByShopId(
			@Param(value = "shopId") Integer shopId,
			@Param(value = "orderCreatedBegin") String orderCreatedBegin,
			@Param(value = "orderCreatedEnd") String orderCreatedEnd);
}
