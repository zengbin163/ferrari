package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.Date;

public class CapacityModel implements Serializable {

	private static final long serialVersionUID = 1434007538675798461L;
	private Integer id;
	private Integer capacityModelId;
	private String json;
	private Integer version;
	private Date createTime;
	private Date modifyTime;

	public CapacityModel() {

	}

	public CapacityModel(Integer capacityModelId, String json, Integer version) {
		this.capacityModelId = capacityModelId;
		this.json = json;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCapacityModelId() {
		return capacityModelId;
	}

	public void setCapacityModelId(Integer capacityModelId) {
		this.capacityModelId = capacityModelId;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
}
