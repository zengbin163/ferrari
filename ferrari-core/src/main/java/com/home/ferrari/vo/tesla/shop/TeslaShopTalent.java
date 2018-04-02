package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 门店技术/人才
 * 
 * @author Administrator
 *
 */
public class TeslaShopTalent implements Serializable {

	private static final long serialVersionUID = 7175083564696173524L;
	private Integer id;
	private Integer shopId;
	private String aftersaleManager;// 售后经理
	private String aftersalePhone;// 售后经理电话
	private String serviceMajordomo;// 服务总监
	private String servicePhone;// 服务总监电话
	private String techMajordomo;// 技术总监名称
	private String techPhone;// 技术总监电话
	private String marketingMajordomo;// 市场营销总监名称
	private String marketingPhone;// 市场营销总监电话
	private String partsMajordomo;// 配件总监名称
	private String partsPhone;// 配件总监电话
	private String saleMajordomo;// 销售总监名称
	private String salePhone;// 销售总监电话
	private Integer serviceConsultant;// 服务顾问数量
	private String servicePhones;// 服务顾问电话，英文逗号分隔
	private Date createTime;
	private Date modifyTime;

	public TeslaShopTalent() {

	}

	public TeslaShopTalent(Integer shopId, String aftersaleManager,
			String aftersalePhone, String serviceMajordomo,
			String servicePhone, String techMajordomo, String techPhone,
			String marketingMajordomo, String marketingPhone,
			String partsMajordomo, String partsPhone, String saleMajordomo,
			String salePhone, Integer serviceConsultant, String servicePhones) {
		this.shopId = shopId;
		this.aftersaleManager = aftersaleManager;
		this.aftersalePhone = aftersalePhone;
		this.serviceMajordomo = serviceMajordomo;
		this.servicePhone = servicePhone;
		this.techMajordomo = techMajordomo;
		this.techPhone = techPhone;
		this.marketingMajordomo = marketingPhone;
		this.marketingPhone = marketingPhone;
		this.partsMajordomo = partsMajordomo;
		this.partsPhone = partsPhone;
		this.saleMajordomo = saleMajordomo;
		this.salePhone = salePhone;
		this.serviceConsultant = serviceConsultant;
		this.servicePhones = servicePhones;
	}

	public static TeslaShopTalent create(String shopTalentJson) {
		JSONObject json = JSONObject.parseObject(shopTalentJson);
		TeslaShopTalent teslaShopTalent = new TeslaShopTalent(
				json.getInteger("shopId"), json.getString("aftersaleManager"),
				json.getString("aftersalePhone"),
				json.getString("serviceMajordomo"),
				json.getString("servicePhone"),
				json.getString("techMajordomo"), json.getString("techPhone"),
				json.getString("marketingMajordomo"),
				json.getString("marketingPhone"),
				json.getString("partsMajordomo"), json.getString("partsPhone"),
				json.getString("saleMajordomo"), json.getString("salePhone"),
				json.getInteger("serviceConsultant"),
				json.getString("servicePhones"));
		return teslaShopTalent;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getAftersaleManager() {
		return aftersaleManager;
	}

	public void setAftersaleManager(String aftersaleManager) {
		this.aftersaleManager = aftersaleManager;
	}

	public String getAftersalePhone() {
		return aftersalePhone;
	}

	public void setAftersalePhone(String aftersalePhone) {
		this.aftersalePhone = aftersalePhone;
	}

	public String getServiceMajordomo() {
		return serviceMajordomo;
	}

	public void setServiceMajordomo(String serviceMajordomo) {
		this.serviceMajordomo = serviceMajordomo;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getTechMajordomo() {
		return techMajordomo;
	}

	public void setTechMajordomo(String techMajordomo) {
		this.techMajordomo = techMajordomo;
	}

	public String getTechPhone() {
		return techPhone;
	}

	public void setTechPhone(String techPhone) {
		this.techPhone = techPhone;
	}

	public String getMarketingMajordomo() {
		return marketingMajordomo;
	}

	public void setMarketingMajordomo(String marketingMajordomo) {
		this.marketingMajordomo = marketingMajordomo;
	}

	public String getMarketingPhone() {
		return marketingPhone;
	}

	public void setMarketingPhone(String marketingPhone) {
		this.marketingPhone = marketingPhone;
	}

	public String getPartsMajordomo() {
		return partsMajordomo;
	}

	public void setPartsMajordomo(String partsMajordomo) {
		this.partsMajordomo = partsMajordomo;
	}

	public String getPartsPhone() {
		return partsPhone;
	}

	public void setPartsPhone(String partsPhone) {
		this.partsPhone = partsPhone;
	}

	public String getSaleMajordomo() {
		return saleMajordomo;
	}

	public void setSaleMajordomo(String saleMajordomo) {
		this.saleMajordomo = saleMajordomo;
	}

	public String getSalePhone() {
		return salePhone;
	}

	public void setSalePhone(String salePhone) {
		this.salePhone = salePhone;
	}

	public Integer getServiceConsultant() {
		return serviceConsultant;
	}

	public void setServiceConsultant(Integer serviceConsultant) {
		this.serviceConsultant = serviceConsultant;
	}

	public String getServicePhones() {
		return servicePhones;
	}

	public void setServicePhones(String servicePhones) {
		this.servicePhones = servicePhones;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
