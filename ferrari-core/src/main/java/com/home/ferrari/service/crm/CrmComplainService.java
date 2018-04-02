package com.home.ferrari.service.crm;

import java.util.Map;

public interface CrmComplainService {

	public Map<String, Object> saveComplain(String remark);
	
	public Map<String, Object> getCrmComplainList(Integer pageOffset, Integer pageSize, Integer operatorId);

	public Map<String, Object> getCrmComplainById(Integer complainId);

}
