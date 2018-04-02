package com.home.ferrari.service.crm;

import java.util.List;
import java.util.Map;

import com.home.ferrari.vo.crm.CrmBizTypeReport;

public interface CrmCustomerReportService {
	
	/**
	 * 潜客管理-全部批次
	 * @return
	 */
	public Map<String, Object> getCustomerBatchReport(Integer pageOffset,
			Integer pageSize, Integer year, Integer month, Integer seed,
			String userName, Integer uploadStatus, String keyword);
	
	/**
	 * 潜客管理-数据分析 右侧统计报表
	 * @return
	 */
	public Map<String, Object> rightStatsCrmCustomer(Integer isPerson);
	
	/**
	 * 潜客管理-数据分析 按业务员数统计
	 * @return
	 */
	public Map<String, Object> statsByBizAdmin(Integer isPerson);

	/**
	 * 潜客管理-数据分析 按客户意向统计
	 * @return
	 */
	public Map<String, Object> statsByPurpose(Integer isPerson);

	/**
	 * 潜客管理-数据分析 按业务类型统计
	 * @return
	 */
	public Map<String, Object> statsByBizType(Integer isPerson);

	/**
	 * 潜客管理-业务员创建的客户统计
	 * @return
	 */
	public Map<String, Object> statsByBizAdminCreate(Integer isPerson);
	
	/**
	 * 查询业务类型报表
	 * @return
	 */
	public List<CrmBizTypeReport> getBizTypeReport(String batchCode, String purpose, List<Integer> crmUserIdList);
	
}
