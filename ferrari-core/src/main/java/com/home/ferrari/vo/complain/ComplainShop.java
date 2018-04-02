package com.home.ferrari.vo.complain;

import java.io.Serializable;
import java.util.Date;

public class ComplainShop implements Serializable {

	private static final long serialVersionUID = 428878037799451028L;

	private Integer id;
	private Integer operatorId;
	private Integer shopId;
	private String remark;
	private Date createTime;
	private Date modifyTime;
	
	private String shopName;
	private String dayLinker;// 日常业务联系人（门头）        ----门店对接人
	private String phone;// 门店24小时服务电话

	private Integer isRemind;
	private Date remindTime;
	
	public ComplainShop() {

	}

	public ComplainShop(Integer operatorId, Integer shopId, String remark) {
		this.operatorId = operatorId;
		this.shopId = shopId;
		this.remark = remark;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDayLinker() {
		return dayLinker;
	}

	public void setDayLinker(String dayLinker) {
		this.dayLinker = dayLinker;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
