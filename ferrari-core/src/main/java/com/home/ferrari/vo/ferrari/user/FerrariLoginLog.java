/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.vo.ferrari.user;

import java.io.Serializable;
import java.util.Date;

public class FerrariLoginLog implements Serializable {

	private static final long serialVersionUID = 671802634824244897L;
	private Integer id;
	private Integer userId;
	private String mobile;
	private String sessionId; // 服务端生成返回给前台，用于存放用户会话信息
	private String loginIp;
	private Date loginTime;
	
	public FerrariLoginLog() {
		
	}

	public FerrariLoginLog(Integer userId, String mobile, String sessionId, String loginIp) {
		this.userId = userId;
		this.mobile = mobile;
		this.sessionId = sessionId;
		this.loginIp = loginIp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
