package com.home.ferrari.service.capacity;

import java.util.Map;

public interface CapacityService {
	
	public Map<String, Object> getCapacityId();
	
	/**
	 * 查询某个能力节点下面的所有的能力树
	 * @param parentCapacityId
	 * @return
	 */
	public Map<String, Object> getCapacityList(Integer parentCapacityId);

	/**
	 * 创建能力模型和header
	 * @param json
	 * @return
	 */
	public Map<String, Object> createCapacityGroup(String json);

	/**
	 * 创建门店能力模型
	 * @param json
	 * @return
	 */
	public Map<String, Object> createShopCapacityGroup(String json);
	
	/**
	 * 查询某个门店能力节点下面的能力树
	 * @param parentCapacityId
	 * @param shopId
	 * @return
	 */
	public Map<String, Object> getShopCapacityList(Integer parentCapacityId, Integer shopId);
	
	/**
	 * 根据groupId删除table
	 * @param groupId
	 * @return
	 */
	public Map<String, Object> deleteCapacityByGroupId(Integer groupId);

	/**
	 * 查询所有服务内容
	 * @return
	 */
	public Map<String, Object> getCapacitySelect();

	/**
	 * 查询所有服务内容下面的叶子能力
	 * @return
	 */
	public Map<String, Object> getCapacitySelectLeaf(Integer capacityId);
	
	
}
