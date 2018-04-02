package com.home.ferrari.vo.ferrari.user;

import java.io.Serializable;
import java.util.List;

public class FerrariRole implements Serializable {

	private static final long serialVersionUID = 1971318505704120895L;

	private Integer id;
	private String roleName;
	private Integer roleType;// 角色类型 @RoleTypeEnum

	private List<FerrariRoleMenu> roleMenuList;

	public FerrariRole() {

	}

	public FerrariRole(String roleName, Integer roleType) {
		this.roleName = roleName;
		this.roleType = roleType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<FerrariRoleMenu> getRoleMenuList() {
		return roleMenuList;
	}

	public void setRoleMenuList(List<FerrariRoleMenu> roleMenuList) {
		this.roleMenuList = roleMenuList;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}
}
