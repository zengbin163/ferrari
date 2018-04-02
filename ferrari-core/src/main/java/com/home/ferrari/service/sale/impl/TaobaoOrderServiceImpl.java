package com.home.ferrari.service.sale.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.home.ferrari.dao.sale.SaleOrderDao;
import com.home.ferrari.dao.sale.SaleOrderDetailDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.enums.OperatorTypeEnum;
import com.home.ferrari.plugin.cache.TeslaShopCacheService;
import com.home.ferrari.plugin.third.TmallFullgetApi;
import com.home.ferrari.service.sale.SaleOrderService;
import com.home.ferrari.service.sale.SaleOrderTraceService;
import com.home.ferrari.service.sale.TaobaoOrderService;
import com.home.ferrari.status.ShopOrderStatus;
import com.home.ferrari.status.TaobaoOrderStatus;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.vo.sale.SaleOrder;
import com.home.ferrari.vo.sale.SaleOrderDetail;
import com.home.ferrari.vo.taobao.TaobaoOrder;
import com.home.ferrari.vo.taobao.TaobaoOrderDetail;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

@Service
public class TaobaoOrderServiceImpl implements TaobaoOrderService {
	private static final Logger logger = LoggerFactory.getLogger(TaobaoOrderServiceImpl.class);
	@Autowired
	private SaleOrderDao saleOrderDao;
	@Autowired
	private SaleOrderDetailDao saleOrderDetailDao;
	@Autowired
	private SaleOrderService saleOrderService;
	@Autowired
	private SaleOrderTraceService saleOrderTraceService;
	@Autowired
	private TeslaShopCacheService teslaShopCacheService;

	@Override
	@Transactional
	public Integer createOrUpdateTaobaoOrder(String bizOrderId, String taobaoOrderStatus, Integer isTmall) {
		if(StringUtils.isEmpty(bizOrderId)) {
			logger.error("bizOrderId is null");
			return -1;
		}
		TmallFullgetApi api = new TmallFullgetApi();
		TaobaoOrder taobaoOrder = api.getTaobaoOrder(new Long(bizOrderId), isTmall);
		SaleOrder tempSaleOrder = trans(taobaoOrder, taobaoOrderStatus);
		SaleOrder saleOrder = this.saleOrderDao.getSaleOrderByBizOrderId(bizOrderId);
		if(null == saleOrder) {
			this.saleOrderDao.insertSaleOrder(tempSaleOrder);
			if (null != tempSaleOrder.getId() && tempSaleOrder.getId() <= 0) {
				logger.error("SaleOrder insert fail,bizOrderId=" + bizOrderId);
				return 0;
			}
			List<SaleOrderDetail> detailList = tempSaleOrder.getOrderDetailList();
			for(SaleOrderDetail detail : detailList) {
				this.saleOrderDetailDao.insertSaleOrderDetail(detail);
				if (null != detail.getId() && detail.getId() <= 0) {
					logger.error("SaleOrderDetail insert fail,bizOrderId=" + bizOrderId);
					return 0;
				}
				//自动分配订单
				TeslaShop teslaShop = this.teslaShopCacheService.getShopByName(detail.getShopName());
				if(null!=teslaShop) {
					logger.error(String.format("==============新增订单自动分单[bizOrderId=%s]===================", bizOrderId));
					this.saleOrderService.distributeShop(bizOrderId, teslaShop.getId());
				}else{
					logger.error(String.format("==============门店名称[%s]在系统中不存在===================", detail.getShopName()));
				}
			}
		}else{
			//屏蔽淘宝重复消息
			if (!StringUtils.isEmpty(saleOrder.getTaobaoOrderStatus())
					&& saleOrder.getTaobaoOrderStatus().equals(taobaoOrderStatus)) {
				logger.error(String.format("==============淘宝重复消息不做处理[%s]===================", taobaoOrderStatus));
				return 1;
			}			
			
			tempSaleOrder.setId(saleOrder.getId());
			tempSaleOrder.setVersion(saleOrder.getVersion() + 1);
			Integer flag = this.saleOrderDao.updateSaleOrder(tempSaleOrder);
			if (flag <= 0) {
				logger.error("SaleOrder update fail,bizOrderId=" + bizOrderId);
				return 0;
			}
			List<SaleOrderDetail> detailList = tempSaleOrder.getOrderDetailList();
			for(SaleOrderDetail detail : detailList) {
				SaleOrderDetail tempOrderDetail = this.saleOrderDetailDao.getSaleOrderDetailByBizDetailId(detail.getBizDetailId());
				detail.setId(tempOrderDetail.getId());
				detail.setVersion(tempOrderDetail.getVersion() + 1);
				flag = this.saleOrderDetailDao.updateSaleOrderDetail(detail);
				if (flag <= 0) {
					logger.error("SaleOrderDetail update fail,bizOrderId=" + bizOrderId);
					return 0;
				}
				//处理老订单
				saleOrder = this.saleOrderDao.getSaleOrderByBizOrderId(bizOrderId);
				if (null == saleOrder.getShopId()
						|| null == saleOrder.getShopOrderStatus()
						|| ShopOrderStatus.WAIT_DISTRIBUTE.equals(saleOrder.getShopOrderStatus())) {
					logger.error(String.format("==============更新订单自动分单[bizOrderId=%s]===================", bizOrderId));
					//自动分配订单
					TeslaShop teslaShop = this.teslaShopCacheService.getShopByName(detail.getShopName());
					if(null!=teslaShop) {
						this.saleOrderService.distributeShop(bizOrderId, teslaShop.getId());
					}else{
						logger.error(String.format("==============门店名称[%s]在系统中不存在===================", detail.getShopName()));
					}
				}	
			}
		}
		// 插入订单跟踪记录
		this.saleOrderTraceService.createSaleOrderTrace(bizOrderId,
				tempSaleOrder.getTaobaoOrderStatus(), 
				tempSaleOrder.getShopOrderStatus(), 
				tempSaleOrder.getShopId(),
				OperatorTypeEnum.SYSTEM.getCode(), -1, 
				OperatorTypeEnum.SYSTEM.toString(),
				(TaobaoOrderStatus.WAIT_SELLER_SEND_GOODS.equals(tempSaleOrder.getTaobaoOrderStatus()) ? DefaultEnum.YES.getCode(): DefaultEnum.NO.getCode()),
				null);
		return 1;
	}
	
	private SaleOrder trans(TaobaoOrder taobaoOrder, String msgTaobaoStatus) {
		if (null == taobaoOrder) {
			return null;
		}
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setBizOrderId(taobaoOrder.getTid());
		saleOrder.setSellerNick(taobaoOrder.getSeller_nick());
		saleOrder.setBuyerNick(taobaoOrder.getBuyer_nick());
		saleOrder.setTaobaoOrderStatus(msgTaobaoStatus);
		if (TaobaoOrderStatus.WAIT_BUYER_PAY.equals(msgTaobaoStatus)) {
			saleOrder.setShopOrderStatus(ShopOrderStatus.WAIT_PAY);
		} else if (TaobaoOrderStatus.WAIT_SELLER_SEND_GOODS.equals(msgTaobaoStatus)) {
			saleOrder.setShopOrderStatus(ShopOrderStatus.WAIT_DISTRIBUTE);
		} else if (TaobaoOrderStatus.TRADE_CLOSED_BY_TAOBAO.equals(msgTaobaoStatus) || TaobaoOrderStatus.TRADE_CLOSED.equals(msgTaobaoStatus)) {
			saleOrder.setShopOrderStatus(ShopOrderStatus.TAOBAO_CLOSED);
		} else {
			//
		}
		saleOrder.setPayment(new BigDecimal(taobaoOrder.getPayment()));
		saleOrder.setPostFee(new BigDecimal(taobaoOrder.getPost_fee()));
		saleOrder.setReceiverName(taobaoOrder.getReceiver_name());
		saleOrder.setReceiverState(taobaoOrder.getReceiver_state());
		saleOrder.setReceiverAddress(taobaoOrder.getReceiver_address());
		saleOrder.setReceiverZip(taobaoOrder.getReceiver_zip());
		saleOrder.setReceiverMobile(taobaoOrder.getReceiver_mobile());
		saleOrder.setReceiverPhone(taobaoOrder.getReceiver_phone());
		saleOrder.setOrderCreated(DateUtil.defaultParseTime(taobaoOrder.getCreated()));
		saleOrder.setOrderPay(DateUtil.defaultParseTime(taobaoOrder.getPay_time()));
		saleOrder.setOrderConsign(DateUtil.defaultParseTime(taobaoOrder.getConsign_time()));
		saleOrder.setOrderEnd(DateUtil.defaultParseTime(taobaoOrder.getEnd_time()));
		saleOrder.setOrderType(taobaoOrder.getType());
		saleOrder.setBuyerMessage(taobaoOrder.getBuyer_message());
		saleOrder.setBuyerAlipayNo(taobaoOrder.getBuyer_alipay_no());
		//订单明细
		List<SaleOrderDetail> saleDetailList = new ArrayList<SaleOrderDetail>();
		List<TaobaoOrderDetail> detailList = taobaoOrder.getOrderList();
		for(TaobaoOrderDetail detail : detailList) {
			SaleOrderDetail saleDetail = new SaleOrderDetail();
			saleDetail.setBizOrderId(taobaoOrder.getTid());
			saleDetail.setBizDetailId(detail.getOid());
			saleDetail.setTaobaoDetailStatus(detail.getStatus());
			saleDetail.setProductName(detail.getTitle());
			saleDetail.setEtShopName(detail.getEt_shop_name());
			saleDetail.setShopName(this.getShopName(detail.getEt_shop_name()));
			saleDetail.setNum(detail.getNum());
			saleDetail.setPayment(new BigDecimal(detail.getPayment()));
			saleDetail.setPrice(new BigDecimal(detail.getPrice()));
			saleDetail.setTotalFee(new BigDecimal(detail.getTotal_fee()));
			saleDetail.setDiscountFee(new BigDecimal(detail.getDiscount_fee()));
			saleDetail.setPicPath(detail.getPic_path());
			saleDetail.setLogisticsCompany(detail.getLogistics_company());
			saleDetail.setInvoiceNo(detail.getInvoice_no());
			saleDetailList.add(saleDetail);
		}
		saleOrder.setOrderDetailList(saleDetailList);
		return saleOrder;
	}

	/**
	 * 截取淘宝店铺名称关键字，比如：星奥车联(丰潭路车潮流店)，取 丰潭路车潮流店
	 * @param etShopName
	 * @return
	 */
	private String getShopName(String etShopName) {
		if(StringUtils.isEmpty(etShopName)) {
			return etShopName;
		}
		int beginIndex = etShopName.indexOf("(");
		int endIndex = etShopName.indexOf(")");
		return etShopName.substring(beginIndex + 1, endIndex);
	}
}
