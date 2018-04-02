package com.home.ferrari.service.capacity;

import java.util.Map;

public interface CapacityModelService {
	
	public Map<String, Object> createOrUpdateCapacityModel(Integer capacityModelId, String json);
	
	public Map<String, Object> listCapacityModel();

	public Map<String, Object> createOrUpdateCapacityShop(Integer shopId, Integer capacityModelId, String searchKey, String json, Integer version);

	public Map<String, Object> getCapacityShopById(Integer shopId, Integer capacityModelId);
	
	public Map<String, Object> getCapacityShopListById(Integer shopId);
	
	public boolean isCapacityFinish(Integer shopId);

	public boolean isCanCapacity(Integer shopId);
}
