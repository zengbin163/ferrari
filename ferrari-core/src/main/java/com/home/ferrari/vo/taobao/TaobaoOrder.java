package com.home.ferrari.vo.taobao;

import java.io.Serializable;
import java.util.List;

public class TaobaoOrder implements Serializable {

	private static final long serialVersionUID = -2242920087602677124L;

	private String tid;
	private String seller_nick;
	private String buyer_nick;
	private String status;
	private String payment;
	private String post_fee;
	private String receiver_name;
	private String receiver_state;
	private String receiver_address;
	private String receiver_zip;
	private String receiver_mobile;
	private String receiver_phone;
	private String created;
	private String pay_time;
	private String consign_time;
	private String end_time;
	private String type;
	private String buyer_message;
	private String buyer_alipay_no;

	private List<TaobaoOrderDetail> orderList;

	public String getBuyer_alipay_no() {
		return buyer_alipay_no;
	}

	public void setBuyer_alipay_no(String buyer_alipay_no) {
		this.buyer_alipay_no = buyer_alipay_no;
	}

	public String getBuyer_nick() {
		return buyer_nick;
	}

	public void setBuyer_nick(String buyer_nick) {
		this.buyer_nick = buyer_nick;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getReceiver_address() {
		return receiver_address;
	}

	public void setReceiver_address(String receiver_address) {
		this.receiver_address = receiver_address;
	}

	public String getReceiver_mobile() {
		return receiver_mobile;
	}

	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}

	public String getReceiver_name() {
		return receiver_name;
	}

	public void setReceiver_name(String receiver_name) {
		this.receiver_name = receiver_name;
	}

	public String getSeller_nick() {
		return seller_nick;
	}

	public void setSeller_nick(String seller_nick) {
		this.seller_nick = seller_nick;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public List<TaobaoOrderDetail> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<TaobaoOrderDetail> orderList) {
		this.orderList = orderList;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPost_fee() {
		return post_fee;
	}

	public void setPost_fee(String post_fee) {
		this.post_fee = post_fee;
	}

	public String getReceiver_state() {
		return receiver_state;
	}

	public void setReceiver_state(String receiver_state) {
		this.receiver_state = receiver_state;
	}

	public String getReceiver_zip() {
		return receiver_zip;
	}

	public void setReceiver_zip(String receiver_zip) {
		this.receiver_zip = receiver_zip;
	}

	public String getReceiver_phone() {
		return receiver_phone;
	}

	public void setReceiver_phone(String receiver_phone) {
		this.receiver_phone = receiver_phone;
	}

	public String getPay_time() {
		return pay_time;
	}

	public void setPay_time(String pay_time) {
		this.pay_time = pay_time;
	}

	public String getConsign_time() {
		return consign_time;
	}

	public void setConsign_time(String consign_time) {
		this.consign_time = consign_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBuyer_message() {
		return buyer_message;
	}

	public void setBuyer_message(String buyer_message) {
		this.buyer_message = buyer_message;
	}

}
