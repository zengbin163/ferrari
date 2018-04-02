package com.home.ferrari.vo.crm;

import java.io.Serializable;

public class CrmRoleMenu implements Serializable {

	private static final long serialVersionUID = -53682025138036690L;
	private Integer menuId;
	private Integer parentMenuId;
	private String menuName;
	private String menuEnName;
	private String menuUrl;
	private Integer isMenu;
	private String menuExtends;
	private Integer crmUserId;
	private Integer roleId;
	private String roleName;
	private Integer roleType;

	public CrmRoleMenu() {

	}

	public CrmRoleMenu(Integer parentMenuId, String menuName,
			String menuEnName, String menuUrl, Integer isMenu) {
		this.parentMenuId = parentMenuId;
		this.menuName = menuName;
		this.menuEnName = menuEnName;
		this.menuUrl = menuUrl;
		this.isMenu = isMenu;
	}

	public CrmRoleMenu(Integer menuId, Integer parentMenuId,
			String menuName, String menuEnName, String menuUrl, Integer isMenu) {
		this.menuId = menuId;
		this.parentMenuId = parentMenuId;
		this.menuName = menuName;
		this.menuEnName = menuEnName;
		this.menuUrl = menuUrl;
		this.isMenu = isMenu;
	}

	public CrmRoleMenu(Integer roleId, Integer menuId) {
		this.roleId = roleId;
		this.menuId = menuId;
	}
	
	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuExtends() {
		return menuExtends;
	}

	public void setMenuExtends(String menuExtends) {
		this.menuExtends = menuExtends;
	}

	public String getMenuEnName() {
		return menuEnName;
	}

	public void setMenuEnName(String menuEnName) {
		this.menuEnName = menuEnName;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Integer isMenu) {
		this.isMenu = isMenu;
	}

	public Integer getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(Integer parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
}
