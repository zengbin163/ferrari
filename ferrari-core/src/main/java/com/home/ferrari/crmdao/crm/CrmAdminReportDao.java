package com.home.ferrari.crmdao.crm;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.crm.CrmCompany;
import com.home.ferrari.vo.crm.CrmProvinceCount;


public interface CrmAdminReportDao {
	
	public List<CrmProvinceCount> getCrmProvinceReport(@Param(value = "page") Page<?> page);
	
	public Integer getCrmProvinceBizAdmin(@Param(value = "province") String province);
	
	public Integer getCrmProvinceCustomer(@Param(value = "province") String province);
	
	public List<Map<Integer, Integer>> getCurrentMonthLoginReport();

	public List<Map<Integer, Integer>> getCurrentMonthNewUserReport();
	
	public Integer countTotalCustomer();
 	
	public List<CrmCompany> getCrmCompanyListReport(
			@Param(value = "page") Page<?> page,
			@Param(value = "crmProvince") String crmProvince,
			@Param(value = "crmCity") String crmCity,
			@Param(value = "crmShopName") String crmShopName);

	public Integer countCrmCompanyListReport(
			@Param(value = "crmProvince") String crmProvince,
			@Param(value = "crmCity") String crmCity,
			@Param(value = "crmShopName") String crmShopName);
	
	public Integer getAvgCustomerByAdminid(@Param(value="adminId") Integer adminId);
}
