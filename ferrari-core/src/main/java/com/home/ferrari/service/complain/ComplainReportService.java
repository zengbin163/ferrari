package com.home.ferrari.service.complain;

import java.util.Map;

public interface ComplainReportService {
	
	public Map<String, Object> reportStatistics(Integer shopId);

	public Map<String, Object> getShopReport(Integer pageOffset,
			Integer pageSize, String province, String city, String bizOrderId);

}
