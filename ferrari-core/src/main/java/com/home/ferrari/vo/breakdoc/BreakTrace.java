package com.home.ferrari.vo.breakdoc;

import java.io.Serializable;
import java.util.Date;

public class BreakTrace implements Serializable {

	private static final long serialVersionUID = 5694463507887034130L;
	private Integer breakId;
	private String remark;// 备注
	private Date createTime;

	public Integer getBreakId() {
		return breakId;
	}

	public void setBreakId(Integer breakId) {
		this.breakId = breakId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
