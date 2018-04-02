package com.home.ferrari.vo.crm;

import java.io.Serializable;

public class CrmBizTypeReport implements Serializable {

	private static final long serialVersionUID = 4205666627723155544L;

	private Integer bizType;
	private String bizValue;
	private Integer sum;

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getBizValue() {
		return bizValue;
	}

	public void setBizValue(String bizValue) {
		this.bizValue = bizValue;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}
}
