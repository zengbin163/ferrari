package com.home.ferrari.vo.common;

import java.io.Serializable;
import java.util.Date;

public class Resource implements Serializable {

	private static final long serialVersionUID = -3526741515738353621L;

	private Integer id;
	private String resourceKey;
	private String resourceDesc;
	private Integer resourceValue;
	private Integer resourceId;
	private Date createTime;
	private Date modifyTime;

	public Resource() {

	}

	public Resource(String resourceKey, String resourceDesc,
			Integer resourceValue, Integer resourceId) {
		this.resourceKey = resourceKey;
		this.resourceDesc = resourceDesc;
		this.resourceValue = resourceValue;
		this.resourceId = resourceId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceKey() {
		return resourceKey;
	}

	public void setResourceKey(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getResourceDesc() {
		return resourceDesc;
	}

	public void setResourceDesc(String resourceDesc) {
		this.resourceDesc = resourceDesc;
	}

	public Integer getResourceValue() {
		return resourceValue;
	}

	public void setResourceValue(Integer resourceValue) {
		this.resourceValue = resourceValue;
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

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}
}
