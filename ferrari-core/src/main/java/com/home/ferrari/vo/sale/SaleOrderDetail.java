package com.home.ferrari.vo.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SaleOrderDetail implements Serializable {

	private static final long serialVersionUID = 6176660944429982144L;

	private Long id;
	private String bizOrderId;
	private String bizDetailId;// 淘宝子订单id
	/**
	 * 订单状态（请关注此状态，如果为TRADE_CLOSED_BY_TAOBAO状态，则不要对此订单进行发货，切记啊！）。 可选值:
	 * TRADE_NO_CREATE_PAY(没有创建支付宝交易) WAIT_BUYER_PAY(等待买家付款)
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款)
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货)
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) TRADE_FINISHED(交易成功)
	 * TRADE_CLOSED(付款以后用户退款成功，交易自动关闭) TRADE_CLOSED_BY_TAOBAO(付款以前，卖家或买家主动关闭交易)
	 * PAY_PENDING(国际信用卡支付付款确认中)
	 */
	private String taobaoDetailStatus;// 淘宝子订单状态
	private String productName;
	private String etShopName;// 门店名称
	private String shopName;// 门店简称
	private Integer num;// 商品数量
	/**
	 * 子订单实付金额。精确到2位小数，单位:元。如:200.07，表示:200元7分。对于多子订单的交易，计算公式如下：payment = price
	 * * num + adjust_fee - discount_fee ；
	 * 单子订单交易，payment与主订单的payment一致，对于退款成功的子订单
	 * ，由于主订单的优惠分摊金额，会造成该字段可能不为0.00元。建议使用退款前的实付金额减去退款单中的实际退款金额计算。
	 */
	private BigDecimal payment;
	/**
	 * 商品价格
	 */
	private BigDecimal price;
	/**
	 * 应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private BigDecimal totalFee;
	/**
	 * 子订单级订单优惠金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private BigDecimal discountFee;
	private String picPath;
	private String logisticsCompany;// 物流公司
	private String invoiceNo;// 运单号
	private Long version;
	private Date createTime;
	private Date modifyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBizDetailId() {
		return bizDetailId;
	}

	public void setBizDetailId(String bizDetailId) {
		this.bizDetailId = bizDetailId;
	}

	public String getTaobaoDetailStatus() {
		return taobaoDetailStatus;
	}

	public void setTaobaoDetailStatus(String taobaoDetailStatus) {
		this.taobaoDetailStatus = taobaoDetailStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getEtShopName() {
		return etShopName;
	}

	public void setEtShopName(String etShopName) {
		this.etShopName = etShopName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getLogisticsCompany() {
		return logisticsCompany;
	}

	public void setLogisticsCompany(String logisticsCompany) {
		this.logisticsCompany = logisticsCompany;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
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
	
	public String getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(String bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
