package com.home.ferrari.vo.product;

import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Serializable {

	private static final long serialVersionUID = -7835718010319115998L;

	/**
	 * 商品价格
	 */
	private BigDecimal price;
	private String picPath;
	private String productName;

	public Product() {

	}

	public Product(String productName, BigDecimal price, String picPath) {
		this.productName = productName;
		this.price = price;
		this.picPath = picPath;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

}
