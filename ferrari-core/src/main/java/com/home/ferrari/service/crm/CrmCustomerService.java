package com.home.ferrari.service.crm;

import java.util.List;
import java.util.Map;

import com.home.ferrari.vo.crm.CrmBatch;
import com.home.ferrari.vo.crm.CrmCustBizType;
import com.home.ferrari.vo.crm.CrmCustRemaind;
import com.home.ferrari.vo.crm.CrmCustTrace;
import com.home.ferrari.vo.crm.CrmCustomer;

public interface CrmCustomerService {
	
	public String createCrmBatch(String batchCode);

	public CrmBatch getCrmBatchByBatchCode(String batchCode);
	
	public Map<String, Object> getCrmBatchCodeByYearAndMonth(Integer year, Integer month);
	
	public Integer saveOrUpdateCrmCustomer(CrmCustomer crmCustomer);

	public Map<String, Object> saveCrmCustomer(CrmCustomer crmCustomer, String bizTypeIds, String remark, Integer sessionUserId, Integer adminId);

	public Map<String, Object> editCrmCustomer(CrmCustomer crmCustomer, String bizTypeIds, String remark, Integer sessionUserId, Integer adminId);

	public Map<String, Object> switchPurpose(Integer customerId, String purpose);
	
	public void distributeCustomerByBatchCode(String crmBatchCode);

	public void distributeCustomerByCustomerId(Integer customerId, Integer crmUserId);
	
	public Map<String, Object> getCrmCustomerById(Integer customerId);
	
	public CrmCustomer getCrmCustomerByLicensePlate(Integer adminId, String licensePlate, Integer customerId);

	public Integer saveCrmCustTrace(CrmCustTrace crmCustTrace);
	
	public Map<String, Object> getCrmCustTrace(Integer customerId);

	public Integer saveCrmCustRemaind(CrmCustRemaind crmCustRemaind);
	
	public Integer deleteCrmCustRemaind(Integer customerId);
	
	public Integer saveCrmCustBizType(CrmCustBizType crmCustBizType);
	
	public Integer deleteCrmCustBizType(Integer customerId);
	
	public List<CrmCustBizType> getCrmCustBizType(Integer customerId);
	
	public List<CrmCustomer> getUploadFailCrmCustomer(String crmBatchCode, List<Integer> crmUserIdList);
	
	public Map<String, Object> getCrmCustomerList(Integer pageOffset,
			Integer pageSize, Integer uploadStatus, String batchCode,
			Integer year, Integer month, Integer seed, String level,
			String purpose, Integer bizType, String userName, String keyword,
			Integer isPerson);

	public List<CrmCustomer> customerList(Integer uploadStatus,
			String batchCode, Integer year, Integer month, Integer seed,
			String level, String purpose, Integer bizType, String userName,
			String keyword, Integer isPerson);
	
	public List<CrmCustomer> customerListByCustId(String []custIds);

	public List<CrmCustomer> customerListByMobiles(String []mobiles);
	
	/**
	 * 潜客管理-全部潜客 右侧统计报表
	 * @return
	 */
	public Map<String, Object> statsCrmCustomer(Integer isPerson);
	
	/**
	 * 潜客管理-全部潜客 右侧统计报表，点击各个条件
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
	public Map<String, Object> statsCrmCustomerList(Integer pageOffset, Integer pageSize, Integer clickType, Integer isPerson);
	
}
