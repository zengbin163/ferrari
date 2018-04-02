package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 门店销售
 * 
 * @author Administrator
 *
 */
public class TeslaShopSales implements Serializable {

	private static final long serialVersionUID = 1122596967161389991L;

	private Integer id;
	private Integer shopId;
	private Integer saleArea;// 销售展厅面积/平方米
	private Integer space;// 展位
	private Integer district;// 交车区
	private Integer custRest;// 客户休息区
	private Integer custExper;// 客户体验区
	private String districtPhoto;//交车区照片
	private String custRestPhoto;//客户休息区照片
	private String custExperPhoto;//客户体验区照片
	private String saleManager;// 销售经理名称
	private String managerPhone;// 销售经理电话
	private String saleCounselor;// 销售顾问
	private Integer counselorNumber;// 销售顾问数量
	private Date createTime;
	private Date modifyTime;

	public TeslaShopSales() {

	}

	public TeslaShopSales(Integer shopId, Integer saleArea, Integer space,
			Integer district, Integer custRest, Integer custExper,
			String saleManager, String managerPhone, String saleCounselor,
			Integer counselorNumber) {
		this.shopId = shopId;
		this.saleArea = saleArea;
		this.space = space;
		this.district = district;
		this.custRest = custRest;
		this.custExper = custExper;
		this.saleManager = saleManager;
		this.managerPhone = managerPhone;
		this.saleCounselor = saleCounselor;
		this.counselorNumber = counselorNumber;
	}

	public static TeslaShopSales create(String shopSalesJson) {
		JSONObject json = JSONObject.parseObject(shopSalesJson);
		TeslaShopSales teslaShopSales = new TeslaShopSales(
				json.getInteger("shopId"), json.getInteger("saleArea"),
				json.getInteger("space"), json.getInteger("district"),
				json.getInteger("custRest"), json.getInteger("custExper"),
				json.getString("saleManager"), json.getString("managerPhone"),
				json.getString("saleCounselor"),
				json.getInteger("counselorNumber"));
		return teslaShopSales;
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

	public Integer getSaleArea() {
		return saleArea;
	}

	public void setSaleArea(Integer saleArea) {
		this.saleArea = saleArea;
	}

	public Integer getSpace() {
		return space;
	}

	public void setSpace(Integer space) {
		this.space = space;
	}

	public Integer getDistrict() {
		return district;
	}

	public void setDistrict(Integer district) {
		this.district = district;
	}

	public Integer getCustRest() {
		return custRest;
	}

	public void setCustRest(Integer custRest) {
		this.custRest = custRest;
	}

	public Integer getCustExper() {
		return custExper;
	}

	public void setCustExper(Integer custExper) {
		this.custExper = custExper;
	}

	public String getSaleManager() {
		return saleManager;
	}

	public void setSaleManager(String saleManager) {
		this.saleManager = saleManager;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getSaleCounselor() {
		return saleCounselor;
	}

	public void setSaleCounselor(String saleCounselor) {
		this.saleCounselor = saleCounselor;
	}

	public Integer getCounselorNumber() {
		return counselorNumber;
	}

	public void setCounselorNumber(Integer counselorNumber) {
		this.counselorNumber = counselorNumber;
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

	public String getDistrictPhoto() {
		return districtPhoto;
	}

	public void setDistrictPhoto(String districtPhoto) {
		this.districtPhoto = districtPhoto;
	}

	public String getCustRestPhoto() {
		return custRestPhoto;
	}

	public void setCustRestPhoto(String custRestPhoto) {
		this.custRestPhoto = custRestPhoto;
	}

	public String getCustExperPhoto() {
		return custExperPhoto;
	}

	public void setCustExperPhoto(String custExperPhoto) {
		this.custExperPhoto = custExperPhoto;
	}
}
