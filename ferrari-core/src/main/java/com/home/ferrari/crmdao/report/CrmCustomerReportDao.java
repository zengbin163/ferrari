package com.home.ferrari.crmdao.report;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.crm.CrmBatch;
import com.home.ferrari.vo.crm.CrmCustCount;

public interface CrmCustomerReportDao {
	
	public List<CrmBatch> getCrmBatchList(
			@Param(value = "page") Page<?> page,
			@Param(value = "crmOperatorId") Integer crmOperatorId,
			@Param(value = "year") Integer year,
			@Param(value = "month") Integer month,
			@Param(value = "seed") Integer seed,
			@Param(value = "userName") String userName,
			@Param(value = "uploadStatus") Integer uploadStatus,
			@Param(value = "keyword") String keyword,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	public Integer countCrmBatchList(
			@Param(value = "crmOperatorId") Integer crmOperatorId,
			@Param(value = "year") Integer year,
			@Param(value = "month") Integer month,
			@Param(value = "seed") Integer seed,
			@Param(value = "userName") String userName,
			@Param(value = "uploadStatus") Integer uploadStatus,
			@Param(value = "keyword") String keyword,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	
	public List<Map<String, Object>> getUploadStatusReport(
			@Param(value = "adminId") Integer adminId,
			@Param(value = "batchCode") String batchCode,
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList);

	public List<Map<String, Object>> getBizTypeReport(@Param(value="batchCode") String batchCode, @Param(value="purpose") String purpose, @Param(value = "crmUserIdList") List<Integer> crmUserIdList);

	public Integer sumFollowedCust(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	public Integer sumTodayRemindCust(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);

	/**
	 * 客户达成数
	 * @return
	 */
	public Integer sumCustomerSuccess(
			@Param(value = "crmUserIdList") List<Integer> crmUserIdList,
			@Param(value = "purpose") String purpose);
	
	public Integer sumTotalCustomer(@Param(value = "crmUserIdList") List<Integer> crmUserIdList, @Param(value="purpose") String purpose, @Param(value = "bizType") Integer bizType);
	public List<CrmCustCount> sumCustomerByBizAdmin(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
	public List<Map<String, Object>> sumPurposeCustomerByBizAdminId(@Param(value = "crmUserIdList") List<Integer> crmUserIdList);
}
