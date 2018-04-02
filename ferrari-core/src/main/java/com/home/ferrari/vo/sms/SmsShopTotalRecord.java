package com.home.ferrari.vo.sms;

import java.io.Serializable;
import java.util.Date;

public class SmsShopTotalRecord implements Serializable {

	private static final long serialVersionUID = -7224423947003534094L;

	private Integer id;
	private Integer adminId; // 管理员id，代表crm门店
	private Long totalNum; // 从星奥一次购买短信数
	private Date createTime;

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

	public Long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Long totalNum) {
		this.totalNum = totalNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
