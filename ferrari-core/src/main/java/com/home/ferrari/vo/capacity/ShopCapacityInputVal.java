package com.home.ferrari.vo.capacity;

import java.io.Serializable;
import java.util.Date;

public class ShopCapacityInputVal implements Serializable {

	private static final long serialVersionUID = -2631348688630068173L;

	private Integer id;
	private Integer shopId;
	private Integer leafCapacityId;
	private Integer headerId;
	private String headerName;
	private String inputValue;
	private Date createTime;
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getHeaderId() {
		return headerId;
	}

	public void setHeaderId(Integer headerId) {
		this.headerId = headerId;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
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

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getLeafCapacityId() {
		return leafCapacityId;
	}

	public void setLeafCapacityId(Integer leafCapacityId) {
		this.leafCapacityId = leafCapacityId;
	}

	public String getInputValue() {
		return inputValue;
	}

	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}

}
