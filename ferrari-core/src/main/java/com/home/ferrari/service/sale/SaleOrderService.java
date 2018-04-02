package com.home.ferrari.service.sale;

import java.util.List;
import java.util.Map;

import com.home.ferrari.vo.sale.SaleOrder;
import com.home.ferrari.vo.sale.SaleOrderDetail;
import com.home.ferrari.vo.sale.SearchSaleOrder;

public interface SaleOrderService {

	/**
	 * 订单搜索
	 * 
	 * @param pageOffset
	 * @param pageSize
	 * @param shopOrderStatus
	 * @param buyerNick
	 * @param bizOrderId
	 * @param productName
	 * @param shopName
	 * @return
	 */
	public Map<String, Object> searchOrder(Integer pageOffset,
			Integer pageSize, Integer shopOrderStatus, Integer shopId,
			String buyerNick, String bizOrderId, String productName,
			String shopName, String orderCreatedBegin, String orderCreatedEnd,
			String taobaoSellerNick, String province, String city);

	/**
	 * 导出订单列表
	 * @param shopId
	 * @param productName
	 * @param province
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @return
	 */
	public List<SearchSaleOrder> searchExportOrder(Integer shopId,
			String productName, String province, String orderCreatedBegin,
			String orderCreatedEnd);

	/**
	 * 订单详情
	 * 
	 * @param bizOrderId
	 * @return
	 */
	public Map<String, Object> orderDetail(String bizOrderId);

	/**
	 * 服务商分配门店
	 * 
	 * @param bizOrderId
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> distributeShop(String bizOrderId, Integer shopId);

	/**
	 * 门店接单
	 * 
	 * @param bizOrderId
	 * @param acceptShopId
	 *            登录门店系统的接单门店
	 * @return
	 */
	public Map<String, Object> acceptOrder(String bizOrderId,
			Integer acceptShopId);

	/**
	 * 门店完成服务
	 * 
	 * @param bizOrderId
	 * @param finishShopId
	 *            登录门店系统的完成服务的门店
	 * @return
	 */
	public Map<String, Object> finishOrder(String bizOrderId,
			Integer finishShopId);
	
	/**
	 * 订单销量统计，仅用于运营数据报表
	 * @param shopOrderStatus
	 * @param shopId
	 * @param buyerNick
	 * @param bizOrderId
	 * @param productName
	 * @param shopName
	 * @param orderCreatedBegin
	 * @param orderCreatedEnd
	 * @param taobaoSellerNick
	 * @return
	 */
	public Integer countSaleOrderList(Integer shopOrderStatus, Integer shopId,
			String buyerNick, String bizOrderId, String productName,
			String shopName, String orderCreatedBegin, String orderCreatedEnd,
			String taobaoSellerNick);
	
	/**
	 * 保存订单和明细，仅用于订单导入
	 * @param saleOrder
	 * @param detailList
	 * @return
	 */
	public Integer saveSaleOrder(SaleOrder saleOrder, List<SaleOrderDetail> detailList);
}
