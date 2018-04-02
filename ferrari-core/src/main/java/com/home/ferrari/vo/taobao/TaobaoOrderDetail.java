package com.home.ferrari.vo.taobao;

import java.io.Serializable;

public class TaobaoOrderDetail implements Serializable {

	private static final long serialVersionUID = -2242920087602677124L;

	private String oid;
	private String status;
	private String title;
	private String et_shop_name;
	private Integer num;
	private String payment;
	private String price;
	private String total_fee;
	private String discount_fee;
	private String pic_path;
	private String logistics_company;
	private String invoice_no;

	public String getDiscount_fee() {
		return discount_fee;
	}

	public void setDiscount_fee(String discount_fee) {
		this.discount_fee = discount_fee;
	}

	public String getEt_shop_name() {
		return et_shop_name;
	}

	public void setEt_shop_name(String et_shop_name) {
		this.et_shop_name = et_shop_name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPic_path() {
		return pic_path;
	}

	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}

	public String getLogistics_company() {
		return logistics_company;
	}

	public void setLogistics_company(String logistics_company) {
		this.logistics_company = logistics_company;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

}
