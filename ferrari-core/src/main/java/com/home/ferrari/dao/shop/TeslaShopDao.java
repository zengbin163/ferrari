package com.home.ferrari.dao.shop;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.tesla.shop.ShopData;
import com.home.ferrari.vo.tesla.shop.TeslaShop;
import com.home.ferrari.vo.tesla.shop.TeslaShopBrand;
import com.home.ferrari.vo.tesla.shop.TeslaShopHardware;
import com.home.ferrari.vo.tesla.shop.TeslaShopInfo;
import com.home.ferrari.vo.tesla.shop.TeslaShopSales;
import com.home.ferrari.vo.tesla.shop.TeslaShopTalent;
import com.home.ferrari.vo.tesla.shop.TeslaShopWorkshop;

public interface TeslaShopDao {
	public List<TeslaShop> getAllShops();

	public List<TeslaShop> getTeslaShopList(
			@Param(value = "page") Page<?> page,
			@Param(value = "shopName") String shopName,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "shopStatusList") String[] shopStatusList,
			@Param(value = "shopExpandStatusList") String[] shopExpandStatusList,
			@Param(value = "monitorStatusList") String[] monitorStatusList,
			@Param(value = "roleType") Integer roleType,
			@Param(value = "operatorId") Integer operatorId,
			@Param(value = "shopType") Integer shopType,
			@Param(value = "abcType") Integer abcType);
	public Integer countTeslaShopList(
			@Param(value = "shopName") String shopName,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "shopStatusList") String[] shopStatusList,
			@Param(value = "shopExpandStatusList") String[] shopExpandStatusList,
			@Param(value = "monitorStatusList") String[] monitorStatusList,
			@Param(value = "roleType") Integer roleType,
			@Param(value = "operatorId") Integer operatorId,
			@Param(value = "shopType") Integer shopType,
			@Param(value = "abcType") Integer abcType);
	public TeslaShop getTeslaShopById(@Param(value = "id") Integer id);
	public TeslaShop getTeslaShopByName(@Param(value = "shopName") String shopName);
	public Integer insertTeslaShop(TeslaShop teslaShop);
	public Integer insertTeslaShopBrand(TeslaShopBrand teslaShopBrand);
	public Integer insertTeslaShopHardware(TeslaShopHardware teslaShopHardware);
	public Integer insertTeslaShopSales(TeslaShopSales teslaShopSales);
	public Integer insertTeslaShopTalent(TeslaShopTalent teslaShopTalent);
	public Integer insertTeslaShopWorkshop(TeslaShopWorkshop teslaShopWorkshop);
	public Integer updateTeslaShop(TeslaShop teslaShop);
	public Integer updateTeslaShopHardware(TeslaShopHardware teslaShopHardware);
	public Integer updateTeslaShopSales(TeslaShopSales teslaShopSales);
	public Integer updateTeslaShopTalent(TeslaShopTalent teslaShopTalent);
	public Integer updateTeslaShopWorkshop(TeslaShopWorkshop teslaShopWorkshop);
	public Integer deleteTeslaShopBrandByShopId(@Param(value = "shopId") Integer shopId);
	public List<TeslaShopBrand> getTeslaShopBrandByShopId(@Param(value = "shopId") Integer shopId);
	public TeslaShopInfo getTeslaShopInfoById(@Param(value = "shopId") Integer shopId);
	public Integer countTeslaShopBrandByShopId(@Param(value = "shopId") Integer shopId);
	public Integer countTeslaShopHardwareByShopId(@Param(value = "shopId") Integer shopId);
	public Integer countTeslaShopSalesByShopId(@Param(value = "shopId") Integer shopId);
	public Integer countTeslaShopTalentByShopId(@Param(value = "shopId") Integer shopId);
	public Integer countTeslaShopWorkshopByShopId(@Param(value = "shopId") Integer shopId);
	
	public List<ShopData> statTeslaShopByType();
	
	public List<Integer> getTeslaShopIds(
			@Param(value = "shopName") String shopName,
			@Param(value = "province") String province,
			@Param(value = "city") String city);
}
