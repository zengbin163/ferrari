package com.home.ferrari.vo.quest;

import java.io.Serializable;
import java.util.Date;

public class QuestInvestigateCount implements Serializable {

	private static final long serialVersionUID = 1739751051112659721L;
	private Integer shopInvestigateId;//门店问卷id
	private Integer investigateId;//问卷id
	private Integer questId; //问卷模板id
	private Integer shopId; //门店id
	private String title;//消息标题
	private String text;//消息内容
	private String templateTitle;//模板标题
	private String templateText;//模板内容
	private String replyTitle;//反馈标题
	private String replyText;//反馈内容
	private Date createTime;
	private Date endTime;
	private Integer operatorId;
	private String operatorName;
	private String replyName;
	private Integer msgType;
	private Integer isReply;//是否回复  0未回复  1已回复
	private Integer isAgree;//是否回复  0未回复  1已回复
	private Integer isRead;
	private Integer agree; //同意数量
	private Integer reply;//反馈数量
	private Integer noReply;//未反馈数量
	private Integer isExpire; //是否过期 0未过期  1已过期
	private Integer questType;
	private Integer noRead;//未阅读数量
	private Integer read;//已阅读数量

	public QuestInvestigateCount() {

	}

	public String getTemplateText() {
		return templateText;
	}

	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public Integer getShopInvestigateId() {
		return shopInvestigateId;
	}

	public void setShopInvestigateId(Integer shopInvestigateId) {
		this.shopInvestigateId = shopInvestigateId;
	}

	public Integer getInvestigateId() {
		return investigateId;
	}

	public void setInvestigateId(Integer investigateId) {
		this.investigateId = investigateId;
	}

	public Integer getQuestId() {
		return questId;
	}

	public void setQuestId(Integer questId) {
		this.questId = questId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getAgree() {
		return agree;
	}

	public void setAgree(Integer agree) {
		this.agree = agree;
	}

	public Integer getReply() {
		return reply;
	}

	public void setReply(Integer reply) {
		this.reply = reply;
	}
	
	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getNoReply() {
		return noReply;
	}

	public void setNoReply(Integer noReply) {
		this.noReply = noReply;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getIsExpire() {
		return isExpire;
	}

	public void setIsExpire(Integer isExpire) {
		this.isExpire = isExpire;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public Integer getIsReply() {
		return isReply;
	}

	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getQuestType() {
		return questType;
	}

	public void setQuestType(Integer questType) {
		this.questType = questType;
	}

	public Integer getNoRead() {
		return noRead;
	}

	public void setNoRead(Integer noRead) {
		this.noRead = noRead;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}
}
