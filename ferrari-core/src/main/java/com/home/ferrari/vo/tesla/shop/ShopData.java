package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;

public class ShopData implements Serializable {

	private static final long serialVersionUID = -9180161968409774934L;

	private String shopType;
	private String abcType;
	private Integer sum;

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getAbcType() {
		return abcType;
	}

	public void setAbcType(String abcType) {
		this.abcType = abcType;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

}
