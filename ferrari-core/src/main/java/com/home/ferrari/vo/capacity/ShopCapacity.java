package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.Date;

public class ShopCapacity implements Serializable {

	private static final long serialVersionUID = 1434007538675798461L;
	private Integer id;
	private Integer shopId;
	private Integer capacityId;
	private Integer parentCapacityId;
	private String name;
	private Integer deep;
	private Integer nodeCount;
	private Integer groupId;
	private Integer isLeaf;
	private Date createTime;
	private Date modifyTime;

	public ShopCapacity() {

	}

	public ShopCapacity(Integer shopId, Integer capacityId,
			Integer parentCapacityId, String name, Integer deep,
			Integer nodeCount, Integer groupId, Integer isLeaf) {
		this.shopId = shopId;
		this.capacityId = capacityId;
		this.parentCapacityId = parentCapacityId;
		this.name = name;
		this.deep = deep;
		this.nodeCount = nodeCount;
		this.groupId = groupId;
		this.isLeaf = isLeaf;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getCapacityId() {
		return capacityId;
	}

	public void setCapacityId(Integer capacityId) {
		this.capacityId = capacityId;
	}

	public Integer getParentCapacityId() {
		return parentCapacityId;
	}

	public void setParentCapacityId(Integer parentCapacityId) {
		this.parentCapacityId = parentCapacityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDeep() {
		return deep;
	}

	public void setDeep(Integer deep) {
		this.deep = deep;
	}

	public Integer getNodeCount() {
		return nodeCount;
	}

	public void setNodeCount(Integer nodeCount) {
		this.nodeCount = nodeCount;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getIsLeaf() {
		return isLeaf;
	}

	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
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
