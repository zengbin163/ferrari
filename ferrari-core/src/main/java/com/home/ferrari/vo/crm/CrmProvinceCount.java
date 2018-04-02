package com.home.ferrari.vo.crm;

import java.io.Serializable;

public class CrmProvinceCount implements Serializable {

	private static final long serialVersionUID = -922644169344846196L;

	private String province;
	private Integer adminSum;// 管理员数
	private Integer bizSum;// 业务员数
	private Integer customerSum;// 客户数

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
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

	public Integer getCustomerSum() {
		return customerSum;
	}

	public void setCustomerSum(Integer customerSum) {
		this.customerSum = customerSum;
	}

}
