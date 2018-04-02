package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SmsSaleOrder implements Serializable {

	private static final long serialVersionUID = 1025275192774919662L;

	private Integer id;
	private Integer mealId;// 套餐id
	private Integer adminId;// 购买套餐的管理员id
	private Integer buyNum;// 购买数量
	private BigDecimal payMoney;// 支付金额
	private Integer isInvoice;// 是否开具发票 0未开 1已开
	private Integer payStatus;//支付状态 0支付失败  1未支付  2支付成功
	private String alipay;// 支付宝账号
	private String alipaySerial;// 支付流水号
	private Integer version;
	private Date createTime;
	private Date modifyTime;

	private String date;//购买日期
	private String crmShopName;// 门店名称
	private String adminName;// 客户经理
	private String loginNo;// 账号
	private String mealName;// 套餐名称
	private Long smsCount;//套餐短信总量

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getMealId() {
		return mealId;
	}

	public void setMealId(Integer mealId) {
		this.mealId = mealId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getIsInvoice() {
		return isInvoice;
	}

	public void setIsInvoice(Integer isInvoice) {
		this.isInvoice = isInvoice;
	}

	public String getAlipay() {
		return alipay;
	}

	public void setAlipay(String alipay) {
		this.alipay = alipay;
	}

	public String getAlipaySerial() {
		return alipaySerial;
	}

	public void setAlipaySerial(String alipaySerial) {
		this.alipaySerial = alipaySerial;
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

	public String getCrmShopName() {
		return crmShopName;
	}

	public void setCrmShopName(String crmShopName) {
		this.crmShopName = crmShopName;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getMealName() {
		return mealName;
	}

	public void setMealName(String mealName) {
		this.mealName = mealName;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Long getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(Long smsCount) {
		this.smsCount = smsCount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
