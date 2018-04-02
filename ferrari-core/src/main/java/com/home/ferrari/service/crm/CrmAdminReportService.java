package com.home.ferrari.service.crm;

import java.util.Map;

public interface CrmAdminReportService {
	
	/**
	 * 星奥-CRM账号管理-数据统计 省份一部分
	 * @return
	 */
	public Map<String, Object> crmProvinceReport(Integer pageOffset, Integer pageSize);

	/**
	 * 星奥-CRM账号管理-数据统计 门店一部分
	 * @return
	 */
	public Map<String, Object> crmShopReport(Integer pageOffset,
			Integer pageSize, String province, String city, String shopName);
}
