package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.Date;

public class TeslaShop implements Serializable {

	private static final long serialVersionUID = -5809132073952800267L;
	private Integer id;
	private String shopType; // 1 车码头;2村淘;3车码头&村淘;4星奥自拓展;5天猫汽车
	private Integer abcType;
	private Integer shopStatus; // 门店状态
								// 100:BD已提交;101:待门店管理员审核;102:待财务审核;103:待法务审核;104:待VI审核;105:待总经理审核
	private Integer shopExpandStatus; // 门店拓展状态 200:待拓展; 201:拓展中; 202:拓展成功;
										// 203:拓展失败
	private Integer monitorStatus;// 300是基础信息填写完成，未开始完善信息；301是完善信息完成，未开始能力评估；302是能力评估完成
	private String province;
	private String city;
	private String county;
	private String shopName;
	private String shopAddress;
	private String companyName;
	private String openTime;// 开店年限
	private String bdOwner;// BD负责人
	private String bdPhone;// BD电话
	private Integer isChezhuka;// 车主卡洗车+空调管道清洗+检测 : 1是 0否
	private String manager;// 总经理名称
	private String linkPhone;// 经理联系电话
	private String phone;// 门店24小时服务电话
	private String dayLinker;// 日常业务联系人（门头）        ----门店对接人
	private String complaintPhone;// 预约投诉/抢修电话  ----门店对接人电话
	private String aftersalesManager; //售后经理
	private String aftersalesPhone; //售后经理电话
	private String lightBox;// 灯箱
	private String backWall;// 背景墙
	private Double fieldArea;// 场地总面积
	private Double workshopArea;// 维修车间面积
	private Integer persons;// 店里总人数
	private Integer subBranchs;// 分店数（0表示没有）
	private Integer operatorId;// 录入门店的市场拓展人员
	private String linkerL45Photo; //门店左45度照片
	private String linkerR45Photo; //门店右45度照片
	private String fullViewPhoto;  //正面全景照片
	private String featurePhoto;	 //摆放位置特写照片
	private Date createTime;
	private Date modifyTime;

	public TeslaShop() {

	}

	public String getCompanyName() {
		return companyName;
	}



	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public String getBdOwner() {
		return bdOwner;
	}

	public void setBdOwner(String bdOwner) {
		this.bdOwner = bdOwner;
	}

	public Integer getIsChezhuka() {
		return isChezhuka;
	}

	public void setIsChezhuka(Integer isChezhuka) {
		this.isChezhuka = isChezhuka;
	}

	public String getManager() {
		return manager;
	}

	public Integer getMonitorStatus() {
		return monitorStatus;
	}

	public void setMonitorStatus(Integer monitorStatus) {
		this.monitorStatus = monitorStatus;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getDayLinker() {
		return dayLinker;
	}

	public void setDayLinker(String dayLinker) {
		this.dayLinker = dayLinker;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getFieldArea() {
		return fieldArea;
	}

	public void setFieldArea(Double fieldArea) {
		this.fieldArea = fieldArea;
	}

	public Double getWorkshopArea() {
		return workshopArea;
	}

	public void setWorkshopArea(Double workshopArea) {
		this.workshopArea = workshopArea;
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

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getComplaintPhone() {
		return complaintPhone;
	}

	public void setComplaintPhone(String complaintPhone) {
		this.complaintPhone = complaintPhone;
	}

	public String getLightBox() {
		return lightBox;
	}

	public void setLightBox(String lightBox) {
		this.lightBox = lightBox;
	}

	public String getBackWall() {
		return backWall;
	}

	public void setBackWall(String backWall) {
		this.backWall = backWall;
	}

	public Integer getPersons() {
		return persons;
	}

	public void setPersons(Integer persons) {
		this.persons = persons;
	}

	public Integer getSubBranchs() {
		return subBranchs;
	}

	public void setSubBranchs(Integer subBranchs) {
		this.subBranchs = subBranchs;
	}

	public Integer getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
	}

	public Integer getShopExpandStatus() {
		return shopExpandStatus;
	}

	public void setShopExpandStatus(Integer shopExpandStatus) {
		this.shopExpandStatus = shopExpandStatus;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getAbcType() {
		return abcType;
	}

	public void setAbcType(Integer abcType) {
		this.abcType = abcType;
	}

	public String getBdPhone() {
		return bdPhone;
	}

	public void setBdPhone(String bdPhone) {
		this.bdPhone = bdPhone;
	}

	public String getAftersalesManager() {
		return aftersalesManager;
	}

	public void setAftersalesManager(String aftersalesManager) {
		this.aftersalesManager = aftersalesManager;
	}

	public String getAftersalesPhone() {
		return aftersalesPhone;
	}

	public void setAftersalesPhone(String aftersalesPhone) {
		this.aftersalesPhone = aftersalesPhone;
	}

	public String getLinkerL45Photo() {
		return linkerL45Photo;
	}

	public void setLinkerL45Photo(String linkerL45Photo) {
		this.linkerL45Photo = linkerL45Photo;
	}

	public String getLinkerR45Photo() {
		return linkerR45Photo;
	}

	public void setLinkerR45Photo(String linkerR45Photo) {
		this.linkerR45Photo = linkerR45Photo;
	}

	public String getFullViewPhoto() {
		return fullViewPhoto;
	}

	public void setFullViewPhoto(String fullViewPhoto) {
		this.fullViewPhoto = fullViewPhoto;
	}

	public String getFeaturePhoto() {
		return featurePhoto;
	}

	public void setFeaturePhoto(String featurePhoto) {
		this.featurePhoto = featurePhoto;
	}
}
