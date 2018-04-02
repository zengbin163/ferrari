package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;

public class CrmUserBizType implements Serializable {

	private static final long serialVersionUID = -270686829563397735L;

	private Integer id;
	private Integer crmUserId;
	private Integer bizType; // t_resource customer_require_key
	private String resourceDesc;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}
}
