package com.home.ferrari.vo.account;

import java.io.Serializable;
import java.util.Date;

public class AccountUpload implements Serializable {

	private static final long serialVersionUID = 5090013550259484269L;

	private Integer id;
	private String finalNo;
	private Integer success;
	private Integer fail;
	private String detail;
	private Date createTime;

	public AccountUpload() {

	}

	public AccountUpload(String finalNo, Integer success, Integer fail,
			String detail) {
		this.finalNo = finalNo;
		this.success = success;
		this.fail = fail;
		this.detail = detail;
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

	public Integer getSuccess() {
		return success;
	}

	public void setSuccess(Integer success) {
		this.success = success;
	}

	public Integer getFail() {
		return fail;
	}

	public void setFail(Integer fail) {
		this.fail = fail;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
