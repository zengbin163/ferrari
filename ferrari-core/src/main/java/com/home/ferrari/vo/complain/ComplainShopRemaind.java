package com.home.ferrari.vo.complain;

import java.io.Serializable;
import java.util.Date;

public class ComplainShopRemaind implements Serializable {

	private static final long serialVersionUID = 428878037799451028L;

	private Integer id;
	private Integer operatorId;
	private Integer shopId;
	private Integer isRemind; // 是否提醒 0不提醒 1提醒
	private Date remindTime;
	private Date createTime;
	private Date modifyTime;
	
	public ComplainShopRemaind() {

	}

	public ComplainShopRemaind(Integer operatorId, Integer shopId, Integer isRemind, Date remindTime) {
		this.operatorId = operatorId;
		this.shopId = shopId;
		this.isRemind = isRemind;
		this.remindTime = remindTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
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

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}

	public Integer getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(Integer isRemind) {
		this.isRemind = isRemind;
	}
}
