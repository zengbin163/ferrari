package com.home.ferrari.vo.account;

import java.io.Serializable;
import java.util.Date;

public class AccountInvoice implements Serializable {

	private static final long serialVersionUID = -7440988624645218440L;

	private Integer id;
	private String invoiceTitle;
	private String taxIdentify;
	private String billingAddress;
	private String billingAccount;
	private String billingPhone;
	private String receiverAddress;
	private String receiverPhone;
	private String accountRemark;
	private Date createTime;
	private Date modifyTime;

	public AccountInvoice() {

	}

	public AccountInvoice(String invoiceTitle, String taxIdentify,
			String billingAddress, String billingAccount, String billingPhone,
			String receiverAddress, String receiverPhone, String accountRemark) {
		this.invoiceTitle = invoiceTitle;
		this.taxIdentify = taxIdentify;
		this.billingAddress = billingAddress;
		this.billingAccount = billingAccount;
		this.billingPhone = billingPhone;
		this.receiverAddress = receiverAddress;
		this.receiverPhone = receiverPhone;
		this.accountRemark = accountRemark;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}

	public String getTaxIdentify() {
		return taxIdentify;
	}

	public void setTaxIdentify(String taxIdentify) {
		this.taxIdentify = taxIdentify;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getBillingPhone() {
		return billingPhone;
	}

	public void setBillingPhone(String billingPhone) {
		this.billingPhone = billingPhone;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
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

	public String getBillingAccount() {
		return billingAccount;
	}

	public void setBillingAccount(String billingAccount) {
		this.billingAccount = billingAccount;
	}

	public String getAccountRemark() {
		return accountRemark;
	}

	public void setAccountRemark(String accountRemark) {
		this.accountRemark = accountRemark;
	}

}
