package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SmsMeal implements Serializable {

	private static final long serialVersionUID = 3638035126275008085L;

	private Integer id;
	private String mealName;// 短信套餐名称
	private Long smsCount;// 套餐短信总量
	private BigDecimal salePrice;// 售价
	private Long saleNum;// 销量
	private Integer uploadStatus;// 0下架 1上架
	private String remark;// 说明备注
	private Integer version;
	private Date createTime;
	private Date modifyTime;
	
	private List<SmsSaleOrder> mealSaleOrderList; //套餐售卖记录
	private Integer mealSaleOrderSum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public Long getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(Long smsCount) {
		this.smsCount = smsCount;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Long getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(Long saleNum) {
		this.saleNum = saleNum;
	}

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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

	public List<SmsSaleOrder> getMealSaleOrderList() {
		return mealSaleOrderList;
	}

	public void setMealSaleOrderList(List<SmsSaleOrder> mealSaleOrderList) {
		this.mealSaleOrderList = mealSaleOrderList;
	}

	public Integer getMealSaleOrderSum() {
		return mealSaleOrderSum;
	}

	public void setMealSaleOrderSum(Integer mealSaleOrderSum) {
		this.mealSaleOrderSum = mealSaleOrderSum;
	}
}
