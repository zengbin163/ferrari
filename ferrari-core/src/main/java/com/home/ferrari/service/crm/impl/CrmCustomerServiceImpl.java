package com.home.ferrari.service.crm.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.home.ferrari.base.ResultCode;
import com.home.ferrari.crmdao.crm.CrmCustomerDao;
import com.home.ferrari.dao.common.ResourceDao;
import com.home.ferrari.enums.CustomerUploadStatusEnum;
import com.home.ferrari.enums.DefaultEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.exception.FerrariBizException;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.common.ResourceService;
import com.home.ferrari.service.crm.CrmCustomerService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.util.DateUtil;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.common.Resource;
import com.home.ferrari.vo.crm.CrmBatch;
import com.home.ferrari.vo.crm.CrmCustBizType;
import com.home.ferrari.vo.crm.CrmCustPurpose;
import com.home.ferrari.vo.crm.CrmCustRemaind;
import com.home.ferrari.vo.crm.CrmCustTrace;
import com.home.ferrari.vo.crm.CrmCustomer;
import com.home.ferrari.vo.crm.CrmUser;

@Service
public class CrmCustomerServiceImpl implements CrmCustomerService {
	
	@Autowired
	private CrmCustomerDao crmCustomerDao;
	@Autowired
	private CrmUserService crmUserService;
	@Autowired
	private ResourceDao resourceDao;

	public String createCrmBatch(String batchCode) {
		Integer year = Calendar.getInstance().get(Calendar.YEAR);
		Integer month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		Integer seed = this.crmCustomerDao.getMaxCrmBatchSeed(year, month);
		seed = seed + 1;
		CrmBatch crmBatch = new CrmBatch();
		if(StringUtils.isBlank(batchCode)){
			 batchCode = "" + year + month + seed;
		}
		crmBatch.setBatchCode(batchCode);
		crmBatch.setYear(year);
		crmBatch.setMonth(month);
		crmBatch.setSeed(seed);
		this.crmCustomerDao.insertCrmBatch(crmBatch);
		return batchCode;
	}
	
	public CrmBatch getCrmBatchByBatchCode(String batchCode) {
		return this.crmCustomerDao.getCrmBatchByBatchCode(batchCode);
	}
	
	public Map<String, Object> getCrmBatchCodeByYearAndMonth(Integer year, Integer month) {
		Assert.notNull(year, "年份不能为空");
		Assert.notNull(month, "月份不能为空");
		return ResultUtil.successMap(this.crmCustomerDao.getCrmBatchCodeByYearAndMonth(year, month));
	}	
	@Override
	public Integer saveOrUpdateCrmCustomer(CrmCustomer crmCustomer) {
		if(null==crmCustomer.getId()) {
			return this.crmCustomerDao.insertCrmCustomer(crmCustomer);
		}else{
			return this.crmCustomerDao.updateCrmCustomer(crmCustomer);
		}
	}

	@Override
	public Map<String, Object> saveCrmCustomer(CrmCustomer crmCustomer, String bizTypeIds, String remark, Integer sessionUserId, Integer adminId) {
		Integer crmUserId = -1000;
		String licensePlate = crmCustomer.getLicensePlate();
		if(StringUtils.isNotBlank(licensePlate)) {
			CrmCustomer tempCust = this.getCrmCustomerByLicensePlate(adminId, licensePlate, null);
			if(null!=tempCust) {
				throw new FerrariBizException(ResultCode.LICENSE_PLATE_EXISTS, "车号号已存在，潜客名称为：" + tempCust.getCustomerName());
			}
			crmCustomer.setLicensePlate(licensePlate);
		}
		//保存潜客
		this.saveOrUpdateCrmCustomer(crmCustomer);
		Integer customerId = crmCustomer.getId();
		//保存业务类型
		Assert.notNull(bizTypeIds, "业务类型id不能为空");
		String []bizTypes = bizTypeIds.split(",");
		for (int i = 0; i < bizTypes.length; i++) {
			CrmCustBizType crmCustBizType = new CrmCustBizType();
			crmCustBizType.setCrmCustomerId(customerId);
			crmCustBizType.setBizType(Integer.parseInt(bizTypes[i]));
			this.saveCrmCustBizType(crmCustBizType);
		}
		//保存跟进记录
		if(StringUtils.isNotBlank(remark)) {
			CrmCustTrace crmCustTrace = new CrmCustTrace();
			crmCustTrace.setCrmCustomerId(customerId);
			crmCustTrace.setCrmUserId(crmUserId);
			crmCustTrace.setRemark(remark);
			this.saveCrmCustTrace(crmCustTrace);
		}
		return ResultUtil.successMap(ResultUtil.DATA_INSERT_SUCC);
	}

	@Override
	public Map<String, Object> editCrmCustomer(CrmCustomer crmCustomer, String bizTypeIds, String remark, Integer sessionUserId, Integer adminId) {
		Integer customerId = crmCustomer.getId();
		String licensePlate = crmCustomer.getLicensePlate();
		if(StringUtils.isNotBlank(licensePlate)) {
			CrmCustomer tempCust = this.getCrmCustomerByLicensePlate(adminId, licensePlate, customerId);
			if(null!=tempCust && !tempCust.getId().equals(crmCustomer.getId())) {
				throw new FerrariBizException(ResultCode.LICENSE_PLATE_EXISTS, "车号号已存在，潜客名称为：" + tempCust.getCustomerName());
			}
			crmCustomer.setLicensePlate(licensePlate);
		}
		//编辑潜客
		this.saveOrUpdateCrmCustomer(crmCustomer);
		//保存业务类型
		Assert.notNull(bizTypeIds, "业务类型id不能为空");
		String []bizTypes = bizTypeIds.split(",");
		this.crmCustomerDao.deleteCrmCustBizType(customerId);
		for (int i = 0; i < bizTypes.length; i++) {
			CrmCustBizType crmCustBizType = new CrmCustBizType();
			crmCustBizType.setCrmCustomerId(customerId);
			crmCustBizType.setBizType(Integer.parseInt(bizTypes[i]));
			this.saveCrmCustBizType(crmCustBizType);
		}
		//保存跟进记录
		if(StringUtils.isNotBlank(remark)) {
			CrmCustTrace crmCustTrace = new CrmCustTrace();
			crmCustTrace.setCrmCustomerId(customerId);
			crmCustTrace.setCrmUserId(sessionUserId);
			crmCustTrace.setRemark(remark);
			this.saveCrmCustTrace(crmCustTrace);
		}
		this.deleteCrmCustRemaind(customerId);//完成提醒
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	@Override
	public Map<String, Object> switchPurpose(Integer customerId, String purpose) {
		Assert.notNull(customerId, "customerId不能为空");
		Assert.notNull(purpose, "客户意向不能为空");
		CrmCustomer customerDB = this.crmCustomerDao.getCrmCustomerById(customerId);
		if(purpose.equals(customerDB.getPurpose())) {
			throw new FerrariBizException(ResultCode.PURPOSE_NOT_CHANGE, ResultCode.PURPOSE_NOT_CHANGE.getString());
		}
		CrmCustomer customer = new CrmCustomer();
		customer.setId(customerId);
		customer.setPurpose(purpose);
		this.saveOrUpdateCrmCustomer(customer);
		CrmCustPurpose crmCustPurpose = new CrmCustPurpose();
		crmCustPurpose.setCustomerId(customerId);
		crmCustPurpose.setPurpose(purpose);
		this.crmCustomerDao.insertCrmCustPurpose(crmCustPurpose);
		this.deleteCrmCustRemaind(customerId);//完成提醒
		return ResultUtil.successMap(ResultUtil.DATA_UPDATE_SUCC);
	}
	
	@Override
	public void distributeCustomerByBatchCode(String crmBatchCode) {
		this.crmCustomerDao.distributeCustomerByBatchCode(crmBatchCode);
	}
	
	public void distributeCustomerByCustomerId(Integer customerId, Integer crmUserId) {
		Assert.notNull(customerId, "客户id不能为空");
		Assert.notNull(crmUserId, "业务员id不能为空");
		CrmCustomer crmCustomer = new CrmCustomer();
		crmCustomer.setId(customerId);
		crmCustomer.setCrmUserId(crmUserId);
		CrmUser crmUser = this.crmUserService.getCrmUserById(crmUserId);
		if(null == crmUser) {
			throw new FerrariBizException(ResultCode.BIZ_USER_NOT_EXISTS, "业务员不存在,crmUserId = " + crmUserId);
		}
		if(DefaultEnum.NO.getCode().equals(crmUser.getIsActive())) {
			throw new FerrariBizException(ResultCode.BIZ_USER_BE_FROZEN, "业务员已冻结,crmUserId = " + crmUserId);
		}
		crmCustomer.setCrmUserName(crmUser.getUserName());
		crmCustomer.setRemark("重新分配业务员");
		crmCustomer.setUploadStatus(CustomerUploadStatusEnum.DISTRIBUTED.getCode());
		this.crmCustomerDao.updateCrmCustomer(crmCustomer);
	}
	
	@Override
	public Map<String, Object> getCrmCustomerById(Integer customerId) {
		Assert.notNull(customerId, "客户编号不能为空");
		CrmCustomer customer = this.crmCustomerDao.getCrmCustomerById(customerId);
		customer.setBizTypeList(this.getCrmCustBizType(customer.getId()));
		return ResultUtil.successMap(customer);
	}
	
	@Override
	public CrmCustomer getCrmCustomerByLicensePlate(Integer adminId, String licensePlate, Integer customerId) {
		Assert.notNull(licensePlate, "车牌号不能为空");
		return this.crmCustomerDao.getCrmCustomerByLicensePlate(adminId, licensePlate, customerId);
	}

	public Integer saveCrmCustTrace(CrmCustTrace crmCustTrace) {
		this.deleteCrmCustRemaind(crmCustTrace.getCrmCustomerId());//完成提醒
		return this.crmCustomerDao.insertCrmCustTrace(crmCustTrace);
	}

	public Map<String, Object> getCrmCustTrace(Integer customerId) {
		Assert.notNull(customerId, "customerId不能为空");
		return ResultUtil.successMap(this.crmCustomerDao.getCrmCustTrace(customerId));
	}
	
	public Integer saveCrmCustRemaind(CrmCustRemaind crmCustRemaind) {
		return this.crmCustomerDao.insertCrmCustRemaind(crmCustRemaind);
	}
	
	public Integer deleteCrmCustRemaind(Integer customerId) {
		Assert.notNull(customerId, "客户id不能为空");
		return this.crmCustomerDao.deleteCrmCustRemaind(customerId);
	}

	public Integer saveCrmCustBizType(CrmCustBizType crmCustBizType) {
		return this.crmCustomerDao.insertCrmCustBizType(crmCustBizType);
	}
	
	public Integer deleteCrmCustBizType(Integer customerId) {
		return this.crmCustomerDao.deleteCrmCustBizType(customerId);
	}
	
	public List<CrmCustBizType> getCrmCustBizType(Integer customerId) {
		Assert.notNull(customerId, "customerId不能为空");
		List<CrmCustBizType> bizTypeList = this.crmCustomerDao.getCrmCustBizType(customerId);
		if(CollectionUtils.isNotEmpty(bizTypeList)) {
			for(CrmCustBizType bizType : bizTypeList) {
				Resource resource = this.resourceDao.getResourcesByKeyVal(ResourceService.CUSTOMER_REQUIRE_KEY, bizType.getBizType());
				if(null!=resource){
					bizType.setResourceDesc(resource.getResourceDesc());
				}
			}
		}
		return bizTypeList;
	}
	
	public List<CrmCustomer> getUploadFailCrmCustomer(String crmBatchCode, List<Integer> crmUserIdList) {
		Assert.notNull(crmBatchCode, "crmBatchCode不能为空");
		return this.crmCustomerDao.getUploadFailCrmCustomer(crmBatchCode, crmUserIdList);
	}
	
	public Map<String, Object> getCrmCustomerList(Integer pageOffset,
			Integer pageSize, Integer uploadStatus, String batchCode,
			Integer year, Integer month, Integer seed, String level,
			String purpose, Integer bizType, String userName, String keyword,
			Integer isPerson) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		Map<String,Object> map = new HashMap<String, Object>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		List<CrmCustomer> list = this.crmCustomerDao.getCrmCustomerList(page,
				uploadStatus, batchCode, year, month, seed, crmUserIdList,
				level, purpose, bizType, userName, keyword);
		if(CollectionUtils.isNotEmpty(list)) {
			for(CrmCustomer crmCustomer : list) {
				crmCustomer.setBizTypeList(this.getCrmCustBizType(crmCustomer.getId()));
				Date remaindTime = this.crmCustomerDao.getRemaindByCustomerId(crmCustomer.getId());
				if(null!=remaindTime) {
					Integer leftDay = DateUtil.getDayBetweenTime(remaindTime, new Date());
					crmCustomer.setLeftDays(leftDay >= 0 ? leftDay : 0);
					crmCustomer.setRemaindTime(remaindTime);
				}
			}
		}
		Integer sum = this.crmCustomerDao.countCrmCustomerList(uploadStatus,
				batchCode, year, month, seed, crmUserIdList, level, purpose,
				bizType, userName, keyword);
		map.put("list", list);
		map.put("sum", sum);
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> statsCrmCustomer(Integer isPerson) {
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		Map<String, Object> map = new HashMap<>();
		map.put("notDistribute", this.crmCustomerDao.sumNotDistributeCust(crmUserIdList));//未分配客户数
		map.put("notFollow", this.crmCustomerDao.sumNotFollowCust(crmUserIdList));//从未跟进客户数
		map.put("treeDayFollow", this.crmCustomerDao.sumTreeDayFollowCust(crmUserIdList));//3日内要跟进客户数
		map.put("treeDayFollowList", this.crmCustomerDao.sumTreeDayFollowCustList(crmUserIdList));//3日内每个业务员要跟进的客户数
		map.put("todayFollow", this.crmCustomerDao.sumTodayFollowCust(crmUserIdList));//今日内要跟进客户数
		map.put("todayNew", this.crmCustomerDao.sumTodayNewCust(crmUserIdList));//今日新增客户数
		return ResultUtil.successMap(map);
	}
	
	public List<CrmCustomer> customerList(Integer uploadStatus,
			String batchCode, Integer year, Integer month, Integer seed,
			String level, String purpose, Integer bizType, String userName,
			String keyword, Integer isPerson) {
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		return this.crmCustomerDao.getCrmCustomerList(null,
				uploadStatus, batchCode, year, month, seed, crmUserIdList,
				level, purpose, bizType, userName, keyword);

	}
	
	public List<CrmCustomer> customerListByCustId(String []custIds) {
		Assert.noNullElements(custIds, "客户id集合不能为空");
		return this.crmCustomerDao.getCrmCustomerListByCustIds(custIds);
	}
	
	public List<CrmCustomer> customerListByMobiles(String []mobiles) {
		Assert.noNullElements(mobiles, "手机号码集合不能为空");
		return this.crmCustomerDao.getCrmCustomerListByMobiles(mobiles);
	}
	
	public Map<String, Object> statsCrmCustomerList(Integer pageOffset, Integer pageSize, Integer clickType, Integer isPerson) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Assert.notNull(clickType, "点击类型不能为空");
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.modify_time");
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		Map<String, Object> map = new HashMap<>();
		List<CrmCustomer> list = this.crmCustomerDao.getCustomerListByClickType(page, clickType, crmUserIdList);
		if(CollectionUtils.isNotEmpty(list)) {
			for(CrmCustomer crmCustomer : list) {
				crmCustomer.setBizTypeList(this.getCrmCustBizType(crmCustomer.getId()));
				Date remaindTime = this.crmCustomerDao.getRemaindByCustomerId(crmCustomer.getId());
				if(null!=remaindTime) {
					Integer leftDay = DateUtil.getDayBetweenTime(remaindTime, new Date());
					crmCustomer.setLeftDays(leftDay >= 0 ? leftDay : 0);
					crmCustomer.setRemaindTime(remaindTime);
				}
			}
		}
		map.put("list", list);
		map.put("sum", this.crmCustomerDao.countCustomerListByClickType(clickType, crmUserIdList));
		return ResultUtil.successMap(map);
	}
}
