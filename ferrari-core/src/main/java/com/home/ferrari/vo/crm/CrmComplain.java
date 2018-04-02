package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;

public class CrmComplain implements Serializable {

	private static final long serialVersionUID = 49786982681965458L;
	private Integer id;
	private String crmShopName;
	private Integer operatorId;
	private String operatorName;
	private String operatorPhone;
	private String remark;
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCrmShopName() {
		return crmShopName;
	}

	public void setCrmShopName(String crmShopName) {
		this.crmShopName = crmShopName;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
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

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorPhone() {
		return operatorPhone;
	}

	public void setOperatorPhone(String operatorPhone) {
		this.operatorPhone = operatorPhone;
	}
}
