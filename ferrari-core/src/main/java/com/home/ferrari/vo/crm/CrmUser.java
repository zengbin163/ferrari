package com.home.ferrari.vo.crm;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CrmUser implements Serializable {

	private static final long serialVersionUID = -1495274443402447159L;

	private Integer id;
	private String mobile;
	private String loginNo;
	private String password;
	private String userName;
	private String address;
	private Integer adminId;
	private Integer roleId;
	private Integer departmentId;
	private Integer isActive;
	private Integer canSms; //0不能发送短信   1能发送短信
	private Integer shopId;
	private String shopName;
	private String shopCompany;
	private String shopAddress;
	private String crmShopName;
	private String crmShopCompany;
	private String crmProvince;
	private String crmCity;
	private String crmShopAddress;
	private Date registerTime;
	private Date modifyTime;

	private String sessionId;
	private Integer roleType;
	private String roleName;
	private String adminName; //管理员名称
	private String adminMobile; //管理员手机
	private List<CrmRoleMenu> crmRoleMenuList; //菜单列表
	private List<CrmUserBizType> bizTypeList;//业务类型
	private Integer clerkSum; //管理员下属业务员
	
	public CrmUser() {
		
	}

	public CrmUser(String mobile, String password, String userName,
			Integer roleId, Integer shopId, String shopName,
			String shopCompany, String shopAddress, String crmShopName,
			String crmShopCompany, String crmProvince, String crmCity,
			String crmShopAddress) {
		this.mobile = mobile;
		this.password = password;
		this.userName = userName;
		this.roleId = roleId;
		this.shopId = shopId;
		this.shopName = shopName;
		this.shopCompany = shopCompany;
		this.shopAddress = shopAddress;
		this.crmShopName = crmShopName;
		this.crmShopCompany = crmShopCompany;
		this.crmProvince = crmProvince;
		this.crmCity = crmCity;
		this.crmShopAddress = crmShopAddress;
	}

	public List<CrmUserBizType> getBizTypeList() {
		return bizTypeList;
	}

	public void setBizTypeList(List<CrmUserBizType> bizTypeList) {
		this.bizTypeList = bizTypeList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<CrmRoleMenu> getCrmRoleMenuList() {
		return crmRoleMenuList;
	}

	public void setCrmRoleMenuList(List<CrmRoleMenu> crmRoleMenuList) {
		this.crmRoleMenuList = crmRoleMenuList;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopCompany() {
		return shopCompany;
	}

	public void setShopCompany(String shopCompany) {
		this.shopCompany = shopCompany;
	}

	public String getShopAddress() {
		return shopAddress;
	}

	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminMobile() {
		return adminMobile;
	}

	public void setAdminMobile(String adminMobile) {
		this.adminMobile = adminMobile;
	}

	public String getCrmShopName() {
		return crmShopName;
	}

	public void setCrmShopName(String crmShopName) {
		this.crmShopName = crmShopName;
	}

	public String getCrmShopCompany() {
		return crmShopCompany;
	}

	public void setCrmShopCompany(String crmShopCompany) {
		this.crmShopCompany = crmShopCompany;
	}

	public String getCrmProvince() {
		return crmProvince;
	}

	public void setCrmProvince(String crmProvince) {
		this.crmProvince = crmProvince;
	}

	public String getCrmCity() {
		return crmCity;
	}

	public void setCrmCity(String crmCity) {
		this.crmCity = crmCity;
	}

	public String getCrmShopAddress() {
		return crmShopAddress;
	}

	public void setCrmShopAddress(String crmShopAddress) {
		this.crmShopAddress = crmShopAddress;
	}

	public Integer getCanSms() {
		return canSms;
	}

	public void setCanSms(Integer canSms) {
		this.canSms = canSms;
	}

	public Integer getClerkSum() {
		return clerkSum;
	}

	public void setClerkSum(Integer clerkSum) {
		this.clerkSum = clerkSum;
	}
}
