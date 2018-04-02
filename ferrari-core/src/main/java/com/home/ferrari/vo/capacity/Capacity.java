package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.Date;

public class Capacity implements Serializable {

	private static final long serialVersionUID = 1434007538675798461L;
	private Integer id;
	private Integer parentId;
	private String name;
	private Integer deep;
	private Integer nodeCount;
	private Integer groupId;
	private Integer isLeaf;
	private Date createTime;
	private Date modifyTime;

	public Capacity() {

	}

	public Capacity(Integer parentId, String name, Integer deep,
			Integer nodeCount, Integer groupId, Integer isLeaf) {
		this.parentId = parentId;
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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
