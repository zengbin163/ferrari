package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CrmCompany implements Serializable {

	private static final long serialVersionUID = 9079051675883188989L;

	private Integer adminId; //CRM门店管理员id
	private String crmShopName; // 门店名称
	private String crmShopCompany; // 公司名称
	private Integer adminSum; // 管理员数
	private Integer bizSum; // 业务员数
	private Integer shopFrequenceByDay; // 门店使用CRM频次/天
	private Integer shopSmsSum;// 门店短信数，@TODO 待处理
	private Integer totalCustSum; // 当前门店总客户数
	private Integer FTotalCustSum; // F类，当前门店客户达成数
	private List<Map<String, Object>> purposeCount; // 当前门店意向客户分布
	private List<CrmBizTypeReport> bizTypeCount; // 当前门店业务类型客户分布

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getCrmShopName() {
		return crmShopName;
	}

	public void setCrmShopName(String crmShopName) {
		this.crmShopName = crmShopName;
	}

	public String getCrmShopCompany() {
		return crmShopCompany;
	}

	public void setCrmShopCompany(String crmShopCompany) {
		this.crmShopCompany = crmShopCompany;
	}

	public Integer getAdminSum() {
		return adminSum;
	}

	public void setAdminSum(Integer adminSum) {
		this.adminSum = adminSum;
	}

	public Integer getBizSum() {
		return bizSum;
	}

	public void setBizSum(Integer bizSum) {
		this.bizSum = bizSum;
	}

	public Integer getShopFrequenceByDay() {
		return shopFrequenceByDay;
	}

	public void setShopFrequenceByDay(Integer shopFrequenceByDay) {
		this.shopFrequenceByDay = shopFrequenceByDay;
	}

	public Integer getShopSmsSum() {
		return shopSmsSum;
	}

	public void setShopSmsSum(Integer shopSmsSum) {
		this.shopSmsSum = shopSmsSum;
	}

	public Integer getTotalCustSum() {
		return totalCustSum;
	}

	public void setTotalCustSum(Integer totalCustSum) {
		this.totalCustSum = totalCustSum;
	}

	public Integer getFTotalCustSum() {
		return FTotalCustSum;
	}

	public void setFTotalCustSum(Integer fTotalCustSum) {
		FTotalCustSum = fTotalCustSum;
	}

	public List<Map<String, Object>> getPurposeCount() {
		return purposeCount;
	}

	public void setPurposeCount(List<Map<String, Object>> purposeCount) {
		this.purposeCount = purposeCount;
	}

	public List<CrmBizTypeReport> getBizTypeCount() {
		return bizTypeCount;
	}

	public void setBizTypeCount(List<CrmBizTypeReport> bizTypeCount) {
		this.bizTypeCount = bizTypeCount;
	}
}
