package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信发送记录
 * 
 * @author zengbin
 *
 */
public class SmsSendRecord implements Serializable {

	private static final long serialVersionUID = 6689331847374184007L;

	private String groupCode;// 分组code
	private Date createTime;
	private String date; // 日期
	private String type;// 类型，比如：短信
	private Integer targetPhoneNum;// 目标号码
	private String senderLoginNo;// 发送人账号
	private String senderUserName;// 发送者姓名
	private String sendContent;// 发送内容
	private Integer markNum;// 扣费条数
	private Integer smsType;// 短信发送类型 1通知 2营销
	private Integer crmUserId;// 发送者id
	private Integer adminId;

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getTargetPhoneNum() {
		return targetPhoneNum;
	}

	public void setTargetPhoneNum(Integer targetPhoneNum) {
		this.targetPhoneNum = targetPhoneNum;
	}

	public String getSenderLoginNo() {
		return senderLoginNo;
	}

	public void setSenderLoginNo(String senderLoginNo) {
		this.senderLoginNo = senderLoginNo;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public Integer getMarkNum() {
		return markNum;
	}

	public void setMarkNum(Integer markNum) {
		this.markNum = markNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}

	public Integer getSmsType() {
		return smsType;
	}

	public void setSmsType(Integer smsType) {
		this.smsType = smsType;
	}

	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
}
