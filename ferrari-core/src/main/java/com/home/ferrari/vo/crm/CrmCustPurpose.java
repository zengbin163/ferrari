package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;

public class CrmCustPurpose implements Serializable {

	private static final long serialVersionUID = -270686829563397735L;

	private Integer id;
	private Integer customerId;
	private String purpose;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
}
