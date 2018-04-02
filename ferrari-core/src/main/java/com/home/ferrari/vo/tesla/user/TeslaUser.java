/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.vo.tesla.user;

import java.io.Serializable;
import java.util.Date;

public class TeslaUser implements Serializable {

	private static final long serialVersionUID = 5170093482512533151L;

	private Integer id;
	private Integer shopId;// 门店id
	private String nickName;
	private String mobile;
	private String password;
	private Date registerTime;
	private Date modifyTime;
	private String headerUrl;
	private String signature;
	private Integer isActive; // 是否被激活 0:未激活 1:已激活
	private Integer version;
	private String sessionId; // 服务端生成返回给前台，用于存放用户会话信息
	private Integer isLinkCrm;// 是否关联CRM账号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getIsLinkCrm() {
		return isLinkCrm;
	}

	public void setIsLinkCrm(Integer isLinkCrm) {
		this.isLinkCrm = isLinkCrm;
	}
}
