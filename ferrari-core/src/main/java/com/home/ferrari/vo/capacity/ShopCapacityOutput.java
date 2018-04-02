package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.List;

public class ShopCapacityOutput implements Serializable {

	private static final long serialVersionUID = -8267323527945420571L;
	private Integer parentId;
	private Integer groupId;
	private List<ShopCapacity> shopCapacityList;
	private List<ShopCapacityHeader> shopHeaderList;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public List<ShopCapacity> getShopCapacityList() {
		return shopCapacityList;
	}

	public void setShopCapacityList(List<ShopCapacity> shopCapacityList) {
		this.shopCapacityList = shopCapacityList;
	}

	public List<ShopCapacityHeader> getShopHeaderList() {
		return shopHeaderList;
	}

	public void setShopHeaderList(List<ShopCapacityHeader> shopHeaderList) {
		this.shopHeaderList = shopHeaderList;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
