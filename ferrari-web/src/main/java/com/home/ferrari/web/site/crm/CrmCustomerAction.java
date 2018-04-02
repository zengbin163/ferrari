package com.home.ferrari.web.site.crm;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.ferrari.antotation.LoginRequired;
import com.home.ferrari.enums.CustomerUploadStatusEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.service.crm.CrmCustomerService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.crm.CrmCustRemaind;
import com.home.ferrari.vo.crm.CrmCustTrace;
import com.home.ferrari.vo.crm.CrmCustomer;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.web.BaseAction;

@Controller
@RequestMapping("/crmCustomer")
public class CrmCustomerAction extends BaseAction {

	private static final long serialVersionUID = -3052588713850217380L;
	
	@Autowired
	private CrmCustomerService crmCustomerService;
	@Autowired
	private CrmUserService crmUserService;

	/**
	 * 潜客管理-根据年和月查询所有批次号
	 * @return
	 */
	@RequestMapping("batchCodeList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> batchCodeList() {
		Integer year = this.getIntParameter(request, "year", null);
		Integer month = this.getIntParameter(request, "month", null);
		return this.crmCustomerService.getCrmBatchCodeByYearAndMonth(year, month);
	}
	

	/**
	 * 潜客管理-全部潜客
	 * @return
	 */
	@RequestMapping("customerList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> customerList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
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
		return this.crmCustomerService.getCrmCustomerList(pageOffset, pageSize, uploadStatus, batchCode, year, month, seed, level, purpose, bizType, userName, keyword, isPerson);
	}

	/**
	 * 潜客管理-全部潜客 右侧统计报表
	 * @return
	 */
	@RequestMapping("statsCrmCustomer")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> statsCrmCustomer() {
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		return this.crmCustomerService.statsCrmCustomer(isPerson);
	}

	/**
	 * 潜客管理-全部潜客 右侧统计报表，点击每个条件
	 * clickType
	 * 	 1   未分配客户
	 * 	 2   从未跟进客户数
	 * 	 3   三日内要跟进客户数
	 * 	 4   今日内要跟进客户数
	 * 	 5   今日新增客户数
	 * 	 6   一日内要跟进数客户数
	 * 	 7 F类客户达成数
	 * 	 8   放弃客户数
	 * 	 9   跟进客户总数
	 * @return
	 */
	@RequestMapping("statsCrmCustomerList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> statsCrmCustomerList() {
		Integer pageOffset = this.getIntParameter(request, "pageOffset", null);
		Integer pageSize = this.getIntParameter(request, "pageSize", null);
		Integer clickType = this.getIntParameter(request, "clickType", null);
		Integer isPerson = this.getIntParameter(request, "isPerson", null); //是否为个人，1是，0否，个人的话管理员当做业务员处理
		return this.crmCustomerService.statsCrmCustomerList(pageOffset, pageSize, clickType, isPerson);
	}
	
	/**
	 * 潜客管理-创建
	 * @return
	 */
	@RequestMapping("create")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> create() {
		String customerName = this.getFilteredParameter(request, "customerName", 0, null);
		Assert.notNull(customerName, "客户名称不能为空");
		String level = this.getFilteredParameter(request, "level", 0, null);
		Assert.notNull(level, "客户等级不能为空");
		String licensePlate = this.getFilteredParameter(request, "licensePlate", 0, null);
		String linkPhone = this.getFilteredParameter(request, "linkPhone", 0, null);
		Assert.notNull(linkPhone, "联系方式不能为空");
		String crmUserName = this.getFilteredParameter(request, "crmUserName", 0, null);
//		Assert.notNull(crmUserName, "业务员名称不能为空");
		String licenseTime = this.getFilteredParameter(request, "licenseTime", 0, null);
		String introducer = this.getFilteredParameter(request, "introducer", 0, null);
		String purpose = this.getFilteredParameter(request, "purpose", 0, null);
		Assert.notNull(purpose, "客户意向不能为空");
		String crmBrandName = this.getFilteredParameter(request, "crmBrandName", 0, null);
		String carSeries = this.getFilteredParameter(request, "carSeries", 0, null);
		String displacement = this.getFilteredParameter(request, "displacement", 0, null);
		String year = this.getFilteredParameter(request, "year", 0, null);
		String vin = this.getFilteredParameter(request, "vin", 0, null);
		String engine = this.getFilteredParameter(request, "engine", 0, null);

		String bizTypeIds = this.getFilteredParameter(request, "bizTypeIds", 0, null);//业务类型id，英文逗号分隔
		Assert.notNull(bizTypeIds, "客户需求不能为空");
		String remark = this.getFilteredParameter(request, "remark", 0, null); //跟进记录，备注
		
		CrmCustomer crmCustomer = new CrmCustomer();
		crmCustomer.setCustomerName(customerName);
		crmCustomer.setLevel(level);
		crmCustomer.setLinkPhone(linkPhone);
		crmCustomer.setLicenseTime(licenseTime);
		crmCustomer.setIntroducer(introducer);
		crmCustomer.setPurpose(purpose);
		crmCustomer.setLicensePlate(licensePlate);
		crmCustomer.setCrmBrandName(crmBrandName);
		crmCustomer.setCarSeries(carSeries);
		crmCustomer.setDisplacement(displacement);
		crmCustomer.setYear(year);
		crmCustomer.setVin(vin);
		crmCustomer.setEngine(engine);
		Integer adminId = this.getCrmAdminId();
		crmCustomer.setCrmOperatorId(adminId);
		crmCustomer.setCreateTime(new Date());
		//查询业务员id
		CrmUser crmUser = this.getCrmUser();
		if(StringUtils.isNotBlank(crmUserName)) {
			crmUser = this.crmUserService.getCrmUserByCrmShopNameAndUserName(crmUser.getCrmShopName(), crmUserName);
		}
		Integer crmUserId = crmUser.getId();
		crmCustomer.setCrmUserId(crmUserId);
		crmCustomer.setCrmUserName(crmUser.getUserName());
		crmCustomer.setUploadStatus(CustomerUploadStatusEnum.DISTRIBUTED.getCode());
		return this.crmCustomerService.saveCrmCustomer(crmCustomer, bizTypeIds, remark, crmUserId, this.getCrmAdminId());
	}
	
	/**
	 * 潜客管理-备注列表
	 * @return
	 */
	@RequestMapping("remarkList")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> remarkList() {
		Integer customerId = this.getIntParameter(request, "customerId", null);
		return this.crmCustomerService.getCrmCustTrace(customerId);
	}
	
	/**
	 * 潜客管理-一键分配
	 * @return
	 */
	@RequestMapping("distribute")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> distribute() {
		String batchCode = this.getFilteredParameter(request, "batchCode", 0, null);
		this.crmCustomerService.distributeCustomerByBatchCode(batchCode);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}

	/**
	 * 潜客管理-根据客户id分配
	 * @return
	 */
	@RequestMapping("distributeByCustId")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> distributeByCustId() {
		Integer customerId = this.getIntParameter(request, "customerId", null);
		Integer crmUserId = this.getIntParameter(request, "crmUserId", null);
		this.crmCustomerService.distributeCustomerByCustomerId(customerId, crmUserId);
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	/**
	 * 潜客管理-潜客详情
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> customerDetail() {
		Integer customerId = this.getIntParameter(request, "customerId", null);
		return this.crmCustomerService.getCrmCustomerById(customerId);
	}

	/**
	 * 潜客管理-潜客资料修改
	 * @return
	 */
	@RequestMapping("edit")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> customerEdit() {
		Integer customerId = this.getIntParameter(request, "customerId", null);
		String customerName = this.getFilteredParameter(request, "customerName", 0, null);
		String level = this.getFilteredParameter(request, "level", 0, null);
		String licensePlate = this.getFilteredParameter(request, "licensePlate", 0, null);
		String linkPhone = this.getFilteredParameter(request, "linkPhone", 0, null);
		//String crmUserName = this.getFilteredParameter(request, "crmUserName", 0, null);
		String licenseTime = this.getFilteredParameter(request, "licenseTime", 0, null);
		String introducer = this.getFilteredParameter(request, "introducer", 0, null);
		//String purpose = this.getFilteredParameter(request, "purpose", 0, null);
		String crmBrandName = this.getFilteredParameter(request, "crmBrandName", 0, null);
		String carSeries = this.getFilteredParameter(request, "carSeries", 0, null);
		String displacement = this.getFilteredParameter(request, "displacement", 0, null);
		String year = this.getFilteredParameter(request, "year", 0, null);
		String vin = this.getFilteredParameter(request, "vin", 0, null);
		String engine = this.getFilteredParameter(request, "engine", 0, null);

		String bizTypeIds = this.getFilteredParameter(request, "bizTypeIds", 0, null);//业务类型id，英文逗号分隔
		String remark = this.getFilteredParameter(request, "remark", 0, null); //跟进记录，备注
		
		CrmCustomer crmCustomer = new CrmCustomer();
		crmCustomer.setId(customerId);
		crmCustomer.setCustomerName(customerName);
		crmCustomer.setLevel(level);
		crmCustomer.setLinkPhone(linkPhone);
		crmCustomer.setLicenseTime(licenseTime);
		crmCustomer.setIntroducer(introducer);
		//crmCustomer.setPurpose(purpose);
		crmCustomer.setLicensePlate(licensePlate);
		//crmCustomer.setCrmUserName(crmUserName);
		crmCustomer.setCrmBrandName(crmBrandName);
		crmCustomer.setCarSeries(carSeries);
		crmCustomer.setDisplacement(displacement);
		crmCustomer.setYear(year);
		crmCustomer.setVin(vin);
		crmCustomer.setEngine(engine);
		crmCustomer.setCrmOperatorId(this.getUserId());
		return this.crmCustomerService.editCrmCustomer(crmCustomer, bizTypeIds, remark, this.getUserId(), this.getCrmAdminId());
	}
	
	/**
	 * 潜客管理-切换客户意向
	 * @return
	 */
	@RequestMapping("switchPurpose")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> switchPurpose() {
		Integer customerId = this.getIntParameter(request, "customerId", null);
		String purpose = this.getFilteredParameter(request, "purpose", 0, null);
		return this.crmCustomerService.switchPurpose(customerId, purpose);
	}
	
	/**
	 * 潜客管理-记录潜客备注
	 * @return
	 */
	@RequestMapping("remark")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> remark() {
		CrmCustTrace crmCustTrace = new CrmCustTrace();
		Integer customerId = this.getIntParameter(request, "customerId", null);
		String remark = this.getFilteredParameter(request, "remark", 0, null);
		crmCustTrace.setCrmCustomerId(customerId);
		crmCustTrace.setCrmUserId(this.getUserId());
		crmCustTrace.setRemark(remark);
		return ResultUtil.successMap(this.crmCustomerService.saveCrmCustTrace(crmCustTrace));
	}
	
	/**
	 * 潜客管理-设置提醒
	 * @return
	 */
	@RequestMapping("remaind")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> remaind() {
		Integer customerId = this.getIntParameter(request, "customerId", null);
		String remindTime = this.getFilteredParameter(request, "remindTime", 0, null);
		//保存下次跟进日期（下次提醒日期）
		CrmCustRemaind crmCustRemaind = new CrmCustRemaind();
		crmCustRemaind.setCrmCustomerId(customerId);
		crmCustRemaind.setCrmUserId(this.getUserId());
		crmCustRemaind.setIsRemind(DefaultEnum.YES.getCode());
		crmCustRemaind.setRemindTime(DateUtil.defaultParseTime(remindTime));
		this.crmCustomerService.saveCrmCustRemaind(crmCustRemaind);
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}

	/**
	 * 潜客管理-完成提醒
	 * @return
	 */
	@RequestMapping("finishRemaind")
	@ResponseBody
	@LoginRequired(needLogin = true)
	public Map<String, Object> finishRemaind() {
		Integer customerId = this.getIntParameter(request, "customerId", null);
		return ResultUtil.successMap(this.crmCustomerService.deleteCrmCustRemaind(customerId));
	}
}
