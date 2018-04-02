package com.home.ferrari.vo.breakdoc;

import java.io.Serializable;
import java.util.Date;

public class BreakType implements Serializable {

	private static final long serialVersionUID = -5848151084198858924L;
	private Integer id;
	private Integer parentId;
	private String name;// 名称
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
