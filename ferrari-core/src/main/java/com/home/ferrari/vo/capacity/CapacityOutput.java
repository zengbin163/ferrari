package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.List;

public class CapacityOutput implements Serializable {

	private static final long serialVersionUID = -8267323527945420571L;
	private Integer parentId;
	private Integer groupId;
	private List<Capacity> capacityList;
	private List<CapacityHeader> headerList;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public List<Capacity> getCapacityList() {
		return capacityList;
	}

	public void setCapacityList(List<Capacity> capacityList) {
		this.capacityList = capacityList;
	}

	public List<CapacityHeader> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<CapacityHeader> headerList) {
		this.headerList = headerList;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
