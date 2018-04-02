package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.util.Date;

public class SmsRecord implements Serializable {

	private static final long serialVersionUID = -7224423947003534094L;

	private Integer id;
	private Integer crmUserId; // 门店发送者
	private Integer customerId; // 客户接收者
	private String groupCode;// 分组id，发送一组
	private Integer groupSeed;// 分组因子，比如长短信拆分几条
	private String smsContent;// 发送内容
	private Integer adminId; // 门店管理员id
	private String receiveMobile;// 收件人手机号码
	private Integer smsType;// 短信发送类型 1通知 2营销
	private Date createTime;
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public String getReceiveMobile() {
		return receiveMobile;
	}

	public void setReceiveMobile(String receiveMobile) {
		this.receiveMobile = receiveMobile;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Integer getSmsType() {
		return smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public Integer getGroupSeed() {
		return groupSeed;
	}

	public void setGroupSeed(Integer groupSeed) {
		this.groupSeed = groupSeed;
	}
}
