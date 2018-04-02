package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 车间
 * 
 * @author Administrator
 *
 */
public class TeslaShopWorkshop implements Serializable {

	private static final long serialVersionUID = 6272498361480220414L;

	private Integer id;
	private Integer shopId;
	private Integer setupStation; // 加/安装工位
	private Integer setupNumber; // 加/安装人员数量
	private Integer quickStation; // 快修工位
	private Integer quickNumber; // 快修人员数量
	private Integer upkeepStation; // 保养工位
	private Integer upkeepNumber; // 保养人员数量
	private Integer machineStation; // 机修工位
	private Integer machineNumber; // 机修人员数量
	private Integer accidentStation; // 事故工位数
	private Integer accidentNumber; // 事故人员数量
	private Integer decorateStation; // 贴膜装潢工位数
	private Integer decorateNumber; // 贴膜装潢人员数量
	private Integer beautyStation; // 美容镀膜工位数
	private Integer beautyNumber; // 美容镀膜人员数
	private Integer washcarStation; // 洗车工位数
	private Integer washcarNumber; // 洗车人员数
	private Integer metalStation; // 钣金工位数
	private Integer metalNumber; // 钣金人员数
	private Integer paintStation; // 油漆工位数
	private Integer paintNumber; // 油漆人员数
	private Date createTime;
	private Date modifyTime;

	public TeslaShopWorkshop() {

	}

	public TeslaShopWorkshop(Integer shopId, Integer setupStation,
			Integer setupNumber, Integer quickStation, Integer quickNumber,
			Integer upkeepStation, Integer upkeepNumber,
			Integer machineStation, Integer machineNumber,
			Integer accidentStation, Integer accidentNumber,
			Integer decorateStation, Integer decorateNumber,
			Integer beautyStation, Integer beautyNumber,
			Integer washcarStation, Integer washcarNumber,
			Integer metalStation, Integer metalNumber, Integer paintStation,
			Integer paintNumber) {
		this.shopId = shopId;
		this.setupStation = setupStation;
		this.setupNumber = setupNumber;
		this.quickStation = quickStation;
		this.quickNumber = quickNumber;
		this.upkeepStation = upkeepStation;
		this.upkeepNumber = upkeepNumber;
		this.machineStation = machineStation;
		this.machineNumber = machineNumber;
		this.accidentStation = accidentStation;
		this.accidentNumber = accidentNumber;
		this.decorateStation = decorateStation;
		this.decorateNumber = decorateNumber;
		this.beautyStation = beautyStation;
		this.beautyNumber = beautyNumber;
		this.washcarStation = washcarStation;
		this.washcarNumber = washcarNumber;
		this.metalStation = metalStation;
		this.metalNumber = metalNumber;
		this.paintStation = paintNumber;
		this.paintNumber = paintNumber;
	}

	public static TeslaShopWorkshop create(String shopWorkshopJson) {
		JSONObject json = JSONObject.parseObject(shopWorkshopJson);
		TeslaShopWorkshop teslaShopWorkshop = new TeslaShopWorkshop(
				json.getInteger("shopId"), json.getInteger("setupStation"),
				json.getInteger("setupNumber"),
				json.getInteger("quickStation"),
				json.getInteger("quickNumber"),
				json.getInteger("upkeepStation"),
				json.getInteger("upkeepNumber"),
				json.getInteger("machineStation"),
				json.getInteger("machineNumber"),
				json.getInteger("accidentStation"),
				json.getInteger("accidentNumber"),
				json.getInteger("decorateStation"),
				json.getInteger("decorateNumber"),
				json.getInteger("beautyStation"),
				json.getInteger("beautyNumber"),
				json.getInteger("washcarStation"),
				json.getInteger("washcarNumber"),
				json.getInteger("metalStation"),
				json.getInteger("metalNumber"),
				json.getInteger("paintStation"), json.getInteger("paintNumber"));
		return teslaShopWorkshop;
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

	public Integer getSetupStation() {
		return setupStation;
	}

	public void setSetupStation(Integer setupStation) {
		this.setupStation = setupStation;
	}

	public Integer getSetupNumber() {
		return setupNumber;
	}

	public void setSetupNumber(Integer setupNumber) {
		this.setupNumber = setupNumber;
	}

	public Integer getQuickStation() {
		return quickStation;
	}

	public void setQuickStation(Integer quickStation) {
		this.quickStation = quickStation;
	}

	public Integer getQuickNumber() {
		return quickNumber;
	}

	public void setQuickNumber(Integer quickNumber) {
		this.quickNumber = quickNumber;
	}

	public Integer getUpkeepStation() {
		return upkeepStation;
	}

	public void setUpkeepStation(Integer upkeepStation) {
		this.upkeepStation = upkeepStation;
	}

	public Integer getUpkeepNumber() {
		return upkeepNumber;
	}

	public void setUpkeepNumber(Integer upkeepNumber) {
		this.upkeepNumber = upkeepNumber;
	}

	public Integer getMachineStation() {
		return machineStation;
	}

	public void setMachineStation(Integer machineStation) {
		this.machineStation = machineStation;
	}

	public Integer getMachineNumber() {
		return machineNumber;
	}

	public void setMachineNumber(Integer machineNumber) {
		this.machineNumber = machineNumber;
	}

	public Integer getAccidentStation() {
		return accidentStation;
	}

	public void setAccidentStation(Integer accidentStation) {
		this.accidentStation = accidentStation;
	}

	public Integer getAccidentNumber() {
		return accidentNumber;
	}

	public void setAccidentNumber(Integer accidentNumber) {
		this.accidentNumber = accidentNumber;
	}

	public Integer getDecorateStation() {
		return decorateStation;
	}

	public void setDecorateStation(Integer decorateStation) {
		this.decorateStation = decorateStation;
	}

	public Integer getDecorateNumber() {
		return decorateNumber;
	}

	public void setDecorateNumber(Integer decorateNumber) {
		this.decorateNumber = decorateNumber;
	}

	public Integer getBeautyStation() {
		return beautyStation;
	}

	public void setBeautyStation(Integer beautyStation) {
		this.beautyStation = beautyStation;
	}

	public Integer getBeautyNumber() {
		return beautyNumber;
	}

	public void setBeautyNumber(Integer beautyNumber) {
		this.beautyNumber = beautyNumber;
	}

	public Integer getWashcarStation() {
		return washcarStation;
	}

	public void setWashcarStation(Integer washcarStation) {
		this.washcarStation = washcarStation;
	}

	public Integer getWashcarNumber() {
		return washcarNumber;
	}

	public void setWashcarNumber(Integer washcarNumber) {
		this.washcarNumber = washcarNumber;
	}

	public Integer getMetalStation() {
		return metalStation;
	}

	public void setMetalStation(Integer metalStation) {
		this.metalStation = metalStation;
	}

	public Integer getMetalNumber() {
		return metalNumber;
	}

	public void setMetalNumber(Integer metalNumber) {
		this.metalNumber = metalNumber;
	}

	public Integer getPaintStation() {
		return paintStation;
	}

	public void setPaintStation(Integer paintStation) {
		this.paintStation = paintStation;
	}

	public Integer getPaintNumber() {
		return paintNumber;
	}

	public void setPaintNumber(Integer paintNumber) {
		this.paintNumber = paintNumber;
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
