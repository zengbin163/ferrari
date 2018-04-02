package com.home.ferrari.service.crm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.home.ferrari.crmdao.report.CrmCustomerReportDao;
import com.home.ferrari.dao.common.ResourceDao;
import com.home.ferrari.enums.CustomerPurposeEnum;
import com.home.ferrari.enums.CustomerUploadStatusEnum;
import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.plugin.session.SessionContainer;
import com.home.ferrari.service.common.ResourceService;
import com.home.ferrari.service.crm.CrmCustomerReportService;
import com.home.ferrari.service.crm.CrmUserService;
import com.home.ferrari.util.ResultUtil;
import com.home.ferrari.vo.common.Resource;
import com.home.ferrari.vo.crm.CrmBatch;
import com.home.ferrari.vo.crm.CrmBizTypeReport;
import com.home.ferrari.vo.crm.CrmCustCount;
import com.home.ferrari.vo.crm.CrmUser;
import com.home.ferrari.vo.crm.CrmUserBizType;

@Service
public class CrmCustomerReportServiceImpl implements CrmCustomerReportService {
	
	@Autowired
	private CrmCustomerReportDao crmCustomerReportDao;
	@Autowired
	private CrmUserService crmUserService;
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public Map<String,Object> getCustomerBatchReport(Integer pageOffset,
			Integer pageSize, Integer year, Integer month, Integer seed,
			String userName, Integer uploadStatus, String keyword) {
		Assert.notNull(pageOffset, "pageOffset不能为空");
		Assert.notNull(pageSize, "pageSize不能为空");
		Integer sessionAdminId = SessionContainer.getSession().getCrmUser().getAdminId();
		if(null==sessionAdminId){
			sessionAdminId = SessionContainer.getSession().getCrmUser().getId();
		}
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), null);
		Map<String,Object> resultMap = new HashMap<String, Object>();
		Page<?> page = new Page<>(pageOffset, pageSize, Page.DESC, "t1.create_time");
		List<CrmBatch> crmBatchList = this.crmCustomerReportDao.getCrmBatchList(page, sessionAdminId, year, month, seed, userName, uploadStatus, keyword, crmUserIdList);
		if(CollectionUtils.isEmpty(crmBatchList)) {
			return ResultUtil.successMap(null);
		}
		for(CrmBatch batch : crmBatchList) {
			Integer adminId = batch.getCrmOperatorId();
			if(null!=adminId) {
				CrmUser admin = this.crmUserService.getCrmUserById(adminId);
				if(null != admin) {
					batch.setUserName(admin.getUserName());//指定客户经理
				}
			}
			String batchCode = batch.getBatchCode();
			//1. 第一个报表
			List<Map<String, Object>> listMap = this.crmCustomerReportDao.getUploadStatusReport(adminId, batchCode, crmUserIdList);
			if(!CollectionUtils.isEmpty(listMap)) {
				Map<String, Object> uploadReportMap = new HashMap<String, Object>();
				Integer uploadSucc = 0; // 导入成功
				Integer uploadFail = 0; // 导入失败
				Integer notDistribute = 0;// 未分配
				Integer distributed = 0;// 已分配
				for(Map<String, Object> map : listMap) {
					Integer uploadStatusDb = Integer.valueOf(map.get("uploadStatus").toString());
					Integer sumDb = Integer.valueOf(map.get("sum").toString());
					if(CustomerUploadStatusEnum.UPLOAD_FAIL.getCode().equals(uploadStatusDb)) {
						uploadFail = uploadFail + sumDb;
					}else if(CustomerUploadStatusEnum.UPLOAD_NOT_DISTRIBUTE.getCode().equals(uploadStatusDb)) {
						notDistribute = notDistribute + sumDb;
						uploadSucc = uploadSucc + sumDb;
					}else if(CustomerUploadStatusEnum.DISTRIBUTED.getCode().equals(uploadStatusDb)) {
						distributed = distributed + sumDb;
						uploadSucc = uploadSucc + sumDb;
					}else{
						
					}
				}
				uploadReportMap.put("uploadSucc", uploadSucc);
				uploadReportMap.put("uploadFail", uploadFail);
				uploadReportMap.put("notDistribute", notDistribute);
				uploadReportMap.put("distributed", distributed);
				batch.setUploadReportMap(uploadReportMap);
			}
			//2. 第二个报表
			List<Map<String, Object>> listMap2 = this.crmCustomerReportDao.getBizTypeReport(batchCode, null, crmUserIdList);
			if(!CollectionUtils.isEmpty(listMap2)) {
				Map<String, Object> bizTypeReportMap = new HashMap<String, Object>();
				for(Map<String, Object> map : listMap2) {
					Resource resource = this.resourceDao.getResourcesByKeyVal(ResourceService.CUSTOMER_REQUIRE_KEY, Integer.valueOf(map.get("bizType").toString()));
					bizTypeReportMap.put(resource.getResourceDesc(), Integer.valueOf(map.get("sum").toString()));
				}
				batch.setBizTypeReportMap(bizTypeReportMap);
			}	
		}
		Integer sum = this.crmCustomerReportDao.countCrmBatchList(sessionAdminId, year, month, seed, userName, uploadStatus, keyword, crmUserIdList);
		resultMap.put("list", crmBatchList);
		resultMap.put("sum", sum);
		return ResultUtil.successMap(resultMap);
	}
	
	public Map<String, Object> rightStatsCrmCustomer(Integer isPerson) {
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		Map<String, Object> map = new HashMap<>();
		map.put("followed", this.crmCustomerReportDao.sumFollowedCust(crmUserIdList));//跟进客户总数
		map.put("todayRemind", this.crmCustomerReportDao.sumTodayRemindCust(crmUserIdList));//今日提醒跟进数
		map.put("FPurpost", this.crmCustomerReportDao.sumCustomerSuccess(crmUserIdList, CustomerPurposeEnum.F.getCode()));//F类客户达成数
		map.put("DPurpost", this.crmCustomerReportDao.sumTotalCustomer(crmUserIdList, CustomerPurposeEnum.D.getCode(), null));//放弃客户数
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> statsByBizAdmin(Integer isPerson) {
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		//业务员总数
		Integer bizAdminSum = crmUserIdList.size();
		//总客户数
		Integer totalCustomer = this.crmCustomerReportDao.sumTotalCustomer(crmUserIdList, null, null);
		//业务员列表
		List<CrmCustCount> bizAdminList = this.crmCustomerReportDao.sumCustomerByBizAdmin(crmUserIdList);
		if(!CollectionUtils.isEmpty(bizAdminList)) {
			for(CrmCustCount crmCustCount : bizAdminList) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(crmCustCount.getCrmUserId());
				crmCustCount.setPurposeCount(this.crmCustomerReportDao.sumPurposeCustomerByBizAdminId(list));
				//业务类型名称
				List<CrmUserBizType> userBizTypeList = this.crmUserService.getCrmUserBizTypeList(crmCustCount.getCrmUserId());
				List<String> bizTypeNames = new ArrayList<String>();
				for(CrmUserBizType bizType : userBizTypeList) {
					Resource resource = this.resourceDao.getResourcesByKeyVal(ResourceService.CUSTOMER_REQUIRE_KEY, bizType.getBizType());
					bizTypeNames.add(resource.getResourceDesc());
				}
				crmCustCount.setBizTypeNames(bizTypeNames);
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("bizAdminSum", bizAdminSum);
		map.put("totalCustomer", totalCustomer);
		map.put("bizAdminList", bizAdminList);
		return ResultUtil.successMap(map);
	}

	public Map<String, Object> statsByPurpose(Integer isPerson) {
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		//总客户数
		Integer totalCustomer = this.crmCustomerReportDao.sumTotalCustomer(crmUserIdList, null, null);
		//A-F类意向客户数
		List<Map<String, Object>> purpostList = this.crmCustomerReportDao.sumPurposeCustomerByBizAdminId(crmUserIdList);
		//A-F意向下面的业务类型客户数
		List<CrmBizTypeReport> bizTypeListA = this.getBizTypeReport(null, CustomerPurposeEnum.A.getCode(), crmUserIdList);
		List<CrmBizTypeReport> bizTypeListB = this.getBizTypeReport(null, CustomerPurposeEnum.B.getCode(), crmUserIdList);
		List<CrmBizTypeReport> bizTypeListC = this.getBizTypeReport(null, CustomerPurposeEnum.C.getCode(), crmUserIdList);
		List<CrmBizTypeReport> bizTypeListD = this.getBizTypeReport(null, CustomerPurposeEnum.D.getCode(), crmUserIdList);
		List<CrmBizTypeReport> bizTypeListE = this.getBizTypeReport(null, CustomerPurposeEnum.E.getCode(), crmUserIdList);
		List<CrmBizTypeReport> bizTypeListF = this.getBizTypeReport(null, CustomerPurposeEnum.F.getCode(), crmUserIdList);
		Map<String, Object> tempMap = new HashMap<>();
		tempMap.put("bizTypeListA", bizTypeListA);
		tempMap.put("bizTypeListB", bizTypeListB);
		tempMap.put("bizTypeListC", bizTypeListC);
		tempMap.put("bizTypeListD", bizTypeListD);
		tempMap.put("bizTypeListE", bizTypeListE);
		tempMap.put("bizTypeListF", bizTypeListF);
		Map<String, Object> map = new HashMap<>();
		map.put("totalCustomer", totalCustomer);
		map.put("purpostList", purpostList);
		map.put("bizTypeList", tempMap);
		return ResultUtil.successMap(map);
	}
	
	public Map<String, Object> statsByBizType(Integer isPerson) {
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		//总客户数
		Integer totalCustomer = this.crmCustomerReportDao.sumTotalCustomer(crmUserIdList, null, null);
		//业务员列表
		List<CrmCustCount> bizAdminList = this.crmCustomerReportDao.sumCustomerByBizAdmin(crmUserIdList);
		if(!CollectionUtils.isEmpty(bizAdminList)) {
			for(CrmCustCount crmCustCount : bizAdminList) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(crmCustCount.getCrmUserId());
				crmCustCount.setBizTypeCount(this.getBizTypeReport(null, null, list));
				List<CrmUserBizType> userBizTypeList = this.crmUserService.getCrmUserBizTypeList(crmCustCount.getCrmUserId());
				List<String> bizTypeNames = new ArrayList<String>();
				for(CrmUserBizType bizType : userBizTypeList) {
					Resource resource = this.resourceDao.getResourcesByKeyVal(ResourceService.CUSTOMER_REQUIRE_KEY, bizType.getBizType());
					bizTypeNames.add(resource.getResourceDesc());
				}
				crmCustCount.setBizTypeNames(bizTypeNames);
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCustomer", totalCustomer);
		map.put("bizAdminList", bizAdminList);
		return ResultUtil.successMap(map);
	}

	public Map<String, Object> statsByBizAdminCreate(Integer isPerson) {
		/**
		 * 区分业务员和管理员两个维度
		 */
		List<Integer> crmUserIdList = this.crmUserService.getCrmUserIdListByRoleType(SessionContainer.getSession().getCrmUser().getId(), isPerson);
		//总客户数
		Integer totalCustomer = this.crmCustomerReportDao.sumTotalCustomer(crmUserIdList, null, null);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalCustomer", totalCustomer);
		map.put("bizAdminList", this.crmCustomerReportDao.sumCustomerByBizAdmin(crmUserIdList));
		return ResultUtil.successMap(map);
	}
	
	public List<CrmBizTypeReport> getBizTypeReport(String batchCode, String purpose, List<Integer> crmUserIdList) {
		List<Map<String, Object>> listMap = this.crmCustomerReportDao.getBizTypeReport(batchCode, purpose, crmUserIdList);
		List<CrmBizTypeReport> reportList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(listMap)) {
			for(Map<String, Object> map : listMap) {
				CrmBizTypeReport report = new CrmBizTypeReport();
				Integer bizType = Integer.valueOf(map.get("bizType").toString());
				report.setBizType(bizType);
				Resource resource = this.resourceDao.getResourcesByKeyVal(ResourceService.CUSTOMER_REQUIRE_KEY, bizType);
				if (null != resource) {
					report.setBizValue(resource.getResourceDesc());
				}
				report.setSum(Integer.valueOf(map.get("sum").toString()));
				reportList.add(report);
			}
		}
		return reportList;
	}

}
