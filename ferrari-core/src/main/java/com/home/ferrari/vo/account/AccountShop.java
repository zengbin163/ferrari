package com.home.ferrari.vo.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AccountShop implements Serializable {

	private static final long serialVersionUID = 2946111194511820969L;

	private Integer id;
	private String finalNo;
	private Integer shopId;
	private String shopName;
	private Integer accountStatus;// @AccountStatus
	private BigDecimal accountFee;// 财务实付金额
	private String financeUrl;// 财务图片URL
	private String logisticsCompany;// 物流公司
	private String logisticsNo;// 物流单号
	private String logisticsRemark;// 物流备注
	private Date createTime;
	private Date modifyTime;

	private BigDecimal shopSettleFee;// 门店结算金额

	private Integer shopAccountListSum;// 门店核销流水总数
	private List<AccountList> shopAccountList;// 门店核销流水列表

	private String paymentDate;// 账期

	public AccountShop() {

	}

	public AccountShop(String finalNo, Integer shopId, String shopName,
			Integer accountStatus) {
		this.finalNo = finalNo;
		this.shopId = shopId;
		this.shopName = shopName;
		this.accountStatus = accountStatus;
	}

	public AccountShop(Integer id, Integer accountStatus,
			BigDecimal accountFee, String financeUrl, String logisticsCompany,
			String logisticsNo, String logisticsRemark) {
		this.id = id;
		this.accountStatus = accountStatus;
		this.accountFee = accountFee;
		this.financeUrl = financeUrl;
		this.logisticsCompany = logisticsCompany;
		this.logisticsNo = logisticsNo;
		this.logisticsRemark = logisticsRemark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFinalNo() {
		return finalNo;
	}

	public void setFinalNo(String finalNo) {
		this.finalNo = finalNo;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
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

	public BigDecimal getShopSettleFee() {
		return shopSettleFee;
	}

	public void setShopSettleFee(BigDecimal shopSettleFee) {
		this.shopSettleFee = shopSettleFee;
	}

	public Integer getShopAccountListSum() {
		return shopAccountListSum;
	}

	public void setShopAccountListSum(Integer shopAccountListSum) {
		this.shopAccountListSum = shopAccountListSum;
	}

	public List<AccountList> getShopAccountList() {
		return shopAccountList;
	}

	public void setShopAccountList(List<AccountList> shopAccountList) {
		this.shopAccountList = shopAccountList;
	}

	public BigDecimal getAccountFee() {
		return accountFee;
	}

	public void setAccountFee(BigDecimal accountFee) {
		this.accountFee = accountFee;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getLogisticsNo() {
		return logisticsNo;
	}

	public void setLogisticsNo(String logisticsNo) {
		this.logisticsNo = logisticsNo;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getLogisticsRemark() {
		return logisticsRemark;
	}

	public void setLogisticsRemark(String logisticsRemark) {
		this.logisticsRemark = logisticsRemark;
	}

	public String getFinanceUrl() {
		return financeUrl;
	}

	public void setFinanceUrl(String financeUrl) {
		this.financeUrl = financeUrl;
	}
	
}
