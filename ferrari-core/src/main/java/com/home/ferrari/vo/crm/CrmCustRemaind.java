package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;

public class CrmCustRemaind implements Serializable {

	private static final long serialVersionUID = 3758281769337010745L;
	private Integer id;
	private Integer crmUserId;
	private Integer crmCustomerId;
	private Integer isRemind; // 是否提醒 0不提醒 1提醒
	private Date remindTime;// 提醒时间
	private Date createTime;
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCrmCustomerId() {
		return crmCustomerId;
	}

	public void setCrmCustomerId(Integer crmCustomerId) {
		this.crmCustomerId = crmCustomerId;
	}

	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
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

	public Integer getIsRemind() {
		return isRemind;
	}

	public void setIsRemind(Integer isRemind) {
		this.isRemind = isRemind;
	}

	public Date getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(Date remindTime) {
		this.remindTime = remindTime;
	}
}
