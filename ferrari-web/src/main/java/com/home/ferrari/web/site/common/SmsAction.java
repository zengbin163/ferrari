package com.home.ferrari.web.site.common;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.base.ResultCode;
import com.home.ferrari.enums.CachePrefixEnum;
import com.home.ferrari.enums.CrmRoleTypeEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.plugin.cache.RedisService;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.sms.MandaoSmsClient;
import com.home.ferrari.service.crm.CrmCustomerService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.service.sms.SendCustomerService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.crm.CrmCustomer;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/sms")
public class SmsAction extends BaseAction {

	private static final long serialVersionUID = 8894154659066325532L;
	@Autowired
	private RedisService redisService;
	@Autowired
	private CrmUserService crmUserService;
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private SendCustomerService sendCustomerService;

	@RequestMapping("send")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> send() throws UnsupportedEncodingException {
		MandaoSmsClient client = null;
		try {
			client = new MandaoSmsClient(MandaoSmsClient.TZ_SN, MandaoSmsClient.TZ_PW);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		Assert.notNull(mobile, "手机号码不能为空");
		String NO = getSixNO();
		this.redisService.set(CachePrefixEnum.PREFIX_SMS_NO_ + mobile, NO);
		String content = java.net.URLEncoder.encode("短信验证码为" + NO + "【xacl】",  "utf-8");
		client.send(mobile, content, "", "", "", "");
		return ResultUtil.successMap(ResultUtil.SEND_SUCCESS);
	}

	@RequestMapping("validate")
	@ResponseBody
	@LoginRequired(needLogin = false)
	public Map<String, Object> validate() {
		String mobile = this.getFilteredParameter(request, "mobile", 0, null);
		String inputNo = this.getFilteredParameter(request, "inputNo", 0, null);
		Assert.notNull(mobile, "手机号码不能为空");
		Assert.notNull(inputNo, "短信验证码不能为空");
		String NO = this.redisService.get(CachePrefixEnum.PREFIX_SMS_NO_ + mobile);
		if(!inputNo.equals(NO)) {
			throw new FerrariBizException(ResultCode.SMS_NO_ERROR, ResultCode.SMS_NO_ERROR.toString());
		}
		this.redisService.del(CachePrefixEnum.PREFIX_SMS_NO_ + mobile);
		return ResultUtil.successMap(ResultUtil.VALIDATE_SUCCESS);
	}
	
	private String getSixNO() {
		String ran = "";
		for (int i = 0; i < 6; i++) {
			int random = (int) (Math.random() * 10);
			ran = ran + random;
		}
		return ran;
	}
	
	/**
	 * 潜客管理-给满足条件的潜客发短信
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("sendCustomer")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> sendCustomer() {
		Integer uploadStatus = this.getIntParameter(request, "uploadStatus", null);
		String batchCode = this.getFilteredParameter(request, "batchCode", 0, null);
		Integer year = this.getIntParameter(request, "year", null);
		Integer month = this.getIntParameter(request, "month", null);
		Integer seed = this.getIntParameter(request, "seed", null);
		String level = this.getFilteredParameter(request, "level", 0, null);
		String purpose = this.getFilteredParameter(request, "purpose", 0, null);
		Integer bizType = this.getIntParameter(request, "bizType", null);
		String userName = this.getFilteredParameter(request, "userName", 0, null);
		String keyword = this.getFilteredParameter(request, "keyword", 0, null);
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		List<CrmCustomer> custList = this.crmCustomerService.customerList(uploadStatus, batchCode, year, month, seed, level, purpose, bizType, userName, keyword, isPerson);
		if(CollectionUtils.isEmpty(custList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
//		Integer adminId = this.getCrmAdminId();
//		Integer sessionUserId = this.getUserId();
//		this.sendCustomerService.sendSms2Customer(adminId, sessionUserId, custList, msgContent);
		StringBuffer mobiles = new StringBuffer();
		int i = 1;
		for(CrmCustomer customer : custList) {
			if(StringUtils.isNotBlank(customer.getLinkPhone())) {
				mobiles.append(customer.getLinkPhone());
				if(i < custList.size()) {
					mobiles.append(",");
				}
				++i;
			}
		}
		return ResultUtil.successMap(mobiles.toString());
	}

	/**
	 * 潜客管理-给指定客户发短信
	 * @return
	 */
	@RequestMapping("sendCustomerIds")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> sendCustomerIds() {
		String customerIds = this.getFilteredParameter(request, "customerIds", 0, null);
		Assert.notNull(customerIds, "潜客id列表不能为空");
		List<CrmCustomer> customerList = this.crmCustomerService.customerListByCustId(customerIds.split(","));
		if(CollectionUtils.isEmpty(customerList)) {
			return ResultUtil.successMap(ResultUtil.DATA_NOT_EXISTS);
		}
//		Integer adminId = this.getCrmAdminId();
//		Integer sessionUserId = this.getUserId();
//		this.sendCustomerService.sendSms2Customer(adminId, sessionUserId, customerList, msgContent);
		StringBuffer mobiles = new StringBuffer();
		int i = 1;
		for(CrmCustomer customer : customerList) {
			if(StringUtils.isNotBlank(customer.getLinkPhone())) {
				mobiles.append(customer.getLinkPhone());
				if(i < customerList.size()) {
					mobiles.append(",");
				}
				++i;
			}
		}
		return ResultUtil.successMap(mobiles.toString());
	}
	
	/**
	 * 根据省市、门店名称搜索客户经理电话号码
	 * @return
	 */
	@RequestMapping("sendCustManager")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> sendCustManager() {
		String province = this.getFilteredParameter(request, "province", 0, null);
		String city = this.getFilteredParameter(request, "city", 0, null);
		String crmShopName = this.getFilteredParameter(request, "crmShopName", 0, null);
		String mobiles = this.sendCustomerService.getSendManagerMobiles(province, city, crmShopName);
		return ResultUtil.successMap(mobiles);
	}

	/**
	 * 潜客管理-给指定手机号码发短信
	 * @return
	 */
	@RequestMapping("sendMobiles")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> sendMobiles() {
		String mobiles = this.getFilteredParameter(request, "mobiles", 0, null);
		String msgContent = this.getFilteredParameter(request, "msgContent", 0, null);
		Assert.notNull(mobiles, "手机号码列表不能为空");
		Assert.notNull(msgContent, "短信内容不能为空");
		Integer adminId = this.getCrmAdminId();
		Integer sessionUserId = this.getUserId();
		Integer sessionRoleType = this.getCrmUser().getRoleType();
		if(!CrmRoleTypeEnum.ROLE_SYSTEM_ADMIN.getCode().equals(sessionRoleType)) {
			CrmUser crmUserDB = this.crmUserService.getCrmUserById(sessionUserId);
			if(null == crmUserDB) {
				throw new FerrariBizException(ResultCode.CRMUSER_IS_FROZEN, ResultCode.CRMUSER_IS_FROZEN.toString());
			}
			if(DefaultEnum.NO.getCode().equals(crmUserDB.getCanSms())) {
				throw new FerrariBizException(ResultCode.CRMUSER_SMS_IS_FROZEN, ResultCode.CRMUSER_SMS_IS_FROZEN.toString());
			}
		}
		this.sendCustomerService.sendSmsByMobiles(adminId, sessionUserId, sessionRoleType, mobiles.split(","), msgContent);
		return ResultUtil.successMap(null);
	}
}
