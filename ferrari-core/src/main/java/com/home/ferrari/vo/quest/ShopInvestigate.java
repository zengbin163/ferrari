package com.home.ferrari.vo.quest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ShopInvestigate implements Serializable {

	private static final long serialVersionUID = 1280875460368751196L;
	private Integer id;
	private Integer shopInvestigateId;
	private Integer shopId;
	private Integer investigateId;
	private Integer operatorId;
	private Integer msgType;// @MsgTypeEnum
	private Integer subMsgType;//@SubMsgTypeEnum
	private Integer isReply;  // 是否反馈
	private Integer isRead;  // 是否阅读  0未阅读  1已阅读
	private Integer isAgree; // 是否同意  0:不同意   1:同意
	private BigDecimal shopPrice;
	private String replyTitle;
	private String replyText;

	private String replyName;
	private Date replyTime;
	private String shopName;
	private String shopAddress;

	private String title;
	private String text;
	private String templateTitle;
	private String templateText;
	
	public ShopInvestigate() {

	}

	public ShopInvestigate(Integer shopId, Integer investigateId,
			Integer operatorId, Integer msgType,Integer subMsgType, Integer isRead,
			Integer isAgree, BigDecimal shopPrice, String replyTitle,
			String replyText, Date replyTime, String title, String text) {
		this.shopId = shopId;
		this.investigateId = investigateId;
		this.operatorId = operatorId;
		this.msgType = msgType;
		this.subMsgType = subMsgType;
		this.isRead = isRead;
		this.isAgree = isAgree;
		this.shopPrice = shopPrice;
		this.replyTitle = replyTitle;
		this.replyText = replyText;
		this.replyTime = replyTime;
		this.title = title;
		this.text = text;
	}

	
	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}

	public BigDecimal getShopPrice() {
		return shopPrice;
	}

	public void setShopPrice(BigDecimal shopPrice) {
		this.shopPrice = shopPrice;
	}

	public String getReplyText() {
		return replyText;
	}

	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReplyName() {
		return replyName;
	}

	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public String getReplyTitle() {
		return replyTitle;
	}

	public void setReplyTitle(String replyTitle) {
		this.replyTitle = replyTitle;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Integer getInvestigateId() {
		return investigateId;
	}

	public void setInvestigateId(Integer investigateId) {
		this.investigateId = investigateId;
	}

	public Integer getShopInvestigateId() {
		return shopInvestigateId;
	}

	public void setShopInvestigateId(Integer shopInvestigateId) {
		this.shopInvestigateId = shopInvestigateId;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	public Integer getSubMsgType() {
		return subMsgType;
	}

	public void setSubMsgType(Integer subMsgType) {
		this.subMsgType = subMsgType;
	}

	public String getTemplateText() {
		return templateText;
	}

	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}

	public Integer getIsReply() {
		return isReply;
	}

	public void setIsReply(Integer isReply) {
		this.isReply = isReply;
	}
}
