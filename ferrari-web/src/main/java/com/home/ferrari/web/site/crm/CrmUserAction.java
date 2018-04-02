package com.home.ferrari.web.site.crm;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.CrmRoleTypeEnum;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.util.MD5Util;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.crm.CrmUserBizType;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/crmUser")
public class CrmUserAction extends BaseAction {

	private static final long serialVersionUID = 2477151474792449583L;
	@Autowired
	private CrmUserService crmUserService;
	
	/**
	 * 管理员登录
	 * @return
	 */
	@RequestMapping("login")
	@ResponseBody
	@LoginRequired(needLogin=false)
	public Map<String, Object> login() {
		String loginNo = this.getFilteredParameter(request, "loginNo", 0, null);
		String password = MD5Util.md5(this.getFilteredParameter(request, "password", 0, null));
		return this.crmUserService.login(loginNo, password, getIpAddr(), response);
	}
	
	/**
	 * 根据门店账号和密码校验门店，并返回门店信息
	 * @return
	 */
	@RequestMapping("validateShop")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> validateShop() {
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		String password = MD5Util.md5(this.getFilteredParameter(request, "password", 0, null));
		return this.crmUserService.validateTeslaUser(shopName, password);
	}

	/**
	 * 根据用户id查询用户详情
	 * @return
	 */
	@RequestMapping("userDetail")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> userDetail() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		return ResultUtil.successMap(this.crmUserService.getCrmUserById(crmUserId));
	}

	/**
	 * 管理员注册
	 * @return
	 */
	@RequestMapping("register")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> register() {
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		String password = MD5Util.md5(this.getFilteredParameter(request, "password", 0, null));
		String userName = this.getFilteredParameter(request, "userName", 0, null);
		Integer shopId = this.getIntParameter(request, "shopId", null);
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		String shopCompany = this.getFilteredParameter(request, "shopCompany", 0, null);
		String shopAddress = this.getFilteredParameter(request, "shopAddress", 0, null);
		String crmShopName = this.getFilteredParameter(request, "crmShopName", 0, null);
		List<CrmUser> tempList = this.crmUserService.getCrmUserByCrmShopName(crmShopName);
		if (CollectionUtils.isNotEmpty(tempList)) {
			throw new FerrariBizException(ResultCode.CRM_USER_IS_EXISTS, ResultCode.CRM_USER_IS_EXISTS.getString());
		}
		String crmShopCompany = this.getFilteredParameter(request, "crmShopCompany", 0, null);
		String crmShopAddress = this.getFilteredParameter(request, "crmShopAddress", 0, null);
		String crmProvince = this.getFilteredParameter(request, "crmProvince", 0, null);
		String crmCity = this.getFilteredParameter(request, "crmCity", 0, null);
		Integer roleId = CrmRoleTypeEnum.ROLE_SHOP_ADMIN.getCode();
		CrmUser crmUser = new CrmUser(mobile, password, userName, roleId,
				shopId, shopName, shopCompany, shopAddress, crmShopName,
				crmShopCompany, crmProvince, crmCity, crmShopAddress);
		this.crmUserService.saveCrmUser(crmUser);
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}
	
	/**
	 * 修改管理员信息
	 * @return
	 */
	@RequestMapping("editCrmUser")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> editCrmUser() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		String loginNo = this.getFilteredParameter(request, "loginNo", 0, null);
		String userName = this.getFilteredParameter(request, "userName", 0, null);
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		String crmProvince = this.getFilteredParameter(request, "crmProvince", 0, null);
		String crmCity = this.getFilteredParameter(request, "crmCity", 0, null);
		String crmShopAddress = this.getFilteredParameter(request, "crmShopAddress", 0, null);
		CrmUser crmUser = new CrmUser();
		crmUser.setId(crmUserId);
		crmUser.setLoginNo(loginNo);
		crmUser.setUserName(userName);
		crmUser.setMobile(mobile);
		crmUser.setCrmProvince(crmProvince);
		crmUser.setCrmCity(crmCity);
		crmUser.setCrmShopAddress(crmShopAddress);
		return this.crmUserService.updateCrmUser(crmUser);
	}
	
	/**
	 * 创建或者编辑业务员
	 * @return
	 */
	@RequestMapping("createOrEditClerk")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> createOrEditClerk() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		String userName = this.getFilteredParameter(request, "userName", 0, null);
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		String password = MD5Util.md5(this.getFilteredParameter(request, "password", 0, null));
		String bizTypeIds = this.getFilteredParameter(request, "bizTypeIds", 0, null);//业务类型id，英文逗号分隔
		//校验业务类型
		Assert.notNull(bizTypeIds, "业务类型id不能为空");
		Integer roleId = CrmRoleTypeEnum.ROLE_BIZ_ADMIN.getCode();
		CrmUser admin = this.crmUserService.getCrmUserById(this.getUserId());
		CrmUser crmUser = new CrmUser();
		crmUser.setUserName(userName);
		crmUser.setMobile(mobile);
		crmUser.setPassword(password);
		crmUser.setRoleId(roleId);
		crmUser.setAdminId(this.getUserId());
		crmUser.setCrmShopName(admin.getCrmShopName());
		crmUser.setCrmShopCompany(admin.getCrmShopCompany());
		crmUser.setCrmProvince(admin.getCrmProvince());
		crmUser.setCrmCity(admin.getCrmCity());
		crmUser.setCrmShopAddress(admin.getCrmShopAddress());
		if(null == crmUserId) {
			crmUserId = this.crmUserService.saveCrmUser(crmUser);
		}else{
			crmUser.setId(crmUserId);
			this.crmUserService.updateCrmUser(crmUser);
			this.crmUserService.deleteCrmUserBizType(crmUserId);
		}
		String []bizTypes = bizTypeIds.split(",");
		for (int i = 0; i < bizTypes.length; i++) {
			CrmUserBizType crmUserBizType = new CrmUserBizType();
			crmUserBizType.setCrmUserId(crmUserId);
			crmUserBizType.setBizType(Integer.parseInt(bizTypes[i]));
			this.crmUserService.saveCrmUserBizType(crmUserBizType);
		}
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}
	
	/**
	 * 修改业务员或者管理员信息密码
	 * @return
	 */
	@RequestMapping("editCrmUserPass")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> editCrmUserPass() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		String oldPassword = MD5Util.md5(this.getFilteredParameter(request, "oldPassword", 0, null));
		String newPassword = MD5Util.md5(this.getFilteredParameter(request, "newPassword", 0, null));
		String loginNo = this.getFilteredParameter(request, "loginNo", 0, null);
		CrmUser crmUser = new CrmUser();
		crmUser.setId(crmUserId);
		crmUser.setLoginNo(loginNo);
		crmUser.setPassword(newPassword);
		return this.crmUserService.editCrmUser(crmUser, oldPassword, newPassword);
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping("resetPass")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> resetPass() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		String newPassword = MD5Util.md5("111111");
		CrmUser crmUser = new CrmUser();
		crmUser.setId(crmUserId);
		crmUser.setPassword(newPassword);
		return this.crmUserService.updateCrmUser(crmUser);
	}
	
	/**
	 * crm账号关联门店账号
	 * @return
	 */
	@RequestMapping("linkShop")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> linkShop() {
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		String shopPassword = MD5Util.md5(this.getFilteredParameter(request, "shopPassword", 0, null));
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		return this.crmUserService.linkShop(shopName, shopPassword, crmUserId);
	}
	
	/**
	 * crm账号重新关联门店账号
	 * @return
	 */
	@RequestMapping("reLinkShop")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> reLinkShop() {
		String newShopName = this.getFilteredParameter(request, "newShopName", 0, null);
		String newShopPassword = MD5Util.md5(this.getFilteredParameter(request, "newShopPassword", 0, null));
		String oldShopName = this.getFilteredParameter(request, "oldShopName", 0, null);
		String oldShopPassword = MD5Util.md5(this.getFilteredParameter(request, "oldShopPassword", 0, null));
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		return this.crmUserService.reLinkShop(newShopName, newShopPassword, oldShopName, oldShopPassword, crmUserId);
	}

	/**
	 * crm账号冻结或者激活
	 * @return
	 */
	@RequestMapping("frozenOrActive")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> frozenOrActive() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		Integer isActive = this.getIntParameter(request, "isActive", null);
		return this.crmUserService.frozenOrActive(crmUserId, isActive);
	}
	
	/**
	 * 开启或者关闭短信开关
	 * @return
	 */
	@RequestMapping("openOrCloseSms")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> openOrCloseSms() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		Integer canSms = this.getIntParameter(request, "canSms", null);
		return this.crmUserService.openOrCloseSms(crmUserId, canSms);
	}
	
	/**
	 * 删除crm账号
	 * @return
	 */
	@RequestMapping("deleteCrmUser")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> deleteCrmUser() {
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		return this.crmUserService.deleteCrmUser(crmUserId);
	}

	/**
	 * 查询所有管理员或者业务员
	 * @return
	 */
	@RequestMapping("getAdminOrBiz")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getAdminOrBiz() {
		Integer roleType = this.getIntParameter(request, "crmRoleType", null); // 1系统管理员  2门店管理员  3业务员
		return this.crmUserService.getCrmUserListByRoleType(roleType);
	}

	/**
	 * 潜客管理-CRM云管理中心
	 * @return
	 */	
	@RequestMapping("getCrmUserList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getCrmUserList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer roleType = null; // 1系统管理员  2门店管理员  3业务员
		Integer adminId = this.getIntParameter(request, "adminId", null);
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String shopName = this.getFilteredParameter(request, "shopName", 0, null);
		return this.crmUserService.getCrmUserList(pageOffset, pageSize, roleType, adminId, province, city, shopName);
	}

	/**
	 * 潜客管理-查询CRM门店或者管理员下面的业务员
	 * @return
	 */	
	@RequestMapping("getCrmClerkList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getCrmClerkList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer adminId = this.getIntParameter(request, "adminId", null);
		String crmShopName = this.getFilteredParameter(request, "crmShopName", 0, null);
		return this.crmUserService.getCrmClerkList(pageOffset, pageSize, adminId, crmShopName);
	}
	
	/**
	 * 潜客管理-查询CRM管理员下面的所有业务员
	 * @return
	 */	
	@RequestMapping("getAdminCrmClerkList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getAdminCrmClerkList() {
		Integer adminId = this.getIntParameter(request, "adminId", null);
		Integer isActive = this.getIntParameter(request, "isActive", null);
		return this.crmUserService.getAdminCrmClerkList(adminId, isActive);
	}
	
	/**
	 * 潜客管理-查询CRM门店管理员
	 * @return
	 */	
	@RequestMapping("getCrmAdminList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> getCrmAdminList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer isActive = this.getIntParameter(request, "isActive", null);
		String crmProvince = this.getFilteredParameter(request, "crmProvince", 0, null);
		String crmCity = this.getFilteredParameter(request, "crmCity", 0, null);
		String crmShopName = this.getFilteredParameter(request, "crmShopName", 0, null);
		return this.crmUserService.getCrmAdminList(pageOffset, pageSize, isActive, crmProvince, crmCity, crmShopName);
	}
}
