package com.home.ferrari.vo.tesla.shop;

import java.io.Serializable;
import java.util.Date;

public class TeslaShopTrace implements Serializable {

	private static final long serialVersionUID = 2653073773345577356L;

	private Integer id;
	private Integer shopId;
	private Integer ferrariUserId;
	private String ferrariNickName;
	private Integer operateType;
	private Integer roleType;
	private Integer isRemark;
	private Integer shopExpandStatus;
	private Integer shopStatus;
	private String remark;
	private Date createTime;

	public TeslaShopTrace() {

	}

	public TeslaShopTrace(Integer shopId, Integer ferrariUserId,
			String ferrariNickName, Integer operateType, Integer roleType,
			Integer isRemark, Integer shopExpandStatus, Integer shopStatus,
			String remark) {
		this.shopId = shopId;
		this.ferrariUserId = ferrariUserId;
		this.ferrariNickName = ferrariNickName;
		this.operateType = operateType;
		this.roleType = roleType;
		this.isRemark = isRemark;
		this.shopExpandStatus = shopExpandStatus;
		this.shopStatus = shopStatus;
		this.remark = remark;
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

	public Integer getFerrariUserId() {
		return ferrariUserId;
	}

	public void setFerrariUserId(Integer ferrariUserId) {
		this.ferrariUserId = ferrariUserId;
	}

	public String getFerrariNickName() {
		return ferrariNickName;
	}

	public void setFerrariNickName(String ferrariNickName) {
		this.ferrariNickName = ferrariNickName;
	}

	public Integer getShopExpandStatus() {
		return shopExpandStatus;
	}

	public void setShopExpandStatus(Integer shopExpandStatus) {
		this.shopExpandStatus = shopExpandStatus;
	}

	public Integer getShopStatus() {
		return shopStatus;
	}

	public void setShopStatus(Integer shopStatus) {
		this.shopStatus = shopStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getIsRemark() {
		return isRemark;
	}

	public void setIsRemark(Integer isRemark) {
		this.isRemark = isRemark;
	}
}
