package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CrmCustomer implements Serializable {

	private static final long serialVersionUID = 1373340079819860726L;

	private Integer id;
	private String crmBatchCode;// 导入时填写的批次号
	private String customerName;// 潜客名称
	private String level;// A重要客户 B普通客户 C低价值客户
	private String licenseTime;// 上牌日期
	private String licensePlate;// 车牌
	private String linkPhone;// 联系方式
	private Integer crmUserId;// 所属业务员id
	private String crmUserName;// 导入时填写的业务员名称
	private Integer crmOperatorId; // 导入潜客或者创建潜客的操作人id
	private String crmBrandName;// 品牌名称
	private String carSeries;// 车系
	private String displacement;// 排量
	private String year;// 年份
	private String vin;// VIN码
	private String engine;// 发动机号
	private String purpose;// 客户意向：A/B/C/D/E/F
	private String remark;// 备注
	private String introducer;// 介绍人
	private Date linkTime;// 联系日期
	private Integer uploadStatus; // 导入状态 0导入失败 1导入成功未分配 2已分配
	private String uploadReason;// 导入失败的理由
	private Date createTime;
	private Date modifyTime;
	
	private List<CrmCustBizType> bizTypeList;//业务类型
	private Date remaindTime; //设置的提醒时间
	private Integer leftDays;// 提醒剩余天数

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
	}

	public String getCarSeries() {
		return carSeries;
	}

	public void setCarSeries(String carSeries) {
		this.carSeries = carSeries;
	}

	public String getDisplacement() {
		return displacement;
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getCrmBatchCode() {
		return crmBatchCode;
	}

	public void setCrmBatchCode(String crmBatchCode) {
		this.crmBatchCode = crmBatchCode;
	}

	public String getCrmUserName() {
		return crmUserName;
	}

	public void setCrmUserName(String crmUserName) {
		this.crmUserName = crmUserName;
	}

	public String getLicenseTime() {
		return licenseTime;
	}

	public void setLicenseTime(String licenseTime) {
		this.licenseTime = licenseTime;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getIntroducer() {
		return introducer;
	}

	public void setIntroducer(String introducer) {
		this.introducer = introducer;
	}

	public Date getLinkTime() {
		return linkTime;
	}

	public void setLinkTime(Date linkTime) {
		this.linkTime = linkTime;
	}

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getUploadReason() {
		return uploadReason;
	}

	public void setUploadReason(String uploadReason) {
		this.uploadReason = uploadReason;
	}

	public String getCrmBrandName() {
		return crmBrandName;
	}

	public void setCrmBrandName(String crmBrandName) {
		this.crmBrandName = crmBrandName;
	}

	public Integer getCrmOperatorId() {
		return crmOperatorId;
	}

	public void setCrmOperatorId(Integer crmOperatorId) {
		this.crmOperatorId = crmOperatorId;
	}

	public List<CrmCustBizType> getBizTypeList() {
		return bizTypeList;
	}

	public void setBizTypeList(List<CrmCustBizType> bizTypeList) {
		this.bizTypeList = bizTypeList;
	}

	public Integer getLeftDays() {
		return leftDays;
	}

	public void setLeftDays(Integer leftDays) {
		this.leftDays = leftDays;
	}

	public Date getRemaindTime() {
		return remaindTime;
	}

	public void setRemaindTime(Date remaindTime) {
		this.remaindTime = remaindTime;
	}
}
