package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CrmCustCount implements Serializable {

	private static final long serialVersionUID = -3171041256462911171L;

	private Integer customerId; // 客户id
	private String customerName; // 客户名称
	private Integer crmUserId; // 业务员id
	private String crmUserName;// 业务员名称
	private Integer sum; // 满足条件的总数

	private List<Map<String, Object>> purposeCount; //潜客意向客户数
	private List<CrmBizTypeReport> bizTypeCount; //潜客业务类型客户数
	private List<String> bizTypeNames;// 业务类型	
	
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
	}

	public String getCrmUserName() {
		return crmUserName;
	}

	public void setCrmUserName(String crmUserName) {
		this.crmUserName = crmUserName;
	}

	public List<Map<String, Object>> getPurposeCount() {
		return purposeCount;
	}

	public void setPurposeCount(List<Map<String, Object>> purposeCount) {
		this.purposeCount = purposeCount;
	}

	public List<String> getBizTypeNames() {
		return bizTypeNames;
	}

	public void setBizTypeNames(List<String> bizTypeNames) {
		this.bizTypeNames = bizTypeNames;
	}

	public List<CrmBizTypeReport> getBizTypeCount() {
		return bizTypeCount;
	}

	public void setBizTypeCount(List<CrmBizTypeReport> bizTypeCount) {
		this.bizTypeCount = bizTypeCount;
	}

}
