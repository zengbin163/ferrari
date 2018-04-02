package com.home.ferrari.vo.sale;

import java.io.Serializable;
import java.util.Date;

public class SaleOrderTrace implements Serializable {

	private static final long serialVersionUID = -2899298180616533056L;
	private Long id;
	private String bizOrderId;
	/**
	 * @TaobaoOrderStatus
	 */
	private String taobaoOrderStatus;
	/**
	 * @ShopOrderStatus
	 */
	private Integer shopOrderStatus;
	private Integer shopId;// 门店id
	private Integer operatorType;// 操作者类型 1系统 2服务商 3门店
	private Integer operatorId;// 操作者id，系统默认为-1，门店和服务商分别为登陆的userId
	private String operatorNickName;// 操作者昵称
	private Integer isShow;// 是否在网页或者app展现 1展现 0不展现
	private String traceAttr;// 订单跟踪详细描述，json串格式
	private Date createTime;
	private Date modifyTime;

	private String operatorTime;

	public SaleOrderTrace() {

	}

	public SaleOrderTrace(String bizOrderId, String taobaoOrderStatus,
			Integer shopOrderStatus, Integer shopId, Integer operatorType,
			Integer operatorId, String operatorNickName, Integer isShow,
			String traceAttr) {
		this.bizOrderId = bizOrderId;
		this.taobaoOrderStatus = taobaoOrderStatus;
		this.shopOrderStatus = shopOrderStatus;
		this.shopId = shopId;
		this.operatorType = operatorType;
		this.operatorId = operatorId;
		this.operatorNickName = operatorNickName;
		this.isShow = isShow;
		this.traceAttr = traceAttr;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(String bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public String getTaobaoOrderStatus() {
		return taobaoOrderStatus;
	}

	public void setTaobaoOrderStatus(String taobaoOrderStatus) {
		this.taobaoOrderStatus = taobaoOrderStatus;
	}

	public Integer getShopOrderStatus() {
		return shopOrderStatus;
	}

	public void setShopOrderStatus(Integer shopOrderStatus) {
		this.shopOrderStatus = shopOrderStatus;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getOperatorType() {
		return operatorType;
	}

	public void setOperatorType(Integer operatorType) {
		this.operatorType = operatorType;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOperatorNickName() {
		return operatorNickName;
	}

	public void setOperatorNickName(String operatorNickName) {
		this.operatorNickName = operatorNickName;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getTraceAttr() {
		return traceAttr;
	}

	public void setTraceAttr(String traceAttr) {
		this.traceAttr = traceAttr;
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

	public String getOperatorTime() {
		return operatorTime;
	}

	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}

}
