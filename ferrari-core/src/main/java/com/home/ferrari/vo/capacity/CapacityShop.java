package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.Date;

public class CapacityShop implements Serializable {

	private static final long serialVersionUID = 1434007538675798461L;

	private Integer id;
	private Integer shopId;
	private Integer capacityModelId;
	private String searchKey;
	private String json;
	private Integer version;
	private Date createTime;
	private Date modifyTime;

	public CapacityShop() {

	}

	public CapacityShop(Integer shopId, Integer capacityModelId,
			String searchKey, String json, Integer version) {
		this.shopId = shopId;
		this.capacityModelId = capacityModelId;
		this.searchKey = searchKey;
		this.json = json;
		this.version = version;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCapacityModelId() {
		return capacityModelId;
	}

	public void setCapacityModelId(Integer capacityModelId) {
		this.capacityModelId = capacityModelId;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
}
