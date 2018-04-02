package com.home.ferrari.vo.tesla.user;

import java.io.Serializable;

public class TeslaMenu implements Serializable {

	private static final long serialVersionUID = 1971318505704120895L;

	private Integer id;
	private String menuCode;
	private String menuName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}
