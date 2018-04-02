package com.home.ferrari.vo.quest;

import java.io.Serializable;
import java.util.Date;

public class CompanyMessage implements Serializable {

	private static final long serialVersionUID = 1703707050046443927L;

	private Integer id;
	private String title;
	private String text;
	private Integer operatorId;
	private Integer ceoId;
	private Date createTime;
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCeoId() {
		return ceoId;
	}

	public void setCeoId(Integer ceoId) {
		this.ceoId = ceoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
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

}
