package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;

public class Brand implements Serializable {

	private static final long serialVersionUID = -7294098840762767266L;
	private Integer id;
	private String brandName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
