package com.home.ferrari.crmdao.crm;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.home.ferrari.plugin.database.page.Page;
import com.home.ferrari.vo.crm.CrmComplain;

public interface CrmComplainDao {
	
	public Integer insertCrmComplain(CrmComplain crmComplain);
	
	public List<CrmComplain> getCrmComplainList(@Param(value = "page") Page<?> page, @Param(value="operatorId") Integer operatorId);

	public Integer countCrmComplainList(@Param(value="operatorId") Integer operatorId);

	public CrmComplain getCrmComplainById(@Param(value="complainId") Integer complainId);
}
