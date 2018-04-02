package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 短信发送详情
 * 
 * @author zengbin
 *
 */
public class SmsSendRecordDetail implements Serializable {

	private static final long serialVersionUID = 6689331847374184007L;

	private String groupCode;// 分组编码
	private String senderLoginNo;// 发送人账号
	private String senderUserName;// 发送者姓名
	private String sendDate; // 发送日期
	private Date createTime;
	private String sendContent;// 发送内容
	private String mobile;// 联系电话
	private Integer sendNum;// 发送数量
	private Integer markNum;// 扣费条数
	private Integer smsType;// 短信发送类型 1通知 2营销
	private Integer crmUserId;// 发送者id
	private Integer adminId;

	private List<Map<String, Object>> targetList; // 发送目标号码
	private Integer targetSum; // 发送目标号码总数

	public String getSenderLoginNo() {
		return senderLoginNo;
	}

	public void setSenderLoginNo(String senderLoginNo) {
		this.senderLoginNo = senderLoginNo;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendContent() {
		return sendContent;
	}

	public void setSendContent(String sendContent) {
		this.sendContent = sendContent;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getSendNum() {
		return sendNum;
	}

	public void setSendNum(Integer sendNum) {
		this.sendNum = sendNum;
	}

	public Integer getMarkNum() {
		return markNum;
	}

	public void setMarkNum(Integer markNum) {
		this.markNum = markNum;
	}

	public List<Map<String, Object>> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<Map<String, Object>> targetList) {
		this.targetList = targetList;
	}

	public Integer getTargetSum() {
		return targetSum;
	}

	public void setTargetSum(Integer targetSum) {
		this.targetSum = targetSum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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

	public String getSenderUserName() {
		return senderUserName;
	}

	public void setSenderUserName(String senderUserName) {
		this.senderUserName = senderUserName;
	}
}
