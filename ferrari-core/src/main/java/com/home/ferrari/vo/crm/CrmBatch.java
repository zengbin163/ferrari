package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class CrmBatch implements Serializable {

	private static final long serialVersionUID = 6980683706891283180L;

	private Integer id;
	private String batchCode;
	private Integer year;
	private Integer month;
	private Integer seed;
	private Date createTime;
	
	private String userName;
	private Integer crmOperatorId;
	
	private Map<String, Object> uploadReportMap; // 导入成功 导入失败 未分配 已分配
	private Map<String, Object> bizTypeReportMap; // 业务类型

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBatchCode() {
		return batchCode;
	}

	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getSeed() {
		return seed;
	}

	public void setSeed(Integer seed) {
		this.seed = seed;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Map<String, Object> getUploadReportMap() {
		return uploadReportMap;
	}

	public void setUploadReportMap(Map<String, Object> uploadReportMap) {
		this.uploadReportMap = uploadReportMap;
	}

	public Map<String, Object> getBizTypeReportMap() {
		return bizTypeReportMap;
	}

	public void setBizTypeReportMap(Map<String, Object> bizTypeReportMap) {
		this.bizTypeReportMap = bizTypeReportMap;
	}

	public Integer getCrmOperatorId() {
		return crmOperatorId;
	}

	public void setCrmOperatorId(Integer crmOperatorId) {
		this.crmOperatorId = crmOperatorId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
