package com.home.ferrari.dao.complain;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;

public interface ComplainReportDao {
	
	public List<Map<String, Object>> getDegreeReport(@Param(value="shopId") Integer shopId);
	
	public List<Map<String, Object>> getStatusReport(@Param(value="shopId") Integer shopId);

	public List<Map<String, Object>> getAreaReport(@Param(value="shopId") Integer shopId);

	public List<Map<String, Object>> getShopReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "bizOrderId") String bizOrderId);
	public Integer countShopReport(@Param(value = "province") String province,
			@Param(value = "city") String city,
			@Param(value = "bizOrderId") String bizOrderId);

}
