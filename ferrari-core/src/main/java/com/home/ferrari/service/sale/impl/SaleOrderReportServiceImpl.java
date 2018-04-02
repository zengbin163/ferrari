package com.home.ferrari.service.sale.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.dao.sale.SaleOrderDao;
import com.home.ferrari.dao.sale.SaleOrderDetailDao;
import com.home.ferrari.dao.sale.SaleOrderReportDao;
import com.home.ferrari.dao.shop.TeslaShopDao;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.global.TaobaoSellerNick;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.sale.SaleOrderReportService;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.product.Product;
import com.home.ferrari.vo.sale.SaleOrderDetail;
import com.home.ferrari.vo.tesla.shop.TeslaShop;

@Service
public class SaleOrderReportServiceImpl implements SaleOrderReportService {
	
	@Autowired
	private SaleOrderReportDao saleOrderReportDao;
	@Autowired
	private SaleOrderDao saleOrderDao;
	@Autowired
	private SaleOrderDetailDao saleOrderDetailDao;
	@Autowired
	private TeslaShopDao teslaShopDao;

	@Override
	public Map<String, Object> provinceReport(Integer percentFlag, Integer pageOffset, Integer pageSize, String orderCreatedBegin, String orderCreatedEnd) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Assert.notNull(percentFlag,"percentFlag不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		Map<String,Object> dataMap = this.saleOrderReportDao.getFerrariTotalReport(orderCreatedBegin, orderCreatedEnd, null, null, null, null, null);
		map.put("totalReport",dataMap);
		map.put("shopReportChart",this.teslaShopDao.statTeslaShopByType());
		map.put("proviceReport",this.saleOrderReportDao.getFerrariProvinceReport(page, null, orderCreatedBegin, orderCreatedEnd));
		map.put("proviceSum",this.saleOrderReportDao.countFerrariProvinceReport(null, orderCreatedBegin, orderCreatedEnd));
		if(DefaultEnum.YES.getCode().equals(percentFlag)) {
			//计算升降比例
			if(StringUtils.isEmpty(orderCreatedBegin) || StringUtils.isEmpty(orderCreatedEnd)) {
				throw new FerrariBizException(ResultCode.ILLEGAL_ARGUMENT, "percentFlag=1时，orderCreatedBegin和orderCreatedEnd必须都不能为空");
			}
			String preBeginTime = DateUtil.getPreTime(orderCreatedBegin, orderCreatedEnd);
			Map<String,Object> preDataMap = this.saleOrderReportDao.getFerrariTotalReport(preBeginTime, orderCreatedBegin, null, null, null, null, null);
			map.put("percent", calculatePercent(preDataMap, dataMap));
		}
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> cityReport(Integer percentFlag, Integer pageOffset, Integer pageSize, String province, String orderCreatedBegin, String orderCreatedEnd) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Assert.notNull(province,"省份不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<Map<String,Object>> dataMapList = this.saleOrderReportDao.getFerrariProvinceReport(null, province, orderCreatedBegin, orderCreatedEnd);
		Map<String,Object> dataMap = new LinkedHashMap<>();
		if(!CollectionUtils.isEmpty(dataMapList)) {
			dataMap = dataMapList.get(0);
		}
		map.put("provinceReport",dataMap);
		map.put("cityReport",this.saleOrderReportDao.getFerrariCityReport(page, province, orderCreatedBegin, orderCreatedEnd));
		map.put("citySum",this.saleOrderReportDao.countFerrariCityReport(province, orderCreatedBegin, orderCreatedEnd));
		if(DefaultEnum.YES.getCode().equals(percentFlag)) {
			//计算升降比例
			if(StringUtils.isEmpty(orderCreatedBegin) || StringUtils.isEmpty(orderCreatedEnd)) {
				throw new FerrariBizException(ResultCode.ILLEGAL_ARGUMENT, "percentFlag=1时，orderCreatedBegin和orderCreatedEnd必须都不能为空");
			}
			String preBeginTime = DateUtil.getPreTime(orderCreatedBegin, orderCreatedEnd);
			List<Map<String,Object>> preDataMapList = this.saleOrderReportDao.getFerrariProvinceReport(null, province, preBeginTime, orderCreatedBegin);
			Map<String,Object> preDataMap = new LinkedHashMap<>();
			if(!CollectionUtils.isEmpty(preDataMapList)) {
				preDataMap = preDataMapList.get(0);
			}
			map.put("percent", calculatePercent(preDataMap, dataMap));
		}
		return ResultUtil.successMap(map);
	}

	public Map<String, Object> shopReport(Integer percentFlag,Integer pageOffset, Integer pageSize, String province, String city, Integer shopId, Integer orderBy, String orderCreatedBegin, String orderCreatedEnd, String shopName, Integer shopType, Integer abcType) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		//Assert.notNull(city,"城市不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		List<Map<String,Object>> listMap = this.saleOrderReportDao.getFerrariShopReport(page, shopId, province, city, orderBy, orderCreatedBegin, orderCreatedEnd, null, shopName, shopType, abcType);
		List<Map<String,Object>> list = new ArrayList<>();
		if(!CollectionUtils.isEmpty(listMap)) {
			for(Map<String,Object> mapTemp : listMap) {
				Integer tempShopId = (Integer) mapTemp.get("shopId");
				TeslaShop shop = this.teslaShopDao.getTeslaShopById(tempShopId);
				mapTemp.put("province", shop.getProvince());
				mapTemp.put("city", shop.getCity());
				mapTemp.put("county", shop.getCounty());
				mapTemp.put("manager", shop.getManager());
				mapTemp.put("linkPhone", shop.getLinkPhone());
				list.add(mapTemp);
			}
		}
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		Map<String,Object> dataMap = this.saleOrderReportDao.getFerrariTotalReport(orderCreatedBegin, orderCreatedEnd, null, province, city, shopType, abcType);
		if(DefaultEnum.YES.getCode().equals(percentFlag)) {
			//计算升降比例
			if(StringUtils.isEmpty(orderCreatedBegin) || StringUtils.isEmpty(orderCreatedEnd)) {
				throw new FerrariBizException(ResultCode.ILLEGAL_ARGUMENT, "percentFlag=1时，orderCreatedBegin和orderCreatedEnd必须都不能为空");
			}
			String preBeginTime = DateUtil.getPreTime(orderCreatedBegin, orderCreatedEnd);
			Map<String,Object> preDataMap = this.saleOrderReportDao.getFerrariTotalReport(preBeginTime, orderCreatedBegin, null, province, city, shopType, abcType);
			map.put("percent", calculatePercent(preDataMap, dataMap));
		}
		//门店列表
		map.put("shopReport",list);
		map.put("shopSum",this.saleOrderReportDao.countFerrariShopReport(shopId, province, city, orderCreatedBegin, orderCreatedEnd, null, shopName, shopType, abcType));
		//总收入金额和总订单数
		map.put("totalReport",dataMap);
		map.put("shopReportChart",this.teslaShopDao.statTeslaShopByType());
//		//总门店数
//		String[] shopStatusList = {"100","101","102","103","104","105","106","444"};
//		map.put("totalShopNum", this.teslaShopDao.countTeslaShopList(null, province, city, shopStatusList, null, null, null, null, null, null));
//		//村淘门店数
//		map.put("cuntaoShopNum", this.teslaShopDao.countTeslaShopList(null, province, city, shopStatusList, null, null, null, null, ShopTypeEnum.CUNTAO.getCode(), null));
//		//车码头门店数
//		map.put("chematouShopNum", this.teslaShopDao.countTeslaShopList(null, province, city, shopStatusList, null, null, null, null, ShopTypeEnum.CHEMATOU.getCode(), null));
//		//星奥门店数
//		map.put("xingaoShopNum", this.teslaShopDao.countTeslaShopList(null, province, city, shopStatusList, null, null, null, null, ShopTypeEnum.XINGAO.getCode(), null));
//		//A类门店数
//		map.put("aShopNum", this.teslaShopDao.countTeslaShopList(null, province, city, shopStatusList, null, null, null, null, null, AbcTypeEnum.A.getCode()));
		return ResultUtil.successMap(map);
	}

	@Override
	public Map<String, Object> productReport(Integer percentFlag,Integer pageOffset, Integer pageSize, Integer shopId, String productName, Integer orderBy, String orderCreatedBegin, String orderCreatedEnd, String taobaoSellerNick) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Assert.notNull(shopId,"shopId不能为空");
		String sellerNick = TaobaoSellerNick.getValue(taobaoSellerNick);
		Page<?> page = new Page<>(pageOffset, pageSize);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<Map<String,Object>> dataMapList = this.saleOrderReportDao.getFerrariShopReport(page, shopId, null, null, 0, orderCreatedBegin, orderCreatedEnd, sellerNick, null, null, null);
		Map<String,Object> dataMap = new LinkedHashMap<>();
		if(!CollectionUtils.isEmpty(dataMapList)) {
			dataMap = dataMapList.get(0);
		}
		map.put("shopReport",dataMap);
		List<Map<String,Object>> listMap = this.saleOrderReportDao.getFerrariProductReport(page, shopId, productName, orderBy, orderCreatedBegin, orderCreatedEnd, sellerNick);
		List<Map<String,Object>> list = new ArrayList<>();
		if(!CollectionUtils.isEmpty(listMap)) {
			for(Map<String,Object> mapTemp : listMap) {
				String tempProductName = (String) mapTemp.get("productName");
				List<SaleOrderDetail> detailList = this.saleOrderDetailDao.getSaleOrderDetailListByProductName(tempProductName);
				if(!CollectionUtils.isEmpty(detailList)){
					SaleOrderDetail detail = detailList.get(0);
					mapTemp.put("picPath", detail.getPicPath());
					mapTemp.put("price", detail.getPrice());
				}
				list.add(mapTemp);
			}
		}
		map.put("productReport",list);
		map.put("productSum",this.saleOrderReportDao.countFerrariProductReport(shopId, productName, orderCreatedBegin, orderCreatedEnd, sellerNick));
		if(DefaultEnum.YES.getCode().equals(percentFlag)) {
			//计算升降比例
			if(StringUtils.isEmpty(orderCreatedBegin) || StringUtils.isEmpty(orderCreatedEnd)) {
				throw new FerrariBizException(ResultCode.ILLEGAL_ARGUMENT, "percentFlag=1时，orderCreatedBegin和orderCreatedEnd必须都不能为空");
			}
			String preBeginTime = DateUtil.getPreTime(orderCreatedBegin, orderCreatedEnd);
			List<Map<String,Object>> preDataMapList = this.saleOrderReportDao.getFerrariShopReport(page, shopId, null, null, 0, preBeginTime, orderCreatedBegin, sellerNick, null, null, null);
			Map<String,Object> preDataMap = new LinkedHashMap<>();
			if(!CollectionUtils.isEmpty(preDataMapList)) {
				preDataMap = preDataMapList.get(0);
			}
			map.put("percent", calculatePercent(preDataMap, dataMap));
		}
		//总销量和总订单量
		Map<String,Object> totalDataMap = this.saleOrderReportDao.getFerrariTotalReport(orderCreatedBegin, orderCreatedEnd, sellerNick, null, null, null, null);
		map.put("totalReport",totalDataMap);
		//按照卖家昵称拆分卡片
		if(StringUtils.isEmpty(taobaoSellerNick)) {
			Map<String,String> taobaoSellerNickMap = TaobaoSellerNick.map();
			for(Map.Entry<String, String> entry : taobaoSellerNickMap.entrySet()) {
				String englishName = TaobaoSellerNick.getEnglishValue(entry.getKey());
				map.put(englishName, this.saleOrderDao.countSaleOrderList(null, shopId, null, null, productName, null, orderCreatedBegin, orderCreatedEnd, entry.getValue(), null, null));
			}
		}else{
			String englishName = TaobaoSellerNick.getEnglishValue(taobaoSellerNick);
			map.put(englishName, this.saleOrderDao.countSaleOrderList(null, shopId, null, null, productName, null, orderCreatedBegin, orderCreatedEnd, sellerNick, null, null));
		}
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> provinceRankReport(Integer pageOffset, Integer pageSize, String productName, String orderCreatedBegin, String orderCreatedEnd) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Assert.notNull(productName,"商品名称不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<SaleOrderDetail> detailList = this.saleOrderDetailDao.getSaleOrderDetailListByProductName(productName);
		if(!CollectionUtils.isEmpty(detailList)){
			SaleOrderDetail detail = detailList.get(0);
			Product product = new Product(detail.getProductName(), detail.getPrice(), detail.getPicPath());
			map.put("product", product);
		}
		map.put("productTotalReport",this.saleOrderReportDao.getFerrariProductTotalReport(productName, orderCreatedBegin, orderCreatedEnd));
		map.put("provinceRankReport",this.saleOrderReportDao.getFerrariProvinceProductReport(page, productName, orderCreatedBegin, orderCreatedEnd));
		map.put("provinceRankSum",this.saleOrderReportDao.countFerrariProvinceProductReport(productName, orderCreatedBegin, orderCreatedEnd));
		return ResultUtil.successMap(map);
	}

	public Map<String, Object> shopRankReport(Integer pageOffset, Integer pageSize, String productName, String orderCreatedBegin, String orderCreatedEnd) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Assert.notNull(productName,"商品名称不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		Map<String,Object> map = new LinkedHashMap<String,Object>();
		List<SaleOrderDetail> detailList = this.saleOrderDetailDao.getSaleOrderDetailListByProductName(productName);
		if(!CollectionUtils.isEmpty(detailList)){
			SaleOrderDetail detail = detailList.get(0);
			Product product = new Product(detail.getProductName(), detail.getPrice(), detail.getPicPath());
			map.put("product", product);
		}
		map.put("productTotalReport",this.saleOrderReportDao.getFerrariProductTotalReport(productName, orderCreatedBegin, orderCreatedEnd));
		map.put("shopRankReport",this.saleOrderReportDao.getFerrariShopProductReport(page, productName, orderCreatedBegin, orderCreatedEnd));
		map.put("shopRankSum",this.saleOrderReportDao.countFerrariShopProductReport(productName, orderCreatedBegin, orderCreatedEnd));
		return ResultUtil.successMap(map);
	}

	private Map<String,Object> calculatePercent(Map<String,Object> preDataMap, Map<String,Object> dataMap) {
		Map<String,Object> retuMap = new LinkedHashMap<>();
		if(null!=dataMap.get("orderTotal") && null!=preDataMap.get("orderTotal")) {
			BigDecimal orderTotal = new BigDecimal(dataMap.get("orderTotal").toString());
			BigDecimal preOrderTotal = new BigDecimal(preDataMap.get("orderTotal").toString());
			if(BigDecimal.ZERO.compareTo(preOrderTotal) == 0){
				preOrderTotal = BigDecimal.ONE;
			}
			retuMap.put("orderTotalPercent", orderTotal.subtract(preOrderTotal).divide(preOrderTotal,2,BigDecimal.ROUND_HALF_UP));
		} else {
			retuMap.put("orderTotalPercent", new BigDecimal("0.00"));
		}
		if(null!=dataMap.get("orderTotalAmount") && null!=preDataMap.get("orderTotalAmount")) {
			BigDecimal orderTotalAmount = new BigDecimal(dataMap.get("orderTotalAmount").toString());
			BigDecimal preOrderTotalAmount = new BigDecimal(preDataMap.get("orderTotalAmount").toString());
			if(BigDecimal.ZERO.compareTo(preOrderTotalAmount) == 0){
				preOrderTotalAmount = BigDecimal.ONE;
			}
			retuMap.put("orderTotalAmountPercent", orderTotalAmount.subtract(preOrderTotalAmount).divide(preOrderTotalAmount,2,BigDecimal.ROUND_HALF_UP));
		} else{
			retuMap.put("orderTotalAmountPercent", new BigDecimal("0.00"));
		}
		return retuMap;
	}
	
	public Map<String, Object> getSaleOrderListDetailByShopId(
			Integer pageOffset, Integer pageSize, Integer shopId,
			String orderCreatedBegin, String orderCreatedEnd) {
		Assert.notNull(pageOffset,"pageOffset不能为空");
		Assert.notNull(pageSize,"pageSize不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", this.saleOrderReportDao.getSaleOrderListDetailByShopId(page, shopId, orderCreatedBegin, orderCreatedEnd));
		map.put("sum", this.saleOrderReportDao.countSaleOrderListDetailByShopId(shopId, orderCreatedBegin, orderCreatedEnd));
		return ResultUtil.successMap(map);
	}

}
