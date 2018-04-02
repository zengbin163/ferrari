package com.home.ferrari.crmdao.crm;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.crm.CrmBatch;
import com.home.ferrari.vo.crm.CrmCustBizType;
import com.home.ferrari.vo.crm.CrmCustCount;
import com.home.ferrari.vo.crm.CrmCustPurpose;
import com.home.ferrari.vo.crm.CrmCustRemaind;
import com.home.ferrari.vo.crm.CrmCustTrace;
import com.home.ferrari.vo.crm.CrmCustomer;

public interface CrmCustomerDao {

	public Integer insertCrmCustomer(CrmCustomer crmCustomer);

	public Integer updateCrmCustomer(CrmCustomer crmCustomer);
	
	public void distributeCustomerByBatchCode(@Param(value="crmBatchCode") String crmBatchCode);

	public Integer insertCrmCustTrace(CrmCustTrace crmCustTrace);
	
	public List<CrmCustTrace> getCrmCustTrace(@Param(value="customerId") Integer customerId);

	public Integer insertCrmCustRemaind(CrmCustRemaind crmCustRemaind);
	
	public Integer deleteCrmCustRemaind(@Param(value="crmCustomerId") Integer crmCustomerId);
	
	public Date getRemaindByCustomerId(@Param(value="customerId") Integer customerId);

	public CrmBatch getCrmBatchByBatchCode(@Param(value = "batchCode") String batchCode);

	public Integer getMaxCrmBatchSeed(@Param(value = "year") Integer year,
			@Param(value = "month") Integer month);
	
	public List<String> getCrmBatchCodeByYearAndMonth(
			@Param(value = "year") Integer year,
			@Param(value = "month") Integer month);
	
	public Integer insertCrmBatch(CrmBatch crmBatch);

	public Integer insertCrmCustBizType(CrmCustBizType crmCustBizType);

	public Integer insertCrmCustPurpose(CrmCustPurpose crmCustPurpose);
	
	public List<CrmCustBizType> getCrmCustBizType(@Param(value="customerId") Integer customerId);

	public Integer deleteCrmCustBizType(@Param(value="customerId") Integer customerId);

	public CrmCustomer getCrmCustomerById(@Param(value="customerId") Integer customerId);

	public CrmCustomer getCrmCustomerByLicensePlate(
			@Param(value = "adminId") Integer adminId,
			@Param(value = "licensePlate") String licensePlate,
			@Param(value = "customerId") Integer customerId);

	public List<CrmCustomer> getUploadFailCrmCustomer(
			@Param(value = "crmBatchCode") String crmBatchCode,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	
	public List<CrmCustomer> getCrmCustomerList(
			@Param(value = "page") Page<?> page,
			@Param(value = "uploadStatus") Integer uploadStatus,
			@Param(value = "batchCode") String batchCode,
			@Param(value = "year") Integer year,
			@Param(value = "month") Integer month,
			@Param(value = "seed") Integer seed,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList,
			@Param(value = "level") String level,
			@Param(value = "purpose") String purpose,
			@Param(value = "bizType") Integer bizType,
			@Param(value = "userName") String userName,
			@Param(value = "keyword") String keyword);
	public Integer countCrmCustomerList(
			@Param(value = "uploadStatus") Integer uploadStatus,
			@Param(value = "batchCode") String batchCode,
			@Param(value = "year") Integer year,
			@Param(value = "month") Integer month,
			@Param(value = "seed") Integer seed,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList,
			@Param(value = "level") String level,
			@Param(value = "purpose") String purpose,
			@Param(value = "bizType") Integer bizType,
			@Param(value = "userName") String userName,
			@Param(value = "keyword") String keyword);
	
	/**
	 * 预分配客户
	 * @return
	 */
	public Integer sumNotDistributeCust(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	/**
	 * 从未跟进过的客户
	 * @return
	 */
	public Integer sumNotFollowCust(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	/**
	 * 3日内要跟进的客户列表
	 * @return
	 */
	public List<CrmCustCount> sumTreeDayFollowCustList(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	/**
	 * 3日内要跟进的客户总数
	 * @return
	 */
	public Integer sumTreeDayFollowCust(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	/**
	 * 当日内要跟进的客户总数
	 * @return
	 */
	public Integer sumTodayFollowCust(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	/**
	 * 今日新增的客户总数
	 * @return
	 */
	public Integer sumTodayNewCust(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	
	/**
	 * 按照类型查询客户列表
	 * @param month
	 * @param crmUserIdList
	 * @return
	 */
	List<CrmCustomer> getCustomerListByClickType(
			@Param(value = "page") Page<?> page,
			@Param(value = "clickType") Integer clickType,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	Integer countCustomerListByClickType(
			@Param(value = "clickType") Integer clickType,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	
	List<CrmCustomer> getCrmCustomerListByCustIds(@Param(value = "custIds") String []custIds);

	List<CrmCustomer> getCrmCustomerListByMobiles(@Param(value = "mobiles") String []mobiles);
	
}
