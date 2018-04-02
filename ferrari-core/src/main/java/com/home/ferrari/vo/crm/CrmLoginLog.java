/*
 * @Project: GZJK
 * @Author: zengbin
 * @Date: 2015年10月28日
 * @Copyright: 2000-2015 CMCC . All rights reserved.
 */
package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;

public class CrmLoginLog implements Serializable {

	private static final long serialVersionUID = 671802634824244897L;
	private Integer id;
	private Integer crmUserId;
	private String loginNo;
	private String sessionId; // 服务端生成返回给前台，用于存放用户会话信息
	private String loginIp;
	private Date loginTime;
	
	public CrmLoginLog() {
		
	}

	public CrmLoginLog(Integer crmUserId, String loginNo, String sessionId, String loginIp) {
		this.crmUserId = crmUserId;
		this.loginNo = loginNo;
		this.sessionId = sessionId;
		this.loginIp = loginIp;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getCrmUserId() {
		return crmUserId;
	}

	public void setCrmUserId(Integer crmUserId) {
		this.crmUserId = crmUserId;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
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
