package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.List;

public class TeslaShopInfo implements Serializable {

	private static final long serialVersionUID = -1302083700438127029L;

	private TeslaShop shopBase;
	private TeslaShopBrand shopBrand;
	private TeslaShopHardware shopHardware;
	private TeslaShopSales shopSales;
	private TeslaShopTalent shopTalent;
	private TeslaShopWorkshop shopWorkshop;
	private List<TeslaShopBrand> shopBrandList;
	private List<TeslaShopTrace> shopTraceList;


	public TeslaShop getShopBase() {
		return shopBase;
	}

	public void setShopBase(TeslaShop shopBase) {
		this.shopBase = shopBase;
	}

	public TeslaShopBrand getShopBrand() {
		return shopBrand;
	}

	public void setShopBrand(TeslaShopBrand shopBrand) {
		this.shopBrand = shopBrand;
	}

	public TeslaShopHardware getShopHardware() {
		return shopHardware;
	}

	public void setShopHardware(TeslaShopHardware shopHardware) {
		this.shopHardware = shopHardware;
	}

	public TeslaShopSales getShopSales() {
		return shopSales;
	}

	public void setShopSales(TeslaShopSales shopSales) {
		this.shopSales = shopSales;
	}

	public TeslaShopTalent getShopTalent() {
		return shopTalent;
	}

	public void setShopTalent(TeslaShopTalent shopTalent) {
		this.shopTalent = shopTalent;
	}

	public TeslaShopWorkshop getShopWorkshop() {
		return shopWorkshop;
	}

	public void setShopWorkshop(TeslaShopWorkshop shopWorkshop) {
		this.shopWorkshop = shopWorkshop;
	}

	public List<TeslaShopBrand> getShopBrandList() {
		return shopBrandList;
	}

	public void setShopBrandList(List<TeslaShopBrand> shopBrandList) {
		this.shopBrandList = shopBrandList;
	}

	public List<TeslaShopTrace> getShopTraceList() {
		return shopTraceList;
	}

	public void setShopTraceList(List<TeslaShopTrace> shopTraceList) {
		this.shopTraceList = shopTraceList;
	}
}
