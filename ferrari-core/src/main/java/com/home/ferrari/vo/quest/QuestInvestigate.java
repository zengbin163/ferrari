package com.home.ferrari.vo.quest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 问卷调查
 * @author zengbin
 *
 */
public class QuestInvestigate implements Serializable {
	private static final long serialVersionUID = 4047128211053784634L;
	private Integer id;
	private Integer questId;
	private Integer operatorId;// 发起调研人id
	private Date beginTime;
	private Date endTime;
	private Date createTime;
	private Integer msgType;//消息类型  1调研消息 2普通(平台)消息 3合同消息 4培训消息
	
	private BigDecimal price;//星奥参考价
	
	private String title;//消息标题
	private String text;//消息内容
	private String templateTitle;//问卷模板名称
	private String templateText;//问卷模板名称

	public QuestInvestigate() {

	}

	public QuestInvestigate(Integer questId, Integer operatorId,
			Date beginTime, Date endTime, Integer msgType, String title,
			String text) {
		this.questId = questId;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.operatorId = operatorId;
		this.msgType = msgType;
		this.title = title;
		this.text = text;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestId() {
		return questId;
	}

	public void setQuestId(Integer questId) {
		this.questId = questId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
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

	public String getTemplateText() {
		return templateText;
	}

	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}
}
