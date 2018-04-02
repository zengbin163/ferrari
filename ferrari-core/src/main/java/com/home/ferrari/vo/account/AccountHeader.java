package com.home.ferrari.vo.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AccountHeader implements Serializable {

	private static final long serialVersionUID = -3405357657771999732L;

	private Integer finalId;// 结算单Id
	private String finalNo;// 结算单编号
	private BigDecimal totalFee;// 总金额
	private BigDecimal totalActualFee;// 财务实付总金额
	private String paymentDate;// 账期（yyyy-mm-dd）
	private Integer accountStatus;//账单状态 @AccountStatus
	private Date createTime;
	private Date modifyTime;
	
	private BigDecimal progress;//门店对账进度
	private Integer aleadyFinish;//已完成对账的门店数
	private Integer totalShop;//全部对账门店数
	
	private Integer accountShopSum;//核销门店总数
	private List<AccountShop> accountShopList;//核销门店列表
	
	private Integer uploadAll;
	private Integer uploadSucc;
	private Integer uploadFail;
	
	public AccountHeader() {
		
	}

	public AccountHeader(String finalNo, BigDecimal totalFee, String paymentDate, Integer accountStatus) {
		this.finalNo = finalNo;
		this.totalFee = totalFee;
		this.paymentDate = paymentDate;
		this.accountStatus = accountStatus;
	}

	public Integer getFinalId() {
		return finalId;
	}

	public void setFinalId(Integer finalId) {
		this.finalId = finalId;
	}

	public String getFinalNo() {
		return finalNo;
	}

	public void setFinalNo(String finalNo) {
		this.finalNo = finalNo;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
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

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public BigDecimal getProgress() {
		return progress;
	}

	public void setProgress(BigDecimal progress) {
		this.progress = progress;
	}

	public List<AccountShop> getAccountShopList() {
		return accountShopList;
	}

	public void setAccountShopList(List<AccountShop> accountShopList) {
		this.accountShopList = accountShopList;
	}

	public Integer getAccountShopSum() {
		return accountShopSum;
	}

	public void setAccountShopSum(Integer accountShopSum) {
		this.accountShopSum = accountShopSum;
	}

	public BigDecimal getTotalActualFee() {
		return totalActualFee;
	}

	public void setTotalActualFee(BigDecimal totalActualFee) {
		this.totalActualFee = totalActualFee;
	}

	public Integer getAleadyFinish() {
		return aleadyFinish;
	}

	public void setAleadyFinish(Integer aleadyFinish) {
		this.aleadyFinish = aleadyFinish;
	}

	public Integer getTotalShop() {
		return totalShop;
	}

	public void setTotalShop(Integer totalShop) {
		this.totalShop = totalShop;
	}

	public Integer getUploadAll() {
		return uploadAll;
	}

	public void setUploadAll(Integer uploadAll) {
		this.uploadAll = uploadAll;
	}

	public Integer getUploadSucc() {
		return uploadSucc;
	}

	public void setUploadSucc(Integer uploadSucc) {
		this.uploadSucc = uploadSucc;
	}

	public Integer getUploadFail() {
		return uploadFail;
	}

	public void setUploadFail(Integer uploadFail) {
		this.uploadFail = uploadFail;
	}
}
