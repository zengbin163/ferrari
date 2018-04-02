package com.home.ferrari.vo.ferrari.user;

import java.io.Serializable;

public class FerrariUserRole implements Serializable {

	private static final long serialVersionUID = -8446140970056442140L;
	private Integer id;
	private Integer userId;
	private Integer roleId;
	
	public FerrariUserRole() {
		
	}

	public FerrariUserRole(Integer userId, Integer roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}
