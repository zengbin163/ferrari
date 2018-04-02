package com.home.ferrari.plugin.session;

import java.io.Serializable;

import com.home.ferrari.enums.PlatformEnum;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.vo.tesla.user.TeslaUser;

public class Session implements Serializable {

	private static final long serialVersionUID = 7680449964098679585L;

	private String mobile;
	private Integer roleType;
	private PlatformEnum platform;
	private FerrariUser ferrariUser;
	private TeslaUser teslaUser;
	private String loginNo;
	private CrmUser crmUser;

	public Session() {
	}

	public Session(String mobile, PlatformEnum platform , FerrariUser ferrariUser, Integer roleType) {
		this.mobile = mobile;
		this.platform = platform;
		this.ferrariUser = ferrariUser;
		this.roleType = roleType;
	}

	public Session(String loginNo, PlatformEnum platform , CrmUser crmUser, Integer roleType) {
		this.loginNo = loginNo;
		this.platform = platform;
		this.crmUser = crmUser;
		this.roleType = roleType;
	}

	public Session(String mobile, PlatformEnum platform , TeslaUser teslaUser) {
		this.mobile = mobile;
		this.platform = platform;
		this.teslaUser = teslaUser;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public FerrariUser getFerrariUser() {
		return ferrariUser;
	}

	public void setFerrariUser(FerrariUser ferrariUser) {
		this.ferrariUser = ferrariUser;
	}

	public TeslaUser getTeslaUser() {
		return teslaUser;
	}

	public void setTeslaUser(TeslaUser teslaUser) {
		this.teslaUser = teslaUser;
	}

	public PlatformEnum getPlatform() {
		return platform;
	}

	public void setPlatform(PlatformEnum platform) {
		this.platform = platform;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public CrmUser getCrmUser() {
		return crmUser;
	}

	public void setCrmUser(CrmUser crmUser) {
		this.crmUser = crmUser;
	}
}
