package com.home.ferrari.vo.tesla.user;

import java.io.Serializable;

public class TeslaRole implements Serializable {

	private static final long serialVersionUID = 1971318505704120895L;

	private Integer id;
	private String roleCode;
	private String roleName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
