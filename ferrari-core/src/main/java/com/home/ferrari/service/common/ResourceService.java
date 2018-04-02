package com.home.ferrari.service.common;

import java.util.Map;

import com.home.ferrari.vo.common.Resource;

public interface ResourceService {
	
	public static final String COMPLAIN_REASON_KEY = "complain_reason_key";
	public static final String CUSTOMER_REQUIRE_KEY = "customer_require_key";
	
	public Map<String, Object> createResource(String resourceKey, String resourceDesc, Integer resourceValue, Integer resourceId);
	public Map<String, Object> getResourcesByKey(String resourceKey);
	public Map<String, Object> getResourcesByKeyAndId(String resourceKey, Integer resourceId);
	public Map<String, Object> getResourcesByKeyVal(String resourceKey, Integer resourceValue);
	public Map<String, Object> deleteResourcesByKeyVal(String resourceKey, Integer resourceValue);
	public Resource getResourceByKeyAndDesc(String resourceKey, String resourceDesc);

}
