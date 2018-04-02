package com.home.ferrari.vo.capacity;

import java.io.Serializable;

public class CapacityGroup implements Serializable {

	private static final long serialVersionUID = -6424990704137722091L;
	private Integer id;
	private Integer parentNodeId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentNodeId() {
		return parentNodeId;
	}

	public void setParentNodeId(Integer parentNodeId) {
		this.parentNodeId = parentNodeId;
	}

}
