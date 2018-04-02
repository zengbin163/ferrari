package com.home.ferrari.vo.complain;

import java.io.Serializable;
import java.util.Date;

public class Complain implements Serializable {

	private static final long serialVersionUID = -8450820173521707003L;

	private Integer complainId;
	private Integer complainType;// 投诉类型 1订单类投诉 2非订单类投诉
	private Integer complainSubType;// 非订单类投诉 1门店 2个人
	private Integer complainStatus;// 投诉状态 @ComplainStatus
	private Integer complainReason;// 投诉原因 @ComplainReasonEnum
	private Integer isRight; // 是否发起申诉 0未发起申诉 1已发起申诉
	private Integer isFixed; // 是否解决 0解决失败 1解决成功
	private String complainName;// 投诉人名称
	private String complainPhone;// 联系方式
	private Integer operatorId; // 操作者id
	private Integer shopId;// 被投诉的门店id
	private String bizOrderId;// 淘宝订单id
	private Integer complainDegree;// 投诉严重程度 1高 2低
	private String beComplainName;// 被投诉人名称
	private String beComplainJob;// 被投诉人职务
	private String text;// 投诉记录
	private Date createTime;
	private Date modifyTime;

	private String shopName;// 门店名称
	private String companyName;// 门店公司名称
	private Integer sum;// 投诉总数
	
	private String complainReasonDesc; //投诉原因描述

	public Complain() {

	}

	public Complain(Integer complainType, Integer complainSubType,
			Integer complainStatus, Integer complainReason, Integer isRight,
			String complainName, String complainPhone, Integer operatorId,
			Integer shopId, String bizOrderId, Integer complainDegree,
			String beComplainName, String beComplainJob, String text) {
		this.complainType = complainType;
		this.complainSubType = complainSubType;
		this.complainStatus = complainStatus;
		this.complainReason = complainReason;
		this.isRight = isRight;
		this.complainName = complainName;
		this.complainPhone = complainPhone;
		this.operatorId = operatorId;
		this.shopId = shopId;
		this.bizOrderId = bizOrderId;
		this.complainDegree = complainDegree;
		this.beComplainName = beComplainName;
		this.beComplainJob = beComplainJob;
		this.text = text;
	}

	public Integer getComplainId() {
		return complainId;
	}

	public void setComplainId(Integer complainId) {
		this.complainId = complainId;
	}

	public Integer getComplainType() {
		return complainType;
	}

	public void setComplainType(Integer complainType) {
		this.complainType = complainType;
	}

	public Integer getComplainStatus() {
		return complainStatus;
	}

	public void setComplainStatus(Integer complainStatus) {
		this.complainStatus = complainStatus;
	}

	public Integer getComplainReason() {
		return complainReason;
	}

	public void setComplainReason(Integer complainReason) {
		this.complainReason = complainReason;
	}

	public Integer getIsRight() {
		return isRight;
	}

	public void setIsRight(Integer isRight) {
		this.isRight = isRight;
	}

	public String getComplainName() {
		return complainName;
	}

	public void setComplainName(String complainName) {
		this.complainName = complainName;
	}

	public String getComplainPhone() {
		return complainPhone;
	}

	public void setComplainPhone(String complainPhone) {
		this.complainPhone = complainPhone;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(String bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public Integer getComplainDegree() {
		return complainDegree;
	}

	public void setComplainDegree(Integer complainDegree) {
		this.complainDegree = complainDegree;
	}

	public String getBeComplainName() {
		return beComplainName;
	}

	public void setBeComplainName(String beComplainName) {
		this.beComplainName = beComplainName;
	}

	public String getBeComplainJob() {
		return beComplainJob;
	}

	public void setBeComplainJob(String beComplainJob) {
		this.beComplainJob = beComplainJob;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getIsFixed() {
		return isFixed;
	}

	public void setIsFixed(Integer isFixed) {
		this.isFixed = isFixed;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}

	public Integer getComplainSubType() {
		return complainSubType;
	}

	public void setComplainSubType(Integer complainSubType) {
		this.complainSubType = complainSubType;
	}

	public String getComplainReasonDesc() {
		return complainReasonDesc;
	}

	public void setComplainReasonDesc(String complainReasonDesc) {
		this.complainReasonDesc = complainReasonDesc;
	}
	
}
