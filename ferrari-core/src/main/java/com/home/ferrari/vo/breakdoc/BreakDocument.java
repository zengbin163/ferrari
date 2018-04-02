package com.home.ferrari.vo.breakdoc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class BreakDocument implements Serializable {

	private static final long serialVersionUID = -3908337909654512702L;

	private Integer breakId;
	private Integer shopId;
	private String title;// 故障单标题
	private Integer docStatus;// 故障单审批状态
	private String carModel;// 车型
	private String carPart;// 配件
	private String remark;// 摘要
	private String text;// 富文本内容
	private Date createTime;
	private Date modifyTime;
	
	private List<Map<String,Object>> listMap;//故障分类三级菜单
	
	private String breakTypeList;// 多个故障三级分类id（叶子节点）
	
	public BreakDocument() {

	}

	public BreakDocument(Integer breakId, Integer docStatus) {
		this.breakId = breakId;
		this.docStatus = docStatus;
	}

	public BreakDocument(Integer breakId, Integer shopId, String title,
			String breakTypeList, Integer docStatus, String carModel,
			String carPart, String remark, String text) {
		this.breakId = breakId;
		this.shopId = shopId;
		this.title = title;
		this.breakTypeList = breakTypeList;
		this.docStatus = docStatus;
		this.carModel = carModel;
		this.carPart = carPart;
		this.remark = remark;
		this.text = text;
	}

	public Integer getBreakId() {
		return breakId;
	}

	public void setBreakId(Integer breakId) {
		this.breakId = breakId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDocStatus() {
		return docStatus;
	}

	public void setDocStatus(Integer docStatus) {
		this.docStatus = docStatus;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarPart() {
		return carPart;
	}

	public void setCarPart(String carPart) {
		this.carPart = carPart;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Map<String, Object>> getListMap() {
		return listMap;
	}

	public void setListMap(List<Map<String, Object>> listMap) {
		this.listMap = listMap;
	}

	public String getBreakTypeList() {
		return breakTypeList;
	}

	public void setBreakTypeList(String breakTypeList) {
		this.breakTypeList = breakTypeList;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}
	
}
