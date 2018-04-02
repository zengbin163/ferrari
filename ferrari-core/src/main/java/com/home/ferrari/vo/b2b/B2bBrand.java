package com.home.ferrari.vo.b2b;

import java.io.Serializable;

public class B2bBrand implements Serializable {

	private static final long serialVersionUID = 1958452721620070009L;

	private Integer brandId;
	private String brandName;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
