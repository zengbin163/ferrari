package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.Date;

/**
 * 店铺品牌
 * 
 * @author Administrator
 *
 */
public class TeslaShopBrand implements Serializable {

	private static final long serialVersionUID = 5155129059118339413L;

	private Integer id;
	private Integer shopId;
	private String carBrands;// 通过","分割 入参
	private String carBrand;
	private Date createTime;
	private Date modifyTime;

	public TeslaShopBrand() {

	}

	public TeslaShopBrand(Integer shopId, String carBrand) {
		this.shopId = shopId;
		this.carBrand = carBrand;
	}

	public static TeslaShopBrand create(Integer shopId, String carBrand) {
		return new TeslaShopBrand(shopId, carBrand);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getCarBrands() {
		return carBrands;
	}

	public void setCarBrands(String carBrands) {
		this.carBrands = carBrands;
	}
}
