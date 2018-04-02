package com.home.ferrari.vo.ferrari.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FerrariUser implements Serializable {

	private static final long serialVersionUID = 4054932723225987649L;
	private Integer id;
	private String nickName;// 姓名
	private String mobile;// 登录账号
	private String password;
	private String pPassword;// 密码明文
	private String idCard; // 身份证号码
	private Integer isAdmin;
	private String province;
	private String city;
	private String phone;// 联系电话
	private String address;// 地址
	private Date registerTime;// 账号注册时间
	private Date modifyTime;
	private String headerUrl;// 头像地址
	private String signature;// 签名
	private Integer isActive; // 是否被激活 0:未激活 1:已激活
	private Integer version;
	private Integer proxyType;// 代理类型(1市代理，2省代理)
	private String proxyCompany;// 代理公司名称
	private BigDecimal proxyGuarantee;// 代理保证金
	private String proxyTarget;// 拓展目标（门店数）
	private Date proxySign;// 代理签订时间
	private String proxyName;// 公司人员姓名
	private Integer proxyPost;// 岗位（1总负责人，2拓展，3客服）
	private String proxyWeixin;// 微信号
	private String proxyEmail;//email
	private String proxyCardPhoto1;// 身份证正面照片
	private String proxyCardPhoto2;// 身份证反面照片
	private String remark;// 备注

	private String sessionId; // 服务端生成返回给前台，用于存放用户会话信息
	private String roleName;// 角色名称
	private List<FerrariRoleMenu> ferrariRoleMenuList;
	private Integer roleType;// 角色类型

	private Integer citySum;// 省代理下面的市代理数量
	private Integer shopSum;// 市代理下面的门店数量

	public FerrariUser() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getpPassword() {
		return pPassword;
	}

	public void setpPassword(String pPassword) {
		this.pPassword = pPassword;
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

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
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

	public String getHeaderUrl() {
		return headerUrl;
	}

	public void setHeaderUrl(String headerUrl) {
		this.headerUrl = headerUrl;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<FerrariRoleMenu> getFerrariRoleMenuList() {
		return ferrariRoleMenuList;
	}

	public void setFerrariRoleMenuList(List<FerrariRoleMenu> ferrariRoleMenuList) {
		this.ferrariRoleMenuList = ferrariRoleMenuList;
	}

	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCitySum() {
		return citySum;
	}

	public void setCitySum(Integer citySum) {
		this.citySum = citySum;
	}

	public Integer getShopSum() {
		return shopSum;
	}

	public void setShopSum(Integer shopSum) {
		this.shopSum = shopSum;
	}

	public Integer getProxyType() {
		return proxyType;
	}

	public void setProxyType(Integer proxyType) {
		this.proxyType = proxyType;
	}

	public String getProxyCompany() {
		return proxyCompany;
	}

	public void setProxyCompany(String proxyCompany) {
		this.proxyCompany = proxyCompany;
	}

	public BigDecimal getProxyGuarantee() {
		return proxyGuarantee;
	}

	public void setProxyGuarantee(BigDecimal proxyGuarantee) {
		this.proxyGuarantee = proxyGuarantee;
	}

	public String getProxyTarget() {
		return proxyTarget;
	}

	public void setProxyTarget(String proxyTarget) {
		this.proxyTarget = proxyTarget;
	}

	public Date getProxySign() {
		return proxySign;
	}

	public void setProxySign(Date proxySign) {
		this.proxySign = proxySign;
	}

	public String getProxyName() {
		return proxyName;
	}

	public void setProxyName(String proxyName) {
		this.proxyName = proxyName;
	}

	public Integer getProxyPost() {
		return proxyPost;
	}

	public void setProxyPost(Integer proxyPost) {
		this.proxyPost = proxyPost;
	}

	public String getProxyWeixin() {
		return proxyWeixin;
	}

	public void setProxyWeixin(String proxyWeixin) {
		this.proxyWeixin = proxyWeixin;
	}

	public String getProxyCardPhoto1() {
		return proxyCardPhoto1;
	}

	public void setProxyCardPhoto1(String proxyCardPhoto1) {
		this.proxyCardPhoto1 = proxyCardPhoto1;
	}

	public String getProxyCardPhoto2() {
		return proxyCardPhoto2;
	}

	public void setProxyCardPhoto2(String proxyCardPhoto2) {
		this.proxyCardPhoto2 = proxyCardPhoto2;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getProxyEmail() {
		return proxyEmail;
	}

	public void setProxyEmail(String proxyEmail) {
		this.proxyEmail = proxyEmail;
	}

}
