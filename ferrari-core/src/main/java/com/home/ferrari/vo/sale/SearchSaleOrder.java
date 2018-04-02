package com.home.ferrari.vo.sale;

import java.io.Serializable;
import java.math.BigDecimal;

public class SearchSaleOrder implements Serializable {

	private static final long serialVersionUID = -6222220463920959969L;

	private Long orderId;// 车码头订单id
	private Long detailId;// 车码头订单明细id
	private String bizOrderId;
	private String sellerId;
	private String sellerNick;
	private String buyerId;
	private String buyerNick;
	private String taobaoOrderStatus;
	private Integer shopOrderStatus;
	private String orderCreated;// 订单创建时间。格式:yyyy-MM-dd HH:mm:ss
	private String productName;
	private String etShopName;// 门店名称
	private String color;
	private String categoryName;
	private BigDecimal price;
	private Integer num;// 商品数量
	/**
	 * 子订单级订单优惠金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private BigDecimal discountFee;
	/**
	 * 应付金额（商品价格 * 商品数量 + 手工调整金额 - 子订单级订单优惠金额）。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	private BigDecimal totalFee;
	/**
	 * 子订单实付金额。精确到2位小数，单位:元。如:200.07，表示:200元7分。对于多子订单的交易，计算公式如下：payment = price
	 * * num + adjust_fee - discount_fee ；
	 * 单子订单交易，payment与主订单的payment一致，对于退款成功的子订单
	 * ，由于主订单的优惠分摊金额，会造成该字段可能不为0.00元。建议使用退款前的实付金额减去退款单中的实际退款金额计算。
	 */
	private BigDecimal payment;
	private BigDecimal postFee;// 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
	private String receiverState;// 收货人省份
	private String receiverAddress;// 收货人详细地址
	private String receiverZip;// 收货人的邮编
	private String logisticsCompany;// 物流公司
	private String invoiceNo;// 运单号
	private Integer shopId;//门店id
	private String shopName;
	private String province;
	private String city;
	private String picPath;
	
	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getDetailId() {
		return detailId;
	}

	public void setDetailId(Long detailId) {
		this.detailId = detailId;
	}

	public String getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(String bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerNick() {
		return buyerNick;
	}

	public void setBuyerNick(String buyerNick) {
		this.buyerNick = buyerNick;
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

	public String getOrderCreated() {
		return orderCreated;
	}

	public void setOrderCreated(String orderCreated) {
		this.orderCreated = orderCreated;
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public BigDecimal getDiscountFee() {
		return discountFee;
	}

	public void setDiscountFee(BigDecimal discountFee) {
		this.discountFee = discountFee;
	}

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public BigDecimal getPostFee() {
		return postFee;
	}

	public void setPostFee(BigDecimal postFee) {
		this.postFee = postFee;
	}

	public String getReceiverState() {
		return receiverState;
	}

	public void setReceiverState(String receiverState) {
		this.receiverState = receiverState;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiverZip() {
		return receiverZip;
	}

	public void setReceiverZip(String receiverZip) {
		this.receiverZip = receiverZip;
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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
