package com.home.ferrari.vo.b2b;

import java.io.Serializable;

public class B2bCategory implements Serializable {

	private static final long serialVersionUID = 1958452721620070009L;

	private Integer categoryId;
	private String categoryName;
	private String shortName;
	private String categoryLogo;
	private Integer orders;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getCategoryLogo() {
		return categoryLogo;
	}

	public void setCategoryLogo(String categoryLogo) {
		this.categoryLogo = categoryLogo;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

}
