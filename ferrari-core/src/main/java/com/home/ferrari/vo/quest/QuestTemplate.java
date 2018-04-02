package com.home.ferrari.vo.quest;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class QuestTemplate implements Serializable {

	private static final long serialVersionUID = -61839591870610845L;

	private Integer id;
	private String title;
	private String richText;
	private BigDecimal price;
	private Integer tempType;//消息模板  1问卷调研 2合同模板 3培训模板
	private Integer questType;//0 新产品问卷 1 已上线产品问卷
	private Integer operatorId;
	private String operatorName;
	private Integer flag; // 是否有效 0无效 1有效
	private Date createTime;
	private Date modifyTime;

	public QuestTemplate() {

	}

	public QuestTemplate(String title, String richText, BigDecimal price,
			Integer tempType, Integer questType, Integer operatorId,
			Integer flag) {
		this.title = title;
		this.richText = richText;
		this.price = price;
		this.tempType = tempType;
		this.questType = questType;
		this.operatorId = operatorId;
		this.flag = flag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRichText() {
		return richText;
	}

	public void setRichText(String richText) {
		this.richText = richText;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getQuestType() {
		return questType;
	}

	public void setQuestType(Integer questType) {
		this.questType = questType;
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getTempType() {
		return tempType;
	}

	public void setTempType(Integer tempType) {
		this.tempType = tempType;
	}
}
