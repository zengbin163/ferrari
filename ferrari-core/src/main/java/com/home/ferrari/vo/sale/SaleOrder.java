package com.home.ferrari.vo.sale;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class SaleOrder implements Serializable {

	private static final long serialVersionUID = 8724609896181657723L;

	private Long id;
	private Integer shopId;// 门店id
	private String bizOrderId;
	private String sellerNick;
	private String buyerNick;
	/**
	 *@TaobaoOrderStatus 
	 */
	private String taobaoOrderStatus;
	/**
	 * @ShopOrderStatus
	 */
	private Integer shopOrderStatus;
	private BigDecimal payment;// 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	private BigDecimal postFee;// 邮费。精确到2位小数;单位:元。如:200.07，表示:200元7分
	private String receiverName;
	private String receiverState;// 收货人省份
	private String receiverAddress;// 收货人详细地址
	private String receiverZip;// 收货人的邮编
	private String receiverMobile;
	private String receiverPhone;
	private Integer orderSource;// 订单来源，1:天猫  2:淘宝  3:村淘   4:阿里车主卡   5:虚拟门店订单
	private Date orderCreated;// 订单创建时间。格式:yyyy-MM-dd HH:mm:ss
	private Date orderPay;// 订单支付时间。格式:yyyy-MM-dd HH:mm:ss
	private Date orderConsign;// 卖家发货时间。格式:yyyy-MM-dd HH:mm:ss
	private Date orderEnd;// 交易结束时间，交易成功/确认收货/交易关闭。格式:yyyy-MM-dd HH:mm:ss
	private String orderCreateds;// 订单创建时间。格式:yyyy-MM-dd HH:mm:ss
	private String orderPays;// 订单支付时间。格式:yyyy-MM-dd HH:mm:ss
	private String orderConsigns;// 卖家发货时间。格式:yyyy-MM-dd HH:mm:ss
	private String orderEnds;// 交易结束时间，交易成功/确认收货/交易关闭。格式:yyyy-MM-dd HH:mm:ss
	private String orderType;// 对应淘宝订单type，比如星奥服务订单为eticket
	private String buyerMessage;// 买家留言
	private String buyerAlipayNo;// 买家支付宝账号
	private Long version;
	private Date createTime;
	private Date modifyTime;

	private List<SaleOrderDetail> orderDetailList;// 子订单列表

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getSellerNick() {
		return sellerNick;
	}

	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
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

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
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

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
	}

	public Date getOrderConsign() {
		return orderConsign;
	}

	public void setOrderConsign(Date orderConsign) {
		this.orderConsign = orderConsign;
	}

	public Integer getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}

	public Date getOrderCreated() {
		return orderCreated;
	}

	public void setOrderCreated(Date orderCreated) {
		this.orderCreated = orderCreated;
	}

	public Date getOrderPay() {
		return orderPay;
	}

	public void setOrderPay(Date orderPay) {
		this.orderPay = orderPay;
	}

	public Date getOrderEnd() {
		return orderEnd;
	}

	public void setOrderEnd(Date orderEnd) {
		this.orderEnd = orderEnd;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<SaleOrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<SaleOrderDetail> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}

	public String getBuyerAlipayNo() {
		return buyerAlipayNo;
	}

	public void setBuyerAlipayNo(String buyerAlipayNo) {
		this.buyerAlipayNo = buyerAlipayNo;
	}

	public String getOrderCreateds() {
		return orderCreateds;
	}

	public void setOrderCreateds(String orderCreateds) {
		this.orderCreateds = orderCreateds;
	}

	public String getOrderPays() {
		return orderPays;
	}

	public void setOrderPays(String orderPays) {
		this.orderPays = orderPays;
	}

	public String getOrderConsigns() {
		return orderConsigns;
	}

	public void setOrderConsigns(String orderConsigns) {
		this.orderConsigns = orderConsigns;
	}

	public String getOrderEnds() {
		return orderEnds;
	}

	public void setOrderEnds(String orderEnds) {
		this.orderEnds = orderEnds;
	}
}
