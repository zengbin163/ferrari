package com.home.ferrari.vo.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AccountList implements Serializable {

	private static final long serialVersionUID = -3902965648513616960L;
	private Integer serialId;// 核销流水Id
	private String finalNo;// 结算单编号
	private String serialNo;// 核销流水编号
	private String productName;// 产品名称
	private Date checkTime;// 核销时间
	private Integer shopId;
	private String shopName;// 门店名称
	private String city;// 城市
	private String buyerNick;// 买家昵称
	private String carModel;// 车型
	private BigDecimal settleFee;// 结算价
	private Date createTime;
	private Date modifyTime;

	public AccountList() {

	}

	public AccountList(String finalNo, String serialNo, String productName,
			Date checkTime, Integer shopId, String shopName, String city,
			String buyerNick, String carModel, BigDecimal settleFee) {
		this.finalNo = finalNo;
		this.serialNo = serialNo;
		this.productName = productName;
		this.checkTime = checkTime;
		this.shopId = shopId;
		this.shopName = shopName;
		this.city = city;
		this.buyerNick = buyerNick;
		this.carModel = carModel;
		this.settleFee = settleFee;
	}

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public String getFinalNo() {
		return finalNo;
	}

	public void setFinalNo(String finalNo) {
		this.finalNo = finalNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public BigDecimal getSettleFee() {
		return settleFee;
	}

	public void setSettleFee(BigDecimal settleFee) {
		this.settleFee = settleFee;
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

	@Override
	public boolean equals(Object object) {
		AccountList accountList = (AccountList)object;
		return (this.finalNo.equals(accountList.getFinalNo()) && this.serialNo
				.equals(accountList.getSerialNo()));
	}

	public int hashCode() {
		String hash = this.finalNo + this.serialNo;
		return hash.hashCode();
	}

}
