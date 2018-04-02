package com.home.ferrari.vo.complain;

import java.io.Serializable;
import java.util.Date;

public class ComplainTrace implements Serializable {

	private static final long serialVersionUID = -1146229733719802770L;

	private Integer id;
	private Integer shopId;
	private Integer operatorId;
	private Integer complainId;
	private Integer remarkType;//备注类型 1客服备注  2门店备注  3门店申诉备注
	private String remark;
	private Date createTime;
	private Date modifyTime;

	public ComplainTrace() {

	}

	public ComplainTrace(Integer shopId, Integer operatorId,
			Integer complainId, Integer remarkType, String remark) {
		this.shopId = shopId;
		this.operatorId = operatorId;
		this.complainId = complainId;
		this.remarkType = remarkType;
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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getComplainId() {
		return complainId;
	}

	public void setComplainId(Integer complainId) {
		this.complainId = complainId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getRemarkType() {
		return remarkType;
	}

	public void setRemarkType(Integer remarkType) {
		this.remarkType = remarkType;
	}
	
}
