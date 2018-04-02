package com.home.ferrari.vo.quest;

import java.io.Serializable;
import java.util.Date;

public class QuestRoleType implements Serializable {

	private static final long serialVersionUID = -8173659479948588990L;

	private Integer id;
	private Integer roleId;
	private Integer msgType;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
