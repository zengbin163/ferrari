package com.home.ferrari.web.site.ferrari;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.service.ferrari.FerrariUserService;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.MD5Util;
import com.home.ferrari.util.WebUtil;
import com.home.ferrari.vo.ferrari.user.FerrariUser;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/ferrari")
public class FerrariUserAction extends BaseAction {

	private static final long serialVersionUID = 7154841574244870282L;
	
	@Autowired
	private FerrariUserService ferrariUserService;
	@Autowired
	private CrmUserService crmUserService;

	@RequestMapping("login")
	@ResponseBody
	@LoginRequired(needLogin=false)
	public Map<String, Object> login() {
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		String password = MD5Util.md5(this.getFilteredParameter(request, "password", 0, null));
		return this.ferrariUserService.login(mobile, password, getIpAddr(), response);
	}

	@RequestMapping("logout")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> logout() {
		String sessionId = this.getFilteredParameter(request, "sessionId", 0, null);
		return this.ferrariUserService.logout(sessionId);
	}

	@RequestMapping("password")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> password() {
		String mobile = this.getFerrariUser().getMobile();
		String oldPass = MD5Util.md5(this.getFilteredParameter(request, "oldPass", 0, null));
		String newPass = MD5Util.md5(this.getFilteredParameter(request, "newPass", 0, null));
		return this.ferrariUserService.password(mobile, oldPass, newPass);
	}

	@RequestMapping("add")
	@ResponseBody
	@LoginRequired(needLogin = true, needAuth=true)
	public Map<String, Object> add() {
		String roleIds = this.getFilteredParameter(request, "roleIds", 0, null);//用户名
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);//用户名
		String password = this.getFilteredParameter(request, "password", 0, null);//密码
		String nickName = WebUtil.decode(this.getFilteredParameter(request, "nickName", 0, null));//姓名
		String idCard = this.getFilteredParameter(request, "idCard", 0, null);
		String address = this.getFilteredParameter(request, "address", 0, null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String phone = this.getFilteredParameter(request, "phone", 0, null);//电话或者手机号码
		Integer proxyType = this.getIntParameter(request, "proxyType", null);
		String proxyCompany = this.getFilteredParameter(request, "proxyCompany", 0, null);
		BigDecimal proxyGuarantee = this.getDecimalParameter(request, "proxyGuarantee", null);
		String proxyTarget = this.getFilteredParameter(request, "proxyTarget", 0, null);
		String proxySign = this.getFilteredParameter(request, "proxySign", 0, null);
		String proxyName = this.getFilteredParameter(request, "proxyName", 0, null);
		Integer proxyPost = this.getIntParameter(request, "proxyPost", null);
		String proxyWeixin = this.getFilteredParameter(request, "proxyWeixin", 0, null);
		String proxyEmail = this.getFilteredParameter(request, "proxyEmail", 0, null);
		String proxyCardPhoto1 = this.getFilteredParameter(request, "proxyCardPhoto1", 0, null);
		String proxyCardPhoto2 = this.getFilteredParameter(request, "proxyCardPhoto2", 0, null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		FerrariUser ferrariUser = new FerrariUser();
		ferrariUser.setMobile(mobile);
		String pass = StringUtils.isEmpty(password)?"111111":password;
		ferrariUser.setPassword(MD5Util.md5(pass));
		ferrariUser.setpPassword(pass);
		ferrariUser.setNickName(nickName);
		ferrariUser.setIdCard(idCard);
		ferrariUser.setAddress(address);
		ferrariUser.setProvince(province);
		ferrariUser.setCity(city);
		ferrariUser.setPhone(phone);
		ferrariUser.setProxyType(proxyType);
		ferrariUser.setProxyCompany(proxyCompany);
		ferrariUser.setProxyGuarantee(proxyGuarantee);
		ferrariUser.setProxyTarget(proxyTarget);
		ferrariUser.setProxySign(DateUtil.defaultParseDate(proxySign));
		ferrariUser.setProxyName(proxyName);
		ferrariUser.setProxyPost(proxyPost);
		ferrariUser.setProxyWeixin(proxyWeixin);
		ferrariUser.setProxyEmail(proxyEmail);
		ferrariUser.setProxyCardPhoto1(proxyCardPhoto1);
		ferrariUser.setProxyCardPhoto2(proxyCardPhoto2);
		ferrariUser.setRemark(remark);
		return this.ferrariUserService.createUser(ferrariUser, roleIds);
	}

	@RequestMapping("edit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> edit() {
		Integer userId = this.getIntParameter(request, "userId", null);
		String roleIds = this.getFilteredParameter(request, "roleIds", 0, null);//用户名
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);//用户名
		String password = this.getFilteredParameter(request, "password", 0, null);//密码
		String nickName = WebUtil.decode(this.getFilteredParameter(request, "nickName", 0, null));//姓名
		String idCard = this.getFilteredParameter(request, "idCard", 0, null);
		String address = this.getFilteredParameter(request, "address", 0, null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String phone = this.getFilteredParameter(request, "phone", 0, null);//电话或者手机号码
		Integer proxyType = this.getIntParameter(request, "proxyType", null);
		String proxyCompany = this.getFilteredParameter(request, "proxyCompany", 0, null);
		BigDecimal proxyGuarantee = this.getDecimalParameter(request, "proxyGuarantee", null);
		String proxyTarget = this.getFilteredParameter(request, "proxyTarget", 0, null);
		String proxySign = this.getFilteredParameter(request, "proxySign", 0, null);
		String proxyName = this.getFilteredParameter(request, "proxyName", 0, null);
		Integer proxyPost = this.getIntParameter(request, "proxyPost", null);
		String proxyWeixin = this.getFilteredParameter(request, "proxyWeixin", 0, null);
		String proxyEmail = this.getFilteredParameter(request, "proxyEmail", 0, null);
		String proxyCardPhoto1 = this.getFilteredParameter(request, "proxyCardPhoto1", 0, null);
		String proxyCardPhoto2 = this.getFilteredParameter(request, "proxyCardPhoto2", 0, null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		FerrariUser ferrariUser = new FerrariUser();
		ferrariUser.setId(userId);
		ferrariUser.setMobile(mobile);
		if(!StringUtils.isEmpty(password)){
			ferrariUser.setPassword(MD5Util.md5(password));
			ferrariUser.setpPassword(password);
		}
		ferrariUser.setNickName(nickName);
		ferrariUser.setIdCard(idCard);
		ferrariUser.setAddress(address);
		ferrariUser.setProvince(province);
		ferrariUser.setCity(city);
		ferrariUser.setPhone(phone);
		ferrariUser.setProxyType(proxyType);
		ferrariUser.setProxyCompany(proxyCompany);
		ferrariUser.setProxyGuarantee(proxyGuarantee);
		ferrariUser.setProxyTarget(proxyTarget);
		ferrariUser.setProxySign(DateUtil.defaultParseDate(proxySign));
		ferrariUser.setProxyName(proxyName);
		ferrariUser.setProxyPost(proxyPost);
		ferrariUser.setProxyWeixin(proxyWeixin);
		ferrariUser.setProxyEmail(proxyEmail);
		ferrariUser.setProxyCardPhoto1(proxyCardPhoto1);
		ferrariUser.setProxyCardPhoto2(proxyCardPhoto2);
		ferrariUser.setRemark(remark);
		return this.ferrariUserService.editFerrariUser(ferrariUser, roleIds);
	}
	
	@RequestMapping("resetPass")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> resetPass() {
		Integer userId = this.getIntParameter(request, "userId", null);
		FerrariUser ferrariUser = new FerrariUser();
		ferrariUser.setId(userId);
		ferrariUser.setPassword(MD5Util.md5("111111"));
		ferrariUser.setpPassword("111111");
		return this.ferrariUserService.updateFerrariUser(ferrariUser);
	}

	@RequestMapping("activeOrInactive")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> activeOrInactive() {
		Integer userId = this.getIntParameter(request, "userId", null);
		Integer isActive = this.getIntParameter(request, "isActive", null);
		Assert.notNull(isActive, "激活状态不能为空，1表示已激活，0表示已冻结");
		FerrariUser ferrariUser = new FerrariUser();
		ferrariUser.setId(userId);
		ferrariUser.setIsActive(isActive);
		Assert.notNull(userId, "用户id不能为空");
		Assert.notNull(isActive, "isActive不能为空");
		return this.ferrariUserService.updateFerrariUser(ferrariUser);
	}

	@RequestMapping("userDetail")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> userDetail() {
		Integer id = this.getIntParameter(request, "userId", null);
		return this.ferrariUserService.getFerrariUserById(id);
	}

	@RequestMapping("userList")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> userList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		String nickName = WebUtil.decode(this.getFilteredParameter(request, "nickName", 0, null));
		Integer roleType = this.getIntParameter(request, "roleType", null);
		return this.ferrariUserService.getFerrariUserList(pageOffset, pageSize, mobile, nickName, roleType);
	}

	@RequestMapping("userMenuList")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> userMenuList() {
		return this.ferrariUserService.getFerrariUserMenuList(this.getFerrariUser().getId());
	}
	
	@RequestMapping("userProxyList")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> userProxyList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		return this.ferrariUserService.getFerrariProxyUserList(pageOffset, pageSize, province, mobile);
	}
	
	@RequestMapping("userProxyDetail")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> userProxyDetail() {
		Integer id = this.getIntParameter(request, "userId", null);
		return this.ferrariUserService.getFerrariUserProxyById(id);
	}
	
	@RequestMapping("proxyExists")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> proxyExists() {
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		return this.ferrariUserService.validateFerrariUserExists(province, city);
	}
	
	@RequestMapping("crmUserList")
	@ResponseBody
	@LoginRequired(needLogin=true)
	public Map<String, Object> crmUserList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		return this.crmUserService.getCrmUserList(pageOffset, pageSize, null, null, province, city, shopName);
	}

}
