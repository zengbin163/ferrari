package com.home.ferrari.service.sale.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.sale.SaleOrderDao;
import com.home.ferrari.dao.sale.SaleOrderDetailDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.OperatorTypeEnum;
import com.home.ferrari.global.TaobaoSellerNick;
import com.home.ferrari.plugin.cache.TeslaShopCacheService;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.TeslaBizException;
import com.home.ferrari.plugin.push.WebSocketConstants;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.sale.SaleOrderService;
import com.home.ferrari.service.sale.SaleOrderTraceService;
import com.home.ferrari.status.ShopOrderStatus;
import com.home.ferrari.status.ShopStatus;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.sale.SaleOrder;
import com.home.ferrari.vo.sale.SaleOrderDetail;
import com.home.ferrari.vo.sale.SearchSaleOrder;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.vo.tesla.user.TeslaUser;

@Service
public class SaleOrderServiceImpl implements SaleOrderService {
	@Autowired
	private SaleOrderDao saleOrderDao;
	@Autowired
	private SaleOrderDetailDao saleOrderDetailDao;
	@Autowired
	private SaleOrderTraceService saleOrderTraceService;
	@Autowired
	private TeslaShopCacheService teslaShopCacheService;
	
	private static final Logger logger = LoggerFactory.getLogger(SaleOrderServiceImpl.class);

	public Map<String, Object> searchOrder(Integer pageOffset, Integer pageSize, Integer shopOrderStatus, Integer shopId, String buyerNick, String bizOrderId, String productName, String shopName, String orderCreatedBegin, String orderCreatedEnd, String taobaoSellerNick, String province, String city) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		String sellerNick = TaobaoSellerNick.getValue(taobaoSellerNick);
		List<SearchSaleOrder> orderList = this.saleOrderDao.getSaleOrderList(page, shopOrderStatus, shopId, buyerNick, bizOrderId, productName, shopName, orderCreatedBegin, orderCreatedEnd, sellerNick, province, city);
		Integer sum = this.saleOrderDao.countSaleOrderList(shopOrderStatus, shopId, buyerNick, bizOrderId, productName, shopName, orderCreatedBegin, orderCreatedEnd, sellerNick, province, city);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", orderList);
		map.put("sum", sum);
		return ResultUtil.successMap(map);
	}
	
	public List<SearchSaleOrder> searchExportOrder(Integer shopId, String productName, String province, String orderCreatedBegin, String orderCreatedEnd) {
		return this.saleOrderDao.getExportSaleOrderList(shopId, productName, province, orderCreatedBegin, orderCreatedEnd);
	}
	
	public Map<String, Object> orderDetail(String bizOrderId) {
		Assert.notNull(bizOrderId, "淘宝订单id不能为空");
		SaleOrder saleOrder = this.saleOrderDao.getSaleOrderByBizOrderId(bizOrderId);
		if(null == saleOrder) {
			throw new TeslaBizException(ResultCode.NOT_EXISTS, String.format("淘宝订单不存在，bizOrderId=%s", bizOrderId));
		}
		List<SaleOrderDetail> saleOrderDetailList = this.saleOrderDetailDao.getSaleOrderDetailListByBizOrderId(bizOrderId);
		if(CollectionUtils.isEmpty(saleOrderDetailList)) {
			throw new TeslaBizException(ResultCode.NOT_EXISTS, String.format("淘宝订单明细不存在，bizOrderId=%s", bizOrderId));
		}
		saleOrder.setOrderDetailList(saleOrderDetailList);
		return ResultUtil.successMap(saleOrder);
	}
	
	@Transactional
	public Map<String, Object> distributeShop(String bizOrderId,Integer shopId) {
		Assert.notNull(bizOrderId,"淘宝订单id不能为空");
		Assert.notNull(shopId,"被分配的门店id不能为空");
		FerrariUser ferrariUser = new FerrariUser();
		ferrariUser.setId(0);
		ferrariUser.setNickName("system");
		if(null != SessionContainer.getSession()) {
			ferrariUser = SessionContainer.getSession().getFerrariUser();
		}
		SaleOrder saleOrder = this.saleOrderDao.getSaleOrderByBizOrderId(bizOrderId);
		if (null == saleOrder) {
			throw new TeslaBizException(ResultCode.ORDER_NOT_EXISTS, "淘宝订单不存在，bizOrderId=" + bizOrderId);
		}
		//校验店铺名称
		TeslaShop teslaShop = this.teslaShopCacheService.getShop(shopId);
		if(null == teslaShop) {
			logger.error("门店不存在，shopId=" + shopId);
			return null;
		}
		if(ShopStatus.SHOP_FROZEN == teslaShop.getShopStatus()) {
			logger.error("门店已被冻结，shopId=" + shopId);
			return null;
		}
		List<SaleOrderDetail> saleOrderDetailList  = this.saleOrderDetailDao.getSaleOrderDetailListByBizOrderId(bizOrderId);
		if(CollectionUtils.isEmpty(saleOrderDetailList)) {
			throw new TeslaBizException(ResultCode.ORDERDETAIL_NOT_EXISTS, "淘宝订单明细不存在，bizOrderId=" + bizOrderId);
		}else{
			boolean isShopExist = false;
			for(SaleOrderDetail detail : saleOrderDetailList) {
				if(teslaShop.getShopName().equals(detail.getShopName())) {
					isShopExist = true;
				}
			}
			if(!isShopExist) {
				throw new TeslaBizException(ResultCode.SHOP_NOT_CORRECT, "门店不存在，shopId=" + shopId);
			}
		}
		saleOrder.setVersion(saleOrder.getVersion() + 1);
		saleOrder.setShopId(shopId);
		saleOrder.setShopOrderStatus(ShopOrderStatus.WAIT_ACCEPT);
		Integer flag = this.saleOrderDao.updateSaleOrder(saleOrder);
		if(flag <= 0) {
			logger.error("淘宝订单分配店铺失败，bizOrderId=" + bizOrderId);
			return null;
		}
		//给门店发消息做语音提醒
		WebUtil.sendSocketMsg(WebSocketConstants.SESSION_SHOP_PREFIX + shopId, String.format("天猫订单%s已分配到您的店铺，请及时处理", bizOrderId));
		// 插入订单跟踪记录
		this.saleOrderTraceService.createSaleOrderTrace(bizOrderId,
				saleOrder.getTaobaoOrderStatus(),
				saleOrder.getShopOrderStatus(), saleOrder.getShopId(),
				OperatorTypeEnum.VENTOR.getCode(), ferrariUser.getId(),
				ferrariUser.getNickName(), DefaultEnum.YES.getCode(), null);
		return ResultUtil.successMap(flag);
	}
	
	@Transactional
	public Map<String, Object> acceptOrder(String bizOrderId, Integer acceptShopId) {
		Assert.notNull(bizOrderId,"淘宝订单id不能为空");
		Assert.notNull(acceptShopId,"接单门店id不能为空");
		TeslaUser teslaUser = SessionContainer.getSession().getTeslaUser();
		if(null==teslaUser) {
			throw new TeslaBizException(ResultCode.SHOP_CAN_ACCEPT, String.format(ResultCode.SHOP_CAN_ACCEPT.toString() + ",bizOrderId=%s", bizOrderId));
		}
		SaleOrder saleOrder = this.saleOrderDao.getSaleOrderByBizOrderId(bizOrderId);
		if (null == saleOrder) {
			throw new TeslaBizException(ResultCode.NOT_EXISTS, "淘宝订单不存在，bizOrderId=" + bizOrderId);
		}
		if(!ShopOrderStatus.WAIT_ACCEPT.equals(saleOrder.getShopOrderStatus())) {
			throw new TeslaBizException(ResultCode.TAOBAO_ORDER_ACCEPT_ERROR, String.format("只有淘宝订单的店铺订单状态是待接单（101）才能接单操作，bizOrderId=%s,acceptShopId=%s", bizOrderId, acceptShopId));
		}
		if(!acceptShopId.equals(saleOrder.getShopId())) {
			throw new TeslaBizException(ResultCode.ILLEGAL_DATA, String.format("接单门店id不等于之前分配的门店id，bizOrderId=%s,acceptShopId=%s,distributeShopId=%s", bizOrderId, acceptShopId, saleOrder.getShopId()));
		}
		saleOrder.setVersion(saleOrder.getVersion() + 1);
		saleOrder.setShopOrderStatus(ShopOrderStatus.WAIT_SERVICE);
		Integer flag = this.saleOrderDao.updateSaleOrder(saleOrder);
		if(flag <= 0) {
			throw new TeslaBizException(ResultCode.UPDATE_FAIL, "淘宝订单店铺接单失败，bizOrderId=" + bizOrderId);
		}
		// 插入订单跟踪记录
		this.saleOrderTraceService.createSaleOrderTrace(bizOrderId,
				saleOrder.getTaobaoOrderStatus(),
				saleOrder.getShopOrderStatus(), saleOrder.getShopId(),
				OperatorTypeEnum.SHOP.getCode(), teslaUser.getId(),
				teslaUser.getNickName(), DefaultEnum.YES.getCode(), null);
		return ResultUtil.successMap(flag);
	}

	@Transactional
	public Map<String, Object> finishOrder(String bizOrderId, Integer finishShopId) {
		Assert.notNull(bizOrderId,"淘宝订单id不能为空");
		Assert.notNull(finishShopId,"完成服务的门店id不能为空");
		TeslaUser teslaUser = SessionContainer.getSession().getTeslaUser();
		if(null==teslaUser) {
			throw new TeslaBizException(ResultCode.SHOP_CAN_ACCEPT, String.format(ResultCode.SHOP_CAN_ACCEPT.toString() + ",bizOrderId=%s", bizOrderId));
		}
		SaleOrder saleOrder = this.saleOrderDao.getSaleOrderByBizOrderId(bizOrderId);
		if (null == saleOrder) {
			throw new TeslaBizException(ResultCode.NOT_EXISTS, "淘宝订单不存在，bizOrderId=" + bizOrderId);
		}
		if(!ShopOrderStatus.WAIT_SERVICE.equals(saleOrder.getShopOrderStatus())) {
			throw new TeslaBizException(ResultCode.TAOBAO_ORDER_SERVICE_ERROR, String.format("只有淘宝订单的店铺订单状态是待服务（102）才能完成服务操作，bizOrderId=%s,finishShopId=%s", bizOrderId, finishShopId));
		}
		if(!finishShopId.equals(saleOrder.getShopId())) {
			throw new TeslaBizException(ResultCode.ILLEGAL_DATA, String.format("完成服务门店id不等于之前分配的门店id，bizOrderId=%s,finishShopId=%s,distributeShopId=%s", bizOrderId, finishShopId, saleOrder.getShopId()));
		}
		saleOrder.setVersion(saleOrder.getVersion() + 1);
		saleOrder.setShopOrderStatus(ShopOrderStatus.FINISH);
		Integer flag = this.saleOrderDao.updateSaleOrder(saleOrder);
		if(flag <= 0) {
			throw new TeslaBizException(ResultCode.UPDATE_FAIL, "淘宝订单店铺完成服务失败，bizOrderId=" + bizOrderId);
		}
		// 插入订单跟踪记录
		this.saleOrderTraceService.createSaleOrderTrace(bizOrderId,
				saleOrder.getTaobaoOrderStatus(),
				saleOrder.getShopOrderStatus(), saleOrder.getShopId(),
				OperatorTypeEnum.SHOP.getCode(), teslaUser.getId(),
				teslaUser.getNickName(), DefaultEnum.YES.getCode(), null);
		return ResultUtil.successMap(flag);
	}
	
	public Integer countSaleOrderList(Integer shopOrderStatus, Integer shopId,
			String buyerNick, String bizOrderId, String productName,
			String shopName, String orderCreatedBegin, String orderCreatedEnd,
			String taobaoSellerNick) {
		return this.saleOrderDao.countSaleOrderList(shopOrderStatus, shopId,
				buyerNick, bizOrderId, productName, shopName,
				orderCreatedBegin, orderCreatedEnd, taobaoSellerNick, null, null);
	}
	
	public Integer saveSaleOrder(SaleOrder saleOrder, List<SaleOrderDetail> detailList) {
		this.saleOrderDao.insertSaleOrder(saleOrder);
		for(SaleOrderDetail detail : detailList) {
			this.saleOrderDetailDao.insertSaleOrderDetail(detail);
		}
		return 1;
	}
}
