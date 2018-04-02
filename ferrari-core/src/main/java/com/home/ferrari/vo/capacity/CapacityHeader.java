package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.Date;

public class CapacityHeader implements Serializable {

	private static final long serialVersionUID = -2631348688630068173L;

	private Integer id;
	private Integer groupId;
	private String headerId;
	private String headerName;
	private Integer inputType;
	private Integer orders;
	private Date createTime;
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}

	public Integer getInputType() {
		return inputType;
	}

	public void setInputType(Integer inputType) {
		this.inputType = inputType;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
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

	public String getHeaderId() {
		return headerId;
	}

	public void setHeaderId(String headerId) {
		this.headerId = headerId;
	}
}
